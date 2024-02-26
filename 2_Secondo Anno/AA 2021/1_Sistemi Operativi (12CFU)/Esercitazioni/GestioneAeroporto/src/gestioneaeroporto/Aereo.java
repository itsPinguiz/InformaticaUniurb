package gestioneaeroporto;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author Arlind Pecmarkaj
 */

/* Thread che simula un aereo. */
public class Aereo extends Thread {
    /* --- Attributi funzionali. --- */   
    private final Aeroporto   aeroporto;
    private final int              peso;
    private final Random            rnd;
    /* --- Attributi di sincronizzazione. --- */
    private final Semaphore sospensione;
    
    /* --- Costruttore. --- */
    public Aereo(String name, Aeroporto a) {
        super(name);
        this.aeroporto      = a;
        this.rnd            = new Random();
        this.peso           = this.rnd.nextInt(101) + 100; //[100, 200]
        this.sospensione    = new Semaphore(0);
    }
    
    /* --- Comportamento del thread. --- */
    @Override
    public void run() {
        int servizio = this.rnd.nextInt(2);
        for (int i = 0; i < 2; i++) {
            if (i == 1) {
                if (servizio == 0)
                    servizio = 1;
                else
                    servizio = 0;
            }
            this.aeroporto.richiediServizio(this, servizio);
            try {
                if (servizio == 0)
                    Thread.sleep(50);
                else
                    Thread.sleep(20);
                this.aeroporto.liberaPista(this);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        

        System.out.println("[" + super.getName() + " termina.]");
    }
    
    /* --- Metodi di interfaccia pubblica. --- */
    // Metodo per sospendere l'aereo. 
    public void sospendi() {
        try {
            this.sospensione.acquire();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
    
    // Metodo per svegliare l'aereo.
    public void sveglia() {
        this.sospensione.release();
    }

    // Metodo che restituisce il peso dell'aereo in tonnellate.
    public int getPeso() {
        return this.peso;
    }
    
}
