package babbuinilago;

import java.util.Random;

public class Babbuino extends Thread {
    /* --- Attributi funzionali. --- */
    private Lago  lago;
    private Random rnd;
    
    /* --- Costruttore. --- */
    public Babbuino(String name, Lago l) {
        super(name);
        this.lago = l;
        this.rnd  = new Random();
    }
    
    @Override
    public void run() {
        int timeToSleep = this.rnd.nextInt(1001); // [0:1000]
        try {
            Thread.sleep(timeToSleep);
            this.lago.richiediAccesso(this);
            Thread.sleep(250);
            this.lago.esci(this);
        } catch (InterruptedException in) {
            System.out.println(in);
        }
    }
}
