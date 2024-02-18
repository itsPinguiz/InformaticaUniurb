
package barbierechedorme;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * oggetto condiviso tra il barbiere e i clienti 
 * sara' un oggetto tread safe e conterra' due metodi pubblici
 * il primo per far sottomettere le richieste dai clienti 
 * e il secondo per permettere al barbiere di gestire le richieste
 * 
 */
public class Negozio{
    
    // attributi funzionali
    private static int NUM_SEDIE = 5;
    private int sedieOccupate;
    
    // attributi di sincronizzazione
    // a guardia di sedie occupate
    private ReentrantLock mutex;
    
    // semaforo contatore per sospendere il barbiere in attesa di clienti
    private Semaphore nuovoCliente;
    
    // semaforo per sospendere i clienti in attesa del servizio
    private Semaphore nuovoTaglio;
    
    // costruttore della classe
    public Negozio(){
        
        // iniziallizazione attributi funzionali
        this.sedieOccupate = 0;
        
        // inizializzo gli attributi di sincronizzzazione
        this.mutex = new ReentrantLock();
        this.nuovoCliente = new Semaphore(0);
        
        // blocca i clienti e li ordina in modo FIFO
        this.nuovoTaglio = new Semaphore(0,true); 
        
    }// fine costruttore
    
    // metodo invocato dai clienti per accedere al negozio ed eventualmente
    // mettersi in coda. Sara' in mutua escusione con se stesso e con il metodo
    // usato dal barbiere per gestire le richieste. Blocchera' i clienti in attesa
    public boolean entra(Cliente c){
        boolean toReturn = true; // ho tagliato i capelli
        
        // controllo se ci sono sedie disponibili
        // se si mi metto in attesa
        // se no esco subito e ritorno false
        // INIZIO SESSIONE CRITICA
        this.mutex.lock();
        try{
            // controllo il numero di sedie disponibili
            if(this.sedieOccupate < Negozio.NUM_SEDIE){
                
                // posso sedermi ed attendere
                this.sedieOccupate++;
                System.out.println("Il cliente "+c.getName()+" si siede per il taglio");
                
                // segnalo al barbiere che c'e' un nuovo cliente
                this.nuovoCliente.release();
                
                //mi sospendo in attesa che il barbiere mi tagli i capelli
                // libero il lock della sessione critica
                this.mutex.unlock();
                // FINE SESSIONE CRITICA
                this.nuovoTaglio.acquire(); // siamo bloccati in attesa
                
            }else{
                
                // esco subito e ritorno false
                toReturn = false;
                System.out.println("Il cliente "+c.getName()+" esce senza aver tagliato i capelli");
                this.mutex.unlock();
                // FINE SESSIONE CRITICA
            }
        }catch(InterruptedException e){
            System.out.println(e);
        }
        
        
        return toReturn;
    }// fine metodo entra
    
    // metodo usato dal barbiere per servire i clienti
    // ogni volta che il metodo esegue serve un cliente
    // deve essere in murua esclusone con il metodo entra()
    // deve sospendere il barbiere in attesa di clienti
    
    // deve inoltrare l'eccezione di InterruotedException al barbiere per
    // effettuare la terminazione defferita
    public void serviCliente() throws InterruptedException {
        
        // mi metto in attesa per una nuova richiesta
        this.nuovoCliente.acquire(); // all'inizio il barbiere e' bloccato qua (dorme)
        // se sono qua significa almeno un cliente in attesa esiste.
        // ora posso tagliargli i capelli ad un cliente
        // INIZIO SESSIONE CRITICA
        this.mutex.lock();
        try{
            
            // decremento sedie occupate
            this.sedieOccupate--;
            
        }finally{
            
            this.mutex.unlock();
            // FINE SESSIONE CRITICA
            
        }
        
        System.out.println("Il barbiere taglia i capelli ad un cliente");
        // simulo il tempo necessario a tagliare i capelli sospendendomi per
        // 20 ms
        Thread.sleep(20);
        // ora posso svegliare un cliente in ordine FIFO
        this.nuovoTaglio.release();
        
    }// fine metodo serviCliente()
        
}// fine classe
