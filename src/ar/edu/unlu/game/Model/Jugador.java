package ar.edu.unlu.game.Model;

public class Jugador {
    private String nombre;
    private int posicion;
    
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.posicion = 0;
    }
    
    public int lanzarDado(Dado d) {
        int avance = d.lanzar();
        return avance;
    }
    public int avanzar(int avance) {
    	this.posicion += avance;
    	if(this.posicion > 100) {
        	this.posicion -= avance;
        	System.out.println(this.nombre + " tiró el dado y no avanzó porque se paso del total de casillas"+ " Posicion actual: " + this.posicion);
        }else {
        System.out.println(this.nombre + " tiró el dado y avanzó " + avance + " casillas.");}
        return getPosicion();
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public int getPosicion() {
        return posicion;
    }
    
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    
    @Override
    public String toString() {
        return nombre + " está en la casilla " + posicion +"\n";
    }
}
