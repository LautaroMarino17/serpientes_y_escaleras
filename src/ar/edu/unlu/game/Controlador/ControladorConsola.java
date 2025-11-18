package ar.edu.unlu.game.Controlador;

import ar.edu.unlu.game.Model.Juego;
import ar.edu.unlu.game.Model.Jugador;
import ar.edu.unlu.game.Observer.Observador;
import ar.edu.unlu.game.Vista.VistaConsola;

public class ControladorConsola implements Observador {
    private Juego modelo;
    private VistaConsola vista;

    public ControladorConsola(Juego modelo, VistaConsola vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.modelo.agregarObservador(this);
    }

    public void iniciar() {
        actualizar();

        while (true) {
            vista.mostrarMenu();
            int opcion = vista.obtenerOpcion();

            switch (opcion) {
                case 1:
                    jugar();
                    break;
                case 2:
                    agregarJugador();
                    break;
                case 0:
                    vista.mostrarMensaje("Hasta luego!");
                    return;
                default:
                    vista.mostrarMensaje("Opción no válida.");
                    break;
            }
        }
    }
  

    public void jugar() {
        if (modelo.getJugadores().isEmpty()) {
            vista.mostrarMensaje("No hay jugadores. Agregue al menos uno.");
            return;
        }

        vista.mostrarMensaje("¡Comienza el juego!");
        String rep = modelo.getTablero().getDescripcionTablero();
        vista.mostrarTablero(rep);
        vista.esperarEnter();   // ← esperar ENTER correctamente

        while (!modelo.isJuegoTerminado()) {

            // 1. Mostrar de quién es el turno
            Jugador actual = modelo.getJugadorActual();
            vista.mostrarTurno(actual.getNombre());

            vista.esperarEnter();  // ← para simular "tirar dado"

            // 2. El modelo procesa el turno
            modelo.iniciarTurno();

            // 3. Cuando el modelo cambia estado, llama a actualizar()
        }

        vista.mostrarGanador(modelo.getGanador().getNombre());
    }

    public void agregarJugador() {
        String nombre = vista.solicitarNombreJugador();
        this.modelo.agregarJugador(nombre);
    }

    @Override
    public void actualizar() {

        // Si no hay jugadores, no hay nada que actualizar
        if (modelo.getJugadores().isEmpty()) {
            return;
        }

        // Si el juego terminó, solo muestro al ganador
        if (modelo.isJuegoTerminado()) {
            vista.mostrarGanador(modelo.getGanador().getNombre());
            return;
        }
        Jugador actual = modelo.getJugadorActual();
        if  (actual.getPosicion()>0)
        { // Mostrar estado del jugador actual después de que el modelo procesó el turno
	     
	        vista.mostrarMensaje(
	            "Posición actual de " + actual.getNombre() + ": " + actual.getPosicion()
	        );}
    }

}
