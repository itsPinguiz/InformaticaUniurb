package ortofrutticolo;

/* Thread che formula le richieste dei chlienti. */
public class Gestore extends Thread {
    private Gruppo gruppo;
    
    public Gestore(Gruppo g) {
        this.gruppo = g;
    }
    
    @Override
    public void run() {
        boolean alive = true;
        while (alive) {
            try {
                this.gruppo.formulaOrdini();
            } catch (InterruptedException e) {
                System.out.println("§§§ Interrupt ricevuto §§§");
                alive = false;
            }            
        }
        System.out.println("--- Il gestore termina. ---");
    }
    
}
