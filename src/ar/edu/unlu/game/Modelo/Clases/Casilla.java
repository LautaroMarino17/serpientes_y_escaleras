package ar.edu.unlu.game.Modelo.Clases;

import java.io.Serializable;

public class Casilla implements Serializable{
    private static final long serialVersionUID = 1L;
    protected int posicion;

    public Casilla(int posicion) {
        this.posicion = posicion;
    }

    public int getPosicion() {
        return this.posicion;
    }

}
