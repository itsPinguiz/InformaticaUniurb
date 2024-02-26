package expo;

/**
 * @author Arlind Pecmarkaj
 */
/* Thread che simula un controllore */
public class Controllore extends Thread {
    /* --- Attributi funzionali. --- */
    // Oggetto condiviso.
    private final Varco varco;
    
    /* --- Costruttore. --- */
    public Controllore(String name, Varco v) {
        super(name);
        this.varco = v;
    }
    
    /* --- Comportamento del thread. --- */
    @Override
    public void run() {
        boolean isAlive = true;
        while (isAlive) {
            try {
                this.varco.controllaVisitatore(this);
            } catch (InterruptedException e) {
                isAlive = false;
                System.out.println(super.getName() + 
                                   " ha ricevuto l'interrupt.");
            }
        }
        System.out.println("[" + super.getName() + " termina.]");
    }
    
}
