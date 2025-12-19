package ar.edu.unlu.game.Vista;

public interface IVista {

    // =========================
    // Ciclo de vida de la vista
    // =========================
    void setControlador(Object controlador);
    void iniciar();

    // =========================
    // Mensajes al jugador
    // =========================
    void mostrarMensaje(String mensaje, int delay);

    // =========================
    // Navegación / Pantallas
    // =========================
    void mostrarPanelInicio();
    void mostrarPanelJuego();

    void mostrarTablero(String descripcionTablero);
    void actualizarPosicionJugador(int posicion);
}
