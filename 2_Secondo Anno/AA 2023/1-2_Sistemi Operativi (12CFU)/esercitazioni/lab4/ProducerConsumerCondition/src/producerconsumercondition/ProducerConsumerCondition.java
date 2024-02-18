/*

 */
package producerconsumercondition;

/**
 *
 * 
 */
public class ProducerConsumerCondition {

    /**
     * 
     */
    public static void main(String[] args) {
        BoundedBuffer buffer = new BoundedBuffer(8);
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);
        //lancio i threads
        producer.start();
        consumer.start();
        try{
            producer.join();
            consumer.join();
        }catch(InterruptedException e){
            System.out.println(e);
        }
        System.out.println("Simulazione terminata");
    }// fine main
    
}// fine classe
