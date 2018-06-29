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
public class CSCAN extends Metodos{
    private boolean direccion; //Variable que indica la direccion del brazo, TRUE=Derecha FALSE=Izquierda 
    PistasInterfaz interfaz = new PistasInterfaz(); //ESTO habra que borrarlo??
    
    public CSCAN(List PNS,int[] PNSv, int PI, boolean direccion, PistasInterfaz interfaz) {
        super(PNS,PNSv, PI);
        this.direccion = direccion;
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
            boolean pendientes = false;
            for (int i = 0; i < PNSv.length; i++){
                if (PNSv[i] == 1){
                    pendientes = true;
                    break;
                }
            }
            if (pendientes){
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
                // se actualizan los datos en la interfaz
                interfaz.setPRealizadasCSCAN(Integer.toString(this.NPRe));
                interfaz.setPRecorridasCSCAN(Integer.toString(this.NP));
                interfaz.setPSatisfacerCSCAN(Integer.toString(this.NPps));
                interfaz.setPSatisfechasCSCAN(Integer.toString(this.NPS));
                interfaz.setPromedioRSCSCAN(Float.toString(this.PPR)); //promedio de recorridas, e
                interfaz.setTiempoRecorriendoCSCAN(Double.toString(this.TRP));
                interfaz.setTTransferenciaCSCAN(Double.toString(this.TTT));
                interfaz.setTTotalCSCAN(Double.toString(this.TTE));
                listar(((ArrayList<Peticion>)this.PNS), interfaz.getListaPorSatisfacerCSCAN());
                listar(((ArrayList<Peticion>)this.PS), interfaz.getListaSatisfechasCSCAN());
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
        this.direccion = !(this.direccion);
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
        this.direccion = !(this.direccion);
    }
    
    
}
