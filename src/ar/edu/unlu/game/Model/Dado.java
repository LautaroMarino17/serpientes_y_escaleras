package ar.edu.unlu.game.Model;
import java.util.Random;

public class Dado {
	private Random valor;
	private int resultado;
	public Dado() {
		this.valor = new Random();
	}
	public int lanzar() {
		this.resultado = valor.nextInt(6) + 1;
		System.out.println(" El dado saco: " + this.resultado);
		return  this.resultado;
	}
	public int getResultado() {
		return this.resultado;
	}
}
