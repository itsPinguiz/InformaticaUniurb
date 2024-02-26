package expo;

/**
 * @author Arlind Pecmarkaj
 */
public class Expo {

    public static void main(String[] args) {
        Varco v                   = new Varco();
        Visitatore visitatori[]   = new Visitatore[25];
        Controllore controllori[] = new Controllore[3];
        for (int i = 0; i < controllori.length; i++) {
            controllori[i] = new Controllore("Controllore " + i, v);
            controllori[i].start();
        }
        for (int i = 0; i < visitatori.length; i++) {
            if (i < visitatori.length - 7)
                visitatori[i] = new Visitatore("Visitatore " + i, v, 0);
            else 
                visitatori[i] = new Visitatore("Visitatore (D) " + i, v, 0);
            visitatori[i].start();
        }
        try {
            for (Visitatore vis : visitatori)
                vis.join();
            for (Controllore c : controllori) {
                c.interrupt();
                c.join();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("§§§ SIMULAZIONE TERMINATA §§§");
    }
    
}
