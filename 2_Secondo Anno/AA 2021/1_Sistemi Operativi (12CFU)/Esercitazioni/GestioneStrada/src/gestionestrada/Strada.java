package gestionestrada;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Arlind Pecmarkaj
 */

/* Oggetto che simula strada a senso alternato. */
public class Strada {
    /* --- ATTRIBUTI FUNZIONALI. --- */
    // Verso come definito nell'enum Verso.
    private Verso                verso;
    // Numero di veicoli che attualmente attraversano la strada.
    private int                 numero;
    // Flag per controllare se c'è uno spazzaneve in coda.
    private boolean   spazzaneveInCoda;
    /* --- ATTRIBUTI DI SINCRONIZZAZIONE. --- */
    // A guardia degli attributi funzionali
    private final ReentrantLock  mutex;
    // Code per ogni tipo di veicolo e di verso.
    private final Condition   codaNord;
    private final Condition    codaSud;
    private final Condition spazzaneve;
    
    /* --- Costruttore. --- */
    public Strada() {
        this.verso            = Verso.LIBERO;
        this.numero           = 0;
        this.spazzaneveInCoda = false;
        this.mutex            = new ReentrantLock();
        this.codaNord         = this.mutex.newCondition();
        this.codaSud          = this.mutex.newCondition();
        this.spazzaneve       = this.mutex.newCondition();
    }
    
    /* --- Metodi di interfaccia pubblica. ---*/
    /* Metodo per accedere alla strada.
    1) Se entra uno spazzaneve setto il flag a true.
    2) Controllo se la strada è libera. Se si la percorro.
    3) Se non è libera aspetto che si liberi sospendedomi. */
    public void accessoStrada(Veicolo v) throws InterruptedException {
        System.out.println("> Il veicolo " + v.getName() + " entra in coda"
                           + " con verso " + v.getVerso());
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            if (v instanceof Spazzaneve) {
                this.spazzaneveInCoda = true;  
                System.out.println("**** SPAZZANEVE IN CODA ****");
            }
            if (this.verso == Verso.LIBERO)
                this.verso = v.getVerso();
            while (this.verso != v.getVerso()) {
                if (v.getVerso() == Verso.NORDSUD)
                    this.codaSud.await();
                else if (v.getVerso() == Verso.SUDNORD)
                    this.codaNord.await();
                else
                    this.spazzaneve.await();
                if (this.verso == Verso.LIBERO)
                    this.verso = v.getVerso();
            }
            this.numero++;
            System.out.println("Il veicolo " + v.getName() + " percorre "
                               + "la strada. [VERSO:" + this.verso + "]");
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
    }
    /* Metodo per liberare la strada.
    1) Diminuisco il numero di veicoli per strada.
    2) Se son l'ultimo e c'è uno spazzaneve in coda lui prende la precedenza. 
       Altrimenti libero la strada nell'altro verso (son l'ultimo del mio verso)
       altrimenti libero entrambi i versi se sono uno spazzaneve: chi arriva 
       per primo deciderà il verso.*/
    public void liberaStrada(Veicolo v) {
        /* INIZIO SEZIONE CRITICA */
        this.mutex.lock();
        try {
            this.numero--;
            System.out.println(v.getName() + " ha lasciato la strada.");
            if (this.numero == 0) {
                this.verso = Verso.LIBERO;
                System.out.println("°°° Il veicolo " + v.getName() 
                                   + " è l'ultimo e libera la strada. °°°");
                if (this.spazzaneveInCoda) {
                    System.out.println("| Lasciata la precedenza allo " + 
                                       " spazzaneve |");
                    this.verso = Verso.SPAZZANEVE;
                    this.spazzaneve.signal();
                    this.spazzaneveInCoda = false;
                } else { 
                    if (v.getVerso() == Verso.NORDSUD) {
                        this.codaNord.signalAll();
                    } else if (v.getVerso() == Verso.SUDNORD) {
                        this.codaSud.signalAll();
                    } else {
                        this.codaNord.signalAll();
                        this.codaSud.signalAll();
                    }
                }
            } else {
                System.out.println("Veicoli rimanenti: " + this.numero);
            }           
        } finally {
            this.mutex.unlock();
            /* FINE SEZIONE CRITICA */
        }
    }
}
