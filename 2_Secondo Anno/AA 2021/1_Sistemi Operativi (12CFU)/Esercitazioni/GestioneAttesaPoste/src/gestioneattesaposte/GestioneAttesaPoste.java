package gestioneattesaposte;

/**
 * @author Arlind Pecmarkaj
 */
public class GestioneAttesaPoste {

    public static void main(String[] args) {
        Ufficio ufficioPostale = new Ufficio();
        Impiegato impiegati[]  = new Impiegato[3];
        Cliente clienti[]      = new Cliente[16];
        for (int i = 0; i < impiegati.length; i++) {
            impiegati[i] = new Impiegato("Impiegato " + i, ufficioPostale);
            impiegati[i].start();
        }
        for (int i = 0; i < clienti.length; i++) {
            clienti[i] = new Cliente("Cliente " + i, ufficioPostale);
            clienti[i].start();
        }
        try {
            for (Cliente c : clienti)
                c.join();
            for (Impiegato i : impiegati) {
                i.interrupt();
                i.join();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("§§§ SIMULAZIONE TERMINATA. §§§");
    }  
}
