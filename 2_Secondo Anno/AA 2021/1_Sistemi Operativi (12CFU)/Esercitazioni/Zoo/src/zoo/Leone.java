package zoo;

import java.util.Random;

/**
 * @author Arlind Pecmarkaj
 */

/* Thread che simula il leone. */
public class Leone extends Thread {
    /* ---  Attributi funzionali._--- */
    private final Capanna    capanna;
    private final int  tempodiArrivo;
    
    /* --- Costruttore. --- */
    public Leone(String name, Capanna c) {
        super(name);
        this.capanna = c;
        this.tempodiArrivo = new Random().nextInt(300) + 1;
    }
    
    /* --- Comportamento del thread. */
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(this.tempodiArrivo);
                this.capanna.richiediDiMangiare(this);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        System.out.println("--- " + super.getName() + " termina. ---");
    }
    
}
