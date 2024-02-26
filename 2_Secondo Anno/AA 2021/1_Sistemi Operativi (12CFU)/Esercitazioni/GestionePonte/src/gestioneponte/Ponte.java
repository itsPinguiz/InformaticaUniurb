package gestioneponte;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Arlind Pecmarkaj
 */

/* Oggetto condiviso tra le auto */
public class Ponte {
    /* --- Attributi funzionali. --- */
    // Indica il verso di percorrenza delle auto.
    // - NORD   (0): le auto vanno da sud a nord.
    // - SUD    (1): le auto vanno da nord a sud.
    // - LIBERO (2): nessun'auto percorre il ponte.
    private Verso verso;
    
    /* --- Attributi di sincronizzazione. --- */
    // A guardia di verso.
    private final ReentrantLock mutex;
    // A guardia del ponte. Semaforo con 3 permessi iniziali.
    private final Semaphore     pontePieno;
    // Variabili condition per fermare le auto in base al loro verso.
    private final Condition     autoNord;
    private final Condition     autoSud;
    
    /* --- Costruttore. --- */
    public Ponte() {
        this.verso      = Verso.LIBERO;
        this.mutex      = new ReentrantLock();
        this.pontePieno = new Semaphore(3);
        this.autoNord   = this.mutex.newCondition();
        this.autoSud    = this.mutex.newCondition();
    }
    
    /* --- Metodi di interfaccia pubblica. --- */
    public void entra(Auto a) {
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        System.out.println(a.getName() + " entra nel ponte con verso."
                           + a.getVerso());
        try {
            if (this.verso == Verso.LIBERO) 
                this.verso = a.getVerso();   
            while (a.getVerso() != this.verso) {
                if (a.getVerso() == Verso.NORD)
                    this.autoNord.await();
                else 
                    this.autoSud.await();
                if (this.verso == Verso.LIBERO)
                    this.verso = a.getVerso();
            }
            this.pontePieno.acquire();
            System.out.println(a.getName() + " inizia l'attraversamento.");
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            this.mutex.unlock();
        }
    }
    
    public void esci(Auto a) {
        System.out.println(a.getName() + " esce dal ponte.");
        this.pontePieno.release();
        if (pontePieno.availablePermits() == 3) {
            /* INIZIO SEZIONE CRITICA */
            this.mutex.lock();
            try {
                this.verso = Verso.LIBERO;
                this.autoNord.signalAll();
                this.autoSud.signalAll();
            } finally {
                this.mutex.unlock();
            }
        }
    }
    
}
