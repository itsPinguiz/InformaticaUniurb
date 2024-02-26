package gestionesmp;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Arlind Pecmarkaj
 */

/* Classe per la coda di scheduling. */
public class CodaScheduling {
    /* --- Attributi funzionali. --- */
    private final ArrayList<Processo> codaProcessi;
    /* --- Attributi di sincronizzazione. --- */
    // A guardia della coda
    private final ReentrantLock              mutex;
    // Semaforo per sospendere i processori.
    private final Semaphore       attesaProcessori;
    
    /* --- Costruttore. --- */
    public CodaScheduling() {
        this.codaProcessi     = new ArrayList<>();
        this.mutex            = new ReentrantLock();
        this.attesaProcessori = new Semaphore(0, true); // FIFO
    }
    
    /* --- Metodi di interfaccia pubblica. --- */
    // Metodo usato dai processi per entrare in coda.
    public void entraInCoda(int nextBurstTime, Processo p) {
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            this.codaProcessi.add(p);
            System.out.println("Il processo " + p.getName() + " entra in coda "
                               + "con burst time: " + nextBurstTime);
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
        this.attesaProcessori.release();
        p.sospendi();
    }
    
    // Metododo usato dai processori per eseguire un processo.
    public void eseguiProcesso(Processore pr) throws InterruptedException {
        this.attesaProcessori.acquire();
        Processo processoDaEseguire;
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            processoDaEseguire = this.shortestJobFirst();
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
        Thread.sleep(processoDaEseguire.getBurstTime());
        System.out.println("[Il processore " + pr.getName() + 
                           " ha eseguito il" + 
                           " processo " + processoDaEseguire.getName() + 
                           " con burst time " + 
                           processoDaEseguire.getBurstTime() + "]");
        processoDaEseguire.risveglia();
    }
    
    // Metodo per ottenere il processo in coda con minor burst time.
    private Processo shortestJobFirst() {
        Processo p = this.codaProcessi.get(0);
        for (int i = 1; i < this.codaProcessi.size(); i++) {
            if (this.codaProcessi.get(i).getBurstTime() < p.getBurstTime())
                p = this.codaProcessi.get(i);
        }
        this.codaProcessi.remove(p);
        return p;
    }
}
