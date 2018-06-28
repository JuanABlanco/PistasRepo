/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pistas;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Juan Arturo Blanco
 */
public class FIFO extends Metodos {
    
    public FIFO(List PNS, int PI) {
        super(PNS, PI);
    }
    
    @Override
    public void run(){
        //Se toma el tiempo en que se inicio la simulacion 
        double TI = (new Double(System.currentTimeMillis())).doubleValue()/1000;
        while(true){
            //Se verifica si existen peticiones pendientes 
            if (this.PNS.get(0) != null){
                //Se verifica a cual lado se movera el brazo 
                if(this.PNS.get(1).getPista()>=this.PI){
                    //Se recorre el disco de forma creciente 
                    recorridoP();
                } else {
                    //Se recorre el disco de forma descendente 
                    recorridoN();
                }
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
    
    public void recorridoP (){
        for (int i=this.PI; i<4000; i++){
            if (this.PNS.get(0).getPista() == i){
                try {
                    //Se calcula el tiempo que le tomo encontrarla 
                    this.TRP = 1000/(i-this.PI);
                    //Transferencia
                    sleep((new Double(this.PNS.get(0).getTT()).longValue())*1000);
                    //Se acumula el tiempo de transferencia de la peticion en el tiempo de transferencia total 
                    this.TTT = this.TTT + this.PNS.get(1).getTT();
                    //Cambio de lista
                    this.PS.set(0, this.PNS.remove(0)); 
                    //Aumentar el contador de peticiones 
                    this.NPS ++;
                    //Aumentar el numero de pistas recorridas
                    this.NP = this.NP + (i-this.PI);
                    //Calcular el promedio de pistas recorridas 
                    this.PPR = this.NP/this.NPS;
                    //La pista actual se convierte en la pista inicial
                    this.PI = i;
                    //Se verifica la siguiente peticion
                    break;   
                } catch (InterruptedException ex) {
                    Logger.getLogger(FIFO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void recorridoN (){
        for (int i=this.PI; i>-1; i--){
            if (this.PNS.get(0).getPista() == i){
                try {
                    //Se calcula el tiempo que le tomo encontrarla 
                    this.TRP = 1000/(this.PI-i);
                    //Transferencia
                    sleep((new Double(this.PNS.get(0).getTT()).longValue())*1000);
                    //Se acumula el tiempo de transferencia de la peticion en el tiempo de transferencia total 
                    this.TTT = this.TTT + this.PNS.get(0).getTT();
                    //Cambio de lista
                    this.PS.set(0, this.PNS.remove(0)); 
                    //Aumentar el contador de peticiones 
                    this.NPS ++;
                    //Aumentar el numero de pistas recorridas
                    this.NP = this.NP + (this.PI-i);
                    //Calcular el promedio de pistas recorridas 
                    this.PPR = this.NP/this.NPS;
                    //La pista actual se convierte en la pista inicial
                    this.PI = i;
                    //Se verifica la siguiente peticion
                    break;   
                } catch (InterruptedException ex) {
                    Logger.getLogger(FIFO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
