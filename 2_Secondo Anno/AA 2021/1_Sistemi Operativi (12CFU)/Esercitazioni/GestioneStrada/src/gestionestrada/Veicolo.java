package gestionestrada;

/**
 * @author Arlind Pecmarkaj
 */

/* Thread che simula un veicolo generico. */
public class Veicolo extends Thread {
    /* --- Attributi funzionali. --- */
    private Strada     strada;
    // Tempo di arrivo.
    private int timeToArrival;
    // Tempo di percorrenza.
    private int   timeToSleep;
    // Verso di percorrenza.
    private Verso       verso;
    
    /* --- Costruttore. --- */
    public Veicolo(String name, Strada s, int arrival, int sleep, Verso verso) {
        super(name);
        this.strada        = s;
        this.timeToArrival = arrival;
        this.timeToSleep   = sleep;
        this.verso         = verso;
    }
    
    /* --- Comportamento del thread. --- */
    @Override
    public void run() {
        boolean isAlive = true;
        while (isAlive) {
            try {
                Thread.sleep(timeToArrival);
                this.strada.accessoStrada(this);
                Thread.sleep(timeToSleep);
                this.strada.liberaStrada(this);
                if (this.verso != Verso.SPAZZANEVE)
                    isAlive = false;
            } catch (InterruptedException e) {
                isAlive = false;
            }
        }
        System.out.println(super.getName() + " termina.");
    }
    
    /* --- Metodi di interfaccia pubblica --- */
    Verso getVerso() {
        return this.verso;
    }
    
}
