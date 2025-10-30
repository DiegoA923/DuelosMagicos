package udistrital.avanzada.duelosmagicos.Vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Clase PanelDuelo.
 * <p>
 * Descripción: [Agrega aquí la descripción de la clase].
 * </p>
 *
 * @author diego
 * @version 1.0
 */
/**
 * Clase PanelDuelo. Panel donde se enfrentan los dos magos y se anima el
 * lanzamiento del hechizo.
 */
public class PanelDuelo extends JPanel {

    private JLabel lblMago1, lblMago2, lblHechizo;
    private JButton btnLanzarHechizo;
    private Timer animacion;
    private boolean turnoMago1 = true; // true = mago1 ataca, false = mago2
    private Image fondo;

    // Posiciones base
    private final int posMago1X = 100;
    private final int posMago2X = 700;
    private final int posMagoY = 300;
    private final int posHechizoY = 340;

    public PanelDuelo() {
        setLayout(null);
        setOpaque(true);

        // ⚙️ Fondo: intenta cargar el fondo.jpg
        try {
            fondo = new ImageIcon(getClass().getResource(
                    "/udistrital/avanzada/duelosmagicos/recursos/fondo.png"
            )).getImage();
        } catch (Exception e) {
            System.err.println("⚠ No se pudo cargar el fondo.jpg: " + e.getMessage());
        }

        // 🧙‍♂️ Magos y hechizo
        lblMago1 = new JLabel(escalarImagen(
                "/udistrital/avanzada/duelosmagicos/recursos/mago1.png", 128, 128
        ));
        lblMago2 = new JLabel(escalarImagen(
                "/udistrital/avanzada/duelosmagicos/recursos/mago2.png", 128, 128
        ));
        lblHechizo = new JLabel(escalarImagen(
                "/udistrital/avanzada/duelosmagicos/recursos/hechizo.png", 64, 64
        ));
        lblHechizo.setVisible(false);

        // 📍 Posiciones iniciales
        lblMago1.setBounds(posMago1X, posMagoY, 128, 128);
        lblMago2.setBounds(posMago2X, posMagoY, 128, 128);
        lblHechizo.setBounds(posMago1X + 100, posHechizoY, 64, 64);

        // ✨ Botón mágico
        btnLanzarHechizo = new JButton("✨ Lanzar Hechizo ✨");
        btnLanzarHechizo.setBounds(370, 500, 200, 45);
        btnLanzarHechizo.setBackground(new Color(80, 30, 120));
        btnLanzarHechizo.setForeground(Color.WHITE);
        btnLanzarHechizo.setFont(new Font("Serif", Font.BOLD, 18));
        btnLanzarHechizo.setFocusPainted(false);
        btnLanzarHechizo.setBorder(
                BorderFactory.createLineBorder(new Color(200, 170, 255), 3, true)
        );

        // ➕ Añadir componentes
        add(btnLanzarHechizo);
        add(lblHechizo);
        add(lblMago2);
        add(lblMago1);

        configurarAnimacion();
        btnLanzarHechizo.addActionListener(e -> lanzarHechizo());

        // 🪄 Marco decorativo
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 150, 80), 3, true),
                "⚔ Duelo de Magos ⚔",
                0, 0,
                new Font("Old English Text MT", Font.BOLD, 22),
                new Color(255, 230, 180)
        ));
    }

    /**
     * Escala una imagen al tamaño indicado.
     */
    private ImageIcon escalarImagen(String ruta, int ancho, int alto) {
        ImageIcon icono = new ImageIcon(getClass().getResource(ruta));
        Image img = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    /**
     * Dibuja el fondo en todo el panel.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Fondo alternativo si no carga la imagen
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.drawString("⚠ No se pudo cargar fondo.jpg", 20, 20);
        }
    }

    /**
     * Configura la animación del hechizo.
     */
    private void configurarAnimacion() {
        animacion = new Timer(30, e -> {
            Point pos = lblHechizo.getLocation();

            // Mueve el hechizo según el turno
            if (turnoMago1) {
                lblHechizo.setLocation(pos.x + 20, pos.y);
                if (pos.x > posMago2X) {
                    detenerHechizo();
                }
            } else {
                lblHechizo.setLocation(pos.x - 20, pos.y);
                if (pos.x < posMago1X + 80) {
                    detenerHechizo();
                }
            }
        });
    }

    /**
     * Lanza el hechizo desde el mago que tiene el turno.
     */
    private void lanzarHechizo() {
        lblHechizo.setVisible(true);

        if (turnoMago1) {
            lblHechizo.setLocation(posMago1X + 100, posHechizoY);
        } else {
            lblHechizo.setLocation(posMago2X - 50, posHechizoY);
        }

        animacion.start();
        btnLanzarHechizo.setVisible(false);
    }

    /**
     * Detiene la animación del hechizo al impactar.
     */
    private void detenerHechizo() {
        animacion.stop();
        lblHechizo.setVisible(false);
        cambiarTurno();
        btnLanzarHechizo.setVisible(true);
    }

    /**
     * Cambia el turno entre los magos.
     */
    private void cambiarTurno() {
        turnoMago1 = !turnoMago1;
    }
}
