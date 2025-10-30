package udistrital.avanzada.duelosmagicos.Control;

import java.util.ArrayList;
import udistrital.avanzada.duelosmagicos.Modelo.Hechizo;

/**
 * ControlHechizo
 * <p>
 * Encargada del manejo de los hechizos
 * </p>
 *
 * @author Mauricio
 * @since 2025-10-30
 */
public class ControlHechizo {

    private ArrayList<Hechizo> hechizos;

    /**
     * Metodo para crear y agregar un hechizo a la lista
     *
     * @param nombre
     * @param puntos cantidad de puntos del hechizo
     */
    public void crearHechizo(String nombre, int puntos) {
        Hechizo hechizo = new Hechizo(nombre, puntos);
        hechizos.add(hechizo);
    }

    /**
     * Obtener la lista de hechizos
     *
     * @return lista de hechizos
     */
    public ArrayList<Hechizo> getHechizos() {
        return hechizos;
    }

    /**
     * Obtener la cantidad de hechizos que tiene
     *
     * @return cantidad hechizos
     */
    public int getSize() {
        return hechizos.size();
    }
    
    /**
     * Metodo para vaciar lista
     */
    public void vaciarLista() {
        this.hechizos.clear();
    }
}
