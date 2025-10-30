package udistrital.avanzada.duelosmagicos.Control;

import java.util.ArrayList;
import udistrital.avanzada.duelosmagicos.Modelo.Hechizo;
import udistrital.avanzada.duelosmagicos.Modelo.Mago;

/**
 * ControlHechizo
 * <p>
 * Encargada del manejo de los magos y su acceso
 * </p>
 *
 * @author Mauricio
 * @since 2025-10-30
 */
public class ControlMago {

    private ArrayList<Mago> magos;

    public ControlMago() {
        this.magos = new ArrayList<>();
    }

    /**
     * Metodo para crear un mago y añadirlo a la lista
     *
     * @param nombre
     * @param casa
     * @param hechizos lista de hechizos que tiene
     */
    public void crearMago(String nombre, String casa, ArrayList<Hechizo> hechizos) {
        Mago mago = new Mago(nombre, casa);
        mago.setHechizos(hechizos);
        magos.add(mago);
    }

    /**
     * Metodo para vaciar lista
     */
    public void vaciarLista() {
        this.magos.clear();
    }

    /**
     * Obtener un mago en especifico por el indice
     *
     * @param indice posicion del mago
     * @return Mago
     */
    public Mago getMago(int indice) {
        if (indice > magos.size()) {
            return null;
        }
        return magos.get(indice);
    }

    /**
     * Metodo para obtener la cantidad de magos
     *
     * @return cantidad magos
     */
    public int getSize() {
        return magos.size();
    }
}
