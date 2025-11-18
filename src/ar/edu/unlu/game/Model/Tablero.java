package ar.edu.unlu.game.Model;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tablero {
    private List<Casilla> casillas;
    private Random random;

    public Tablero() {
        random = new Random();
        casillas = new ArrayList<>();

        // Crear 100 casillas normales
        for (int i = 1; i <= 100; i++) {
            casillas.add(new Casilla(i));
        }

        // Generar 9 escaleras
        for (int i = 0; i < 9; i++) {
            int inicio = random.nextInt(85) + 1;  // 
            int fin = inicio + random.nextInt(10) + 5; // sube entre 5 y 15 posiciones

            if (fin <= 100) {
                CasillaEscalera escalera = new CasillaEscalera(inicio, fin);
                casillas.set(inicio - 1, escalera);
            }
        }

        // Generar 9 serpientes
        for (int i = 0; i < 9; i++) {
            int inicio = random.nextInt(85) + 15;  // entre 11 y 100
            int fin = inicio - random.nextInt(10) - 5; // baja entre 5 y 15 posiciones

            if (fin > 0) {
                CasillaSerpiente serpiente = new CasillaSerpiente(inicio, fin);
                casillas.set(inicio - 1, serpiente);
            }
        }
    }

    public Casilla getCasilla(int numero) {
        return casillas.get(numero - 1);
    }

    public int getTotalCasillas() {
        return casillas.size();
    }

    public String getDescripcionTablero() {
        String texto = "=== Tablero de juego ===\n";

        for (Casilla c : casillas) {

            if (c instanceof CasillaEscalera) {
                texto += "[E→" + ((CasillaEscalera) c).getDestino() + "]";
            }
            else if (c instanceof CasillaSerpiente) {
                texto += "[S→" + ((CasillaSerpiente) c).getDestino() + "]";
            }
            else {
                texto += "[" + c.getPosicion() + "]";
            }
        }

        return texto;
    }

        }
    



