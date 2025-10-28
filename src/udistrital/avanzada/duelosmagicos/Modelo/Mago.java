package udistrital.avanzada.duelosmagicos.Modelo;

/**
 * Clase Mago.
 * <p>
 * Clase que representa a un mago que participa en el torneo
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-25
 */
public class Mago {

    private String nombre;
    private String casa;

    public Mago(String nombre, String casa) {
        this.nombre = nombre;
        this.casa = casa;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCasa() {
        return casa;
    }
}
