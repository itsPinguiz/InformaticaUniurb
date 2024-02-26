package gestionemontagnerusse;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Arlind Pecmarkaj
 */

/* Oggetto condiviso tra Vettura e Passeggeri */
public class MontagneRusse {
    /* --- Attributi funzionali. --- */
    private int         numPasseggeri;
    
    /* --- Attributi di sincronizzazione. --- */
    // A guardia di numPasseggeri
    private final ReentrantLock       mutex;
    
    private final Semaphore   semaforoBoard;
    private final Semaphore semaforoUnboard;
    private final Semaphore    semaforoLoad;
    private final Semaphore  semaforoUnload;
    
    /* --- Costruttore. --- */
    public MontagneRusse() {
        this.numPasseggeri   = 0;
        this.mutex           = new ReentrantLock();
        this.semaforoBoard   = new Semaphore(0, true);
        this.semaforoUnboard = new Semaphore(0, true);
        this.semaforoLoad    = new Semaphore(0);
        this.semaforoUnload  = new Semaphore(0);
    }
    
    /* --- Metodi di interfaccia pubblica. --- */
    public void board(Passeggero p) {
        System.out.println(p.getName() + " si mette in fila.");
        try {
            // Chiedo un permesso al semaforo per entrare nella vettura.
            this.semaforoBoard.acquire();
            System.out.println(p.getName() + " ottiene un posto.");
            // Se ho ricevuto il permesso, ho il posto in vettura.
            this.mutex.lock();
            /* INIZIO SEZIONE CRITICA */
            // Incremento il contatore dei passeggeri.
            this.numPasseggeri++;
            // Se sono l'ottavo passeggero lo segnalo alla vettura per 
            // farla partire.
            if (this.numPasseggeri == 8) {
                System.out.println("--- Vettura piena. ---");
                this.semaforoLoad.release();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            this.mutex.unlock();
        }
    }
    
    public void load() throws InterruptedException {
        // La vettura arriva e permette a 8 passeggeri di entrare
        System.out.println("La vettura fa entrare 8 passeggeri: ");
        this.semaforoBoard.release(8);
        // Sospendo la vettura fino a che non è piena.
        // Infatti l'ottavo passeggere rilascia il semaforoLoad.
        this.semaforoLoad.acquire();
    }
    
    public void go() {
        System.out.println("#### La vettura parte. ####");
        try {
            Thread.sleep(300); // La vettura si sospende simulando la corsa.
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }     
    }
    
    public void unload() throws InterruptedException {
        System.out.println("La vettura ha finito la corsa " +
                           "e sta scaricando i passeggeri.");
        // Segnalo agli 8 passeggeri che possono scendere giù
        // liberando i semafori.
        this.semaforoUnboard.release(8);
        // Attesa fino allo svuotamento della vettura.
        this.semaforoUnload.acquire();
    }
     
    public void unboard(Passeggero p) {
        try {
            // Mi metto in attesa fino a che non posso scendere
            this.semaforoUnboard.acquire();
            // Se son qua la vettura ha terminato la propria corsa e 
            // posso scendere.
            System.out.println(p.getName() + " scende dalla vettura. ");
            this.mutex.lock();
            /* INIZIO SEZIONE CRITICA */
            // Aggiorno il contatore dei passeggeri nella vettura.
            this.numPasseggeri--;
            if (this.numPasseggeri == 0) {
                System.out.println("La vettura è completamente libera!");
                // Libero il semaforo unload in modo tale da poter far ripartire
                // la vettura che può richiamare load().
                this.semaforoUnload.release();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            this.mutex.unlock();
        }
        

    }
    
}
