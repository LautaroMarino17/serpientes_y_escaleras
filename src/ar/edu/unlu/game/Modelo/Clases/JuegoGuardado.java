package ar.edu.unlu.game.Modelo.Clases;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class JuegoGuardado implements Serializable {

    //-------------------------------------------------------------------------------------
    // Atributos
    //-------------------------------------------------------------------------------------

    private List<Jugador> logueados;
    private int turnoActual;
    private int seisConsecutivos;
    private boolean juegoTerminado;
    private Jugador ganador;
    private int casillaDestino;
    private int ultimoAvance;

    //-------------------------------------------------------------------------------------
    // Constructor
    //-------------------------------------------------------------------------------------

    public JuegoGuardado() {
        Juego juego = Juego.getInstancia();

        try {
            this.logueados = new ArrayList<>(juego.getLogueados());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        this.turnoActual = juego.getTurnoActual();
        this.seisConsecutivos = juego.getSeisConsecutivos();
        this.ganador = juego.getGanador();
        this.casillaDestino = juego.getCasillaDestino();
        this.ultimoAvance = juego.getUltimoAvance();


    }

    //-------------------------------------------------------------------------------------
    // Métodos
    //-------------------------------------------------------------------------------------

    public void restaurarEstado() {
        Juego juego = Juego.getInstancia();

        juego.setLogueados(new ArrayList<>(this.logueados));
        juego.setTurnoActual(this.turnoActual);
        juego.setSeisConsecutivos(this.seisConsecutivos);
        juego.setGanador(this.ganador);
        juego.setCasillaDestino(this.casillaDestino);
        juego.setUltimoAvance(this.ultimoAvance);
    }
}
