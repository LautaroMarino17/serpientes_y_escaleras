package ar.edu.unlu.game;

import ar.edu.unlu.game.Controlador.ControladorConsola;
import ar.edu.unlu.game.Model.Juego;
import ar.edu.unlu.game.Vista.VistaConsola;

public class App {
    public static void main(String[] args) {
        // Crear modelo
        Juego modelo = new Juego();

        // Crear vista
        VistaConsola vista = new VistaConsola();

        // Crear controlador conectando modelo y vista
        ControladorConsola controlador = new ControladorConsola(modelo, vista);

        // Iniciar la aplicación
        controlador.iniciar();
    }
}
