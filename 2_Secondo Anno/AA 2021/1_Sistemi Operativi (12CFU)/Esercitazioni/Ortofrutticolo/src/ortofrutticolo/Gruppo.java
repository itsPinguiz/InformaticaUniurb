package ortofrutticolo;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;

/* Oggetto condiviso tra i clienti e il gestore. */
public class Gruppo {
    /* --- Attributi funzionali. --- */
    private LinkedList<Cliente> insalate;
    private LinkedList<Cliente> pomodori;
    
    /* --- Attributi di sincronizzazione --- */
    // A guardia delle due liste.
    private ReentrantLock          mutex;
    // Semafori per il gestore.
    private Semaphore    clientiInsalate;
    private Semaphore    clientiPomodori;
    
    public Gruppo() {
        this.insalate        = new LinkedList<>();
        this.pomodori        = new LinkedList<>();
        this.mutex           = new ReentrantLock();
        this.clientiInsalate = new Semaphore(0);
        this.clientiPomodori = new Semaphore(0);
    }
    
    public void sottomettiRichiesta(Cliente c) {
        this.mutex.lock();
        /* INIZIO SEZIONE CRITICA */
        try {
            /* 
            1) Metto il cliente in lista in base all'ortaggio.
            2) Libero un semaforo per il gestore.
            3) Sospendo il cliente: lo libererà il gestore quando avrà
                                    processato la richiesta. 
            */
            if (c.getOrtaggio()) {
                this.pomodori.add(c);
                this.clientiPomodori.release();
                System.out.println("|| Il cliente " + c.getName() + " ha fatto "
                                   + "richiesta di " + c.getPeso() + " kg di "
                                   + "pomodori. ||");
            } else {
                this.insalate.add(c);
                this.clientiInsalate.release();
                System.out.println("|| Il cliente " + c.getName() + " ha fatto "
                                   + "richiesta di " + c.getPeso() + " kg di "
                                   + "insalata. ||");
            }
            
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
        c.sospendi();
    }
    
    public void formulaOrdini() throws InterruptedException {
        Cliente daServire = null;
        // Metto in attesa per le richieste.
        // Richiedo 3 insalate e 2 pomodori: se non ci sono rimango fermo.
        this.clientiInsalate.acquire(3);
        this.clientiPomodori.acquire(2);
        // Simulo l'ordine che viene processato.
        Thread.sleep(100);
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            // Cerco le 3 insalate più grosse e risveglio i clienti.
            for (int i = 0; i < 3; i++) {
                daServire = this.cercaPesanteInsalate();
                System.out.println("*** Formulato ordine per il cliente " + 
                                   daServire.getName() + ". ***");
                daServire.risveglia();
            }
            // Cerco i 2 pomodori più grossi e risveglio i clienti.
            for (int i = 0; i < 2; i++) {
                daServire = this.cercaPesantePomodori();
                System.out.println("*** Formulato ordine per il cliente " + 
                                   daServire.getName() + ". ***");
                daServire.risveglia();
            }                
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
        
    }
    
    /* Metodi per cercare il cliente con l'ordine più grosso. 
       Vengono anche rimossi dalle liste in quanto se vengono chiamati i
       metodi non hanno più bisogno di essere lì.
    */
    private Cliente cercaPesanteInsalate() {
        Cliente pesante = insalate.get(0);
        Cliente attuale = insalate.get(0);
        for (int i = 1; i < insalate.size(); i++) {
            attuale = insalate.get(i);
            if (attuale.getPeso() > pesante.getPeso())
                pesante = attuale;
        }           
        this.insalate.remove(pesante);       
        return pesante;
    }
    
    private Cliente cercaPesantePomodori() {
        Cliente pesante = pomodori.get(0);
        Cliente attuale = pomodori.get(0);
        for (int i = 1; i < pomodori.size(); i++) {
            attuale = pomodori.get(i);
            if (attuale.getPeso() > pesante.getPeso())
                pesante = attuale;
        }           
        this.pomodori.remove(pesante);       
        return pesante;
    }
}
