package gestioneaeroporto;

/**
 * @author Arlind Pecmarkaj
 */

public class GestioneAeroporto {

    public static void main(String[] args) {
        Aeroporto aeroporto = new Aeroporto();
        Gestore   g         = new Gestore(aeroporto);
        Aereo aerei[]       = new Aereo[22];
        g.start();
        for (int i = 0; i < aerei.length; i++) {
            aerei[i] = new Aereo("A" + i, aeroporto);
            aerei[i].start();
        }        
        try {
            for (Aereo a : aerei)
                a.join();
            g.interrupt();
            g.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("§§§ SIMULAZIONE TERMINATA. §§§");
    }
    
}
