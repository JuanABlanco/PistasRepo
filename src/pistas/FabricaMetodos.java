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
public class FabricaMetodos {
    private List<Peticion> l = new ArrayList<Peticion>();
    private int listaVector[];
    private int PI;
    private boolean direccion;
    private PistasInterfaz interfaz = new PistasInterfaz();
    
    public FabricaMetodos(PistasInterfaz interfaz) {
        this.interfaz = interfaz; // ESTO no lo he pasado a los metodos
    }
    
    public Metodos getMetodo(String s){
        List<Peticion> clone = new ArrayList<Peticion>(l);
        List<Peticion> clone1 = new ArrayList<Peticion>(l);
        List<Peticion> clone2 = new ArrayList<Peticion>(l);
        List<Peticion> clone3 = new ArrayList<Peticion>(l);
        List<Peticion> clone4 = new ArrayList<Peticion>(l);
        
        int listvector [] = new int[listaVector.length];
        int listvector1 [] = new int[listaVector.length];
        int listvector2 [] = new int[listaVector.length];
        int listvector3 [] = new int[listaVector.length];
        int listvector4 [] = new int[listaVector.length];
        
        for (int i=0; i<listaVector.length;i++){
            listvector[1]=listaVector[i];
            listvector1[1]=listaVector[i];
            listvector2[1]=listaVector[i];
            listvector3[1]=listaVector[i];
            listvector4[1]=listaVector[i];
        }
        if (s.equalsIgnoreCase("FIFO")){
            return new FIFO(clone,listvector, PI, interfaz);
        } else if (s.equalsIgnoreCase("SSTF")){
            return new SSTF(clone1 ,listvector1, PI, interfaz);
        } else if (s.equalsIgnoreCase("SCANL")){
            return new SCANL(clone2,listvector2, PI, direccion, interfaz);
        } else if (s.equalsIgnoreCase("CSCAN")){
            return new CSCAN(clone3,listvector3, PI, direccion, interfaz);
        } else {
            return new FSCAN(clone4,listvector4, PI, direccion, interfaz);
        }
    }

    public int getPI() {
        return PI;
    }

    public void setPI(int PI) {
        this.PI = PI;
    }

    public List<Peticion> getL() {
        return l;
    }

    public void setL(List<Peticion> l) {
        this.l = l;
    }

    public boolean isDireccion() {
        return direccion;
    }

    public void setDireccion(boolean direccion) {
        this.direccion = direccion;
    }

    public void setListaVector(int[] listaVector) {
        this.listaVector = listaVector;
    }
    
    
}
