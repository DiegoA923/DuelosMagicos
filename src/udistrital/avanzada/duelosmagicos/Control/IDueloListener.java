package udistrital.avanzada.duelosmagicos.Control;

/**
 * Interfaz para quienes necesiten escuchar los eventos del juego para actualizar
 * la interfaz grafica
 *
 * @author Mauricio
 * @since 30-10-2025
 */
public interface IDueloListener {

    /**
     * Cuando hay un ganador
     *
     * @param nombre del mago
     * @param casa donde pertenece
     * @param hechizosLanzados cantidad de hechizos lanzados
     * @param puntajeActual
     */
    void onGanador(String nombre, String casa, int hechizosLanzados, int puntajeActual);

    /**
     * Cuando lanzan un hechizo
     *
     * @param indice el mago que lo lanzo
     * @param nombreHechizo
     * @param puntaje del hechizo
     */
    void onLanzarHechizo(int indice, String nombreHechizo, int puntaje);

    /**
     * Cuando se aturde un mago
     *
     * @param indice del mago a aturdir
     */
    void onAturdir(int indice);

    /**
     * Cuando se despierta un mago
     *
     * @param indice del mago a despertar
     */
    void onDespertar(int indice);
}
