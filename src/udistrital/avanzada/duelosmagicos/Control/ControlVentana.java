package udistrital.avanzada.duelosmagicos.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import udistrital.avanzada.duelosmagicos.Vista.PanelDuelo;
import udistrital.avanzada.duelosmagicos.Vista.VentanaPrincipal;

/**
 * ControlVentana
 * <p>
 * Controlador que gestiona la navegación general de la aplicación.
 * </p>
 *
 * Aplica SRP, DIP y MVC. Solo coordina la vista principal y delega la lógica al
 * ControlDuelo o ControlPrincipal.
 *
 * @author Diego
 * @version 2.1
 * @since 2025-10-31
 */
public class ControlVentana implements ActionListener {

    private final VentanaPrincipal ventana;
    private final PanelDuelo panelDuelo;
    private final ControlPrincipal cPrincipal;

    // Se inyecta después de construir la ventana
    private ControlDuelo cDuelo;

    /**
     * Constructor: crea la ventana principal e inyecta el panel de duelo.
     *
     * @param cPrincipal controlador principal del sistema
     */
    public ControlVentana(ControlPrincipal cPrincipal) {
        this.cPrincipal = cPrincipal;
        this.panelDuelo = new PanelDuelo();
        this.ventana = new VentanaPrincipal(panelDuelo);
    }

    /**
     * Inyecta el controlador de duelo (se hace desde ControlPrincipal).
     *
     * @param cDuelo controlador de duelos
     */
    public void setControlDuelo(ControlDuelo cDuelo) {
        this.cDuelo = cDuelo;
    }

    /**
     * Muestra la ventana principal.
     */
    public void mostrarVentanaPrincipal() {
        ventana.mostrarVentana(true);
    }

    /**
     * Devuelve el panel del duelo para conexión con otros controladores.
     */
    public PanelDuelo getPanelDuelo() {
        return panelDuelo;
    }

    /**
     * Diálogo de selección de archivo de propiedades.
     */
    public File obtenerArchivoPropiedades(String ruta, String mensaje) {
        JFileChooser chooser = ventana.getFileChoser(
                mensaje, "properties", JFileChooser.FILES_ONLY, ruta
        );
        int seleccion = chooser.showOpenDialog(null);
        return (seleccion == JFileChooser.APPROVE_OPTION) ? chooser.getSelectedFile() : null;
    }

    /**
     * Muestra un mensaje informativo.
     */
    public void mostrarMensaje(String mensaje) {
        ventana.mostrarMensajeEmergente(mensaje);
    }

    /**
     * Manejo de eventos globales (botones).
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equalsIgnoreCase("iniciarDuelo")) {
            if (cDuelo != null) {
                cDuelo.iniciarDuelo();
            } else {
                System.err.println("⚠️ ControlDuelo no está conectado aún.");
            }
        } else if (cmd.equalsIgnoreCase("salir")) {
            System.exit(0);
        }
    }
}
