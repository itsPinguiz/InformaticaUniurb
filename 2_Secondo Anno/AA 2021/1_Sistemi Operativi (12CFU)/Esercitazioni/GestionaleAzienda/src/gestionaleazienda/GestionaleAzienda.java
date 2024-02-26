package gestionaleazienda;

/**
 * @author Arlind Pecmarkaj
 */

public class GestionaleAzienda {

    public static void main(String[] args) {
        Gestionale g        = new Gestionale();
        Venditore vend[]    = new Venditore[12];
        Inseritore inse[]   = new Inseritore[8];
        Controllore cont[]  = new Controllore[4];
        for (int i = 0; i < vend.length; i++) {
            vend[i] = new Venditore("VEND" + i, g);
            vend[i].start();
        }
        for (int i = 0; i < inse.length; i++) {
            inse[i] = new Inseritore("INS" + i, g);
            inse[i].start();
        }
        for (int i = 0; i < cont.length; i++) {
            cont[i] = new Controllore("CONT" + i, g);
            cont[i].start();
        }
        try {
            for (Venditore v : vend)
                v.join();
            for (Inseritore i : inse) {
                i.interrupt();
                i.join();
            }
            for (Controllore c : cont) {
                c.interrupt();
                c.join();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("§§§ SIMULAZIONE TERMINATA §§§");
    }
    
}
