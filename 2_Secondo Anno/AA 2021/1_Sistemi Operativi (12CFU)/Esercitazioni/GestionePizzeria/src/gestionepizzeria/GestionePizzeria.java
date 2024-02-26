package gestionepizzeria;

/**
 * @author Arlind Pecmarkaj
 */
public class GestionePizzeria {

    public static void main(String[] args) {
        Pizzeria pizzeria   = new Pizzeria();
        Pizzaiolo pizzaiolo = new Pizzaiolo(pizzeria);
        Cliente[]   clienti = new Cliente[12];
        for (int i = 0; i < clienti.length; i++) {
            clienti[i] = new Cliente("{" + i +"}", pizzeria);
            clienti[i].start();
        }
        pizzaiolo.start();
        try {
            for (Cliente c : clienti)
                c.join();
            pizzaiolo.interrupt();
            pizzaiolo.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("+++++ Simulazione terminata. +++++");
    }
    
}
