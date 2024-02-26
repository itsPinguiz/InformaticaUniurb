package gestionaleazienda;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Arlind Pecmarkaj
 */

/* Oggetto condiviso tra i thread. */
public class Gestionale {
    /* --- Attributi funzionali. --- */
    private int                          venditori;
    private final ArrayList<String>    nomiClienti;
    private final ArrayList<Integer> budgetClienti;
    /* --- Attributi di sincronizzazione. --- */
    private final ReentrantLock              mutex;
    private final Semaphore                writing;
    
    /* --- Csotruttore. --- */
    public Gestionale() {
        this.venditori     = 0;
        this.nomiClienti   = new ArrayList<>();
        this.budgetClienti = new ArrayList<>();
        this.mutex         = new ReentrantLock();
        this.writing       = new Semaphore(1);
    }
    
    /* --- Metodi di interfaccia pubblica. --- */
    public String trovaMax(Venditore v) throws InterruptedException {
        String result = "-1";
        int index     = 0;
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            this.venditori++;
            if (venditori == 1) writing.acquire();
            System.out.println("> Il venditore " + v.getName() 
                               + " sta leggendo la lista dei clienti.");
        } finally {
            this.mutex.unlock();
        }       
        if (!nomiClienti.isEmpty())
            for (int i = 0; i < nomiClienti.size(); i++) {
                if (budgetClienti.get(i) > budgetClienti.get(index)) {
                    result = nomiClienti.get(i);
                    index = i;
                }
            }       
        /* INIZIO SEZIONE CRITICA */
        try {
            this.mutex.lock();
            this.venditori--;
            if (result.equals("-1"))
                System.out.println("*** Il venditore " + v.getName() 
                                   + " ha finito di leggere ma non ha"
                                   + " trovato clienti. ***");
            else
                System.out.println("*** Il venditore " + v.getName() 
                                   + " ha finito di leggere e ha trovato"
                                   + " il cliente " + result + " con budget "
                                   + budgetClienti.get(index) + " ***");
  
            if (venditori == 0) this.writing.release();
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA. */
        }
        return result;      
    }
    
    public void inserisciCliente(Inseritore i, String name, int budget) 
                                                   throws InterruptedException {
        this.writing.acquire();
        System.out.println(">> L'inseritore " + i.getName() + " inserisce"
                           + " il cliente " + name + " con budget " + budget);
        this.nomiClienti.add(name);
        this.budgetClienti.add(budget);
        this.writing.release();
        
    }
    
    public void rimuoviCliente(Controllore c) throws InterruptedException {
        this.writing.acquire();
        if (!nomiClienti.isEmpty()) {
            System.out.println(">>> Il controllore " + c.getName()
                               + " rimuove " +  this.nomiClienti.get(0));
            this.nomiClienti.remove(0);
            this.budgetClienti.remove(0);
        } else {
            System.out.println(">>> Il controllore non ha trovato clienti da "
                               + "rimuovere!");
        }
        this.writing.release();
    }
}
