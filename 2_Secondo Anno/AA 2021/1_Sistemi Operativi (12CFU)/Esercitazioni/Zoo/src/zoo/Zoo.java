package zoo;

/**
 * @author Arlind Pecmarkaj
 */
public class Zoo {

    public static void main(String[] args) {
        Capanna c     = new Capanna();
        Guardiano g   = new Guardiano(c);
        Leone leoni[] = new Leone[20];
        for (int i = 0; i < leoni.length; i++) { 
            leoni[i] = new Leone("leone " + i, c);
            leoni[i].start();
        }
        g.start();
        try {
            for (Leone l : leoni)
                l.join();
            g.interrupt();
            g.join();
        } catch(InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("§§§ SIMULAZIONE TERMINATA. §§§");
    }
    
}
