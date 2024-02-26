package gestionaleazienda;

import java.util.Random;

/**
 * @author Arlind Pecmarkaj
 */

/* Thread che simula un venditore. */
public class Venditore extends Thread {
    private final Gestionale gestionale;
    private final Random            rnd;
    
    public Venditore(String name, Gestionale g) {
        super(name);
        this.gestionale = g;
        this.rnd        = new Random();
    }
    
    @Override
    public void run() {
        int richiesteBuonFine = 0;
        while (richiesteBuonFine < 12) {
            try {
                if (!gestionale.trovaMax(this).equals("-1"))
                    richiesteBuonFine++;
                Thread.sleep(this.rnd.nextInt(251) + 100);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        System.out.println("°°° " + super.getName() + " termina. °°°");
    }
    
}
