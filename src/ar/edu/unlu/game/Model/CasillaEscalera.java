package ar.edu.unlu.game.Model;

public class CasillaEscalera extends Casilla {
	private int destino;
	public CasillaEscalera(int posicion, int destino) {
		super(posicion);
		this.destino = destino;
	}
	
	public int getDestino() {
		return this.destino;
	}
}
