package udistrital.avanzada.duelosmagicos.Control;

import javax.swing.*;
import udistrital.avanzada.duelosmagicos.Modelo.Mago;

/**
 * ControlDuelo Coordina un duelo entre dos magos conectando la vista
 * (PanelDuelo) con la lógica del modelo (CampoDuelo y MagoHilo).
 *
 * Gestiona la secuencia visual (mensajes, animaciones y ritmo) sin mezclar
 * lógica de combate.
 *
 * @author Diego
 * @version 2.2
 * @since 2025-10-31
 */
public class ControlDuelo implements IDueloListener {

    private final ControlVentana vista;
    private final ControlMago cMago;
    private final CampoDuelo campoDuelo;

    private int indiceActual;
    private StringBuilder historialMensajes;
    private boolean mostrandoMensaje;
    private int dueloActual;
    private int maxDuelos;

    public ControlDuelo(ControlVentana vista, ControlMago cMago) {
        this.vista = vista;
        this.cMago = cMago;
        this.mostrandoMensaje = false;
        this.campoDuelo = new CampoDuelo(this);
        this.historialMensajes = new StringBuilder();
        this.dueloActual = 1;
    }

    public void prepararSiguienteDuelo() {
        if (dueloActual > maxDuelos) {
            mostrarMensajeTemporal("⚔️ No hay más duelos disponibles.");
            vista.setBotonIniciarActivo(false);
            return;
        }
        Mago ganadorAnterior = campoDuelo.getGanador();
        Mago mago1 = (ganadorAnterior != null) ? ganadorAnterior : cMago.getMago(indiceActual);
        Mago mago2 = cMago.getMago(indiceActual+1);

        campoDuelo.setMagos(mago1, mago2);
        vista.setNombresMagos(mago1.getNombre(), mago2.getNombre());

        historialMensajes.setLength(0);
        vista.mostrarMensajeDuelo("");
        vista.mostrarMensajeDuelo("");
        vista.mostrarMensajeDuelo("");
        vista.mostrarMensajeDuelo("");
        vista.mostrarMensajeDuelo("");
        vista.mostrarMensajeDuelo("Preparando duelo: " + mago1.getNombre() + " 🆚 " + mago2.getNombre());
    }

    void iniciarDuelo() {
        vista.setBotonIniciarActivo(false);
        historialMensajes.setLength(0);        
        vista.mostrarMensajeDuelo("🔥 ¡El duelo ha comenzado!");       
        campoDuelo.iniciarDuelo();
    }

    // ==================================================
    // EVENTOS DEL DUELO (notificados por el modelo)
    // ==================================================
    @Override
    public void onGanador(String nombre, String casa, int hechizosLanzados, int puntajeActual) {
        SwingUtilities.invokeLater(() -> {
            String mensaje = "🏆 Ganador: " + nombre + " (" + casa + ")\n"
                    + "Hechizos lanzados: " + hechizosLanzados + "\n"
                    + "Puntaje total: " + puntajeActual;
            mostrarMensajeTemporal(mensaje);

            JOptionPane.showMessageDialog(
                    null,
                    mensaje,
                    (dueloActual == maxDuelos) ? "Toneo finalizado": "Duelo finalizado",
                    JOptionPane.INFORMATION_MESSAGE
            );
            
            vista.setBotonIniciarActivo(true);
            indiceActual++;
            dueloActual++;
            if (dueloActual > maxDuelos) {
                vista.mostrarBotonSalir();                
            } else {
                vista.mostrarBotonSiguienteDuelo();                                
            }            
        });
    }

    @Override
    public void onLanzarHechizo(int indice, String nombreHechizo, int puntaje) {
        // Nombre real del mago
        String nombre = vista.getNombreMago(indice);
        int objetivo = (indice == 1) ? 2 : 1;

        // 1) Mostrar mensaje (rápido)
        mostrarMensajeSecuencial("🔥 " + nombre + " lanzó " + nombreHechizo + " (" + puntaje + " pts)");

        // 2) Esperar un poco para que el usuario lea el mensaje y luego animar
        Timer t = new Timer(350, e -> {
            // animación visual (PanelDuelo mueve el hechizo)
            SwingUtilities.invokeLater(() -> vista.animarLanzamiento(indice));
        });
        t.setRepeats(false);
        t.start();

        // 3) Mostrar daño flotante *con un ligero delay* (para que coincida con impacto)
        Timer t2 = new Timer(700, e2 -> {
            SwingUtilities.invokeLater(() -> vista.mostrarDanioFlotante(indice, puntaje));
        });

        t2.setRepeats(false);
        t2.start();
    }

    @Override
    public void onAturdir(int indice) {
        String nombre = vista.getNombreMago(indice);
        SwingUtilities.invokeLater(() -> {
            mostrarMensajeSecuencial("💫 " + nombre + " quedó aturdido!");
            vista.mostrarAturdido(indice, 1000);
        });
    }

    @Override
    public void onDespertar(int indice) {
        String nombre = vista.getNombreMago(indice);
        SwingUtilities.invokeLater(() -> {
            mostrarMensajeSecuencial("✨ " + nombre + " se recupera!");
        });
    }

    // ==================================================
    // PRESENTACIÓN DE MENSAJES
    // ==================================================
    /**
     * Muestra un mensaje temporal que desaparece tras 2.5 segundos.
     */
    private void mostrarMensajeTemporal(String texto) {
        vista.mostrarMensajeDuelo(texto);
        Timer limpiar = new Timer(2500, e -> vista.mostrarMensajeDuelo(""));
        limpiar.setRepeats(false);
        limpiar.start();
    }

    /**
     * Muestra mensajes con ritmo, evitando que se amontonen. Cada mensaje
     * espera brevemente antes del siguiente.
     */
    private synchronized void mostrarMensajeSecuencial(String nuevo) {
        if (mostrandoMensaje) {
            // Esperar y reintentar en 600ms
            Timer delay = new Timer(600, e -> mostrarMensajeSecuencial(nuevo));
            delay.setRepeats(false);
            delay.start();
            return;
        }

        mostrandoMensaje = true;

        historialMensajes.append(nuevo).append("\n");

        // Limitar a 5 líneas recientes
        String[] lineas = historialMensajes.toString().split("\n");
        if (lineas.length > 5) {
            historialMensajes = new StringBuilder();
            for (int i = lineas.length - 5; i < lineas.length; i++) {
                historialMensajes.append(lineas[i]).append("\n");
            }
        }

        vista.mostrarMensajeDuelo(historialMensajes.toString());

        // Esperar 1 segundo antes del siguiente mensaje
        Timer pausa = new Timer(1000, e -> mostrandoMensaje = false);
        pausa.setRepeats(false);
        pausa.start();
    }

    public int getDueloActual() {
        return dueloActual;
    }

    public int getMaxDuelos() {
        return maxDuelos;
    }

    public void setMaxDuelos(int maxDuelos) {
        this.maxDuelos = maxDuelos;
    }
}
