package udistrital.avanzada.duelosmagicos.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import udistrital.avanzada.duelosmagicos.Vista.VentanaPrincipal;

/**
 * ControlVentana
 * <p>
 * Controlador que gestiona la navegación general de la aplicación
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-26-10
 */
public class ControlVentana implements ActionListener {

    private VentanaPrincipal ventana;
    private ControlPrincipal cPrincipal;
        
    public ControlVentana(ControlPrincipal cPrincipal) {
        this.ventana = new VentanaPrincipal();
        this.cPrincipal = cPrincipal;
    }
    
    public void mostrarVentanaPrincipal() {
        ventana.mostrarVentana(true);
    }

    /**
     * Permite al usuario seleccionar un archivo propiedades y lo procesa.
     *
     * @param ruta ruta inicial del explorador de archivos
     * @param mensaje que se solicita
     * @return archivo seleccionado por usuario
     */
    public File obtenerArchivoPropiedades(String ruta, String mensaje) {
        File archivoSeleccionado = null;
        JFileChooser chooser = ventana.getFileChoser(
                mensaje,
                "properties",
                JFileChooser.FILES_ONLY,
                ruta
        );
        int seleccion = chooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            archivoSeleccionado = chooser.getSelectedFile();
        }
        return archivoSeleccionado;
    }
    
    public void mostrarMensaje(String mensaje) {
        ventana.mostrarMensajeEmergente(mensaje);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if(cmd.equalsIgnoreCase("iniciarDuelo")) {
            //llamar a metodo de control principal
        }else if(cmd.equalsIgnoreCase("salir")) {
            //metodo salir
        }
    }
}
