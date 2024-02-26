package gestionestrada;
import java.util.Random;

/**
 * @author Arlind Pecmarkaj
 */

/* Sottoclasse di Veicolo che simula un auto.*/
public class Auto extends Veicolo {
    /* --- Costruttore. --- */
    public Auto(int i, Strada s) {
        super("AUTO " + i, s, 
              new Random().nextInt(801) + 200, 100, 
              new Random().nextInt(2) == 0? Verso.NORDSUD : Verso.SUDNORD);
    }
    
}
