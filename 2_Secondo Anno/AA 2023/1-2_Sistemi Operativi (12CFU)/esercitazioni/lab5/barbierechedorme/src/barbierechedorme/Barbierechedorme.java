/*

 */
package barbierechedorme;

/**
 *
 * @author roxen
 */
public class Barbierechedorme {

    /**
     * 
     */
    public static void main(String[] args) {
        Negozio negozio = new Negozio();
        Barbiere berbiere = new Barbiere(negozio);
        Cliente clienti[] = new Cliente[15];
        
        for(int i = 0;i < clienti.length ;i++){
            clienti[i] = new Cliente(negozio,"Cliente" + i);
        }
        
        // esecuzione del threads
        for(int i = 0;i < clienti.length ;i++){
            clienti[i].start();
        }
        
        berbiere.start();
        
        // ora mi metto in attesa per la terminazione dei soli Clienti
        try{
            for(int i = 0;i < clienti.length ;i++)
                clienti[i].join();
            // se sono qa tutti i clineti sono terminati
            // quindi posso generare l'interrupt verso il barbiere per
            // obbligandlo a terminare l'esecuzione
            
            // genero l'interrupt verso il barbiere
            berbiere.interrupt();
            // attendo per la terminazione del barbiere
            berbiere.join();
        }catch(InterruptedException e){
                System.out.println(e);
        }
        System.out.println("Tutti i thread sono terminati");
    }// fine main()
    
}// fine classe
