package udistrital.avanzada.duelosmagicos.Vista;

import java.awt.*;
import javax.swing.*;
import udistrital.avanzada.duelosmagicos.Control.ControlVentana;

/**
 * PanelDuelo
 * <p>
 * Panel gráfico donde se muestra el desarrollo del duelo mágico.
 * </p>
 *
 * @author sebas
 * @since 2025-10-29
 */
public class PanelDuelo extends JPanel {

    private JLabel lblMago1Nombre, lblMago1Casa, lblMago1Puntos, lblMago1Hechizos, lblMago1Actual;
    private JLabel lblMago2Nombre, lblMago2Casa, lblMago2Puntos, lblMago2Hechizos, lblMago2Actual;
    private JLabel lblEstadoDuelo;
    private JButton btnIniciar, btnSalir;

    public PanelDuelo(ControlVentana control) {
        setLayout(new BorderLayout());
        setBackground(new Color(25, 25, 35));

        // Panel superior con título
        JLabel titulo = new JLabel("⚔️ Duelo Mágico ⚡", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(titulo, BorderLayout.NORTH);

        // Panel central para los magos
        JPanel panelCentro = new JPanel(new GridLayout(1, 2, 40, 0));
        panelCentro.setBackground(new Color(25, 25, 35));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Panel del Mago 1
        panelCentro.add(crearPanelMago("Mago 1"));
        // Panel del Mago 2
        panelCentro.add(crearPanelMago("Mago 2"));

        add(panelCentro, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 15));
        panelInferior.setBackground(new Color(25, 25, 35));

        btnIniciar = new JButton("Iniciar Duelo");
        btnIniciar.setActionCommand("iniciarDuelo");
        btnIniciar.addActionListener(control);

        btnSalir = new JButton("Salir");
        btnSalir.setActionCommand("salir");
        btnSalir.addActionListener(control);

        panelInferior.add(btnIniciar);
        panelInferior.add(btnSalir);

        add(panelInferior, BorderLayout.SOUTH);

        // Estado general del duelo
        lblEstadoDuelo = new JLabel("Esperando inicio del duelo...", SwingConstants.CENTER);
        lblEstadoDuelo.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblEstadoDuelo.setForeground(Color.LIGHT_GRAY);
        add(lblEstadoDuelo, BorderLayout.NORTH);
    }

    private JPanel crearPanelMago(String nombrePanel) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 5));
        panel.setBackground(new Color(45, 45, 65));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                nombrePanel,
                0, 0,
                new Font("Serif", Font.BOLD, 18),
                Color.WHITE
        ));

        JLabel lblNombre = new JLabel("Nombre: ");
        JLabel lblCasa = new JLabel("Casa: ");
        JLabel lblPuntos = new JLabel("Puntos: 0");
        JLabel lblHechizos = new JLabel("Hechizos lanzados: 0");
        JLabel lblHechizoActual = new JLabel("Hechizo actual: -");

        lblNombre.setForeground(Color.WHITE);
        lblCasa.setForeground(Color.WHITE);
        lblPuntos.setForeground(Color.WHITE);
        lblHechizos.setForeground(Color.WHITE);
        lblHechizoActual.setForeground(Color.WHITE);

        panel.add(lblNombre);
        panel.add(lblCasa);
        panel.add(lblPuntos);
        panel.add(lblHechizos);
        panel.add(lblHechizoActual);

        if (nombrePanel.equals("Mago 1")) {
            lblMago1Nombre = lblNombre;
            lblMago1Casa = lblCasa;
            lblMago1Puntos = lblPuntos;
            lblMago1Hechizos = lblHechizos;
            lblMago1Actual = lblHechizoActual;
        } else {
            lblMago2Nombre = lblNombre;
            lblMago2Casa = lblCasa;
            lblMago2Puntos = lblPuntos;
            lblMago2Hechizos = lblHechizos;
            lblMago2Actual = lblHechizoActual;
        }

        return panel;
    }

    // ----- Métodos para actualizar datos desde el Control -----

    public void setDatosMago(int indice, String nombre, String casa) {
        if (indice == 1) {
            lblMago1Nombre.setText("Nombre: " + nombre);
            lblMago1Casa.setText("Casa: " + casa);
        } else {
            lblMago2Nombre.setText("Nombre: " + nombre);
            lblMago2Casa.setText("Casa: " + casa);
        }
    }

    public void actualizarDatosMago(int indice, int puntos, int lanzados) {
        if (indice == 1) {
            lblMago1Puntos.setText("Puntos: " + puntos);
            lblMago1Hechizos.setText("Hechizos lanzados: " + lanzados);
        } else {
            lblMago2Puntos.setText("Puntos: " + puntos);
            lblMago2Hechizos.setText("Hechizos lanzados: " + lanzados);
        }
    }

    public void mostrarHechizo(int indice, String hechizo) {
        if (indice == 1) {
            lblMago1Actual.setText("Hechizo actual: " + hechizo);
        } else {
            lblMago2Actual.setText("Hechizo actual: " + hechizo);
        }
    }

    public void mostrarGanador(String nombre, String casa, int puntos) {
        lblEstadoDuelo.setText("🏆 Ganador: " + nombre + " (" + casa + ") con " + puntos + " puntos!");
        lblEstadoDuelo.setForeground(Color.YELLOW);
    }

    public void reiniciarEstado() {
        lblEstadoDuelo.setText("Esperando inicio del duelo...");
        lblEstadoDuelo.setForeground(Color.LIGHT_GRAY);
    }
}
