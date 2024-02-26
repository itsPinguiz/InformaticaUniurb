package gestionebagno;

import java.util.Random;

/**
 * @author Arlind Pecmarkaj
 */
/* Thread che simula una persona. */
public class Persona extends Thread {
    /* --- Attributi funzionali --- */
    private final int    tipo;
    private final Bagno bagno;
    private final Random  rnd;
    
    /* --- Costruttore. --- */
    public Persona(String name, int tipo, Bagno b) {
        super(name);
        this.tipo  = tipo;
        this.bagno = b;
        this.rnd   = new Random();
    }
    
    /* --- Comportamendo del thread. --- */
    @Override
    public void run() {
        try {
            int timeToSleepArrivo;
            int timeToSleepServizio;
            if (this.tipo == 0) {
                timeToSleepArrivo = this.rnd.nextInt(1301) + 100; //[100, 1400]
                timeToSleepServizio = this.rnd.nextInt(101) + 100; //[100, 200]
            } else {
                timeToSleepArrivo = this.rnd.nextInt(1101) + 100; //[100, 1200]
                timeToSleepServizio = this.rnd.nextInt(301) + 100; //[100, 400]
            }
            Thread.sleep(timeToSleepArrivo);
            this.bagno.accessoAlBagno(this);
            Thread.sleep(timeToSleepServizio);
            this.bagno.notificaUScita(this);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("--- " + super.getName() + " termina. ---");
    }  

    public int getTipo() {
        return this.tipo;
    }
    
}
