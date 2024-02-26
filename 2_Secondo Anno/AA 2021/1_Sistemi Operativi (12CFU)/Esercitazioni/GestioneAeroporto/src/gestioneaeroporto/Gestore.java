package gestioneaeroporto;

/**
 * @author Arlind Pecmarkaj
 */

/* Thread che simula il gestore dell'aeroporto. */
public class Gestore extends Thread {
    /* --- Attributi funzionali. --- */
    private final Aeroporto aeroporto;
    
    /* --- Costruttore. --- */
    public Gestore(Aeroporto a) {
        super();
        this.aeroporto = a;
    }
    
    /* --- Comportamento del thread. --- */
    @Override
    public void run() {
        boolean isAlive = true;
        while (isAlive) {
            try {
                this.aeroporto.gestisciAerei();
            } catch(InterruptedException e) {
                isAlive = false;
            }
        }
        System.out.println("[Il gestore termina.]");
    }
    
}
