package expo;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Arlind Pecmarkaj
 */
/* Oggetto condiviso tra i thread. */
public class Varco {
    /* --- Attributi funzionali. --- */
    // Code dei visistatori.
    private final ArrayList<Visitatore> visitatori;
    private final ArrayList<Visitatore>   disabili;
    /* --- Attributi di sincronizzazione. --- */
    // A guardia delle liste.
    private final ReentrantLock              mutex;
    // Semaforo per svegliare i controllori.
    private final Semaphore     avvertiControllore;
    
    /* --- Costruttore. --- */
    public Varco() {
        this.visitatori         = new ArrayList<>();
        this.disabili           = new ArrayList<>();
        this.mutex              = new ReentrantLock();
        this.avvertiControllore = new Semaphore(0);
    }
    
    public void richiediAccesso(Visitatore v) {
        System.out.println(v.getName() + " si presenta al varco.");
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            if (v.getTipo() == 0)
                this.visitatori.add(v);
            else
                this.disabili.add(v);
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
        this.avvertiControllore.release();
        v.sospendi();
    }
    
    public void controllaVisitatore(Controllore c) throws InterruptedException {
        Visitatore v = null;
        this.avvertiControllore.acquire();
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            v = this.cercaDallaCoda();
            System.out.println("**** " + v.getName() + " è controllato da " 
                               + c.getName() + " ****");
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
        Thread.sleep(200);
        v.risveglia();
    }
    
    private Visitatore cercaDallaCoda() {
        Visitatore v;
        if (disabili.isEmpty()) {
            v = this.visitatori.get(0);
            this.visitatori.remove(v);
        } else {
            v = this.disabili.get(0);
            this.disabili.remove(v);
        }
        return v;
    }
    
}
