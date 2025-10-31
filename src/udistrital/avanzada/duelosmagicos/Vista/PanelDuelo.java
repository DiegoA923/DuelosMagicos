package udistrital.avanzada.duelosmagicos.Vista;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Clase PanelDuelo.
 * <p>
 * Representa el escenario visual donde se enfrentan los dos magos en el juego.
 * Contiene sus animaciones de lanzamiento de hechizos, efectos visuales (explosión,
 * aturdimiento) y los botones que permiten lanzar los ataques.
 * </p>
 *
 * <p>
 * Esta clase maneja la parte visual del duelo y se puede ajustar fácilmente
 * modificando constantes de posición, colores o tamaños.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-29
 */
public class PanelDuelo extends JPanel {

    // ===========================
    // Labels de magos y estados
    // ===========================
    private JLabel lblMago1, lblLanzamiento1, lblAturdido1;
    private JLabel lblMago2, lblLanzamiento2, lblAturdido2;
    private JLabel lblHechizo, lblExplosion;
    private JButton btnHechizoMago1, btnHechizoMago2;

    // ===========================
    // Timers (controlan animaciones temporizadas)
    // ===========================
    private Timer animacion;
    private Timer timerLanzamiento;
    private Timer timerAturdido;
    private Timer timerExplosion;

    // ===========================
    // Estados del juego
    // ===========================
    private boolean turnoMago1 = true;
    private boolean mago1Aturdido = false;
    private boolean mago2Aturdido = false;

    // Imagen de fondo del panel
    private Image fondo;

    // ===========================
    // Constantes de posición y tamaño
    // ===========================
    
    private final int posMago1X = 150;   // Posición horizontal del mago 1
    private final int posMago2X = 700;   // Posición horizontal del mago 2
    private final int posMagoY = 365;    // Altura base de los magos
    private final int posHechizoY = posMagoY + 15; // Altura donde viaja el hechizo
    private final int anchoMago = 150;   // Ancho estándar de las imágenes de magos
    private final int anchoBoton = 150;  // Ancho de los botones de lanzamiento

    /**
     * Constructor del panel del duelo.
     * Configura los componentes visuales, imágenes, botones y animaciones.
     */
    public PanelDuelo() {
        setLayout(null);  // Posicionamiento absoluto de los elementos
        setOpaque(true);

        // ===========================
        // Carga del fondo
        // ===========================
        try {
            fondo = new ImageIcon(getClass().getResource(
                    "/udistrital/avanzada/duelosmagicos/recursos/fondo.png")).getImage();
        } catch (Exception e) {
            System.err.println("No se pudo cargar fondo.png: " + e.getMessage());
        }

        // ===========================
        // Creación de magos y sus estados visuales
        // ===========================
        lblMago1 = new JLabel(escalar("/udistrital/avanzada/duelosmagicos/recursos/mago1.png"));
        lblLanzamiento1 = new JLabel(escalar("/udistrital/avanzada/duelosmagicos/recursos/lanzamiento1.png"));
        lblAturdido1 = new JLabel(escalarAturdido("/udistrital/avanzada/duelosmagicos/recursos/aturdido1.png"));

        lblMago2 = new JLabel(escalar("/udistrital/avanzada/duelosmagicos/recursos/mago2.png"));
        lblLanzamiento2 = new JLabel(escalar("/udistrital/avanzada/duelosmagicos/recursos/lanzamiento2.png"));
        lblAturdido2 = new JLabel(escalarAturdido("/udistrital/avanzada/duelosmagicos/recursos/aturdido2.png"));

        // Imágenes del hechizo y la explosión
        lblHechizo = new JLabel(escalar("/udistrital/avanzada/duelosmagicos/recursos/hechizo.png", 64, 64));
        lblExplosion = new JLabel(escalar("/udistrital/avanzada/duelosmagicos/recursos/explosion.png", 96, 96));

        // Oculta inicialmente los elementos que no deben mostrarse
        for (JLabel lbl : new JLabel[]{lblLanzamiento1, lblAturdido1, lblLanzamiento2, lblAturdido2, lblHechizo, lblExplosion}) {
            lbl.setVisible(false);
        }

        // ===========================
        // Posicionamiento de los elementos
        // ===========================

        lblMago1.setBounds(posMago1X, posMagoY, anchoMago, 128);
        lblLanzamiento1.setBounds(posMago1X, posMagoY, anchoMago, 128);
        lblAturdido1.setBounds(posMago1X, posMagoY, anchoMago, 128);

        lblMago2.setBounds(posMago2X, posMagoY, anchoMago, 128);
        lblLanzamiento2.setBounds(posMago2X, posMagoY, anchoMago, 128);
        lblAturdido2.setBounds(posMago2X, posMagoY - 6, anchoMago, 128);

        lblHechizo.setBounds(posMago1X + 100, posHechizoY, 64, 64);
        lblExplosion.setBounds(0, 0, 96, 96);

        // ===========================
        // Botones de lanzamiento
        // ===========================
        btnHechizoMago1 = crearBoton("Lanzar Hechizo", new Color(50, 70, 160));
        btnHechizoMago2 = crearBoton("Lanzar Hechizo", new Color(160, 50, 50));

        // Centra los botones debajo de cada mago
        btnHechizoMago1.setBounds(posMago1X + (anchoMago / 2) - (anchoBoton / 2), posMagoY + 140, anchoBoton, 40);
        btnHechizoMago2.setBounds(posMago2X + (anchoMago / 2) - (anchoBoton / 2), posMagoY + 140, anchoBoton, 40);

        // Añadir todos los elementos al panel
        add(lblMago1);
        add(lblLanzamiento1);
        add(lblAturdido1);
        add(lblMago2);
        add(lblLanzamiento2);
        add(lblAturdido2);
        add(lblHechizo);
        add(lblExplosion);
        add(btnHechizoMago1);
        add(btnHechizoMago2);

        // Asegura que la explosión siempre quede al frente visualmente
        setComponentZOrder(lblExplosion, 0);

        // ===========================
        // Acciones de los botones
        // ===========================
        btnHechizoMago1.addActionListener(e -> {
            if (turnoMago1) {
                lanzarHechizo();
            }
        });
        btnHechizoMago2.addActionListener(e -> {
            if (!turnoMago1) {
                lanzarHechizo();
            }
        });

        configurarAnimacion();
        actualizarVisibilidadBotones();
        setBorder(crearMarcoDecorativo());
    }

    /**
     * Configura la animación del hechizo.
     * Controla el movimiento horizontal del proyectil entre los magos.
     */
    private void configurarAnimacion() {
        animacion = new Timer(25, e -> {
            Point pos = lblHechizo.getLocation();
            if (turnoMago1) {
                lblHechizo.setLocation(pos.x + 25, pos.y);
                if (pos.x > posMago2X) {
                    detenerHechizo();
                }
            } else {
                lblHechizo.setLocation(pos.x - 25, pos.y);
                if (pos.x < posMago1X + 80) {
                    detenerHechizo();
                }
            }
        });
    }

    /**
     * Inicia la animación de lanzamiento del hechizo.
     * Muestra el sprite de lanzamiento y mueve el hechizo hacia el oponente.
     */
    private void lanzarHechizo() {
        if (timerLanzamiento != null && timerLanzamiento.isRunning()) {
            timerLanzamiento.stop();
        }

        lblHechizo.setVisible(true);

        if (turnoMago1) {
            mostrarEstado(lblMago1, lblLanzamiento1, 600);
            lblHechizo.setLocation(posMago1X + 100, posHechizoY);
        } else {
            mostrarEstado(lblMago2, lblLanzamiento2, 600);
            lblHechizo.setLocation(posMago2X - 50, posHechizoY);
        }

        animacion.start();
        actualizarVisibilidadBotones();
    }

    /**
     * Detiene la animación del hechizo y genera los efectos visuales del impacto.
     */
    private void detenerHechizo() {
        animacion.stop();
        lblHechizo.setVisible(false);

        if (timerAturdido != null && timerAturdido.isRunning()) timerAturdido.stop();
        if (timerExplosion != null && timerExplosion.isRunning()) timerExplosion.stop();

        int xImpacto, yImpacto = posHechizoY - 20;

        // Determina qué mago fue golpeado
        if (turnoMago1) {
            xImpacto = posMago2X;
            mago2Aturdido = true;
            mostrarEstado(lblMago2, lblAturdido2, 700, () -> {
                mago2Aturdido = false;
                actualizarVisibilidadBotones();
            });
        } else {
            xImpacto = posMago1X + 60;
            mago1Aturdido = true;
            mostrarEstado(lblMago1, lblAturdido1, 700, () -> {
                mago1Aturdido = false;
                actualizarVisibilidadBotones();
            });
        }

        // Efecto visual de explosión
        lblExplosion.setBounds(xImpacto, yImpacto, 96, 96);
        lblExplosion.setVisible(true);
        timerExplosion = new Timer(900, e -> lblExplosion.setVisible(false));
        timerExplosion.setRepeats(false);
        timerExplosion.start();

        cambiarTurno();
    }

    /**
     * Muestra un sprite temporalmente y luego vuelve al sprite normal.
     */
    private void mostrarEstado(JLabel normal, JLabel alterno, int duracion) {
        mostrarEstado(normal, alterno, duracion, null);
    }

    private void mostrarEstado(JLabel normal, JLabel alterno, int duracion, Runnable alTerminar) {
        normal.setVisible(false);
        alterno.setVisible(true);
        Timer t = new Timer(duracion, e -> {
            alterno.setVisible(false);
            normal.setVisible(true);
            if (alTerminar != null) alTerminar.run();
        });
        t.setRepeats(false);
        t.start();
    }

    private void cambiarTurno() {
        turnoMago1 = !turnoMago1;
        actualizarVisibilidadBotones();
    }

    /**
     * Muestra solo el botón del mago activo y que no esté aturdido.
     */
    private void actualizarVisibilidadBotones() {
        btnHechizoMago1.setVisible(turnoMago1 && !mago1Aturdido);
        btnHechizoMago2.setVisible(!turnoMago1 && !mago2Aturdido);
    }

    // ===========================
    // Métodos utilitarios de imágenes y estilo
    // ===========================

    private ImageIcon escalar(String ruta) {
        return escalar(ruta, anchoMago, 150);
    }

    private ImageIcon escalar(String ruta, int ancho, int alto) {
        URL url = getClass().getResource(ruta);
        if (url == null) {
            System.err.println("Imagen no encontrada: " + ruta);
            return new ImageIcon();
        }
        Image img = new ImageIcon(url).getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private ImageIcon escalarAturdido(String ruta, int ancho, int alto) {
        URL url = getClass().getResource(ruta);
        if (url == null) {
            System.err.println("Imagen no encontrada: " + ruta);
            return new ImageIcon();
        }
        int margen = 10; // reduce un poco el alto para evitar recortes
        Image img = new ImageIcon(url).getImage().getScaledInstance(ancho, alto - margen, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private ImageIcon escalarAturdido(String ruta) {
        return escalarAturdido(ruta, anchoMago, 150);
    }

    /**
     * Crea un botón estilizado con color base y bordes mágicos.
     * Puedes cambiar el color o la fuente para modificar el estilo del juego.
     */
    private JButton crearBoton(String texto, Color colorBase) {
        JButton btn = new JButton(texto);
        btn.setBackground(colorBase);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Georgia", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 220, 255), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }

    /**
     * Crea el marco decorativo del panel principal.
     * Puedes modificar los colores dorado, sombra o fuente del título.
     */
    private Border crearMarcoDecorativo() {
        Color dorado = new Color(212, 175, 55);
        Color sombra = new Color(40, 20, 60, 120);
        Color luz = new Color(255, 235, 180);
        Border bordePrincipal = new LineBorder(dorado, 6, true);
        Border sombraExterior = new MatteBorder(10, 10, 10, 10, sombra);
        TitledBorder titulo = BorderFactory.createTitledBorder(
                bordePrincipal, "Duelo de Magos",
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Old English Text MT", Font.BOLD, 28), luz
        );
        return new CompoundBorder(sombraExterior, titulo);
    }

    /**
     * Dibuja el fondo del panel (imagen o gradiente).
     * Si no se encuentra la imagen, aplica un degradado de color morado.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (fondo != null) {
            g2.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        } else {
            GradientPaint grad = new GradientPaint(0, 0, new Color(30, 0, 60),
                    0, getHeight(), new Color(100, 40, 150));
            g2.setPaint(grad);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
        g2.setColor(new Color(20, 0, 30, 80));
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}
