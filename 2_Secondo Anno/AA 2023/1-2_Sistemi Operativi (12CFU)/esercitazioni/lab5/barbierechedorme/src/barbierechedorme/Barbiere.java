/*

 */
package barbierechedorme;

/**
 *
 * thread che implemeta il barbiere che dorme
 * il thread sara' in terminazione defferita sfruttando 
 * il fatto che il metodo serviCliente evoca l'ecccezione
 * all 'invocante
 * 
 */
public class Barbiere extends Thread {
    // attributi funzionali
    private Negozio mioNegozio;
    
    // costruttore
    public Barbiere(Negozio n){
        
        // setto il nome thread
        super("Barbiere");
        this.mioNegozio = n;
    
    }
    
    // comportamento del barbiere
    @Override
    public void run(){
        
        // per terminare in modo asincrono il thread
        // serve fare un ciclo su una variabile booleana 
        // inizializzata a true 
        // e catturare l'eccezione inoltrata dal metodo serviCliente
        // per settare la variabile a false
        boolean isAlive = true;
        while(isAlive){
            
            // invoco il metodo serviCliente() di negozio
            try{
                this.mioNegozio.serviCliente();
            }catch(InterruptedException e){
                // verra' eseguita quando il thread registra (main)
                // generera' l'interrupt verso il barbiere
                isAlive = false;
                System.out.println("Interupt ricevuto" + e);
            }
        }// fine while
        
        System.out.println("Il thread "+super.getName()+" termina");
        
    }// fine metodo run()
}// fine classe
