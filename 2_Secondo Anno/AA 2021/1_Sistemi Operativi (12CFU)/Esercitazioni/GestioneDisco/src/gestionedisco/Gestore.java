package gestionedisco;

/**
 * @author Arlind Pecmarkaj
 */
/* Thread che gestisce il disco */
public class Gestore extends Thread {
    /* --- Attributi funzionali. --- */
    private final Disco disco;
    
    /* --- Costruttore. --- */
    public Gestore(Disco d) {
        this.disco = d;
    }
    
    /* --- Comportamento del thread. --- */
    @Override 
    public void run() {
        var isAlive = true;
        while (isAlive) {
            try {
                disco.serviRichiesta();
                Thread.sleep(35);
            } catch (InterruptedException e) {
                System.out.println("° Interrupt ricevuta °");
                isAlive = false;
            }
        }
        System.out.println("# Il gestore termina. #");
    }
}
