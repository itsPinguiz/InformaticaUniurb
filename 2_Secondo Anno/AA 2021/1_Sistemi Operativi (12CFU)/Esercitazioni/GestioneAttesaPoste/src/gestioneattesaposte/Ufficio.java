package gestioneattesaposte;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Arlind Pecmarkaj
 */
/* Oggetto condiviso tra impiegati e clienti. */
public class Ufficio {
    /* --- Attributi funzionali. --- */
    private final ArrayList<Cliente> codaP; // Clienti prodotti postali.
    private final ArrayList<Cliente> codaA; // Clienti prodotti finanziari.
    private final ArrayList<Cliente> codaE; // Clienti bancoposta.
    private final ArrayList<Cliente> codaB; // Clienti poste business.
    /* --- Attributi di sincronizzazione. --- */
    // A guardia delle code.
    private final ReentrantLock      mutex;
    // Semaforo per gli impiegati che li notifica se ci son clienti in fila.
    private final Semaphore  clientiInCoda;
    
    /* --- Costruttore. --- */
    public Ufficio() {
        this.codaP         = new ArrayList<>();
        this.codaA         = new ArrayList<>();
        this.codaE         = new ArrayList<>();
        this.codaB         = new ArrayList<>();
        this.mutex         = new ReentrantLock();
        this.clientiInCoda = new Semaphore(0);       
    }
    
    /* --- Metodi di interfaccia pubblica. --- */
    // Metodo per richiedere un servizio per i clienti.
    public void richiediServizio(int tipo, Cliente c) {
        // Per prima cosa metto in coda il cliente.
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            // In base al tipo il cliente avrà la sua coda.
            switch (tipo) {
                case 0:
                    System.out.println(c.getName() 
                                       + " entra nella coda prodotti postali.");
                    this.codaP.add(c);
                    break;
                case 1:
                    System.out.println(c.getName() 
                                    + " entra nella coda prodotti finanziari.");
                    this.codaA.add(c);
                    break;
                case 2:
                    System.out.println(c.getName() 
                                       + " entra nella coda bancoposta.");
                    this.codaE.add(c);
                    break;
                default:
                    System.out.println(c.getName() 
                                       + " entra nella coda poste business.");
                    this.codaB.add(c);
                    break;
            }
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
        // Avverto gli impiegati e poi sospendo il cliente.
        this.clientiInCoda.release();
        c.sospendi();        
    }
    
    // Metodo usato dagli impiegati per servire i clienti.
    public void serviCliente(Impiegato i) throws InterruptedException{
        Cliente daServire;
        // Mi sospendo nel semaforo se non ci sono clienti
        this.clientiInCoda.acquire();
        // Entro in sezione critica in quanto vado a vedere le code.
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
           daServire = this.prendiDaCoda();
           System.out.println(i.getName() + " serve il cliente " 
                              + daServire.getName());
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA. */
        }
        // Evado la richiesta.
        Thread.sleep(50);
        // Libero il cliente
        daServire.sveglia();
        
    }
    
    // Algoritmo per prendere il cliente con più priorità nelle code.
    private Cliente prendiDaCoda() {
        Cliente c;
        if (this.codaB.size() > 0) {
            c = codaB.get(0);
            codaB.remove(0);
        } else if (this.codaE.size() > 0) {
            c = codaE.get(0);
            codaE.remove(0);
        } else if (this.codaA.size() > 0) {
            c = codaA.get(0);
            codaA.remove(0);
        } else {
           c = codaP.get(0);
           codaP.remove(0);
        }
        return c;
    }
}
