package gestionemontagnerusse;

public class GestioneMontagneRusse {
    
    public static void main(String[] args) {
        MontagneRusse m = new MontagneRusse();
        Vettura       v = new Vettura(m);
        Passeggero[]  p = new Passeggero[32]; 
        for (int i = 0; i < p.length; i++) {
            p[i] = new Passeggero(m, "Passeggero " + (i + 1));
            p[i].start();
        }        
        v.start();
        try {
            for (Passeggero pa : p) {
                pa.join();
            }
            v.interrupt();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }       
        System.out.println("|||||| Simulazione terminata. ||||||");       
    } 
}
