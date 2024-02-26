package gestioneaeroporto;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Arlind Pecmarkaj
 */

/* Oggetto condiviso tra aerei e gestore */
public class Aeroporto {
    /* --- Attributi funzionali. --- */
    private final ArrayList<Aereo> codaAtterraggio;
    private final ArrayList<Aereo>     codaDecollo;
    /* --- Attributi di sincronizzazione. --- */
    // A guardia delle code.
    private final ReentrantLock              mutex;
    // Per svegliare il gestore.
    private final Semaphore            aereiInCoda;
    // Per notificare l'occupazione della pista;
    private final Semaphore          pisteOccupate;
    
    /* --- Costruttore. --- */
    public Aeroporto() {
        this.codaAtterraggio = new ArrayList<>();
        this.codaDecollo     = new ArrayList<>();
        this.mutex           = new ReentrantLock();
        this.aereiInCoda     = new Semaphore(0);
        this.pisteOccupate   = new Semaphore(2);
    }
    
    /* --- Metodi di interfaccia pubblica. --- */
    // Metodo usato dagli aerei per richiedere di atterrare o decollare.
    public void richiediServizio(Aereo a, int servizio) {
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            if (servizio == 0) {
                System.out.println("> L'aereo " + a.getName() + " richiede "
                                   + "l'atterraggio ed entra in coda. " 
                                   + "Il suo peso è " + a.getPeso());
                this.codaAtterraggio.add(a);
            } else {
                System.out.println("> L'aereo " + a.getName() + " richiede "
                                   + "il decollo ed entra in coda. "
                                   + "Il suo peso è " + a.getPeso());
                this.codaDecollo.add(a);
            }
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
        this.aereiInCoda.release();
        a.sospendi();
    }
    
    // Metodo usato dal gstore per coordinare i servizi degli aerei.
    public void gestisciAerei() throws InterruptedException {
        Aereo daServire;
        this.aereiInCoda.acquire();
        this.pisteOccupate.acquire();
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            if (!this.codaAtterraggio.isEmpty()) {
                daServire = this.aereoDaServire(this.codaAtterraggio);
                System.out.println("°°° Il gestore ha scelto l'aereo " 
                                   + daServire.getName() 
                                   + " e lo fa atterrare. °°°");
            } else {
                daServire = this.aereoDaServire(this.codaDecollo);
                System.out.println("°°° Il gestore ha scelto l'aereo " 
                                   + daServire.getName() 
                                   + " e lo fa decollare. °°°");
            }
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
        daServire.sveglia();     
    }
    
    // Metodo usato dagli aerei per notificare la liberazione della pista.
    public void liberaPista(Aereo a) {
        System.out.println("- " + a.getName() + " libera la pista. -");
        this.pisteOccupate.release();
    }
    
    // Metodo per cercare l'aereo più pesante da una coda.
    private Aereo aereoDaServire(ArrayList<Aereo> coda) {
        Aereo a = coda.get(0);
        for (int i = 1; i < coda.size(); i++) {
            if (coda.get(i).getPeso() > a.getPeso())
                a = coda.get(i);
        }
        coda.remove(a);
        return a;
    }   
}
