package ar.edu.unlu.game.Vista;

// Imports del controlador
import ar.edu.unlu.game.Controlador.Controlador;

// Imports del modelo
import ar.edu.unlu.game.Modelo.Enums.AccionFormulario;

// Imports Swing / AWT
import javax.swing.*;
import java.awt.*;

// Imports Java
import java.util.HashMap;
import java.util.Map;

public class VistaGrafica extends JFrame implements IVista {

    // =========================
    // Controlador
    // =========================
    private Controlador controlador;

    // =========================
    // Layout
    // =========================
    private CardLayout cardLayout;
    private JPanel panelDerecho;

    // =========================
    // Paneles
    // =========================
    private JPanel panelInicio;
    private JPanel panelJuego;
    private JPanel panelTablero;

    // =========================
    // Tablero
    // =========================
    private Map<Integer, JButton> botonesPorPosicion = new HashMap<>();

    private static final String PANEL_INICIO = "inicio";
    private static final String PANEL_JUEGO = "juego";

    private boolean logueado = false;

    // =========================
    // Formulario
    // =========================
    private JPanel panelFormulario;
    private JTextField txtAlias;
    private JPasswordField txtPass;
    private AccionFormulario accionActual;

    // =========================
    // Constructor
    // =========================
    public VistaGrafica() {

        setTitle("Serpientes y Escaleras");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        panelDerecho = new JPanel(cardLayout);

        panelInicio = crearPanelInicio();
        panelJuego = crearPanelJuego();

        panelDerecho.add(panelInicio, PANEL_INICIO);
        panelDerecho.add(panelJuego, PANEL_JUEGO);

        add(panelDerecho, BorderLayout.CENTER);
        cardLayout.show(panelDerecho, PANEL_INICIO);

    }

    // =========================
    // Panel Inicio
    // =========================
    private JPanel crearPanelInicio() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(
                BorderFactory.createTitledBorder("Inicio")
        );

        JButton btnLogin = new JButton("Iniciar sesión");
        JButton btnRegistro = new JButton("Registrarse");
        JButton btnContinuar = new JButton("Continuar partida");
        JButton btnCierre = new JButton("Cerrar sesión");

        btnLogin.addActionListener(e -> {
            accionActual = AccionFormulario.LOGIN;
            panelFormulario.setVisible(true);
        });

        btnRegistro.addActionListener(e -> {
            accionActual = AccionFormulario.REGISTRO;
            panelFormulario.setVisible(true);
        });

        btnContinuar.addActionListener(e -> {
            if (logueado) {
                controlador.continuarPartida();
                mostrarPanelJuego();
                mostrarTablero(controlador.verTablero());
            } else {
                mostrarMensaje("Inicie sesión primero", 2000);
            }
        });

        btnCierre.addActionListener(e -> {
            logueado = false;
            mostrarMensaje("Cerraste sesión", 2000);
            mostrarPanelInicio();
        });

        panelFormulario = crearPanelFormulario();

        panel.add(btnLogin);
        panel.add(btnRegistro);
        panel.add(btnContinuar);
        panel.add(btnCierre);
        panel.add(panelFormulario);

        return panel;
    }

    // =========================
    // Panel Juego
    // =========================
    private JPanel crearPanelJuego() {

        JPanel contenedor = new JPanel(new BorderLayout());

        panelTablero = new JPanel(new GridLayout(10, 10));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panelTablero.setBorder(
                BorderFactory.createTitledBorder("Tablero")
        );
        contenedor.add(panelTablero, BorderLayout.CENTER);

        JPanel acciones = new JPanel(new GridLayout(4, 1, 8, 8));
        acciones.setBorder(
                BorderFactory.createTitledBorder("Acciones")
        );

        JButton btnTirar = new JButton(
                new ImageIcon(
                        getClass().getResource("/img/dado.png")
                )
        );
        btnTirar.setBackground(Color.WHITE);
        btnTirar.addActionListener(e ->
                controlador.iniciarTurno()
        );

        JButton btnVerTablero = new JButton("Ver tablero");
        btnVerTablero.addActionListener(e ->
                mostrarTablero(controlador.verTablero())
        );

        JButton btnTop5 = new JButton("Top 5");
        btnTop5.addActionListener(e ->
                controlador.top5()
        );

        JButton btnSalir = new JButton("Volver a menú");
        btnSalir.addActionListener(e ->
                mostrarPanelInicio()
        );

        acciones.add(btnTirar);
        acciones.add(btnVerTablero);
        acciones.add(btnTop5);
        acciones.add(btnSalir);

        contenedor.add(acciones, BorderLayout.EAST);
        return contenedor;
    }

    // =========================
    // Formulario
    // =========================
    private JPanel crearPanelFormulario() {

        // Panel principal RESPONSIVE
        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 5));
        panel.setBorder(
                BorderFactory.createTitledBorder("Datos")
        );

        // Fuente y tamaño de inputs
        Font fuente = new Font("Segoe UI", Font.PLAIN, 14);
        Dimension inputSize = new Dimension(220, 32);

        // ===== Alias =====
        JPanel panelAlias = new JPanel();
        panelAlias.setLayout(new BoxLayout(panelAlias, BoxLayout.Y_AXIS));

        txtAlias = new JTextField();
        txtAlias.setFont(fuente);
        txtAlias.setMaximumSize(inputSize);
        txtAlias.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));

        panelAlias.add(txtAlias);

        // ===== Password =====
        JPanel panelPass = new JPanel();
        panelPass.setLayout(new BoxLayout(panelPass, BoxLayout.Y_AXIS));

        txtPass = new JPasswordField();
        txtPass.setFont(fuente);
        txtPass.setMaximumSize(inputSize);
        txtPass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));

        panelPass.add(txtPass);

        // ===== Botón =====
        JButton btnConfirmar = new JButton("Confirmar");

        // Agregado al GridLayout
        panel.add(new JLabel("Alias:"));
        panel.add(panelAlias);

        panel.add(new JLabel("Contraseña:"));
        panel.add(panelPass);

        panel.add(btnConfirmar);

        btnConfirmar.addActionListener(e -> {

            String alias = txtAlias.getText();
            String pass = new String(txtPass.getPassword());

            if (accionActual == AccionFormulario.LOGIN) {
                logueado = true;
                controlador.iniciarSesion(alias, pass);
            } else {
                controlador.registrarse(alias, pass);
            }

            txtAlias.setText("");
            txtPass.setText("");
            panel.setVisible(false);
        });
        txtAlias.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtPass.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.setVisible(false);
        return panel;
    }


    // =========================
    // Cartel temporal
    // =========================
    private void mostrarCartel(String mensaje, int delay) {

        JDialog dialog = new JDialog(this, false);
        dialog.setUndecorated(true);

        JLabel lbl = new JLabel(mensaje, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        lbl.setBorder(
                BorderFactory.createEmptyBorder(15, 25, 15, 25)
        );

        dialog.add(lbl);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        new Timer(delay, e -> dialog.dispose()).start();
    }

    // =========================
    // Implementación IVista
    // =========================
    @Override
    public void setControlador(Object controlador) {
        this.controlador = (Controlador) controlador;
    }

    @Override
    public void iniciar() {
        setVisible(true);
        mostrarCartel(
                "========== BIENVENIDO A SERPIENTES Y ESCALERAS ==========",
                5000
        );
    }

    @Override
    public void mostrarMensaje(String mensaje, int delay) {
        mostrarCartel(mensaje, delay);
    }

    @Override
    public void mostrarPanelJuego() {
        cardLayout.show(panelDerecho, PANEL_JUEGO);
    }

    @Override
    public void mostrarPanelInicio() {
        cardLayout.show(panelDerecho, PANEL_INICIO);
    }

    @Override
    public void mostrarTablero(String tableroTexto) {

        panelTablero.removeAll();
        botonesPorPosicion.clear();

        Font fuente = new Font("Arial", Font.BOLD, 12);

        String textoLimpio = tableroTexto
                .replace("TABLERO", "")
                .trim();

        String[] filas = textoLimpio.split("\n");
        int posicion = 1;

        for (String fila : filas) {
            if (fila.trim().isEmpty()) continue;

            String[] celdas = fila.trim().split("\\s+");

            for (String celda : celdas) {

                JButton boton = new JButton();
                boton.setEnabled(false);
                boton.setLayout(new BorderLayout());
                boton.setFocusable(false);
                boton.setOpaque(true);
                boton.setBackground(Color.BLACK);
                boton.setForeground(Color.WHITE);

                JLabel lbl = new JLabel("", SwingConstants.CENTER);
                lbl.setFont(fuente);

                // Escalera
                if (celda.contains("E→")) {
                    String[] p = celda.split("E→");
                    lbl.setText(p[0] + " ↑ " + p[1]);
                    boton.setIcon(
                            new ImageIcon(
                                    getClass().getResource(
                                            "/img/escalera.png"
                                    )
                            )
                    );
                }
                // Serpiente
                else if (celda.contains("S→")) {
                    String[] p = celda.split("S→");
                    lbl.setText(p[0] + " ↓ " + p[1]);
                    boton.setIcon(
                            new ImageIcon(
                                    getClass().getResource(
                                            "/img/serpiente.png"
                                    )
                            )
                    );
                }
                // Normal
                else {
                    lbl.setText(celda);
                }

                boton.add(lbl, BorderLayout.SOUTH);
                panelTablero.add(boton);

                botonesPorPosicion.put(posicion, boton);
                posicion++;
            }
        }

        panelTablero.revalidate();
        panelTablero.repaint();
    }

    @Override
    public void actualizarPosicionJugador(int pos) {

        botonesPorPosicion.values().forEach(b -> {
            b.setBackground(Color.BLACK);
            b.setForeground(Color.WHITE);
        });

        JButton actual = botonesPorPosicion.get(pos);
        if (actual != null) {
            actual.setBackground(Color.WHITE);
            actual.setForeground(Color.BLACK);
        }
    }
}
