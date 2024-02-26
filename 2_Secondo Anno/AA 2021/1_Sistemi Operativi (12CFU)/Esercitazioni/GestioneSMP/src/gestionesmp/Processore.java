package gestionesmp;

/**
 * @author Arlind Pecmarkaj
 */

/* Thread che simula un processore. */
public class Processore extends Thread {
    /* --- Attributi funzionali. --- */
    private final CodaScheduling coda;
    
    /* --- Costruttore. --- */
    public Processore(String name, CodaScheduling cs) {
        super(name);
        this.coda = cs;
    }
    
    /* --- Comportamento del thread. --- */
    @Override
    public void run() {
        boolean isAlive = true;
        while (isAlive) {
            try {
                this.coda.eseguiProcesso(this);
            } catch (InterruptedException e) {
                isAlive = false;
                System.out.println("Interrupt ricevuto.");
            }
        }
        System.out.println("# Il processore " 
                           + super.getName() +  " termina. #");
    }
    
}
