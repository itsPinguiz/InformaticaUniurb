/*
 */
package barbiereprioritario;

/**
 *
 * 
 */
public class Barbiereprioritario {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Negozio negozio = new Negozio();
        Barbiere barbiere = new Barbiere(negozio);
        
        // creo array clienti
        Cliente clienti[] = new Cliente[10];
        
        // inizializzo i clienti
        for(int i = 0; i < clienti.length; i++){
            clienti[i] = new Cliente(negozio,"Cliente " + i);
        }
        
        // ora posso far partire i thread
        barbiere.start();
        for(int i = 0; i < clienti.length; i++){
            clienti[i].start();
        }
        
        // mi metto in attesa della terminazione dei clienti
        try{
                
                for(int i = 0; i < clienti.length; i++){
                    clienti[i].join();
                }
                
                // ora inivio l'interrupt al barbiere
                barbiere.interrupt();
                barbiere.join();
                
            }catch(InterruptedException e){
            
                System.out.println(e);
        
            }
        // svuoto la stringa di stampa sulla shell
        System.out.println(negozio.getAndCleanPrintBuffer());
        
        // stampo il valore del tempo di attesa per ogni cliente
        for(int i = 0; i < clienti.length ; i++){
            System.out.println(clienti[i].getName()+" L: "+clienti[i].getLungCapelli()+" Tempo attesa: "+clienti[i].getTempoTotale()/20);
        }
        System.out.println("Tutti i thread sono terminati");
    }// fine main
    
}// fine classe
