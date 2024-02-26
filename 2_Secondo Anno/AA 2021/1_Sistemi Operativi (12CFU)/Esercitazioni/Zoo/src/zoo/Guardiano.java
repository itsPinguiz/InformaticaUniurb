package zoo;

/**
 * @author Arlind Pecmarkaj
 */

/* Thread che simula il guardiano. */
public class Guardiano extends Thread {
    /* --- Attributi funzionali. --- */
    private final Capanna capanna;
    
    /* --- Costruttore. --- */
    public Guardiano(Capanna c) {
        this.capanna = c;
    }
    
    /* --- Comportamento dl thread. --- */
    @Override
    public void run() {
        boolean isAlive = true;
        while (isAlive) {
            try {
                this.capanna.distribuisciPorzioni();
            } catch (InterruptedException e) {
                System.out.println("*** Interrupt ricevuta. ***");
                isAlive = false;
            }
        }
        System.out.println("--- Il guardiano termina. ---");
    }
}
