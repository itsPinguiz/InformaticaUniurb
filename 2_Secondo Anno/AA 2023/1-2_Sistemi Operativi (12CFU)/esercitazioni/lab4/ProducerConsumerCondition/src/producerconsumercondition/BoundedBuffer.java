/*

 */
package producerconsumercondition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * Oggetto che sara' condiviso fra producer e consumer che sara' thread-safe
 * 
 */
public class BoundedBuffer {
    
    // attributi funzionali
    private int buffer[];
    
    // puntatori logici
    private int in,out;
    
    // contatore di elementi presenti nel buffer
    private int elements;
    
    // attributi di sincronizzazione
    // a guardia di elements,buffer,in e out
    private ReentrantLock mutex;
    
    // due variabili condition per sospendere il thread
    // produttore e consumatore in caso di buffer pieno e
    // buffer vuoto
    
    // variabile che sospende il produttore in caso di buffer pieno
    private Condition notFull;
    
    // variabile che sospende il consumatore in caso del buffer vuoto
    private Condition notEmpty;
    
    // costruttore della classe
    public BoundedBuffer(int size){
        // inizializzo gli attributi funzionali
        this.buffer     = new int[size];
        this.in         = 0;
        this.out        = 0;
        this.elements   = 0;
        
        // inizializzo gli attributi di sincronizzazione
        this.mutex = new ReentrantLock();
        
        // le variabili condition non possono essere inizializzate con l'operatore new
        // ma devono essere sempre prodotte a partire da un ReentrantLock esistente
        // questo le leghera' per sempre al lock chele le ha prodotte andando a liberare
        // la sezione critica nel momento della sospensione
        this.notFull = this.mutex.newCondition();
        this.notEmpty = this.mutex.newCondition();
        
    }// fine costruttore
    
    // METODI INTERFACCIA
    
    // metodo inserimento mutua esclusione e dovra' bloccare il thread invocante quando
    // il buffer sara' pieno
    public void insertItem(int item){
        
        // devo controllare che il buffer non sia pieno in caso positivo 
        // inserico l'elemento in caso negativo devo sospendermi sulla mia variabile
        // condition
        // INIZIA LA SESSIONE CRITICA
        this.mutex.lock();
        
        try{
            
            // controllo il contenuto del buffer per vedere se c'e' un posto disponibile
            // al posto di un if si una while che non generano errori e rivanno a dormire
            while(this.elements == this.buffer.length) // vado a dormire sulla condition
                this.notFull.await();
            
            // se sto eseguendo qua significa che il buffer non era pieno oppure qualcuno mi ha risvegliato
            // dopo aver rimosso un elemento dal buffer
            this.elements++;
            this.buffer[this.in] = item;
            this.in = (this.in + 1) % this.buffer.length;
            System.out.println("Inserito elemento " + item + " nel buffer");
            
            // devo notificare ad un eventuale consumatore in attesa
            // che ora c'e' un nuovo elemento da cinsumare
            this.notEmpty.signal(); // se il consumatore sta dormendo verra' risvegliato altrimenti la 
                                    // signal va persa

        }catch(InterruptedException e){
            System.out.println(e);
        }finally{ // anche se la macchinavirtuale di java crascia viene eseguito comunque l'unlock
            this.mutex.unlock();
            // FINE SESSIONE CRITICA
        }  
    }// fine metodo inserimento
    
    // metodo di rimozione: deve essere in mutua esclusione e deve bloccare il thread
    // invocante in caso di buffer vuoto
    public int getItem(){
        int toReturn = -1;
        // devo controllare che il buffer non sia vuoto
        // in caso positivo devo consumare un elemento 
        // ma in caso negativo devo sopsendermi all'interno della
        // sessione critica
        // INIZIO SESSIONE CRITICA
        this.mutex.lock();
        try{
            // devo accedere alle variabili condivise
            // controllo se il buffer e' vuoto
            while(this.elements == 0) // mi sospendo fino a che il buffer non contine elementi
                this.notEmpty.await();
            
            // se sto eseguendo qua significa che il buffer non e' vuoto oppure qualcuno mi ha risvegliato
            // dopo aver inserito un elemento dal buffer
            toReturn = this.buffer[this.out];
            this.elements--;
            this.out = (this.out + 1) % this.buffer.length;
            System.out.println("Rimosso elemento " + toReturn + " nel buffer");
            this.notFull.signal();
            
        }catch(InterruptedException e){
            System.out.println(e);
        }finally{
            this.mutex.unlock();
            // FINE SESSIONE CRITCA
        }
        
        return toReturn;
      
    }// fine metodo rimozione
    
}// fine classe
