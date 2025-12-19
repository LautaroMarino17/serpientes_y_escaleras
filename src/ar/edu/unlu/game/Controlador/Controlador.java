package ar.edu.unlu.game.Controlador;

// Imports del modelo
import ar.edu.unlu.game.Modelo.Clases.Jugador;
import ar.edu.unlu.game.Modelo.Enums.Eventos;
import ar.edu.unlu.game.Modelo.Interfaces.IJuego;

// Imports de vista
import ar.edu.unlu.game.Vista.IVista;

// Imports RMI
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

// Imports Java
import java.rmi.RemoteException;
import java.util.List;

import static ar.edu.unlu.game.Modelo.Enums.Fase.SIN_COMENZAR;

public class Controlador implements IControladorRemoto {

    // =========================
    // Atributos
    // =========================
    private String miAlias;
    private IVista vista;
    private IJuego juego;

    // =========================
    // Constructor
    // =========================
    public Controlador(IVista vista) {
        this.vista = vista;
    }

    // =========================
    // Registro & Login
    // =========================
    public void registrarse(String alias, String contrasena) {
        try {
            boolean ok = juego.registrarNuevoJugador(alias, contrasena);

            if (ok) {
                miAlias = alias;
                vista.mostrarMensaje(
                        "Registro exitoso. Bienvenido, " + alias + ".",
                        3000
                );
            } else {
                vista.mostrarMensaje(
                        "No se pudo registrar el usuario. El alias ya existe.",
                        3000
                );
            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void iniciarSesion(String alias, String contrasena) {
        try {
            boolean ok = juego.iniciarSesion(alias, contrasena);

            if (ok) {
                miAlias = alias;
                vista.mostrarMensaje(
                        "Sesión iniciada correctamente. Usuario: " + alias + ".",
                        5000
                );

                juego.ingresarALaSala(alias, contrasena);
                vista.mostrarMensaje(
                        "Ingresaste a la sala de juego.",
                        3000
                );
            } else {
                vista.mostrarMensaje(
                        "No se pudo iniciar sesión. Verificá alias y contraseña.",
                        3000
                );
                vista.mostrarPanelInicio();
            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void cerrarSesion(String alias, String contrasena) {
        boolean ok;

        try {
            ok = juego.quitarDeLaSala(alias, contrasena);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        if (ok) {
            miAlias = alias;
            vista.mostrarMensaje(
                    "Saliste de la sala. Iniciá sesión para volver a ingresar.",
                    3000
            );
        } else {
            vista.mostrarMensaje(
                    "No se pudo salir de la sala.",
                    3000
            );
        }
    }

    // =========================
    // Juego
    // =========================
    public void comenzarPartida() {
        try {
            juego.iniciarPartida();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void iniciarTurno() {
        try {
            if (!juego.getTurnoDeJugador().equals(miAlias)) {
                vista.mostrarMensaje(
                        "No es tu turno. Esperá al jugador correspondiente.",
                        1000
                );
                return;
            }

            juego.iniciarTurno(miAlias);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void continuarPartida() {
        try {
            juego.continuarPartida();

            if (juego.getFase() == SIN_COMENZAR) {
                comenzarPartida();
                return;
            } else {
                vista.mostrarMensaje(
                        "Partida restaurada correctamente.",
                        2000
                );
            }

            if (miAlias.equals(juego.getTurnoDeJugador())) {
                vista.mostrarPanelJuego();
                vista.mostrarMensaje(
                        "Es tu turno.\n",
                        1000
                );
            } else {
                vista.mostrarMensaje(
                        "Turno de " + juego.getTurnoDeJugador() + ". Esperá...",
                        1000
                );
            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void top5() {
        try {
            List<Jugador> top = juego.obtenerTop5();

            if (top == null || top.isEmpty()) {
                vista.mostrarMensaje(
                        "No hay estadísticas disponibles.",
                        3000
                );
                return;
            }

            String mensaje = "Top 5 jugadores:\n";
            for (Jugador j : top) {
                mensaje += j.getAlias()
                        + " - Partidas ganadas: "
                        + j.getPartidasGanadas()
                        +"|  " + "\n";
            }

            vista.mostrarMensaje(mensaje, 10000);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public String verTablero() {
        try {
            return juego.getTablero().getDescripcionTablero();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    // =========================
    // RMI
    // =========================
    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T t)
            throws RemoteException {
        this.juego = (IJuego) t;
    }

    @Override
    public void actualizar(IObservableRemoto obs, Object o)
            throws RemoteException {

        Eventos evento = (Eventos) o;

        switch (evento) {

            case COMIENZO -> {
                vista.mostrarMensaje(
                        "La partida ha comenzado.",
                        3000
                );
                vista.actualizarPosicionJugador(
                        juego.posicionJugador(miAlias)
                );

                String turnoActual = juego.getTurnoDeJugador();

                if (turnoActual.equals(miAlias)) {
                    vista.mostrarPanelJuego();
                    vista.mostrarMensaje(
                            "Comenzás vos. Es tu turno.\n",
                            1000
                    );
                } else {
                    vista.mostrarMensaje(
                            "Esperando el turno correspondiente.",
                            3000
                    );
                }
            }

            case CAMBIOTURNO -> {
                String jugador = juego.getTurnoDeJugador();

                if (jugador.equals(miAlias)) {
                    vista.mostrarPanelJuego();
                    vista.mostrarMensaje(
                            "Es tu turno.\n",
                            1000
                    );
                } else {
                    vista.mostrarMensaje(
                            "Turno de " + jugador + ". Esperá...",
                            1000
                    );
                }
            }

            case AVANCE -> {
                if (juego.getTurnoDeJugador().equals(miAlias)) {
                    vista.mostrarMensaje(
                            "Avanzás " + juego.getUltimoAvance()
                                    + " casilla(s). Posición actual: "
                                    + juego.posicionJugador(miAlias) + ".\n",
                            3000
                    );

                    vista.actualizarPosicionJugador(
                            juego.posicionJugador(miAlias)
                    );
                }
            }

            case SACOSEIS -> {
                if (juego.getTurnoDeJugador().equals(miAlias)) {
                    vista.mostrarMensaje(
                            "Sacaste 6. Tenés un turno extra.\n",
                            2000
                    );
                }
            }

            case ESCALERA -> {
                if (miAlias.equals(juego.getTurnoDeJugador())) {
                    vista.mostrarMensaje(
                            "Escalera encontrada. Avanzás hasta la casilla "
                                    + juego.getCasillaDestino() + ".\n",
                            7000
                    );
                    vista.actualizarPosicionJugador(
                            juego.posicionJugador(miAlias)
                    );
                }
            }

            case SERPIENTE -> {
                if (miAlias.equals(juego.getTurnoDeJugador())) {
                    vista.mostrarMensaje(
                            "Serpiente encontrada. Retrocedés hasta la casilla "
                                    + juego.getCasillaDestino() + ".\n",
                            7000
                    );
                    vista.actualizarPosicionJugador(
                            juego.posicionJugador(miAlias)
                    );
                }
            }

            case SEPASO -> {
                if (miAlias.equals(juego.getTurnoDeJugador())) {
                    vista.mostrarMensaje(
                            "Te pasaste de la casilla 100. Retrocedés. Posición actual: "
                                    + juego.posicionJugador(miAlias) + ".\n",
                            7000
                    );
                }
            }

            case TRESSEISSEGUIDOS -> {
                if (miAlias.equals(juego.getTurnoDeJugador())) {
                    vista.mostrarMensaje(
                            "Tres seis consecutivos. Perdés el turno.\n",
                            5000
                    );
                }
            }


            case GANADOR -> {
                String ganador = juego.consultarGanador();
                vista.mostrarMensaje(
                        "Fin de la partida. Ganador: " + ganador + ".",
                        3000
                );

                vista.mostrarPanelInicio();
                vista.actualizarPosicionJugador(
                        juego.posicionJugador(miAlias)
                );
            }

            default -> vista.mostrarMensaje(
                    "Se produjo un evento desconocido.",
                    2000
            );
        }
    }

}
