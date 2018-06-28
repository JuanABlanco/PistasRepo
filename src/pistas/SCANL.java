/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pistas;

import java.util.List;

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
        
    }
    
    //Metodo encargado de buscar las peticiones extremas para que el brazo no tenga que leer toda la memoria 
    public void extremar(){
        // Nos aseguramos que los extremos iniciales no sean alcanzables por el brazo y tengan valores que siempre seran sustituidos en la comparacion
        this.extremos[0] = 4000;
        this.extremos[1] = -1;
        //Nos aseguramos de obtener un limite de iteraciones sin que la introduccion de nuevos requerimientos puedan causar un fallo 
        int limite = this.PNS.size();
        
        
        
    }
    
}
