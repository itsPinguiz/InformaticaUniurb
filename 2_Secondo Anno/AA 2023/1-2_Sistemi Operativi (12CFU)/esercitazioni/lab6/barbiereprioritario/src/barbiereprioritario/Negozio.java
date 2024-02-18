/*
 */
package barbiereprioritario;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * oggetto condiviso tra i thread cliente e il thread barbiere
 * avra' una coda esplicita per memorizzare i rifermineti hai tread 
 * cliente che si sono messi in attesa per il taglio
 * 
 */
public class Negozio {
    
    // attributi funzionali
    // coda per memorizzare i clienti in attesa
    private LinkedList<Cliente> coda;
    
    // variabile per memorizzare il tempo start dell'applicazione
    private long bootTime;
    
    // buffer in ram per stampare tutto il debug
    private String printBuffer;
    
    // attributi di sincronizzazione
    // guardia di coda
    private ReentrantLock mutex;
    private ReentrantLock printLock;
    // semaforo per sospendere il barbiere
    private Semaphore nuovoCliente;
    
    // costruttore
    public  Negozio(){
        
        this.coda           = new LinkedList();
        this.bootTime       = System.currentTimeMillis(); // salvo il tempo auutale di boot
        // semaforo binario a guardia di printBuffer
        this.printLock      = new ReentrantLock();
        this.printBuffer    = "@Start debug: ";
        // attributi di sincronizzazione
        this.mutex          = new ReentrantLock();
        this.nuovoCliente   = new Semaphore(0);
        
    }
    
    // metodi di interfaccia pubblica
    // 1) metodo entra per mettersi in attesa del taglio
    // 2) metodo serviCliente per tagliare i capelli ad un cliente
    
    public void entra(Cliente c){
        // inserisco il mio riferimento nella coda esplicita
        // segnalo al barbiere l'arrivo di un nuovo cliente
        // mi sospendo sul semaforo interno privato , in attesa 
        // che mi vengano tagliati i capelli
        
        // INIZIO SEZIONE CRITICA
        this.mutex.lock();
        try{
            
            this.stringPrintln(this.timeFromBoot() + "> " + "Il cliente " + c.getName() + " con capelli: " + c.getLungCapelli() + " si mette in coda" );
            // metto in coda il mio riferimento 
            this.coda.add(c);
            // segnalo al barbiere il mio arrivo
            this.nuovoCliente.release();
            // ora mi devo sospendere sul semaforo interno
            
        }finally{
            
            this.mutex.unlock();
            // FINE SESSIONE CRITICA
        }
        // qua mi sospendo senza bloccare senza bloccare la sessione critica
        try{
            
            c.sospendi();
            
        }catch(InterruptedException e){
            
            System.out.println(e);
        
        }
    }// fine metodo entra
    
    // metodo utilizzato dal barbiere per tagliare i capelli hai clienti
    // deve permettere la terminazione defferita del barbiere
    // deve quindi inoltrare l'eccezione interrupted exception
    public void serviCliente() throws InterruptedException{
        
        // variabile per tenere il riferimento al cliente migliori
        // con capelli lunghi
        Cliente capellone = null;
        this.nuovoCliente.acquire(); // mi sospendo in attesa di clienti
        
        // se sono qua significa che almeno un cliente e' entrato 
        // e si e' messo in attesa
        // posso cercare nella coda il cliente migliore
        // INIZIO SESSIONE CRITICA
        this.mutex.lock();
        
        try{
            
            capellone = cercaCapellone();
            this.stringPrintln(this.timeFromBoot() + "> " + "Il barbiere taglia i capelli a " + capellone.getName() 
                                                                 + " con linghezza: " + 
                                                                   capellone.getLungCapelli());
            
        }finally{
            
            this.mutex.unlock();
            // FINE SESSIONE CRITICA
            
        }
        
        // mi sospendo per simulare il tempo necessario
        // a tagliare i capelli
        Thread.sleep(10);
        // ora vado a liberare il cliente a cui ho tagliato i capelli
        capellone.risveglia();
        
    }// fine metodo serviCliente

    private Cliente cercaCapellone() {
        
         Cliente capellone = this.coda.getFirst();
         int maxL = capellone.getLungCapelli();
         
         for(int i = 0; i < this.coda.size(); i++){
             
             Cliente attuale = this.coda.get(i);
             
             if(attuale.getLungCapelli() > maxL){
                 
                 maxL = attuale.getLungCapelli();
                 capellone = attuale;
                 
             }
         }
         // rimuovere dalla coda il capellone attuale
         this.coda.remove(capellone);
         return capellone;
        
    }// fine funzione cercaCapellone
    
    // metodo per ritornare il tempo trascorso dal boot
    private int timeFromBoot(){
        return ((int)(System.currentTimeMillis() - this.bootTime));
    }
    
    // metodo per stampare su stringa in ram
    public void stringPrintln(String toPrint){
        this.printLock.lock();
        // INIZIO SESSIONE CRITICA
        try{
        this.printBuffer = this.printBuffer + toPrint + "\n";
        }finally{
            this.printLock.unlock();
            // FINE SESSIONE CRITICA
        }
    }
    
    // metodo per leggere
    public String getAndCleanPrintBuffer(){
        
        String toRet = null;
        
        this.printLock.lock();
        try{
            toRet = this.printBuffer;
            this.printBuffer = "@Start debug: ";
            return toRet;
        }finally{
            this.printLock.unlock();
        }
    }
 
}// fine classe
