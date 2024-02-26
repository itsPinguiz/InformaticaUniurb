package zoo;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Arlind Pecmarkaj
 */

/* Oggetto condiviso tra leoni e guardiano. */
public class Capanna {
    /* --- Attributi funzionali. --- */
    private int                numeroPorzioni;
    private final Random                  rnd;
    /* --- Attributi di sincronizzazione. --- */
    // A guardia del numero di porzioni.
    private final ReentrantLock         mutex;
    private final Semaphore sospendiGuardiano;
    private final Condition     sospendiLeoni;
    
    /* --- Costruttore. --- */
    public Capanna() {
        this.numeroPorzioni      = 0;
        this.rnd                 = new Random();
        this.mutex               = new ReentrantLock();
        this.sospendiGuardiano   = new Semaphore(0);
        this.sospendiLeoni       = this.mutex.newCondition();
    }
    
    /* --- Metodi di interfaccia pubblica. --- */
    public void richiediDiMangiare(Leone l) {
        System.out.println("Il leone " + l.getName() + " chiede di mangiare.");
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            if (this.numeroPorzioni == 0 && 
                this.sospendiGuardiano.availablePermits() == 0)
                this.sospendiGuardiano.release();
            while (this.numeroPorzioni == 0)
                this.sospendiLeoni.await();
            this.numeroPorzioni--;
            System.out.println("Il leone " + l.getName() 
                               + " ha mangiato la sua porzione");
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
    }
    
    public void distribuisciPorzioni() throws InterruptedException {
        this.sospendiGuardiano.acquire();
        System.out.println("Il guardiano è stato chiamato dai leoni. ");
        /* INIZIO SEZIONE CRITICA. */
        this.mutex.lock();
        try {
            this.numeroPorzioni += this.rnd.nextInt(5)+ 2;
            System.out.println("Il guardiano ha preparato " 
                               + this.numeroPorzioni 
                               + " porzioni e si mette a riposare");
            this.sospendiLeoni.signalAll();
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
    }
    
    
}
