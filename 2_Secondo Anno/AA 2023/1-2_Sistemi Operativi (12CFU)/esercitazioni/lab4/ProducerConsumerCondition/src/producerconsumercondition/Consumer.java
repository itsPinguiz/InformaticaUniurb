/*

 */
package producerconsumercondition;

import java.util.Random;

/**
 *
 * Thread che rimuove periodicamnete elementi dal buffer
 * avra' una velocità casuale simulata sospendendosi per un tempo
 * compreso nell'intevallo [5,35] ms ad ogni rimozione
 * 
 */
public class Consumer extends Thread {
    // attributi funzionali
    private BoundedBuffer myBuffer;
    private Random rnd;
    
    // costruttore
    public Consumer(BoundedBuffer b){
        super("Consumer");
        this.myBuffer = b;
        this.rnd = new Random();
        
    }
    // implementazione del comportamento thread
    @Override
    public void run(){
        
        // per 50 volte rimuovo un elemento dal buffer
        // mi sospendo per un tempo casuale nell'intervallo [5,35] ms
        for(int i = 0; i < 50; i++){
            int item = this.myBuffer.getItem();
            int timeToSleep = 5 + this.rnd.nextInt(31);
            
            // ora mi sospendo per tot tempo
            try{
                Thread.sleep(timeToSleep);
            }catch(InterruptedException e){
                System.out.println(e);
            }
            
        }// fine for
        System.out.println("Il Thread "+super.getName()+" termina");
    }// fine metodo
}// fine classe
