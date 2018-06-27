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
public abstract class Metodos extends Thread{
    int NPRe=0; //Numero de peticiones realizadas 
    int NPps=0; //Numero de peticiones por satisfacer 
    int NPS=0; //Numero de peticiones satisfechas
    List<Peticion> PNS; // Lista de peticiones a satisfacer
    List<Peticion> PS= new ArrayList<Peticion>();  // Lista de peticiones satisfechas
    int NP;  // Numero de pistas recorridas 
    float PPR; // Promedio de pistas recorridas por cadapeticion 
    double TRP; //Tiempo empleado recorriendo pistas 
    double TTT; //Tiempo de transferencia entre todas las peticiones 
    double TTE; // Tiempo total empleado en la simulacion
    int PI; // Pista en la que inicia el brazo
    boolean pausa = false; //Indicador de no quedan mas peticiones a servir y el metodo debe pausarse 

    public Metodos(List<Peticion> PNS, int PI) {
        this.PNS = PNS;
        this.PI = PI;
    }
    //------------------------------------------------------      Metodos       ----------------------------------------------------------//
    //Este metodo enlista la peticion entrante de ultima en la lista de peticiones a satisfacer y aumenta en 1 el numero de peticiones a satisfacer y el numero de peticiones realizadas
    public void agregarP (Peticion a){
        this.PNS.add(a);
        this.NPps ++;
        this.NPRe ++;
        if (this.pausa){
            this.pausa = false;
        }
    }
    
    public synchronized void pausa(){
        //Se avisa que no hay peticiones pendientes 
        this.pausa=true;
    }
    
    public synchronized void reanudar(){
        //Se avisa que ya hay peticiones pendientes
        this.pausa=false;
        notify();
    }
    
    //------------------------------------------------------ Seters and Getters----------------------------------------------------------//

    public int getNPRe() {
        return NPRe;
    }

    public void setNPRe(int NPRe) {
        this.NPRe = NPRe;
    }

    public int getNPps() {
        return NPps;
    }

    public void setNPps(int NPps) {
        this.NPps = NPps;
    }

    public int getNPS() {
        return NPS;
    }

    public void setNPS(int NPS) {
        this.NPS = NPS;
    }

    public List<Peticion> getPNS() {
        return PNS;
    }

    public void setPNS(List<Peticion> PNS) {
        this.PNS = PNS;
    }

    public List<Peticion> getPS() {
        return PS;
    }

    public void setPS(List<Peticion> PS) {
        this.PS = PS;
    }

    public int getNP() {
        return NP;
    }

    public void setNP(int NP) {
        this.NP = NP;
    }

    public float getPPR() {
        return PPR;
    }

    public void setPPR(float PPR) {
        this.PPR = PPR;
    }

    public double getTRP() {
        return TRP;
    }

    public void setTRP(double TRP) {
        this.TRP = TRP;
    }

    public double getTTT() {
        return TTT;
    }

    public void setTTT(double TTT) {
        this.TTT = TTT;
    }

    public double getTTE() {
        return TTE;
    }

    public void setTTE(double TTE) {
        this.TTE = TTE;
    }
    
    
    
}
