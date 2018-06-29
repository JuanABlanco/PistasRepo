/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pistas;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan Arturo Blanco
 */
public class FabricaMetodos {
    private List<Peticion> l = new ArrayList<Peticion>();
    private int PI;
    private boolean direccion;
    private PistasInterfaz interfaz = new PistasInterfaz();
    
    public FabricaMetodos(PistasInterfaz interfaz) {
        this.interfaz = interfaz; // ESTO no lo he pasado a los metodos
    }
    
    public Metodos getMetodo(String s){
        if (s.equalsIgnoreCase("FIFO")){
            return new FIFO(l, PI, interfaz);
        } else if (s.equalsIgnoreCase("SSTF")){
            return new SSTF(l , PI, interfaz);
        } else if (s.equalsIgnoreCase("SCANL")){
            return new SCANL(l, PI, direccion, interfaz);
        } else if (s.equalsIgnoreCase("CSCAN")){
            return new CSCAN(l, PI, direccion, interfaz);
        } else {
            return new FSCAN(l, PI, direccion, interfaz);
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
    
    
}
