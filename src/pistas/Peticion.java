/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pistas;

import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Arturo Blanco
 */
public class Peticion {
    private double TT; //Tiempo de transferencia 
    private int pista; //Pista asignada 

    public Peticion(int pista) {
        this.pista = pista;
       
        Random e = new Random(System.currentTimeMillis());
        this.TT = 0.1+(0.1+1)*e.nextDouble();
    }
    
    //---------------------------------------- Setters and Getters ---------------------------------------------//

    public double getTT() {
        return TT;
    }

    public void setTT(double TT) {
        this.TT = TT;
    }

    public int getPista() {
        return pista;
    }

    public void setPista(int pista) {
        this.pista = pista;
    }
    
    
    
    
}
