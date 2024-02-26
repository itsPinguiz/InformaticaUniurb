package gestionepizzeria;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author Arlind Pecmarkaj
 */

/* Thread che simula un cliente. */
public class Cliente extends Thread {
    /* --- Attributi funzionali. --- */
    // Oggetto condiviso.
    private final Pizzeria pizzeria;
    // Rappresenta il numero di pizze ordinate.
    private final int   numeroPizze;
    
    /* --- Attributi di sincronizzazione. --- */
    // Semaforo per sospendere i cliente quando è in attesa per le pizze.
    private final Semaphore attesaPizza;
    
    /* --- Costruttore. --- */
    public Cliente(String name, Pizzeria p) {
        super(name);
        this.pizzeria    = p;
        this.numeroPizze = (new Random()).nextInt(8) + 1;
        this.attesaPizza = new Semaphore(0);
    }
    
    /* --- Comportamento del thread. --- */
    @Override
    public void run() {
        this.pizzeria.ordinaPizze(this.numeroPizze, this);
        System.out.println("### Il thread " + super.getName() + 
                           " termina. ###");
    }
    
    /* --- Metodi di interfaccia pubblica. --- */
    public void sospendi() {
        try {
            this.attesaPizza.acquire();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        
    }
    
    public void risveglia() {
        this.attesaPizza.release();
    }

    public int getNumeroPizze() {
        return this.numeroPizze;
    }
    
}
