package udistrital.avanzada.duelosmagicos.Modelo;

import java.util.ArrayList;

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
    private ArrayList<Hechizo> hechizos;

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

    public void setHechizos(ArrayList<Hechizo> hechizos) {
        this.hechizos = hechizos;
    }        

    /**
     * Obtener nombre hechizo con incide
     *
     * @param indice
     * @return
     */
    public String getHechizoNombre(int indice) {
        return hechizos.get(indice).getNombre();
    }

    /**
     * Obtener putnos de hechizo con incide
     *
     * @param indice
     * @return
     */
    public int getHechizoPuntos(int indice) {
        return hechizos.get(indice).getPuntos();
    }

    /**
     * Obtener la cantidad de hechizos que tiene el mago
     *
     * @return cantidad hechizos
     */
    public int getCantHechizos() {
        return hechizos.size();
    }
}
