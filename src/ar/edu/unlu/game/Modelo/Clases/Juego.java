package ar.edu.unlu.game.Modelo.Clases;

// Imports del modelo
import ar.edu.unlu.game.Modelo.Enums.Eventos;
import ar.edu.unlu.game.Modelo.Enums.Fase;
import ar.edu.unlu.game.Modelo.Interfaces.IJuego;

// Imports RMI
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

// Imports Java
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Juego extends ObservableRemoto
        implements IJuego, Serializable {

    // =========================
    // Constantes / Singleton
    // =========================
    private static final long serialVersionUID = 1L;
    private static Juego instancia;

    // =========================
    // Guardado
    // =========================
    private JuegoGuardado guardado;

    // =========================
    // Administrativos
    // =========================
    private List<Jugador> jugadores;
    private List<Jugador> logueados;

    // =========================
    // Estado del juego
    // =========================
    private Fase fase;
    private String aliasGanador;

    // =========================
    // Turnos
    // =========================
    private String turnoDeJugador;
    private int indiceJugadorActual;

    // =========================
    // Control de juego
    // =========================
    private int casillaDestino;
    private Dado dado;
    private int seisConsecutivos;
    private int ultimoAvance;

    // =========================
    // Tablero
    // =========================
    private Tablero tablero;

    // =========================
    // Constructor / Singleton
    // =========================
    private Juego() {
        this.fase = Fase.SIN_COMENZAR;
        this.tablero = new Tablero();
        this.dado = new Dado();
        this.jugadores = new ArrayList<>();
        this.logueados = new ArrayList<>();
        this.guardado = null;
        this.indiceJugadorActual = 0;
        this.turnoDeJugador = null;
        this.seisConsecutivos = 0;
        this.ultimoAvance = 0;
    }

    public static Juego getInstancia() {
        if (instancia == null) {
            instancia = new Juego();
        }
        return instancia;
    }

    public static void setInstancia(Juego nuevaInstancia) {
        instancia = nuevaInstancia;
    }

    // =========================
    // Utilidades
    // =========================
    public Jugador buscarJugador(String alias) {
        if (alias == null) return null;

        for (Jugador j : jugadores) {
            if (j.getAlias().equalsIgnoreCase(alias)) {
                return j;
            }
        }
        return null;
    }

    public Jugador buscarLogueados(String alias) {
        if (alias == null) return null;

        for (Jugador j : logueados) {
            if (j.getAlias().equalsIgnoreCase(alias)) {
                return j;
            }
        }
        return null;
    }

    private boolean aliasEsValido(String alias) {
        return buscarJugador(alias) != null;
    }

    // =========================
    // Turnos
    // =========================
    public void cambiarTurno() throws RemoteException {

        if (logueados.isEmpty()) {
            turnoDeJugador = null;
            return;
        }

        indiceJugadorActual =
                (indiceJugadorActual + 1) % logueados.size();

        turnoDeJugador =
                logueados.get(indiceJugadorActual).getAlias();

        seisConsecutivos = 0;

        notificarObservadores(Eventos.CAMBIOTURNO);
    }

    // =========================
    // IJuego
    // =========================
    @Override
    public boolean registrarNuevoJugador(String alias, String contrasena) {
        if (alias == null || alias.isBlank()) return false;
        if (buscarJugador(alias) != null) return false;

        jugadores.add(new Jugador(alias, contrasena));
        return true;
    }

    @Override
    public boolean ingresarALaSala(String alias, String contrasena)
            throws RemoteException {

        if (alias == null || alias.isBlank()) return false;

        Jugador jugador = buscarJugador(alias);
        if (jugador == null) return false;
        if (logueados.contains(jugador)) return false;

        logueados.add(jugador);
        return true;
    }

    @Override
    public boolean quitarDeLaSala(String alias)
            throws RemoteException {

        if (alias == null || alias.isBlank()) return false;
        if (buscarJugador(alias) == null) return false;

        logueados.remove(buscarLogueados(alias));
        return true;
    }

    @Override
    public boolean iniciarSesion(String alias, String contrasena) {
        Jugador j = buscarJugador(alias);
        return j != null && j.iniciarSesion(alias, contrasena);
    }

    @Override
    public void iniciarPartida() throws RemoteException {

        if (fase == Fase.SIN_COMENZAR && !logueados.isEmpty()) {
            fase = Fase.EN_CURSO;

            indiceJugadorActual =
                    new Random().nextInt(logueados.size());

            turnoDeJugador =
                    logueados.get(indiceJugadorActual).getAlias();

            notificarObservadores(Eventos.COMIENZO);
        }
    }

    @Override
    public void iniciarTurno(String alias) throws RemoteException {

        // Validaciones
        if (!aliasEsValido(alias)) return;
        if (!alias.equalsIgnoreCase(turnoDeJugador)) return;

        Jugador jugador = buscarJugador(alias);

        int avance = dado.lanzar();
        ultimoAvance = avance;

        seisConsecutivos =
                (avance == 6) ? seisConsecutivos + 1 : 0;

        // Tres seis seguidos
        if (seisConsecutivos >= 3) {
            seisConsecutivos = 0;
            notificarObservadores(Eventos.TRESSEISSEGUIDOS);
            cambiarTurno();
            return;
        }

        // Cálculo de posición
        int nuevaPos = jugador.getPosicion() + avance;

        if (nuevaPos > 100) {
            notificarObservadores(Eventos.AVANCE);
            notificarObservadores(Eventos.SEPASO);

            if (avance != 6) cambiarTurno();
            return;
        }

        jugador.setPosicion(nuevaPos);

        // Ganador
        if (nuevaPos == 100) {
            jugador.sumarVictoria();
            aliasGanador = jugador.getAlias();
            fase = Fase.FINALIZADO;

            notificarObservadores(Eventos.AVANCE);
            notificarObservadores(Eventos.GANADOR);

            reiniciar();
            return;
        }

        // Serpiente / Escalera
        Casilla casilla = tablero.getCasilla(nuevaPos);

        if (casilla instanceof CasillaEscalera escalera) {
            notificarObservadores(Eventos.AVANCE);
            casillaDestino = escalera.getDestino();
            jugador.setPosicion(casillaDestino);
            notificarObservadores(Eventos.ESCALERA);

        } else if (casilla instanceof CasillaSerpiente serpiente) {
            notificarObservadores(Eventos.AVANCE);
            casillaDestino = serpiente.getDestino();
            jugador.setPosicion(casillaDestino);
            notificarObservadores(Eventos.SERPIENTE);
        }else{
            // Avance, sacar si quiero ver la tirada
            notificarObservadores(Eventos.AVANCE);
        }

        // Turno
        if (avance == 6) {
            notificarObservadores(Eventos.SACOSEIS);
        } else {
            cambiarTurno();
        }
    }

    // =========================
    // Consultas
    // =========================
    @Override
    public String consultarGanador() {
        return aliasGanador;
    }

    @Override
    public int posicionJugador(String alias) {
        Jugador j = buscarJugador(alias);
        return j != null ? j.getPosicion() : 0;
    }

    // =========================
    // Guardado / Restauración
    // =========================
    @Override
    public void guardarPartida() throws RemoteException {
        this.guardado = new JuegoGuardado();
    }

    @Override
    public boolean continuarPartida() throws RemoteException {
        if (this.guardado != null) {
            guardado.restaurarEstado();
            this.guardado = null;
            return true;
        }
        return false;
    }

    @Override
    public void reiniciar() throws RemoteException {

        tablero = new Tablero();
        fase = Fase.SIN_COMENZAR;
        aliasGanador = null;
        guardado = null;
        turnoDeJugador = null;
        indiceJugadorActual = 0;
        seisConsecutivos = 0;
        ultimoAvance = 0;

        for (Jugador j : logueados) {
            j.setPosicion(1);
        }
    }

    // =========================
    // Getters / Setters
    // =========================
    public void setLogueados(List<Jugador> logueados) {
        this.logueados = logueados;
    }

    public int getTurnoActual() {
        return indiceJugadorActual;
    }

    public void setTurnoActual(int turnoActual) {

        if (logueados == null || logueados.isEmpty()) {
            turnoDeJugador = null;
            return;
        }

        if (turnoActual < 0 || turnoActual >= logueados.size()) {
            turnoActual = 0;
        }

        this.indiceJugadorActual = turnoActual;
        this.turnoDeJugador =
                logueados.get(turnoActual).getAlias();
    }

    @Override
    public int getCasillaDestino() {
        return casillaDestino;
    }

    public int getSeisConsecutivos() {
        return seisConsecutivos;
    }

    @Override
    public String getTurnoDeJugador() {
        return turnoDeJugador;
    }

    public void setSeisConsecutivos(int seisConsecutivos) {
        this.seisConsecutivos = seisConsecutivos;
    }

    public boolean isJuegoTerminado() {
        return fase == Fase.FINALIZADO;
    }

    public Jugador getGanador() {
        return buscarJugador(aliasGanador);
    }

    public void setGanador(Jugador ganador) {
        this.aliasGanador =
                ganador != null ? ganador.getAlias() : null;
    }

    public void setCasillaDestino(int casillaDestino) {
        this.casillaDestino = casillaDestino;
    }

    public int getUltimoAvance() {
        return ultimoAvance;
    }

    public void setUltimoAvance(int ultimoAvance) {
        this.ultimoAvance = ultimoAvance;
    }

    @Override
    public List<Jugador> obtenerTop5() throws RemoteException {

        List<Jugador> copia = new ArrayList<>(jugadores);

        // Ordenar de mayor a menor
        for (int i = 0; i < copia.size() - 1; i++) {
            for (int j = i + 1; j < copia.size(); j++) {
                if (copia.get(j).getPartidasGanadas()
                        > copia.get(i).getPartidasGanadas()) {

                    Jugador aux = copia.get(i);
                    copia.set(i, copia.get(j));
                    copia.set(j, aux);
                }
            }
        }

        // Recortar a 5
        List<Jugador> top5 = new ArrayList<>();
        for (int i = 0; i < copia.size() && i < 5; i++) {
            top5.add(copia.get(i));
        }

        return top5;
    }

    @Override
    public Tablero getTablero() throws RemoteException {
        return this.tablero;
    }

    @Override
    public List<Jugador> getJugadores() throws RemoteException {
        return jugadores;
    }

    @Override
    public List<Jugador> getLogueados() throws RemoteException {
        return logueados;
    }

    public Fase getFase() throws RemoteException {
        return this.fase;
    }
}
