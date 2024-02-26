package gestionaleazienda;

import java.util.Random;

/**
 * @author Arlind Pecmarkaj
 */

/* Thread che simula l'addetto all'inserimento. */
public class Inseritore extends Thread {
    /* --- Attributi funzionali. --- */
    private final Gestionale gestionale;
    private final Random            rnd;
    
    /* --- Costruttore. --- */
    public Inseritore(String name, Gestionale g) {
        super(name);
        this.gestionale = g;
        this.rnd        = new Random();
    }
    
    /* --- Comportamento del Thread. --- */
    @Override
    public void run() {
        var isAlive = true;
        while (isAlive) {
            try {
                int budget = this.rnd.nextInt(1000) + 1;
                String name = "CL" + this.rnd.nextInt();
                this.gestionale.inserisciCliente(this, name, budget);
                Thread.sleep(this.rnd.nextInt(301) + 100);
            } catch (InterruptedException e) {
                isAlive = false;
            }
        }
        System.out.println("°°° " + super.getName() + " termina. °°°");
    }
    
    
}
