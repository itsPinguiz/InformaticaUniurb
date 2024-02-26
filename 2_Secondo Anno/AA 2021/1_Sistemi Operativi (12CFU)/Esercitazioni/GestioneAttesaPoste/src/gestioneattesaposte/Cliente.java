package gestioneattesaposte;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author Arlind Pecmarkaj
 */
/* Thread che simula un cliente. */
public class Cliente extends Thread {
    /* Attributi funzionali */
    private final Random             rnd;
    private final Ufficio ufficioPostale;
    /* Atttributi di sincronizzazione. */
    // Semaforo per sospendere il cliente.
    private final Semaphore       attesa;
    
    /* --- Costruttore. --- */
    public Cliente(String name, Ufficio u) {
        super(name);
        this.rnd            = new Random();
        this.ufficioPostale = u;
        this.attesa         = new Semaphore(0);
    }
    
    /* --- Comportamento del thread. --- */
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            int richiesta = this.rnd.nextInt(4); //[0, 4]
            this.ufficioPostale.richiediServizio(richiesta, this);
            System.out.println("Evasa la richiesta di " + super.getName() +
                               " [" + richiesta + "]");
        }
        System.out.println("°°° " + super.getName() + " termina. °°°");
    }
    
    /* --- Metodi per sospendere e svegliare il cliente. --- */
    void sospendi() {
        try {
            this.attesa.acquire();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    void sveglia() {
        this.attesa.release();
    }   
}
