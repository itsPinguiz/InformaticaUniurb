package gestionesmp;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author Arlind Pecmarkaj
 */

/* Thread che simula un processo. */
public class Processo extends Thread {
    /* --- Attributi funzionali. --- */
    private final CodaScheduling   coda;
    private int               burstTime;
    private final Random            rnd;
    /* --- Attributi di sincronizzazione. --- */
    // Semaforo per sospendere il processo quando è in coda.
    private final Semaphore sospensione;
    
    /* --- Costruttore. --- */
    public Processo(String name, CodaScheduling cs) {
        super(name);
        this.coda        = cs;
        this.rnd         = new Random();
        this.sospensione = new Semaphore(0);
    }
    
    /* --- Comportamento del thread. --- */
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            this.burstTime = this.rnd.nextInt(21) + 10; // [10, 30]
            this.coda.entraInCoda(burstTime, this);
        }
        System.out.println("# Il processo " + super.getName() + " termina. #");
    }
    
    /* --- Metodi di interfaccia pubblica. --- */
    // Metodo per ottenere il burst time.
    public int getBurstTime() {
        return this.burstTime;
    }
    
    // Metodo per sospendere il processo.
    void sospendi() {
        try {
            this.sospensione.acquire();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
    
    // Metodo per riattivare il processo.
    void risveglia() {
        this.sospensione.release();
    }
    
}
