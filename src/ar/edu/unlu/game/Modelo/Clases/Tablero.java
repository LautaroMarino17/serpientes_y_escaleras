package ar.edu.unlu.game.Modelo.Clases;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tablero implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Casilla> casillas;
    private Random random;

    public Tablero() {
        random = new Random();
        casillas = new ArrayList<>();

        // Crear 100 casillas normales
        for (int i = 1; i <= 100; i++) {
            casillas.add(new Casilla(i));
        }

        for (int i = 0; i < 9; i++) {

            int inicio = random.nextInt(98) + 2;

            int subidaMax = 99 - inicio;

            if (subidaMax <= 0) continue;

            int fin = inicio + random.nextInt(subidaMax) + 1;

            CasillaEscalera escalera = new CasillaEscalera(inicio, fin);
            casillas.set(inicio - 1, escalera);
        }
        for (int i = 0; i < 9; i++) {

            int inicio = random.nextInt(98) + 2;

            int bajadaMax = inicio;

            if (bajadaMax <= 0) continue;

            int fin = inicio - (random.nextInt(bajadaMax));

            CasillaSerpiente serpiente = new CasillaSerpiente(inicio, fin);
            casillas.set(inicio - 1, serpiente);
        }


    }

    public Casilla getCasilla(int numero) {
        return casillas.get(numero - 1);
    }

    public int getTotalCasillas() {
        return casillas.size();
    }

    public String getDescripcionTablero() {
        String texto = "\nTABLERO \n\n";
        int contador = 0;

        for (Casilla c : casillas) {
            String celda;

            if (c instanceof CasillaEscalera) {
                celda = String.format("[E→%02d]", ((CasillaEscalera) c).getDestino());
            }
            else if (c instanceof CasillaSerpiente) {
                celda = String.format("[S→%02d]", ((CasillaSerpiente) c).getDestino());
            }
            else {
                celda = String.format("[%02d]", c.getPosicion());
            }

            texto += celda + " ";
            contador++;

            // Cada 10 casillas, salto de línea
            if (contador % 10 == 0) {
                texto += "\n";
            }
        }

        texto += "\n";
        return texto;
    }


}




