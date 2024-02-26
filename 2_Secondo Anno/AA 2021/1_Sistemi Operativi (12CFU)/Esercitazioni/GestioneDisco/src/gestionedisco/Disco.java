package gestionedisco;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Arlind Pecmarkaj
 */
/* Oggetto che rappresenta un disco. */
public class Disco {
    /* --- Attributi funzionali. --- */
    // Lista dei processi in coda.
    private final ArrayList<Processo> lista;
    // Cilindro a cui punta la testina.
    private int testina;
    /* --- Attributi di sincronizzazione. --- */
    // A guardia degli attributi funzionali.
    private final ReentrantLock      mutex;
    // Semaforo per il gstore.
    private final Semaphore processiInCoda; 
    
    /* --- Costruttore. --- */
    public Disco() {
        this.lista          = new ArrayList<>();
        this.testina        = 0;
        this.mutex          = new ReentrantLock();
        this.processiInCoda = new Semaphore(0);
    }
    
    /* --- Metodi di interfaccia pubblica --- */
    // Metodo usato dei processi per richiedere un cilindro.
    // 1) Inserico il processo nella coda.
    // 2) Il gestore viene avvertito del processo che è entrato.
    // 3) Sospendo il processo fino a che non viene scelto dall'algoritmo.
    public void richiestaCilindro(Processo p) {
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            System.out.println("[" + p.getName() + 
                               " ha richiesto il cilindro " +
                               p.getRichiesta()+"]");
            this.lista.add(p);
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
        this.processiInCoda.release();
        p.sospendi();
    }
    
    // Metodo usato dal gestore per servire una richiesta.
    public void serviRichiesta() throws InterruptedException {
        Processo daServire = null;
        this.processiInCoda.acquire();
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            daServire = this.shortestSeek();
            this.testina = daServire.getRichiesta();
            System.out.println("*** Il gestore serve la richiesta al cilindro "
                               + this.testina + " del processo " 
                               + daServire.getName() + " ***");
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
        daServire.risveglia();
    }
    
    private Processo shortestSeek() {
        Processo p = null;
        var shortestSeek = 999;
        for (Processo pr : lista) {
            if (Math.abs(testina - pr.getRichiesta()) < shortestSeek) {
                p = pr;
                shortestSeek = Math.abs(testina - pr.getRichiesta());
            }
        }
        this.lista.remove(p);
        return p;
    }
     
}
