
package expo;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author Arlind
 */
/* Thread che simula un visitatore. */
public class Visitatore extends Thread {
    /* --- Attributi funzionali. --- */
    private final Varco varco;
    private final int    tipo; // 0: non disabile, 1: disabile.
    private final Random  rnd;
    /* --- Attributi di sincronizzazione. --- */
    // Per sospendere il thread.
    private final Semaphore attesa;
    
    public Visitatore(String name, Varco v, int tipo) {
        super(name);
        this.varco  = v;
        this.tipo   = tipo;
        this.rnd    = new Random();
        this.attesa = new Semaphore(0);
    }
    
    @Override
    public void run() {
        int timeToSleep;
        if (this.tipo == 0)
            timeToSleep = this.rnd.nextInt(501) + 100; //[100, 600]
        else 
            timeToSleep = this.rnd.nextInt(1001) + 100; //[100; 1100]
        try {
            Thread.sleep(timeToSleep);
            this.varco.richiediAccesso(this);
            System.out.println(super.getName() + " è entrato all'EXPO.");
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("[" + super.getName() + " termina.]");
    }

    public int getTipo() {
        return this.tipo;
    }

    public void sospendi() {
        try {
            this.attesa.acquire();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public void risveglia() {
        this.attesa.release();
    }
    
}
