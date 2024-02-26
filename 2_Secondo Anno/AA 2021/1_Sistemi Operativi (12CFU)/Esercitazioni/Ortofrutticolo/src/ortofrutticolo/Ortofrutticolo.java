package ortofrutticolo;

public class Ortofrutticolo {

    public static void main(String[] args) {
        Gruppo gruppo      = new Gruppo();
        Gestore gestore    = new Gestore(gruppo);
        Cliente insalate[] = new Cliente[30];
        Cliente pomodori[] = new Cliente[20];
        gestore.start(); 
        int j = 0;
        for (int i = 0; i < insalate.length; i++) {
            insalate[i] = new Cliente("Cliente " + i, gruppo, false);
            insalate[i].start();
            j = i + 1;
        }
        for (int i = 0; i < pomodori.length; i++) {
            pomodori[i] = new Cliente("Cliente " + (j + i), gruppo, true);
            pomodori[i].start();
        }      
        try {
            for (Cliente i : insalate)
                i.join();
            for (Cliente p : pomodori)
                p.join();
            gestore.interrupt();
            gestore.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("#### SIMULAZIONE TERMINATA ####");

    }
    
}
