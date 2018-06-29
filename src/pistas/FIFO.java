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
    PistasInterfaz interfaz = new PistasInterfaz(); //ESTO habra que borrarlo??
    public FIFO(List PNS, int PI, PistasInterfaz interfaz) {
        super(PNS, PI);
        this.interfaz = interfaz;
    }
    
    @Override
    public void run(){
        //Se toma el tiempo en que se inicio la simulacion 
        double TI = (new Double(System.currentTimeMillis())).doubleValue()/1000;
        while(true){
            Peticion p [] = new Peticion[this.PNS.size()];
            p = this.PNS.toArray(p);
            System.out.println(p.length);
            for(int i=0; i<p.length; i++){
                System.out.println(p[i]);
            }
            //Se verifica si existen peticiones pendientes 
            if (this.PNS.size() != 0){
                //Se verifica a cual lado se movera el brazo 
                if(this.PNS.get(0).getPista()>=this.PI){
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
                // se actualizan los datos en la interfaz
                interfaz.setPRealizadasFIFO(Integer.toString(this.NPRe));
                interfaz.setPRecorridasFIFO(Integer.toString(this.NP));
                interfaz.setPSatisfacerFIFO(Integer.toString(this.NPps));
                interfaz.setPSatisfechasFIFO(Integer.toString(this.NPS));
                interfaz.setPromedioRSFIFO(Float.toString(this.PPR)); //promedio de recorridas, e
                interfaz.setTiempoRecorriendoFIFO(Double.toString(this.TRP));
                interfaz.setTTransferenciaFIFO(Double.toString(this.TTT));
                interfaz.setTTotalFIFO(Double.toString(this.TTE));
                listar(((ArrayList<Peticion>)this.PNS), interfaz.getListaPorSatisfacerFIFO());
                listar(((ArrayList<Peticion>)this.PS), interfaz.getListaSatisfechasFIFO());
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
            if (this.PNS.get(0).getPista() == i){
                try {
                    //Se calcula el tiempo que le tomo encontrarla 
                    this.TRP = 1000/(i-this.PI);
                    //Transferencia
                    sleep((new Double(this.PNS.get(0).getTT()).longValue())*1000);
                    //Se acumula el tiempo de transferencia de la peticion en el tiempo de transferencia total 
                    this.TTT = this.TTT + this.PNS.get(0).getTT();
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
