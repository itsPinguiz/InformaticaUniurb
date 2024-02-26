package gestioneponte;

/**
 * @author Arlind Pecmarkaj
 */

public class GestionePonte {
    
    public static void main(String[] args) {
        try {
            Ponte p = new Ponte();
            Auto[] automobili = new Auto[10];
            for (int i = 0; i < automobili.length; i++) {
                automobili[i] = new Auto("Auto " + i, p);
                automobili[i].start();
            }
            for (Auto a : automobili)
                a.join();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
        System.out.println("--- SIMULAZIONE TERMINATA ---");
    }
    
}
