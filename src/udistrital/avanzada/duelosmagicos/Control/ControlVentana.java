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
 * Controlador que gestiona la ventana principal y las acciones del usuario.
 * </p>
 *
 * @author Mauricio
 * @since 2025-10-29
 */
public class ControlVentana implements ActionListener {

    private VentanaPrincipal ventana;
    private PanelDuelo panelDuelo;
    private ControlPrincipal controlPrincipal;

    public ControlVentana(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        ventana = new VentanaPrincipal();
        panelDuelo = new PanelDuelo(this);
        ventana.add(panelDuelo);
    }

    public void mostrarVentanaPrincipal() {
        ventana.mostrarVentana(true);
    }

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

    // 🔹 Métodos que el ControlPrincipal usa para actualizar la vista
    public void setDatosMago(int indice, String nombre, String casa) {
        panelDuelo.setDatosMago(indice, nombre, casa);
    }

    public void actualizarMago(int indice, String hechizo, int puntos, int lanzados) {
        panelDuelo.mostrarHechizo(indice, hechizo);
        panelDuelo.actualizarDatosMago(indice, puntos, lanzados);
    }

    public void mostrarGanador(String nombre, String casa, int puntos) {
        panelDuelo.mostrarGanador(nombre, casa, puntos);
    }

    public void mostrarAccion(String texto) {
        System.out.println(texto); // También puedes crear una JTextArea para ver esto en pantalla si lo deseas.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        switch (cmd.toLowerCase()) {
            case "iniciarduelo":
                panelDuelo.reiniciarEstado();
                controlPrincipal.iniciarDuelo();
                break;
            case "salir":
                System.exit(0);
                break;
        }
    }
}
