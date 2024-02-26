package gestionedisco;

/**
 * @author Arlind Pecmarkaj
 */
public class GestioneDisco {

    public static void main(String[] args) {
        Disco disco         = new Disco();
        Gestore gestore     = new Gestore(disco);
        Processo processi[] = new Processo[16];
        for (int i = 0; i < processi.length; i++) {
            processi[i] = new Processo("P" + i, disco);
            processi[i].start();
        }
        gestore.start();
        try {
            for (Processo p : processi)
                p.join();
            gestore.interrupt();
            gestore.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("§§§ SIMULAZIONE TERMINATA §§§");
    }
    
}
