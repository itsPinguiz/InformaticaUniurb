package gestioneponte;

import java.util.Random;

/**
 * @author Arlind Pecmarkaj
 */

/* Thread che rappresenta l'auto */
public class Auto extends Thread {
    /* --- Attributi funzionali. --- */
    // Indica il verso di percorrenza dell'auto.
    // - NORD   (0): Da sud a nord.
    // - SUD    (1): Da nord a sud.
    private Verso        verso;
    // Generatore di numeri presudocasuali per il verso.
    private final Random rnd;
    // Ponte da attraversare.
    private final Ponte  ponte;
    
    /* --- Costruttore. --- */
    public Auto(String name, Ponte p) {
        super(name);
        this.rnd   = new Random();
        this.verso = (this.rnd.nextInt() % 2) == 0 ? Verso.NORD : Verso.SUD;
        this.ponte = p;
    }
    
    /* --- Comportamento del thread. --- */
    @Override
    public void run() {
        try {
            this.ponte.entra(this);
            Thread.sleep(100); // Simulo l'attraversamento.
            this.ponte.esci(this);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
        System.out.println(super.getName() + " termina.");
    }

    public Verso getVerso() {
        return this.verso;
    }    
}
