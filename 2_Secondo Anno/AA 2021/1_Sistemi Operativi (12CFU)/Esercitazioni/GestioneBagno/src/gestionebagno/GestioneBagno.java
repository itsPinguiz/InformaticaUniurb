package gestionebagno;

/**
 * @author Arlind Pecmarkaj
 */
public class GestioneBagno {

    public static void main(String[] args) {
        Bagno bagno = new Bagno();
        Uomo uomini[] = new Uomo[12];
        Donna donne[] = new Donna[12];
        for (int i = 0; i < uomini.length; i++) {
            uomini[i] = new Uomo("Uomo " + (i + 1), bagno); 
            uomini[i].start();
        }
        for (int i = 0; i < donne.length; i++) {
            donne[i] = new Donna("Donna " + (i + 1), bagno);
            donne[i].start();
        }
        try {
            for (Uomo u : uomini)
                u.join();
            for (Donna d : donne)
                d.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("§§§ SIMULAZIONE TERMINATA. §§§");
    }
    
}
