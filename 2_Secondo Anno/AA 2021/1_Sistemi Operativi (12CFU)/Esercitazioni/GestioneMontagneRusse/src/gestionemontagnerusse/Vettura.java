package gestionemontagnerusse;

/**
 * @author Arlind Pecmarkaj
 */

/* Thread che simula la vettura. */
public class Vettura extends Thread{
    private MontagneRusse montagne;
    
    public Vettura(MontagneRusse m) {
        this.montagne = m;
    }
    
    @Override
    public void run() {
        boolean isAlive = true;
        while (isAlive) {
            try {
                this.montagne.load();
                this.montagne.go();
                this.montagne.unload();
            } catch (InterruptedException ex) {
                isAlive = false;
            }
        }
        System.out.println("----    Interrupt ricevuto.     ----");
        System.out.println("---- Il thread vettura termina. ----");
    }
}
