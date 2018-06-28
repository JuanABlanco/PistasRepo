/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pistas;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Arturo Blanco
 */
public class SSTF extends Metodos{
    
    public SSTF(List PNS, int PI) {
        super(PNS, PI);
    }
//pi pista en la que inicia el brazo
    @Override
    public void run() {
        //tiempo en que inicia la simulacion
        double TI = (new Double(System.currentTimeMillis())).doubleValue()/1000; 
        while(true){
            //Se verifica si existen peticiones pendientes 
            if (this.PNS.get(1) != null){
                //Se verifica a cual lado se movera el brazo 
                // a traves de los metodos 
                //Se toma el tiempo en que termina una iteracion 
                double TFI = (new Double(System.currentTimeMillis())).doubleValue()/1000;
                //Se actualiza el tiempo empleado en la simulacion
                this.TTE = this.TTE + (TFI-TI);
            } else {
                synchronized(this){
                    
                    if (pausa)
                        try {
                            this.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FIFO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        
                }
            }
        }
    }
    
        
    public int proximaCercanaCreciente(){
        List<Peticion> aux = this.PNS;
        int distancia = 0;
        for (int i=PI; i<4000; i++){
            if (aux.get(1).getPista() == i){
                distancia++;
                break;
            }
            distancia++;
        }
        return distancia;
    } 
    public int proximaCercanaDecreciente(){
        List<Peticion> aux = this.PNS;
        int distancia = 0;
        for (int i=PI; i>-1; i--){
            if (aux.get(1).getPista() == i){
                distancia++;
                break;
            }
            distancia++;
        }
        return distancia;
    }
        
    public void recorridoP(){
        //Se toma el tiempo en que se inicia a recorrer las pistas
        double TIR = (new Double(System.currentTimeMillis())).doubleValue()/1000;
        
    }
    
    public void recorridoN(){
        //Se toma el tiempo en que se inicia a recorrer las pistas
        double TIR = (new Double(System.currentTimeMillis())).doubleValue()/1000;
        
    }
    
}
