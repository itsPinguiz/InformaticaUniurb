/*

 */
package barbierechedorme;

/**
 *
 * thread che rappresenta il cliente e che per un certo numero di volte 
 * richiedera' ti tagliare i capelli
 * 
 */
public class Cliente extends Thread{
    private Negozio mioNegozio;
    
    // costruttore
    public Cliente(Negozio n,String name){
        
        super(name);
        this.mioNegozio = n;
        
    }// fine costruttore
    
    
    // comportamento del thread
    @Override
    public void run(){
        // il clinete termina quando avra' ottenuto 100 tagli di capelli
        for(int i = 0; i < 100; i++){
            boolean tagliato = this.mioNegozio.entra(this);
            if(tagliato){
                // ho ottenuto il taglio
                // minulo la ricrescita
                System.out.println(super.getName()+" ha tagliato i capelli");
                try{
                    Thread.sleep(35);
                }catch(InterruptedException e){
                    System.out.println(e);
                }
            }else{
                // non c'era posot per il taglio
                i--;
                // simulo il tempo necessario ad andare al bar
                // a prendere un caffe'
                try{
                    Thread.sleep(10);
                }catch(InterruptedException e){
                    System.out.println(e);
                } 
            }
        }// fine for()
        
        System.out.println("Il thread "+super.getName()+" termina");
       
    }// fine metodo run()
    
}// fine classe
