package ar.edu.unlu.game.Model;

public class CasillaSerpiente extends Casilla {
	private int destino;
	public CasillaSerpiente(int posicion, int destino) {
		super(posicion);
		this.destino = destino;
	}
	public int getDestino() {
		return this.destino;
	}
}
