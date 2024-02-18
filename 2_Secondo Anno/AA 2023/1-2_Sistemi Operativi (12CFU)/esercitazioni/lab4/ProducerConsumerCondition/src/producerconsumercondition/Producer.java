/*

 */
package producerconsumercondition;

/**
 *
 * sara' il thread che inserira ' gli elementi nel buffer
 * avra' una velocita' costante sospendendosi 10 ms ad ogni inserimento
 * 
 */
public class Producer extends Thread {
    
    // attributi funzionali 
    // riferimento all'oggetto da condividere 
    private BoundedBuffer myBuffer;
    
    // costruttore
    public Producer(BoundedBuffer b){
        super("Producer"); // setto il nome del poducer
        this.myBuffer = b;
    }
    
    // implementazione del comportamento del thread
    @Override
    public void run(){
        // inseriamo 50 elementi consecutivamente
        for(int i = 0; i < 50; i++){
            this.myBuffer.insertItem(i);
            
            // ora mi sospendo per 10 ms per simulare una velocita'
            // costante di produzione
            try{
                Thread.sleep(10);
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }// fine for
        System.out.println("Il Thread "+super.getName()+" termina");
    }// fine metodo
    
}// fine classe
