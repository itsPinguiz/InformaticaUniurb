package gestioneambulatorio;

/**
 * @author Arlind Pecmarkaj
 */
public class GestioneAmbulatorio {

    public static void main(String[] args) {
        Ambulatorio ambulatorio   = new Ambulatorio();
        Paziente pazienti[]       = new Paziente[12];
        Informatore informatori[] = new Informatore[3];
        
        for (int i = 0; i < pazienti.length; i ++){
            pazienti[i] = new Paziente("Paziente "+i, ambulatorio);
            pazienti[i].start();
        }
        
        for (int i = 0; i < informatori.length; i++){
            informatori[i] = new Informatore("Informatore " + i, ambulatorio);
            informatori[i].start(); 
        }
        try {
            for (Paziente p : pazienti) {
                p.join();
            }
            for (Informatore i : informatori) {
                i.join();
            }
        }catch(InterruptedException e){
            System.out.println(e);
        } 
    }
    
}