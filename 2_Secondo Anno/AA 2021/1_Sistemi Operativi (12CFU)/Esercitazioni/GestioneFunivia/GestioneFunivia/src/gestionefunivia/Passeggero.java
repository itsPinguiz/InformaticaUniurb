package gestionefunivia;

import java.util.Random;

public class Passeggero extends Thread {
    private final Funivia miaFunivia;
    private final int           tipo;
    private final Random         rnd;
    
    public Passeggero(Funivia f, int tipo, int index) {
        super("Passeggero " + index + (tipo > 0? " panoramico":" normale"));
        this.miaFunivia = f;
        this.tipo       = tipo;
        this.rnd        = new Random();
    }
    
    @Override
    public void run() {
        // Attendo un tempo casuale compreso tra [0, 1500] ms e richiedo 
        // l'imbarco.
        int timeToSleep = this.rnd.nextInt(1501);
        try {
            Thread.sleep(timeToSleep);
            // Ora chiedo l'imbarco.
            this.miaFunivia.richiestaImbarco(this);
        } catch (InterruptedException in) {
            System.out.println(in);
        }
        System.out.println(super.getName() + " termina l'esecuzione.");
    }

    public int getTipo() {
        return this.tipo;
    }
    
}
