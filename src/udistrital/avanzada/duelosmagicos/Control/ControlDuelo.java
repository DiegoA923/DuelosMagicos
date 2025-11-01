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
 * @version 2.3
 * @since 2025-10-31
 */
public class ControlDuelo implements IDueloListener {

    private final IDueloVista vista;
    private final ControlMago cMago;
    private final CampoDuelo campoDuelo;

    private int indiceActual;
    private StringBuilder historialMensajes;
    private boolean mostrandoMensaje;
    private int dueloActual;
    private int maxDuelos;

    public ControlDuelo(IDueloVista vista, ControlMago cMago) {
        this.vista = vista;
        this.cMago = cMago;
        this.mostrandoMensaje = false;
        this.campoDuelo = new CampoDuelo(this);
        this.historialMensajes = new StringBuilder();
        this.dueloActual = 1;
    }

    /**
     * Prepara los magos para el siguiente duelo.
     */
    public void prepararSiguienteDuelo() {
        if (dueloActual > maxDuelos) {
            mostrarMensajeTemporal("No hay más duelos disponibles.");
            vista.setBotonIniciarActivo(false);
            return;
        }

        Mago ganadorAnterior = campoDuelo.getGanador();
        Mago mago1 = (ganadorAnterior != null) ? ganadorAnterior : cMago.getMago(indiceActual);
        Mago mago2 = cMago.getMago(indiceActual + 1);

        campoDuelo.setMagos(mago1, mago2);
        vista.setNombresMagos(mago1.getNombre(), mago2.getNombre());

        // Limpiar historial
        historialMensajes.setLength(0);
        vista.mostrarMensajeDuelo("");
        vista.mostrarMensajeDuelo("Preparando duelo: " + mago1.getNombre() + " 🆚 " + mago2.getNombre());
    }

    /**
     * Inicia el duelo y bloquea el botón hasta finalizar.
     */
    void iniciarDuelo() {
        vista.setBotonIniciarActivo(false);
        historialMensajes.setLength(0);
        vista.mostrarMensajeDuelo("¡El duelo ha comenzado!");
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
            vista.mostrarGanador(mensaje, (dueloActual == maxDuelos) ? "Toneo finalizado" : "Duelo finalizado");
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
        String nombre = vista.getNombreMago(indice);
        int objetivo = (indice == 1) ? 2 : 1;

        // Mostrar mensaje acumulativo
        mostrarMensajeSecuencial(nombre + " lanzó " + nombreHechizo + " (" + puntaje + " pts)");

        // Esperar un poco para que el usuario lea el mensaje y luego animar
        Timer t = new Timer(350, e -> {
            // animación visual (PanelDuelo mueve el hechizo)
            SwingUtilities.invokeLater(() -> vista.animarLanzamiento(indice));
        });
        t.setRepeats(false);
        t.start();

        // Mostrar daño flotante con un ligero delay para que coincida con el impacto
        Timer t2 = new Timer(700, e2 -> {
            SwingUtilities.invokeLater(() -> vista.mostrarDanioFlotante(indice, puntaje));
        });

        t2.setRepeats(false);
        t2.start();
    }

    @Override
    public void onAturdir(int indice) {
        SwingUtilities.invokeLater(() -> vista.mostrarAturdido(indice, 1000));
    }

    @Override
    public void onDespertar(int indice) {
        SwingUtilities.invokeLater(() -> {
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
        // Ya no se espera ni se retrasa ningún mensaje
        historialMensajes.append(nuevo).append("\n");

        // Mantener solo las últimas 5 líneas visibles
        String[] lineas = historialMensajes.toString().split("\n");
        if (lineas.length > 5) {
            historialMensajes = new StringBuilder();
            for (int i = lineas.length - 5; i < lineas.length; i++) {
                historialMensajes.append(lineas[i]).append("\n");
            }
        }

        // Mostrar el mensaje inmediatamente
        SwingUtilities.invokeLater(() -> vista.mostrarMensajeDuelo(historialMensajes.toString()));
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
