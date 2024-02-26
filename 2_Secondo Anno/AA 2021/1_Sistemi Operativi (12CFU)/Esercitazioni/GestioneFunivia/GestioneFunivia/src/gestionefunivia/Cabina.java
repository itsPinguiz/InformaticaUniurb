package gestionefunivia;

public class Cabina extends Thread {
    private Funivia miaFunivia;
    private int           tipo;
    private int          index;
    
    public Cabina(Funivia f, int tipo, int index) {
        super("Funivia " + index + (tipo > 0? " panoramica": " normale"));
        this.miaFunivia = f;
        this.tipo       = tipo;
        this.index      = index;
    }
    
    @Override
    public void run() {
        boolean isAlive = true;
        // Partenza scaglionata, si sospende per index * 25 ms
        try {
            Thread.sleep(25 * this.index);
            // Ora ogni 250 ms invoco il metodo caricaPasseggeri.
            while (isAlive && !this.isInterrupted()) {
                this.miaFunivia.caricaPasseggeri(this);
                Thread.sleep(250);
            }
        } catch (InterruptedException in) {
            System.out.println(in);
            isAlive = false;
        }
        System.out.println(super.getName() + " termina l'esecuzione.");
    }

    public int getTipo() {
        return this.tipo;
    }
    
}
