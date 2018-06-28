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
public class CSCAN extends Metodos{
    private boolean direccion; //Variable que indica la direccion del brazo, TRUE=Derecha FALSE=Izquierda 
    
    public CSCAN(List PNS, int PI, boolean direccion) {
        super(PNS, PI);
        this.direccion = direccion;
    }
    
    @Override
    public void run(){
        //Se toma el tiempo en que se inicio la simulacion 
        double TI = (new Double(System.currentTimeMillis())).doubleValue()/1000;
        while(true){
            //Se verifica si existen peticiones pendientes 
            if (this.PNS.get(0) != null){
                //Se verifica a cual lado se movera el brazo 
                if(direccion){
                    recorridoP();
                    if (this.PI == 3999){
                        this.PI = 0;
                    }
                } else {
                    recorridoN();
                    if (this.PI == 0){
                        this.PI = 3999;
                    }
                }
                //Se toma el tiempo en que termina una iteracion 
                double TFI = (new Double(System.currentTimeMillis())).doubleValue()/1000;
                //Se actualiza el tiempo empleado en la simulacion
                this.TTE = this.TTE + (TFI-TI);
            } else {
                synchronized(this){
                    pausa();
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
            for (int j=0; j<this.PNS.size(); j++){
                if (this.PNS.get(j).getPista() == i){
                    try {
                        //Se calcula el tiempo que le tomo encontrarla 
                        this.TRP = 1000/(i-this.PI);
                        //Transferencia
                        sleep((new Double(this.PNS.get(j).getTT()).longValue())*1000);
                        //Se acumula el tiempo de transferencia de la peticion en el tiempo de transferencia total 
                        this.TTT = this.TTT + this.PNS.get(j).getTT();
                        //Cambio de lista
                        this.PS.add(this.PNS.remove(j)); 
                        //Aumentar el contador de peticiones 
                        this.NPS ++;
                        //Aumentar el numero de pistas recorridas
                        this.NP = this.NP + (i-this.PI);
                        //Calcular el promedio de pistas recorridas 
                        this.PPR = this.NP/this.NPS;
                        //La pista actual se convierte en la pista inicial
                        this.PI = i;  
                        break;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FIFO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    } 
    
       public void recorridoN (){
        for (int i=this.PI; i>-1; i--){
            for (int j=0; j<this.PNS.size(); j++){
                if (this.PNS.get(j).getPista() == i){
                    try {
                        //Se calcula el tiempo que le tomo encontrarla 
                        this.TRP = 1000/(this.PI-i);
                        //Transferencia
                        sleep((new Double(this.PNS.get(j).getTT()).longValue())*1000);
                        //Se acumula el tiempo de transferencia de la peticion en el tiempo de transferencia total 
                        this.TTT = this.TTT + this.PNS.get(j).getTT();
                        //Cambio de lista
                        this.PS.add(this.PNS.remove(j)); 
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
    
    
}
