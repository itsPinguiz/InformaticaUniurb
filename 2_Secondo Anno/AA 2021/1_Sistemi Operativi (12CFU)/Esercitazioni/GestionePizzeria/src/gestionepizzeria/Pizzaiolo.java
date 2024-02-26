package gestionepizzeria;

/**
 *
 * @author Arlind Pecmarkaj
 */

/* Thread che simula il pizzaiolo */
public class Pizzaiolo extends Thread {
    /* --- Attributi funzionali. --- */
    // Oggetto condiviso.
    private final Pizzeria pizzeria;
    
    /* --- Costruttore. --- */
    public Pizzaiolo(Pizzeria p) {
        this.pizzeria = p;
    }
    
    /* --- Comportamento del thread. --- */
    @Override
    public void run() {
        boolean isAlive = true;
        try {
            while (isAlive)
                this.pizzeria.serviCliente();
        } catch (InterruptedException e) {
            isAlive = false;
            System.out.println("*** Interrupt ricevuta! ***");
        }
        System.out.println("--- Il thread pizzaiolo termina. ---");
    }
}
