/*
 */
package barbiereprioritario;

/**
 *
 *  sara' il barchiere che taglia i capelli hai clienti
 * 
 */
public class Barbiere extends Thread{
   // attributi interni
    private Negozio mioNegozio;
    
    // costruttore
    public Barbiere(Negozio n){
        
        super("Barbiere");
        this.mioNegozio = n;
    
    }
    
    // comportamento del tread
    @Override
    public void run(){
        boolean isAlive = true;
        
        while(isAlive){
            try{
                
                this.mioNegozio.serviCliente();
                
            }catch(InterruptedException e){
            
            System.out.println(e);
            isAlive = false;
        
            }
        }// fine while
        this.mioNegozio.stringPrintln("Il thread " + super.getName() + " termina!!");
    }// fine metodo
}// fine classe
