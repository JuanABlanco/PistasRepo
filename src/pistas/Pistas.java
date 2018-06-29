/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pistas;

/**
 *
 * @author Juan Arturo Blanco
 */
public class Pistas {
static public PistasInterfaz interfaz = new PistasInterfaz();
static public FabricaMetodos fabricametodos = new FabricaMetodos(interfaz);
static public Metodos metodos = new Metodos(fabricametodos.getL(), fabricametodos.getPI()) {};

        
        
    public static void main(String[] args) {
        interfaz.setVisible(true);
        interfaz.setDefaultCloseOperation(interfaz.EXIT_ON_CLOSE);
        interfaz.setResizable(false);
        
        
    }
}
