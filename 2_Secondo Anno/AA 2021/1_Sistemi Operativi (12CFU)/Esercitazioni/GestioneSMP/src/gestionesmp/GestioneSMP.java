
package gestionesmp;

/**
 * @author Arlind Pecmarkaj
 */

public class GestioneSMP {

    public static void main(String[] args) {
        CodaScheduling codaScheduling = new CodaScheduling();
        Processore processori[]       = new Processore[4];
        Processo processi[]           = new Processo[36];
        for (int i = 0; i < processori.length; i++) {
            processori[i] = new Processore ("CPU"+i, codaScheduling);
            processori[i].start();
        }
        for (int i = 0; i < processi.length; i++) {
            processi[i] = new Processo("P"+i, codaScheduling);
            processi[i].start();
        }
        try {
            for (Processo p : processi)
                p.join();
            for (Processore pr : processori) {
                pr.interrupt();
                pr.join();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("**** SIMULAZIONE TERMINATA. ****");
    }
    
}
