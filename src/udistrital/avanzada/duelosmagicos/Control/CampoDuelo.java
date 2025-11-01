package udistrital.avanzada.duelosmagicos.Control;

import javax.swing.SwingUtilities;
import udistrital.avanzada.duelosmagicos.Modelo.Mago;

/**
 * CampoDuelo
 * <p>
 * Coordina y sincroniza los lanzamientos de hechizos entre dos magos.
 * Implementa la lógica del duelo, mantiene el puntaje máximo y notifica eventos
 * al listener (ControlDuelo).
 * </p>
 *
 * @author Diego
 * @version 2.1
 * @since 2025-10-31
 */
public class CampoDuelo {

    private MagoHilo magoHilo1;
    private MagoHilo magoHilo2;
    private int puntajeMax;
    private IDueloListener dueloListener;

    public CampoDuelo(IDueloListener dueloListener) {
        this.dueloListener = dueloListener;
        this.puntajeMax = 0;
    }

    /**
     * Configura los magos que van a batirse en duelo.
     */
    public void setMagos(Mago mago1, Mago mago2) {
        if (estaEnDuelo()) {
            return;
        }

        magoHilo1 = new MagoHilo(mago1);
        magoHilo2 = new MagoHilo(mago2);

        magoHilo1.setName("mago1");
        magoHilo2.setName("mago2");

        magoHilo1.setCampo(this);
        magoHilo2.setCampo(this);

        magoHilo1.setRival(magoHilo2);
        magoHilo2.setRival(magoHilo1);

        puntajeMax = 0;
    }

    /**
     * Método sincronizado que ejecuta la lógica de lanzar hechizos y actualiza
     * la interfaz.
     */
    public synchronized void lanzarHechizo(MagoHilo magoHilo) {
        String hiloActual = Thread.currentThread().getName();
        int indice = hiloActual.equalsIgnoreCase("mago1") ? 1 : 2;

        // Si el mago está aturdido
        if (magoHilo.estaAturdido()) {
            SwingUtilities.invokeLater(() -> dueloListener.onAturdir(indice));
            return;
        }

        // Despierta visualmente al mago
        SwingUtilities.invokeLater(() -> dueloListener.onDespertar(indice));

        // Mientras no se haya alcanzado el puntaje de victoria
        if (puntajeMax < 250) {
            String[] hechizoLanzado = magoHilo.lanzarHechizo();
            String nombreHechizo = hechizoLanzado[0];
            int puntajeHechizo = Integer.parseInt(hechizoLanzado[1]);

            SwingUtilities.invokeLater(()
                    -> dueloListener.onLanzarHechizo(indice, nombreHechizo, puntajeHechizo)
            );
        }

        // Actualiza el puntaje máximo general
        if (magoHilo.getPuntos() > puntajeMax) {
            puntajeMax = magoHilo.getPuntos();
        }

        // Si se alcanzó o superó el puntaje para ganar
        if (puntajeMax >= 250) {
            String nombre = magoHilo.getNombreMago();
            String casa = magoHilo.getCasaMago();
            int cantHechizos = magoHilo.getHechizosLanzados();
            int puntajeFinal = magoHilo.getPuntos();

            // Mostrar visualmente el ganador
            SwingUtilities.invokeLater(()
                    -> dueloListener.onGanador(nombre, casa, cantHechizos, puntajeFinal)
            );
            dueloListener.onDespertar(1);
            dueloListener.onDespertar(2);

            detenerDuelo();
        }
    }

    /**
     * Detiene el duelo forzando la interrupción de ambos hilos.
     */
    private void detenerDuelo() {
        try {
            if (magoHilo1 != null && magoHilo1.isAlive()) {
                magoHilo1.interrupt();
            }
            if (magoHilo2 != null && magoHilo2.isAlive()) {
                magoHilo2.interrupt();
            }
        } catch (Exception e) {
            System.err.println("Error al detener hilos: " + e.getMessage());
        }
    }

    /**
     * Devuelve el mago ganador, o null si el duelo sigue en curso.
     */
    public Mago getGanador() {
        if (magoHilo1 == null || magoHilo2 == null || magoHilo1.isAlive() || magoHilo2.isAlive()) {
            return null;
        }
        return (magoHilo1.getPuntos() > magoHilo2.getPuntos()) ? magoHilo1.getMago() : magoHilo2.getMago();
    }

    /**
     * Inicia los hilos del duelo.
     */
    public void iniciarDuelo() {
        if (magoHilo1 == null || magoHilo2 == null || magoHilo1.isAlive() || magoHilo2.isAlive()) {
            return;
        }

        puntajeMax = 0;
        magoHilo1.start();
        magoHilo2.start();
    }

    /**
     * Indica si hay un duelo activo.
     */
    public boolean estaEnDuelo() {
        if (magoHilo1 != null && magoHilo2 != null) {
            return (magoHilo1.isAlive() || magoHilo2.isAlive());
        }
        return false;
    }

    public int getPuntajeMax() {
        return puntajeMax;
    }

    /**
     * Retorna los datos de un mago según su índice (0 = mago1, 1 = mago2).
     */
    public String[] getDatosMago(int indice) {
        String[] aux = new String[2];
        if (indice == 0) {
            aux[0] = magoHilo1.getNombreMago();
            aux[1] = magoHilo1.getCasaMago();
        } else {
            aux[0] = magoHilo2.getNombreMago();
            aux[1] = magoHilo2.getCasaMago();
        }
        return aux;
    }
}
