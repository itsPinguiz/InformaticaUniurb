package gestionestrada;

/**
 * @author Arlind Pecmarkaj
 */

/* Sottoclasse di Veicolo che simula lo spazzaneve. */
public class Spazzaneve extends Veicolo {
    /* --- Costruttore. --- */
    public Spazzaneve(Strada s) {
        super("SPAZZANEVE", s, 400, 300, Verso.SPAZZANEVE);
    }
    
}
