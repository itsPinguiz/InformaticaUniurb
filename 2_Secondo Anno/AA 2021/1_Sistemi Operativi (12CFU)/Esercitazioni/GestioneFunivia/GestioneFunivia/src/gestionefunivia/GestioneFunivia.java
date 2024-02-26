package gestionefunivia;

public class GestioneFunivia {

    public static void main(String[] args) {
        // Oggetto condiviso.
        Funivia f               = new Funivia();
        // Array di 10 cabine.
        Cabina cabine[]         = new Cabina[10];
        // Array di 164 passeggeri.
        Passeggero passeggeri[] = new Passeggero[164];
        for (int i = 0; i < cabine.length; i++) {
            cabine[i] = new Cabina(f, (i < 2? 1 : 0), i);
            cabine[i].start();
        }
        for (int i = 0; i < passeggeri.length; i++) {
            passeggeri[i] = new Passeggero(f, (i < 132? 0 : 1), i);
            passeggeri[i].start();
        }
        // Mi metto in attesa dei passeggeri
        try {
            for (Passeggero p : passeggeri)
                p.join();
            for (Cabina c : cabine)
                c.interrupt();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("Simulazione terminata.");
    }
    
}
