package ar.edu.unlu.game.Modelo.Clases;

public class CasillaEscalera extends Casilla {
    private static final long serialVersionUID = 1L;
    private int destino;
    public CasillaEscalera(int posicion, int destino) {
        super(posicion);
        this.destino = destino;
    }

    public int getDestino() {
        return this.destino;
    }
}
