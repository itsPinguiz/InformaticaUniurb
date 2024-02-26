package babbuinilago;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Lago {
    /* --- Attributi funzionali. --- */
    private int colore;
    
    /* --- Attributi di sincronizzazione. --- */
    /* A guardia di colore. */
    private final ReentrantLock mutex;
    
    /* A guardia dell'accesso del lago. */
    private final Semaphore lagoPieno;
    
    /* A guardia dei babbuini */
    private final Condition risvegliaGialli;
    private final Condition risvegliaVerdi;
    
    public Lago() {
        this.colore          = 0;
        this.mutex           = new ReentrantLock();
        this.lagoPieno       = new Semaphore(6);
        this.risvegliaGialli = this.mutex.newCondition();
        this.risvegliaVerdi  = this.mutex.newCondition();
    
    }

    public void richiediAccesso(Babbuino b) {
        this.mutex.lock();
        try {
            if (this.colore == 0) {
               if (b instanceof BabbuinoGiallo)
                   this.colore = 1;
               else
                   this.colore = 2;
            }
            while (!(b instanceof BabbuinoGiallo && colore == 1) || 
                   !(b instanceof BabbuinoVerde  && colore == 2)) {                
                if (b instanceof BabbuinoGiallo)
                    this.risvegliaGialli.await();
                else
                    this.risvegliaVerdi.await();
                
                if (this.colore == 0) 
                    if (b instanceof BabbuinoGiallo)
                        this.colore = 1;
                    else
                        this.colore = 2;
                    
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            this.mutex.unlock();
        }
    }

    public void esci(Babbuino b) {
        this.lagoPieno.release();
        if (lagoPieno.availablePermits() == 6) {
            this.mutex.lock();
            try {
                this.colore = 0;
                this.risvegliaGialli.signalAll();
                this.risvegliaVerdi.signalAll();
            } finally {
                this.mutex.unlock();
            }
        }
    }
    
}
