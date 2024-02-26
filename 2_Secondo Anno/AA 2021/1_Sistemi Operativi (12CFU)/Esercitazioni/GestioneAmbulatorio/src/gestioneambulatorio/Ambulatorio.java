package gestioneambulatorio;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Arlind Pecmarkaj
 */
/* Oggetto condiviso tra Paziente ed Informatore. */
public class Ambulatorio {
    /* --- Attributi funzionali. --- */
    private int             numPaz;
    private int             numInf;
    private int         pazServiti;
    private boolean       occupato;
    /* --- Attributi di sincronizzazione. --- */
    // A guardia del numero di utenti in ambulatorio.
    private final ReentrantLock   mutex;
    private final Condition sospendiPaz;
    private final Condition sospendiInf;
    
    /* --- Costruttore. --- */
    public Ambulatorio() {
        this.numInf      = this.numPaz = this.pazServiti = 0;
        this.occupato    = false;
        this.mutex       = new ReentrantLock();
        this.sospendiPaz = this.mutex.newCondition();
        this.sospendiInf = this.mutex.newCondition();
        
    }
    
    /* --- Metodi di interfaccia pubblica. --- */
    public void mettitiInCoda(Utente u) {
        if (u instanceof Paziente) {
            System.out.println("| Il paziente " + u.getName() + 
                               " entra in coda. |");
            /* INIZIO SEZIONE CRITICA */
            this.mutex.lock();
            try {
                this.numPaz++;
                // Controllo se vengo svegliato mentre l'ambulatorio è occupato.
                while (this.occupato)
                    this.sospendiPaz.await();
                // Se son qua vuol dire che l'ambulatorio è libero.
                this.occupato = true;
                // Aumento il numero di pazienti serviti.
                this.pazServiti++;
                // Diminuisco il numero di pazienti in coda.
                this.numPaz--;
            } catch (InterruptedException e) {
                System.out.println(e);
            } finally {
                this.mutex.unlock();
                /* FINE SEZIONE CRITICA */
            }             
        } else if (u instanceof Informatore){
            System.out.println("| L'informatore " + u.getName() + 
                               " entra in coda. |");
            /* INIZIO SEZIONE CRITICA */
            this.mutex.lock();
            try {
                this.numInf++;
                while (this.occupato)
                    this.sospendiInf.await();
                this.occupato = true;
                this.numInf--;
                
            } catch (InterruptedException e) {
                System.out.println(e);
            } finally {
                this.mutex.unlock();
                /* FINE SEZIONE CRITICA */
            }
        }
    }
    
    public void ottieniPrestazione(Utente u) {
        System.out.println("*** "+ u.getName() + " entra in ambulatorio. ***");
        try {
            Thread.sleep(300);
            System.out.println("*** " + u.getName() + 
                               " termina la prestazione. ***");
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
    
    public void esci(Utente u) {     
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            this.occupato = false;
            if (this.pazServiti == 3) {
                if (this.numInf > 0) 
                    this.sospendiInf.signal();
                else 
                    this.sospendiPaz.signal();
                this.pazServiti = 0;
            } else
                if (this.numPaz > 0)
                    this.sospendiPaz.signal();
                else
                    this.sospendiInf.signal();
               
            System.out.println("§ " + u.getName() + 
                               " esce dall'ambulatorio. §");
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
    }
    
}
