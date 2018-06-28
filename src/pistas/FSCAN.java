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
    
    public FSCAN(List PNS, int PI, boolean direccion) {
        super(PNS, PI);
        this.direccion = direccion;
    }
    
    //---------------------------------------------------- Metodos ------------------------------//
    @Override
    public void run (){
        //Verificamos que las listas pendiente y activa no esten vacias 
        if(!(this.PEP.isEmpty()) && !(this.PNS.isEmpty())){
            //Se verifica a que direccion se comienza a recorrer el disco 
            if(this.direccion){
                //Recorrido positivo
            }else{
               //Recorrido negativo 
            }
        } else if(this.PEP.isEmpty() && !(this.PNS.isEmpty())){
            descarga();
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
