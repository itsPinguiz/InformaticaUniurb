package gestionestrada;

/**
 * @author Arlind Pecmarkaj
 */

public class GestioneStrada {

    public static void main(String[] args) {
        Strada strada     = new Strada();
        Auto auto[]       = new Auto[32];
        Spazzaneve spazza = new Spazzaneve(strada);
        for (int i = 0; i < auto.length; i++) {
            auto[i] = new Auto(i, strada);
            auto[i].start();
        }
        spazza.start();
        try {
            for (Auto a : auto)
                a.join();
            spazza.interrupt();
            spazza.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("§§§ SIMULAZIONE TERMINATA §§§");
    }
    
}
