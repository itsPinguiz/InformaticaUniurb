package gestionebagno;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Arlind Pecmarkaj
 */

/* Oggetto condiviso tra ler Persone */
public class Bagno {
    /* --- Attributi funzionali. --- */
    private int           tipo; // [0 = uomini; 1 = donne; 2 = libero]
    /* --- Attributi di sincronizzazione. --- */
    // A guardia degli attributi funzionali.
    private final ReentrantLock  mutex;
    // Per gestire il numero di persone.
    private final Semaphore   capacita;
    // Condition per gestire le code differenziate
    private final Condition codaUomini;
    private final Condition  codaDonne;
    
    /* --- Costruttore. --- */
    public Bagno() {
        this.tipo       = 2;
        this.mutex      = new ReentrantLock();
        this.capacita   = new Semaphore(3, true);
        this.codaUomini = this.mutex.newCondition();
        this.codaDonne  = this.mutex.newCondition();
    }

    /* --- Metodi di interfaccia pubblica. --- */
    public void accessoAlBagno(Persona p) {
        System.out.println(p.getName() + " si mette in coda.");
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            if (this.tipo == 2) 
                this.tipo = p.getTipo();
            while (this.tipo != p.getTipo()) {
                if (p.getTipo() == 0)
                    this.codaUomini.await();
                else
                    this.codaDonne.await();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
        try {
            this.capacita.acquire();
            System.out.println(p.getName() + " entra al bagno.");
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
    
    public void notificaUScita(Persona p) {
        System.out.println(p.getName() +  " esce dal bagno.");
        this.capacita.release();
        if (this.capacita.availablePermits() == 3) {
            System.out.println(" --- BAGNO LIBERO ---");
            /* INIZIO SEZIONE CRITICA */
            this.mutex.lock();
            try {
                this.tipo = 2;
                if (p.getTipo() == 0) 
                    this.codaDonne.signalAll();  
                else 
                    this.codaUomini.signalAll();
            } finally {
                this.mutex.unlock();
                /* FINE SEZIONE CRITICA */
            }
        }
    }
}
