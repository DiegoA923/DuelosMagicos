package udistrital.avanzada.duelosmagicos.Control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import udistrital.avanzada.duelosmagicos.Modelo.Hechizo;
import udistrital.avanzada.duelosmagicos.Modelo.Mago;

/**
 *
 * @author Mauricio
 * @since 2025-10-26
 */
public class GestorArchivoPropiedades {

    private File archivo;
    private Properties propiedades;
    private InputStream entrada;

    public GestorArchivoPropiedades() {
        this.archivo = null;
        this.propiedades = new Properties();
        this.entrada = null;
    }

    /**
     * Metodo para conectar al archivo de propiedades
     *
     * @return true si se logro la conexcion sino false
     */
    public boolean cargar() {
        try {
            entrada = new FileInputStream(archivo);
            propiedades.load(entrada);
            return true;
        } catch (IOException | NullPointerException ex) {
            return false;
        }
    }

    /**
     * Metodo para obtener los magos del archivo de propiedades antes se debio
     * llamar el metodo cargar
     *
     * @return Lista de magos
     */
    public ArrayList<Mago> getMagos() {
        ArrayList<Mago> magos = new ArrayList<>();
        try {
            int n = Integer.parseInt(propiedades.getProperty("nHechizos"));
            for (int i = 1; i < n + 1; i++) {
                String nombre = propiedades.getProperty("mago" + i + ".nombre");
                String casa = propiedades.getProperty("mago" + i + ".nombre");
                // si las propiedades son validas crear el objeto hechizo y añadir a la lista
                if ((casa != null && !casa.isEmpty()) && (nombre != null && !nombre.isEmpty())) {
                    Mago mago = new Mago(nombre, casa);
                    magos.add(mago);
                }
            }
        } catch (NumberFormatException | NullPointerException e) {
            // No se pudo obtener la cantidad de magos en el archivo
        }
        return magos;
    }

    /**
     * Metodo para obtener los hechizos del archivo de propiedades antes se debio
     * llamar el metodo cargar
     *
     * @return Lista de magos
     */
    public ArrayList<Hechizo> getHechizos() {
        ArrayList<Hechizo> hechizos = new ArrayList<>();
        try {
            int n = Integer.parseInt(propiedades.getProperty("nHechizos"));
            for (int i = 1; i < n + 1; i++) {
                String nombre = propiedades.getProperty("hechizo" + i + ".nombre");
                int puntos = 0;
                try {
                    puntos = Integer.parseInt(propiedades.getProperty("hechizo" + i + ".puntos"));
                } catch (NumberFormatException e) {
                    // No hay puntos validos entonces continuar a la siguiente iteracion
                    continue;
                }
                // si las propiedades son validas crear el objeto hechizo y añadir a la lista
                if ((puntos >= 5 && puntos <= 25) && (nombre != null && !nombre.isEmpty())) {
                    Hechizo hechizo = new Hechizo(nombre, puntos);
                    hechizos.add(hechizo);
                }
            }
        } catch (NumberFormatException e) {
            // No se pudo obtener la cantidad de hechizos en el archivo
        }
        return hechizos;
    }

    /**
     * Metodo para cerrar el archivo despues de hacer las operaciones requeridas
     *
     * @return true si se logro la cerrar sino false
     */
    public boolean cerrarArchivo() {
        if (entrada != null) {
            try {
                entrada.close();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metodo para asignar el archivo de propiedades
     *
     * @param archivo
     */
    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

}
