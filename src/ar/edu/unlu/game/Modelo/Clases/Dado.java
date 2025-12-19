package ar.edu.unlu.game.Modelo.Clases;
import java.io.Serializable;
import java.util.Random;

public class Dado implements Serializable{
    private static final long serialVersionUID = 1L;
    private Random valor;
    private int resultado;
    public Dado() {
        this.valor = new Random();
    }
    public int lanzar() {
        this.resultado = valor.nextInt(6) + 1;
        return  this.resultado;
    }
}
