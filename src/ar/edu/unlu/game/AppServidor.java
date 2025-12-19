package ar.edu.unlu.game;
import ar.edu.unlu.game.Modelo.Clases.Juego;
import ar.edu.unlu.rmimvc.RMIMVCException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import ar.edu.unlu.game.Modelo.Persistencia.Serializador;

import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.servidor.Servidor;

public class AppServidor {

    public static void main(String[] args) {
        ArrayList<String> ips = Util.getIpDisponibles();
        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchará peticiones el servidor", "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );
        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchará peticiones el servidor", "Puerto del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                8888
        );

        Serializador serializador = new Serializador("juego.dat");
        Juego juegoRecuperado = (Juego) serializador.readFirstObject();
        if (juegoRecuperado != null) {
            Juego.setInstancia(juegoRecuperado);
            System.out.println("Juego cargado desde archivo.");
        } else {
            Juego.getInstancia();
            System.out.println("No se encontró archivo, se creó un nuevo juego.");
        }

        Juego juego = Juego.getInstancia();

        Servidor servidor = new Servidor(ip, Integer.parseInt(port));
        try {
            servidor.iniciar(juego);
        } catch (RemoteException | RMIMVCException e) {
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                juego.guardarPartida();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            serializador.writeOneObject(Juego.getInstancia());
            System.out.println("Juego guardado automáticamente al cerrar.");
        }));
    }
}