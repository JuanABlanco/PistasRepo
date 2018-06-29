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
static public FabricaMetodos fabricametodos;
static public Metodos metodos;

        
        
    public static void main(String[] args) {
        interfaz.setVisible(true);
        interfaz.setDefaultCloseOperation(interfaz.EXIT_ON_CLOSE);
        interfaz.setResizable(false);
        fabricametodos = new FabricaMetodos(interfaz);
        int[] vectorPeticiones = new int[4000];
        for (int i = 0; i < vectorPeticiones.length ;i++){
            vectorPeticiones[i] = 0;
        }
        int[] vectorSatisfechas = new int[4000];
        for (int i = 0; i < vectorSatisfechas.length ;i++){
            vectorPeticiones[i] = 0;
        }
        fabricametodos.setListaVector(vectorPeticiones);
        metodos = new Metodos(fabricametodos.getL(), vectorPeticiones, fabricametodos.getPI()) {} ;
        
        
    }
}
