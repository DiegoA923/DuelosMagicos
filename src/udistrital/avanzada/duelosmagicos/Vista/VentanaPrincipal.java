package udistrital.avanzada.duelosmagicos.Vista;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Clase VentanaPrincipal.
 * <p>
 * Ventana principal de la aplicación Duelo de Magos.
 * Esta clase representa la vista base del sistema y recibe
 * su panel principal (PanelDuelo) mediante inyección de dependencias.
 * </p>
 *
 * Principios aplicados:
 * - SRP: solo se encarga de la interfaz gráfica general.
 * - DIP: no crea directamente los paneles, los recibe como dependencia.
 *
 * @author Diego
 * @version 2.0
 * @since 2025-10-31
 */
public class VentanaPrincipal extends JFrame {

    /** Panel principal del duelo (inyectado desde el controlador) */
    private final PanelDuelo panelDuelo;

    /**
     * Constructor que recibe el panel principal de duelo.
     * 
     * @param panelDuelo instancia del panel que se mostrará en la ventana
     */
    public VentanaPrincipal(PanelDuelo panelDuelo) {
        super("Duelos Mágicos");

        // Inyección de dependencia: el panel viene desde afuera
        this.panelDuelo = panelDuelo;

        // Configuración general de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(950, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        // Añadimos el panel al contenedor principal
        add(panelDuelo);
    }

    /**
     * Muestra u oculta la ventana principal.
     *
     * @param mostrar true para mostrar, false para ocultar
     */
    public void mostrarVentana(boolean mostrar) {
        setVisible(mostrar);
    }

    /**
     * Permite acceder al panel principal de duelo.
     * Este método es útil para controladores que requieran manipular la vista.
     *
     * @return instancia actual de {@link PanelDuelo}
     */
    public PanelDuelo getPanelDuelo() {
        return panelDuelo;
    }

    /**
     * Muestra una ventana de selección de archivos configurada
     * para escoger archivos .properties de configuración del torneo.
     *
     * @param descripcion descripción del tipo de archivo (por ejemplo: "Archivo de configuración")
     * @param extension extensión aceptada sin punto (por ejemplo: "properties")
     * @param modoSeleccion modo de selección (ver {@link JFileChooser})
     * @param rutaPredeterminada ruta inicial del explorador
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

    /**
     * Muestra un mensaje emergente informativo.
     *
     * @param mensaje texto a mostrar
     */
    public void mostrarMensajeEmergente(String mensaje) {
        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Información",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
