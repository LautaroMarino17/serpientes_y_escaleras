package ar.edu.unlu.game.Modelo.Clases;

public class CasillaSerpiente extends Casilla {
    private static final long serialVersionUID = 1L;
    private int destino;
    public CasillaSerpiente(int posicion, int destino) {
        super(posicion);
        this.destino = destino;
    }
    public int getDestino() {
        return this.destino;
    }
}
