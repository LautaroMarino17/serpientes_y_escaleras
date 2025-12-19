package ar.edu.unlu.game.Vista;

// Imports del controlador
import ar.edu.unlu.game.Controlador.Controlador;

// Imports Swing / AWT
import javax.swing.*;
import java.awt.*;

// Imports Java
import java.util.HashMap;
import java.util.Map;

public class VistaConsola extends JFrame implements IVista {

    // =========================
    // Atributos
    // =========================
    private JPanel panelPrincipal;
    private JPanel panelTablero;

    private JTextArea txtSalida;
    private JTextField txtEntrada;
    private JButton btnEnter;

    private Controlador controlador;
    private Map<String, JButton> botonesPorPosicion = new HashMap<>();

    private boolean logueado = false;

    // =========================
    // Constructor
    // =========================
    public VistaConsola() {

        setTitle("Serpientes y Escaleras - Consola Visual");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // =========================
        // Panel Principal
        // =========================
        panelPrincipal = new JPanel(new BorderLayout());

        // =========================
        // Panel Derecho (Consola)
        // =========================
        JPanel panelDerecho = new JPanel(new BorderLayout());

        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        txtSalida.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(txtSalida);
        panelDerecho.add(scroll, BorderLayout.CENTER);

        // =========================
        // Panel Entrada
        // =========================
        JPanel panelEntrada = new JPanel(new BorderLayout());

        txtEntrada = new JTextField();
        txtEntrada.setFont(new Font("Arial", Font.PLAIN, 16));

        btnEnter = new JButton("Enviar");
        btnEnter.setFont(new Font("Arial", Font.BOLD, 16));

        panelEntrada.add(txtEntrada, BorderLayout.CENTER);
        panelEntrada.add(btnEnter, BorderLayout.EAST);
        panelEntrada.setPreferredSize(new Dimension(400, 50));

        panelDerecho.add(panelEntrada, BorderLayout.SOUTH);
        panelPrincipal.add(panelDerecho, BorderLayout.CENTER);

        setContentPane(panelPrincipal);

        // =========================
        // Acción del botón
        // =========================
        btnEnter.addActionListener(e -> {
            procesarEntrada(txtEntrada.getText());
            txtEntrada.setText("");
        });
    }

    // =========================
    // Tablero
    // =========================
    @Override
    public void mostrarTablero(String tableroTexto) {
        mostrarMensaje(tableroTexto, 0);
    }

    @Override
    public void actualizarPosicionJugador(int i) {
        // No implementado
    }

    // =========================
    // Procesamiento de comandos
    // =========================
    private void procesarEntrada(String comando) {

        String[] partes = comando.trim().split(" ");
        if (partes.length == 0) return;

        switch (partes[0].toLowerCase()) {

            case "registrarse" -> {
                if (partes.length == 3) {
                    controlador.registrarse(partes[1], partes[2]);
                } else {
                    mostrarMensaje(
                            "Uso: registrarse <alias> <contraseña>",
                            0
                    );
                }
            }

            case "iniciarsesion" -> {
                if (partes.length == 3) {
                    controlador.iniciarSesion(partes[1], partes[2]);
                    logueado = true;
                } else {
                    mostrarMensaje(
                            "Uso: iniciarSesion <alias> <contraseña>",
                            0
                    );
                }
            }

            case "continuar" -> {
                if (logueado) {
                    controlador.continuarPartida();
                } else {
                    mostrarMensaje(
                            "Inicie Sesion antes de iniciar una partida",
                            0
                    );
                }
            }

            case "tirar" -> {
                if (logueado) {
                    controlador.iniciarTurno();
                } else {
                    mostrarMensaje(
                            "Inicie Sesion antes de iniciar una partida",
                            0
                    );
                }
            }

            case "top5" -> controlador.top5();

            case "tablero" ->
                    mostrarMensaje(controlador.verTablero(), 0);

            case "cerrarsesion" -> {
                if (partes.length == 3) {
                    controlador.cerrarSesion(partes[1], partes[2]);
                    logueado = false;
                    mostrarMensaje("Cerraste sesión", 0);
                } else {
                    mostrarMensaje(
                            "Uso: cerrarSesion <alias> <contraseña>",
                            0
                    );
                }
            }

            case "help" -> mostrarMensaje("""
                    Comandos disponibles:
                    • registrarse <alias> <contraseña>
                    • iniciarSesion <alias> <contraseña>
                    • continuar
                    • tirar
                    • top5
                    • tablero
                    • help
                    """, 0);

            default ->
                    mostrarMensaje("Comando no reconocido.", 0);
        }
    }

    // =========================
    // Implementación IVista
    // =========================
    @Override
    public void iniciar() {

        setVisible(true);
        mostrarMensaje("¡Bienvenido a Serpientes y Escaleras!", 0);
        mostrarMensaje("Escribí 'help' para ver los comandos disponibles.", 0);
    }

    @Override
    public void setControlador(Object controlador) {
        this.controlador = (Controlador) controlador;
    }

    @Override
    public void mostrarMensaje(String mensaje, int delay) {
        txtSalida.append(mensaje + "\n");
        txtSalida.setCaretPosition(
                txtSalida.getDocument().getLength()
        );
    }

    @Override
    public void mostrarPanelJuego() {
        // No implementado
    }

    @Override
    public void mostrarPanelInicio() {
        // No implementado
    }

}
