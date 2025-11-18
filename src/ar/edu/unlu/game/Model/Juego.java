package ar.edu.unlu.game.Model;

import java.util.ArrayList;
import java.util.List;
import ar.edu.unlu.game.Observer.*;

public class Juego implements Observable{
    protected Tablero tablero;
    private Dado dado;
    private List<Jugador> jugadores;
    private int turnoActual;
    private int seisConsecutivos;
    private List<Observador> observadores;
    private boolean juegoTerminado;
    private Jugador ganador;

    public Juego() {
        tablero = new Tablero();
        dado = new Dado();
        jugadores = new ArrayList<>();
        turnoActual = 0;
        seisConsecutivos = 0;
        juegoTerminado = false;
        ganador=null;
        observadores = new ArrayList<>();
        
    }

    public void agregarJugador(String nombre) {
        jugadores.add(new Jugador(nombre));
        notificarObservadores(); 
    }

    public void iniciarTurno() {
    	Jugador jugador = jugadores.get(turnoActual);
    	int avance = jugador.lanzarDado(dado);
         
    	if (avance == 6) {
            seisConsecutivos++;
        } else {
            seisConsecutivos = 0;
            turnoActual = (turnoActual + 1) % jugadores.size();
        }
            
        if (seisConsecutivos == 3) {
           System.out.println("Sacaste tres veces seguidas el 6 ¡Perdés el turno y no avanzás!");
           seisConsecutivos = 0;
           turnoActual = (turnoActual + 1) % jugadores.size();
        }else {
	         int nuevaPos = jugador.avanzar(avance);
	         Casilla actual = tablero.getCasilla(nuevaPos);
	      
	         // Revisar si cayó en serpiente o escalera
	         if (actual instanceof CasillaEscalera) {
	            int destino = ((CasillaEscalera) actual).getDestino();
	            System.out.println("¡Subiste por una escalera a la casilla " + destino + "!");
	            jugador.setPosicion(destino);
	         }else if (actual instanceof CasillaSerpiente) {
	            int destino = ((CasillaSerpiente) actual).getDestino();
	            System.out.println(" ¡Caíste en una serpiente y bajás a la casilla " + destino + "!");
	            jugador.setPosicion(destino);
	         }

            // Mostrar posición acumulada
            System.out.println(jugador);

            // Verificar si ganó
            if (jugador.getPosicion() == tablero.getTotalCasillas()) {
                ganador=jugador;
                notificarObservadores();  
                juegoTerminado = true;
                return;
            }
        }
        notificarObservadores();
        }
       

    public Jugador getJugadorActual() {
        return jugadores.get(turnoActual);
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }
    
    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public Jugador getGanador() {
        return ganador;
    }
    public Tablero getTablero() {
    	return tablero;
    }

	@Override
	public void agregarObservador(Observador observador) {
		 observadores.add(observador);
		
	}

	@Override
	public void quitarObservador(Observador observador) {
		 observadores.remove(observador);
		
	}

	@Override
	public void notificarObservadores() {
		 for (Observador o : observadores) {
	            o.actualizar();
	        }
		
	}
}
