/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pistas;

import static java.lang.Thread.sleep;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Arturo Blanco
 */
public class SCANL extends Metodos {
    private boolean direccion; //Variable que indica la direccion del brazo, TRUE=Derecha FALSE=Izquierda 
    private int[] extremos = new int[2];// La posicion 0 corresponde al limite inferior y la psicion 1 al superior
    
    public SCANL(List PNS, int PI, boolean direccion) {
        super(PNS, PI);
        this.direccion = direccion;
    }
    
    //--------------------------------------------------------- Metodos -------------------------------------------------------------//
    
    @Override
    public void run(){
        //Se toma el tiempo en que se inicio la simulacion 
        double TI = (new Double(System.currentTimeMillis())).doubleValue()/1000;
        while(true){
            //Se buscan los extremos
            extremar();
            //Se verifica si existen peticiones pendientes 
            if (this.PNS.get(0) != null){
                //Se verifica a cual lado se movera el brazo 
                if(this.direccion){
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
    
    //Metodo encargado de buscar las peticiones extremas para que el brazo no tenga que leer toda la memoria 
    public void extremar(){
        // Nos aseguramos que los extremos iniciales no sean alcanzables por el brazo y tengan valores que siempre seran sustituidos en la comparacion
        this.extremos[0] = 4000;
        this.extremos[1] = -1;
        //Nos aseguramos de obtener un limite de iteraciones sin que la introduccion de nuevos requerimientos puedan causar un fallo 
        int limite = this.PNS.size();
        //Buscamos los limites
        for(int i=0; i<limite; i++){
            if(this.PNS.get(i).getPista() > this.extremos[1]){
                this.extremos[1] = this.PNS.get(i).getPista();
            }
            if(this.PNS.get(i).getPista() < this.extremos[0]){
                this.extremos[0] = this.PNS.get(i).getPista();
            }
        } 
    }
    
    public void recorridoP (){
        for (int i=this.PI; i<this.extremos[1]; i++){
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
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FIFO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        this.direccion = !(this.direccion);
    } 
    
       public void recorridoN (){
        for (int i=this.PI; i>this.extremos[0]; i--){
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
        this.direccion = !(this.direccion);
    }
       
}
