package gestionedisco;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author Arlind Pecmarkaj
 */
public class Processo extends Thread {
    /* --- Attributi funzionali. --- */
    private final Disco disco;
    private final Random  rnd;
    private int      cilindro;
    /* --- Attributi di sincronizzazione. --- */
    // Semaforo per l'attesa.
    private final Semaphore attesa;
    
    /* --- Cotruttore. --- */
    public Processo(String name, Disco d) {
        super(name);
        this.disco  = d;
        this.rnd    = new Random();
        this.attesa = new Semaphore(0);
    } 
    
    /* --- Comportamente del thread. --- */
    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            this.cilindro = this.rnd.nextInt(1000);
            this.disco.richiestaCilindro(this);
            try {
                Thread.sleep(this.rnd.nextInt(201) + 100);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        System.out.println("# Il processo " + super.getName() + " termina. #");
    }

    /* --- Metodi di interfaccia pubblica. --- */
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

    public int getRichiesta() {
        return this.cilindro;
    }   
}
