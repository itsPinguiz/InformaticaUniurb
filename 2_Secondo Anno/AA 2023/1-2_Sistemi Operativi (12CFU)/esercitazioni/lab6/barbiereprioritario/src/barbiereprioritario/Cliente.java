/*
 */
package barbiereprioritario;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 *  implementa un thread cliente con sistema di sospensione interno per 
 *  permettere una qualsiasi politca di accodamento e risveglio
 * 
 */
public class Cliente extends Thread {
    
    // attributi interni 
    private Negozio mioNegozio;
    private int lungCapelli;
    private Random rnd;
    // tempo totale di servizio
    private int tempoTotale;
    
    // attributi di sincronizzazione
    // semaforo privato per autosospensione
    private Semaphore mySem;
    
    // costruttore
    public Cliente(Negozio n,String name){
        
        super(name);
        this.mioNegozio     = n;
        this.rnd            = new Random();
        this.lungCapelli    = this.rnd.nextInt(101); // [0,100]
        this.tempoTotale    = 0;
        
        // attributo di sincronizzazione
        this.mySem          = new Semaphore(0);
    
    }
    
    // metodo di get per leggere la lunghezza dei capelli
    public int getLungCapelli(){
        return this.lungCapelli;
    }
    
    // implemetare il metodo sospendi per sospendere il cliente
    void sospendi() throws InterruptedException{
        this.mySem.acquire();
    }
    
    // metodo per risvegliare il cliente
    public void risveglia(){
        this.mySem.release();
    }
    
    // metodo per leggere il valore del tempo totale
    public int getTempoTotale(){
        return this.tempoTotale;
    }
    
    // metodo comportamento del thread
    @Override
    public void run(){
        
        // per 20 volte si fara' tagliare i capelli
        for(int i = 0; i < 20 ; i++){
            
            // devo calcolare il tempo trascorso all'interno del matodo entra
            long t0 = System.currentTimeMillis();
            this.mioNegozio.entra(this); // qua taglio dei capelli
            long t1 = System.currentTimeMillis();
            // sommo il tempo trascorso nella mia variabile tempo totale
            this.tempoTotale += (t1 - t0);
            
            this.mioNegozio.stringPrintln("Il cliente " + super.getName() + " ha tagliato i capelli");
            
            // simulo la ricrescita
            try{
                Thread.sleep(10);
            }catch(InterruptedException e){
            
            System.out.println(e);
        
            }
            
        }// fine for
        
        this.mioNegozio.stringPrintln("Il thread " + super.getName() + " termina!!");
        
    }// fine run()
    
}// fine classe cliente
