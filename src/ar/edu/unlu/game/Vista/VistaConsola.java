package ar.edu.unlu.game.Vista;
import java.util.Scanner;

public class VistaConsola {
    private Scanner sc = new Scanner(System.in);
    public void mostrarMenu() {
        System.out.println("\n --- Juego de Serpientes y Escaleras ---");
        System.out.println("1. Iniciar juego");
        System.out.println("2. Agregar jugador");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public int obtenerOpcion() {
        while (!sc.hasNextInt()) {
            System.out.print("Ingrese una opción válida: ");
            sc.next();
        }
        return sc.nextInt();
    }
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public String solicitarNombreJugador() {
        System.out.print("Ingrese el nombre del jugador: ");
        sc.nextLine();
        return sc.nextLine();
    }
    
    public void mostrarTurno(String nombreJugador) {
        System.out.println("Turno de " + nombreJugador + ". Presioná Enter para tirar el dado...");
    }

    public void mostrarResultadoDado(int valor) {
        System.out.println("Sacaste un " + valor + ".");
    }

    public void mostrarTablero(String texto) {
        System.out.println(texto);
    }

    public void mostrarGanador(String nombre) {
        System.out.println(nombre + " ha ganado la partida!");
    }
    public void esperarEnter() {
        sc.nextLine(); 
    }

}