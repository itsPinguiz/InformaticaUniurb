package gestioneattesaposte;

/**
 *
 * @author arlin
 */
/* Thread che sumula un impiegato. */
public class Impiegato extends Thread {
    /* --- Attributi funzionali. --- */
    private final Ufficio ufficioPostale;
    
    /* --- Costruttore. --- */
    public Impiegato(String name, Ufficio u) {
        super(name);
        this.ufficioPostale = u;
    }
    
    /* --- Comportamento del thread. --- */
    @Override
    public void run() {
        boolean isAlive = true;
        while (isAlive) {
            try {
                this.ufficioPostale.serviCliente(this);
            } catch(InterruptedException e) {
                isAlive = false;
                System.out.println("|" + super.getName() 
                                   + " ha ricevuto l'interrupt.|");
            }
        }
        System.out.println("°°° " + super.getName() + " termina. °°°");
    }    
}
