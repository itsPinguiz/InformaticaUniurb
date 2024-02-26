package gestionaleazienda;

import java.util.Random;

/**
 * @author Arlind Pecmarkaj
 */

/* Thread che simula il controllore. */
public class Controllore extends Thread {
    /* --- Attributi funzionali. --- */
    private final Gestionale gestionale;
    private final Random            rnd;
    
    /* --- Costruttore. --- */
    public Controllore(String name, Gestionale g) {
        super(name);
        this.gestionale = g;
        this.rnd        = new Random();
    }
    
    /* --- Comportamento del thread. --- */
    @Override
    public void run() {
        var isAlive = true;
        while (isAlive) {
            try {
                this.gestionale.rimuoviCliente(this);
                Thread.sleep(this.rnd.nextInt(401) + 100);
            } catch(InterruptedException e) {
                isAlive = false;
            }
        }
        System.out.println("°°° " + super.getName() + " termina. °°°");
    }
}
