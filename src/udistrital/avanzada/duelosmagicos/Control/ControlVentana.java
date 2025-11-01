package udistrital.avanzada.duelosmagicos.Control;

import java.awt.Color;
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
public class ControlVentana implements ActionListener, IDueloVista {

    private final VentanaPrincipal ventana;
    private final PanelDuelo panelDuelo;
    private final ControlPrincipal cPrincipal;

    /**
     * Constructor: crea la ventana principal e inyecta el panel de duelo.
     *
     * @param cPrincipal controlador principal del sistema
     */
    public ControlVentana(ControlPrincipal cPrincipal) {
        this.cPrincipal = cPrincipal;
        this.panelDuelo = new PanelDuelo();
        panelDuelo.getBotonIniciarDuelo().addActionListener(this);
        panelDuelo.getBotonIniciarDuelo().setActionCommand("iniciarDuelo");
        this.ventana = new VentanaPrincipal(panelDuelo);
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
    public void mostrarMensaje(String titulo, String mensaje) {
        ventana.mostrarMensajeEmergente(titulo, mensaje);
    }

    /**
     * Mostrar mensaje error en consola
     *
     * @param mensaje
     */
    public void mostrarErrorConsola(String mensaje) {
        //delegar a ventana
        ventana.mostrarErrorConsola(mensaje);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mostrarBotonIniciarDuelo() {
        panelDuelo.getBotonIniciarDuelo().setText("Iniciar Duelo");
        panelDuelo.getBotonIniciarDuelo().setActionCommand("iniciarDuelo");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mostrarBotonSiguienteDuelo() {
        panelDuelo.getBotonIniciarDuelo().setText("Siguiente Duelo");
        panelDuelo.getBotonIniciarDuelo().setActionCommand("siguienteDuelo");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mostrarBotonSalir() {
        panelDuelo.getBotonIniciarDuelo().setText("Salir");
        panelDuelo.getBotonIniciarDuelo().setActionCommand("salir");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBotonIniciarActivo(boolean activo) {
        panelDuelo.setBotonIniciarActivo(activo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mostrarMensajeDuelo(String mensaje) {
        panelDuelo.mostrarMensajeDuelo(mensaje);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNombreMago(int indice) {
        return panelDuelo.getNombreMago(indice);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void animarLanzamiento(int indice) {
        panelDuelo.animarLanzamiento(indice);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mostrarDanioFlotante(int indice, int puntos) {
        panelDuelo.mostrarAturdido(indice, puntos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mostrarAturdido(int indice, int duracionMs) {
        panelDuelo.mostrarAturdido(indice, duracionMs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mostrarMensajeDuelo(String mensaje, Color colorBase, Color colorResaltado) {
        panelDuelo.mostrarMensajeDuelo(mensaje, colorBase, colorResaltado);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNombresMagos(String nombre1, String nombre2) {
        panelDuelo.setNombresMagos(nombre1, nombre2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mostrarGanador(String mensaje, String titulo) {
        ventana.mostrarMensajeEmergente(titulo, mensaje);
    }

    /**
     * Manejo de eventos globales (botones).
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equalsIgnoreCase("iniciarDuelo")) {
            cPrincipal.iniciarDuelo();
        } else if (cmd.equalsIgnoreCase("siguienteDuelo")) {
            cPrincipal.prepararSiguienteDuelo();
            mostrarBotonIniciarDuelo();
        } else if (cmd.equalsIgnoreCase("salir")) {
            System.exit(0);
        }
    }
}
