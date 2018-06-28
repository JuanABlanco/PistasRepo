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
    private boolean direccion; //Variable que indica la direccion del brazo 
    
    public SCANL(List PNS, int PI, boolean direccion) {
        super(PNS, PI);
        this.direccion = direccion;
    }
    
}
