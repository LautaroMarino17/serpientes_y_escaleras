package ar.edu.unlu.game.Modelo.Interfaces;

import ar.edu.unlu.game.Modelo.Clases.Jugador;
import ar.edu.unlu.game.Modelo.Clases.Tablero;
import ar.edu.unlu.game.Modelo.Enums.Fase;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.rmi.RemoteException;
import java.util.List;

public interface IJuego extends IObservableRemoto {

    // --------------------------------------------------
    // Gestión de jugadores
    // --------------------------------------------------

    boolean registrarNuevoJugador(String alias, String contrasena) throws RemoteException;

    boolean ingresarALaSala (String alias, String contrasena) throws RemoteException;

    boolean quitarDeLaSala(String alias, String contrasena)throws RemoteException;

    boolean iniciarSesion(String alias, String contrasena) throws RemoteException;

    // --------------------------------------------------
    // Flujo del juego
    // --------------------------------------------------

    void iniciarPartida() throws RemoteException;

    void iniciarTurno(String alias) throws RemoteException;

    String getTurnoDeJugador() throws RemoteException;

    int posicionJugador(String alias) throws RemoteException;

    // --------------------------------------------------
    // Estado del juego
    // --------------------------------------------------

    String consultarGanador() throws RemoteException;

    int getCasillaDestino() throws RemoteException;

    int getUltimoAvance() throws RemoteException;

    boolean isJuegoTerminado() throws RemoteException;

    // --------------------------------------------------
    // Guardado / restauración
    // --------------------------------------------------

    void guardarPartida() throws RemoteException;

    boolean continuarPartida() throws RemoteException;

    void reiniciar() throws RemoteException;

    List<Jugador> obtenerTop5() throws RemoteException;

    Tablero getTablero() throws RemoteException;

    List<Jugador> getJugadores()  throws RemoteException;

    List<Jugador> getLogueados() throws RemoteException;

    Fase getFase()  throws RemoteException;
}