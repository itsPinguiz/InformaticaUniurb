package ortofrutticolo;

import java.util.Random;
import java.util.concurrent.Semaphore;

/* Thread che simula il cliente. */
public class Cliente extends Thread {
    /* --- Attributi funzionali. --- */
    private boolean   ortaggio; // False: insalata | True: pomodoro.
    private int       quantita;
    private Random         rnd;
    private Gruppo      gruppo;
    
    /* --- Attributi di sincronizzazione. --- */
    // Semaforo per la sospensione.
    private Semaphore semaforo;
    
    public Cliente(String name, Gruppo g, boolean ortaggio) {
        super(name);
        this.ortaggio = ortaggio;
        this.gruppo   = g;
        this.rnd      = new Random();
        this.semaforo = new Semaphore(0);
        this.quantita = rnd.nextInt(11) + 10; // [10, 20]
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(this.rnd.nextInt(11) + 500);
            this.gruppo.sottomettiRichiesta(this);
            System.out.println("[ "+super.getName() + 
                               " ha ricevuto l'ordine. ] ");
            
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("--- " + super.getName() + " termina. ---");
    }
    
    public boolean getOrtaggio() {
        return this.ortaggio;
    }

    public int getPeso() {
        return this.quantita;
    }

    public void sospendi() {
        try {
            this.semaforo.acquire();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public void risveglia() {
        this.semaforo.release();
    }
    
}
