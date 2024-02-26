package gestioneambulatorio;

import java.util.Random;

public class Utente extends Thread {
    private Ambulatorio ambulatorio;
    private Random              rnd;
    
    public Utente(String name, Ambulatorio a) {
        super(name);
        this.ambulatorio = a;
        this.rnd         = new Random();
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(this.rnd.nextInt(700) + 1);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        this.ambulatorio.mettitiInCoda(this);
        this.ambulatorio.ottieniPrestazione(this);
        this.ambulatorio.esci(this);
        System.out.println("°°° " + super.getName() + " termina. °°°");
    }
}
