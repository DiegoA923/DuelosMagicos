package udistrital.avanzada.duelosmagicos.Control;

import java.awt.Color;

/**
 *
 * @author Mauricio
 */
public interface IDueloVista {

    /**
     * mostar el boton para iniciar duelo
     */
    public void mostrarBotonIniciarDuelo();

    /**
     * mostar el boton para pasar al siguiente duelo
     */
    public void mostrarBotonSiguienteDuelo();

    /**
     * mostrar boton salir del duelo
     */
    public void mostrarBotonSalir();

    /**
     * habilitar boton iniciar duelo
     *
     * @param activo
     */
    public void setBotonIniciarActivo(boolean activo);

    /**
     * mostrar mensaje de duelo
     *
     * @param mensaje
     */
    public void mostrarMensajeDuelo(String mensaje);

    /**
     * obtener nombre de un mago por indice
     *
     * @param indice
     * @return
     */
    public String getNombreMago(int indice);

    /**
     * Animar lanzamiento de hechizo
     *
     * @param indice del mago a lanzar
     */
    public void animarLanzamiento(int indice);

    /**
     * mostrar puntos hechos
     *
     * @param indice mago donde mostrar
     * @param puntos puntos hechos
     */
    public void mostrarDanioFlotante(int indice, int puntos);

    /**
     * mostrar un mago aturdido
     *
     * @param indice
     * @param duracionMs
     */
    public void mostrarAturdido(int indice, int duracionMs);

    /**
     * mostrar un mensaje de lo sucedido en el duelo
     *
     * @param mensaje
     * @param colorBase
     * @param colorResaltado
     */
    public void mostrarMensajeDuelo(String mensaje, Color colorBase, Color colorResaltado);

    /**
     * cambiar nombres de magos en duelo
     *
     * @param nombre1
     * @param nombre2
     */
    public void setNombresMagos(String nombre1, String nombre2);

    /**
     * Mostrar quien gano el duelo
     *
     * @param mensaje contiene quien gano
     * @param titulo el titulo a mostrar
     */
    public void mostrarGanador(String mensaje, String titulo);
}
