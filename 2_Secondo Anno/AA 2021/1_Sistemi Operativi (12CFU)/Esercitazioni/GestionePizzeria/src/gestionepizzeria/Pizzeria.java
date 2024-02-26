package gestionepizzeria;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Arlind Pecmarkaj
 */
/* Oggetto che simula la pizzeria. */
public class Pizzeria {
    /* --- Attributi funzionali. --- */
    // Rappresenta la lista dei clienti con associato il numero di pizze 
    // ordinate.
    private final LinkedList<Cliente> listaClienti;    
    
    /* --- Attributi di sincronizzazione. --- */
    // A guardia di listaClienti.
    private final ReentrantLock        mutex;
    // Semaforo per svegliare/addormentare il pizzaiolo.
    private final Semaphore richiesteDiPizza;
    
    /* --- Costruttore. --- */
    public Pizzeria() {
        this.listaClienti     = new LinkedList<>();
        this.mutex            = new ReentrantLock();
        this.richiesteDiPizza = new Semaphore(0);
    }
    
    /* --- Metodi di interfaccia pubblica. --- */
    // Metodo per far ordinare le pizze ai clienti.
    // 1) Mi aggiungo alla lista dei clienti bloccando il mutez.
    // 2) Libero un permesso dal semaforo che ferma il pizzaiolo.
    // 3) Mi sospendo: il pizzaiolo mi libera appena finisce le pizze.
    public void ordinaPizze(int n, Cliente c) {
        System.out.println("---- Il cliente " + c.getName() + " entra e ordina "
                           + n + " pizze. ---");
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {     
            this.listaClienti.add(c);
            this.richiesteDiPizza.release();
        } finally {
            this.mutex.unlock();
        }      
        c.sospendi();
        // Se son qua vuol dire che il cliente ha ricevuto l'ordine.
        System.out.println("[ Il cliente " + c.getName() + 
                           " ha ricevuto le pizze! ]");
    }
    
    /* Metodo per servire le pizze al cliente con l'ordine più grande. */
    // 1) Chiedo un permesso dal semaforo: se non ce ne sono non ci sono clienti
    //    e rimango fermo.
    // 2) Appena si libera il permesso cerco il cliente con l'ordine più grande.
    // 3) Mi addormento per n pizze ordinate * 10 ms e sveglio il cliente.
    public void serviCliente() throws InterruptedException {
        this.richiesteDiPizza.acquire();
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            // Se son qua allora devo servire un cliente e
            // cerco quello con più pizze da fare.
            Cliente daServire = this.cercaOrdine();
            System.out.println("§§§ Il pizzaiolo serve il cliente " +
                               daServire.getName() + ". §§§");
            // Simulo la produzione.
            Thread.sleep(10 * daServire.getNumeroPizze());
            // Risveglio il cliente 
            daServire.risveglia();
        } finally {
            this.mutex.unlock();
        }
    }
    
    /* Metodo per cercare l'ordine più grosso. */
    private Cliente cercaOrdine() {
        Cliente conOrdineMaggiore = listaClienti.get(0);
        for (int i = 1; i < listaClienti.size(); i++) {
            if (listaClienti.get(i).getNumeroPizze() >
                conOrdineMaggiore.getNumeroPizze())
                conOrdineMaggiore = listaClienti.get(i);
        }
        this.listaClienti.remove(conOrdineMaggiore);
        return conOrdineMaggiore;
    }  
}
