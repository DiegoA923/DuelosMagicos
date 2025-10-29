package udistrital.avanzada.duelosmagicos.Vista;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Clase VentanaPrincipal.
 * <p>
 * Descripción:
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-25
 */
public class VentanaPrincipal extends JFrame {

    private PanelDuelo panelDuelo;

    public VentanaPrincipal() {
        super("Duelos Magicos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(950, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        panelDuelo = new PanelDuelo(null); 
        add(panelDuelo);
    }

    public PanelDuelo getPanelDuelo() {
        return panelDuelo;
    }

    /**
     * Metodo para mostrar ventana principal de la aplicacion
     *
     * @param mostrar bandera que nos dice si mostrar la ventana
     */
    public void mostrarVentana(boolean mostrar) {
        setVisible(mostrar);
    }

    /**
     * Muestra una ventana de selección de archivo personalizada.
     *
     * @param descripcion descripción del tipo de archivo a mostrar
     * @param extension extensión de archivo aceptada (sin punto)
     * @param modoSeleccion tipo de selección (por ejemplo,
     * {@link JFileChooser#FILES_ONLY})
     * @param rutaPredeterminada carpeta inicial donde abrir el explorador
     * @return instancia configurada de {@link JFileChooser}
     */
    public JFileChooser getFileChoser(String descripcion, String extension, int modoSeleccion, String rutaPredeterminada) {
        JFileChooser fileChooser = new JFileChooser();
        File carpetaInicial = new File(rutaPredeterminada);
        fileChooser.setCurrentDirectory(carpetaInicial);
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(descripcion, extension);
        fileChooser.setFileFilter(filtro);
        fileChooser.setFileSelectionMode(modoSeleccion);
        return fileChooser;
    }

    public void mostrarMensajeEmergente(String mensaje) {
        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Info",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
