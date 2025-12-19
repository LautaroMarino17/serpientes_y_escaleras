package ar.edu.unlu.game.Modelo.Clases;

import java.io.Serializable;

public class Jugador implements Serializable {

    // =========================
    // Atributos
    // =========================
    private String alias;
    private String contrasena;
    private int partidasGanadas;
    private int posicion;

    // =========================
    // Constructor
    // =========================
    public Jugador(String alias, String contrasena) {
        this.alias = alias;
        this.contrasena = contrasena;
        this.partidasGanadas = 0;
        this.posicion = 1;
    }

    // =========================
    // Getters / Setters
    // =========================
    public String getAlias() {
        return alias;
    }

    public String getContrasena() {
        return contrasena;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    // =========================
    // Lógica del jugador
    // =========================
    public boolean iniciarSesion(String alias, String contrasena) {
        return this.alias.equals(alias)
                && this.contrasena.equals(contrasena);
    }

    public void sumarVictoria() {
        this.partidasGanadas++;
    }
}
