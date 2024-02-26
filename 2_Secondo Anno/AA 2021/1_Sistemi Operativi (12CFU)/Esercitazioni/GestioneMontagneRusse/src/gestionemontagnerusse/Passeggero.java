package gestionemontagnerusse;

import java.util.Random;

/**
 * @author Arlind Pecmarkaj
 */

/* Thread che simula un passeggero. */
public class Passeggero extends Thread {
    private MontagneRusse montagne;
    private Random             rnd;
    
    public Passeggero(MontagneRusse m, String name) {
        super(name);
        this.montagne = m;
        this.rnd = new Random();
    }
    
    @Override
    public void run() {
        try {
            int timeToSleep = this.rnd.nextInt(1000) + 1; //[1,1000]
            Thread.sleep(timeToSleep);
            this.montagne.board(this);
            this.montagne.unboard(this);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
}
