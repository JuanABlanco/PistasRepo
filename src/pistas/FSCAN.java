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
public class FSCAN extends Metodos{
    List<Peticion> PEP= new ArrayList<Peticion>();//Lista de peticiones que se estan sirviendo 
    private boolean direccion; //Variable que indica la direccion del brazo, TRUE=Derecha FALSE=Izquierda
    PistasInterfaz interfaz = new PistasInterfaz(); //ESTO habra que borrarlo??
    
    public FSCAN(List PNS,int[] PNSv, int PI, boolean direccion, PistasInterfaz interfaz) {
        super(PNS, PNSv, PI);
        this.direccion = direccion;
        this.interfaz = interfaz;
    }
    
    //---------------------------------------------------- Metodos ------------------------------//
    @Override
    public void run (){
        Peticion p [] = new Peticion[this.PNS.size()];
        p = this.PNS.toArray(p);
        System.out.println(p.length);
        for(int i=0; i<p.length; i++){
            System.out.println(p[i]);
        }
        //Verificamos que las listas pendiente y activa no esten vacias 
        if(!(this.PEP.isEmpty())){
            listar(((ArrayList<Peticion>)this.PNS), interfaz.getListaPorSatisfacerFSCAN());
            listar(((ArrayList<Peticion>)this.PS), interfaz.getListaSatisfechasFSCAN());
            listar(((ArrayList<Peticion>)this.PEP), interfaz.getColaSatisfaciendoFSCAN());
            //Se verifica a que direccion se comienza a recorrer el disco 
            if(this.direccion){
                //Recorrido positivo
                recorridoP();
            }else{
                //Recorrido negativo 
                recorridoN();
            }
            // se actualizan los datos en la interfaz
            interfaz.setPRealizadasFSCAN(Integer.toString(this.NPRe));
            interfaz.setPRecorridasFSCAN(Integer.toString(this.NP));
            interfaz.setPSatisfacerFSCAN(Integer.toString(this.NPps));
            interfaz.setPSatisfechasFSCAN(Integer.toString(this.NPS));
            interfaz.setPromedioRSFSCAN(Float.toString(this.PPR)); //promedio de recorridas, e
            interfaz.setTiempoRecorriendoFSCAN(Double.toString(this.TRP));
            interfaz.setTTransferenciaFSCAN(Double.toString(this.TTT));
            interfaz.setTTotalFSCAN(Double.toString(this.TTE));
            listar(((ArrayList<Peticion>)this.PNS), interfaz.getListaPorSatisfacerFSCAN());
            listar(((ArrayList<Peticion>)this.PS), interfaz.getListaSatisfechasFSCAN());
            listar(((ArrayList<Peticion>)this.PEP), interfaz.getColaSatisfaciendoFSCAN());
        } else if(this.PEP.isEmpty() && !(this.PNS.isEmpty())){
            descarga();
            listar(((ArrayList<Peticion>)this.PNS), interfaz.getListaPorSatisfacerFSCAN());
            listar(((ArrayList<Peticion>)this.PS), interfaz.getListaSatisfechasFSCAN());
            listar(((ArrayList<Peticion>)this.PEP), interfaz.getColaSatisfaciendoFSCAN());
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
    
    public void recorridoP (){
        for (int i=this.PI; i<4000; i++){
            for (int j=0; j<this.PEP.size(); j++){
                if (this.PEP.get(j).getPista() == i){
                    try {
                        //Se calcula el tiempo que le tomo encontrarla 
                        this.TRP = 1000/(i-this.PI);
                        //Transferencia
                        sleep((new Double(this.PEP.get(j).getTT()).longValue())*1000);
                        //Se acumula el tiempo de transferencia de la peticion en el tiempo de transferencia total 
                        this.TTT = this.TTT + this.PEP.get(j).getTT();
                        //Cambio de lista
                        this.PS.add(this.PEP.remove(j)); 
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
            for (int j=0; j<this.PEP.size(); j++){
                if (this.PEP.get(j).getPista() == i){
                    try {
                        //Se calcula el tiempo que le tomo encontrarla 
                        this.TRP = 1000/(this.PI-i);
                        //Transferencia
                        sleep((new Double(this.PEP.get(j).getTT()).longValue())*1000);
                        //Se acumula el tiempo de transferencia de la peticion en el tiempo de transferencia total 
                        this.TTT = this.TTT + this.PEP.get(j).getTT();
                        //Cambio de lista
                        this.PS.add(this.PEP.remove(j)); 
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
    
    //Llenamos la lista a ser recorrida 
    public void descarga(){
        //Declaramos un arreglo auxiliar para poder pasar los objetos de la lista PNS a la PEP con un for
        Peticion[] lista = new Peticion[this.PNS.size()];
        lista = this.PNS.toArray(lista);
        //Vaciamos PNS para que se enlisten nuevas peticiones 
        this.PNS.clear();
        //Iteramos sobre el arreglo para vaciar su contenido en PEP 
        for (int i=0; i<lista.length; i++){
            this.PEP.add(lista[i]);
        }
        
    }
    //---------------------------------------------------- Setters and Getters ------------------------------//

    public List<Peticion> getPEP() {
        return PEP;
    }

    public void setPEP(List<Peticion> PEP) {
        this.PEP = PEP;
    }
    
}
