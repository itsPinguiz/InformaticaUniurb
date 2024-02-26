package gestionefunivia;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* Oggetto condiviso tra Cabine e Passeggeri */
public class Funivia { 
    /* --- Attributi funzionali. --- */
    private int        passNorm; // Conta i passeggeri normali in attesa.
    private int        passPano; // Conta i passeggeri panoramici in attesa.
    /* --- Attributi di sincronizzazione. --- */
    private final ReentrantLock mutex; // A guardia di passNorm e passPano;
    // Attributi Condition per creare due code di passeggere in base al tipo.
    private final Condition  codaNorm;
    private final Condition  codaPano;
    
    /* --- Costruttore. --- */
    public Funivia() {
        // Inizializzazione attributi funzionali.
        this.passNorm = 0;
        this.passPano = 0;
        // Inizializzazione attributi di sincronizzazione.
        this.mutex    = new ReentrantLock();
        this.codaNorm = this.mutex.newCondition();
        this.codaPano = this.mutex.newCondition();
    }
    
    /* --- Metodi di interfaccia. --- */
    // Metodo per richiedere l'imbarco da parte dei passeggeri.
    // Metodo bloccante fino all'ottenimento dell'imbarco in mutua esclusione
    // con il metodo caricaPasseggeri.
    public void richiestaImbarco(Passeggero pass) {
        // Vado a distinguere se il passeggero è normale o panoramico per 
        // aggiornare il contatore corrispondente.
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            if (pass.getTipo() == 0) { // '0' normale, '1' panoramico.
                this.passNorm++;
                System.out.println("Il passeggero " + pass.getName() +
                                   " richiede l'imbarco normale.");
                // Vado a sospendere il passeggero sulla coda normale.
                this.codaNorm.await();
            } else {
                this.passPano++;
                System.out.println("Il passeggero " + pass.getName() +
                                   " richiede l'imbarco panoramico.");
                this.codaPano.await();
            }
        } catch(InterruptedException in) {
            System.out.println(in);
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
        System.out.println("Il passeggero " + pass.getName() + " ha ottenuto" +
                           " l'imbarco");
    }
    
    // Metodo invocato dai thread Cabina in mutua esclusione
    // con richiestaImbarco.
    public void caricaPasseggeri(Cabina cab) {
        // Distinguiamo le cabine normali da quelle panoramiche per poi
        // valutare la presenza di passeggeri opportuni e svegliare i 
        // passeggeri imbarcati.
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            if ((cab.getTipo() == 0) || // '0' normale, '1' panoramica.
                (cab.getTipo() == 1 && this.passPano == 0)) { 
                // Se ci sono passeggeri normali in attesa ne carico fino
                // a un massimo di 6 altrimenti riparte vuota.
                for (int i = 0; i < 6; i++)
                    this.codaNorm.signal();
                int svegliati = Math.min(6, this.passNorm);
                this.passNorm -= svegliati;
                System.out.println("La cabina " + cab + " riparte con " +
                                   svegliati + " passeggeri.");
            } else {
                for (int i = 0; i < 6; i++)
                    this.codaPano.signal();
                int svegliati = Math.min(6, this.passPano);
                this.passPano -= svegliati;
                System.out.println("La cabina " + cab + " riparte con " +
                                   svegliati + " passeggeri.");
            }
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
    } 
    
}
