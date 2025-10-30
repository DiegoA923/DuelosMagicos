package udistrital.avanzada.duelosmagicos.Control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * GestorArchivoPropiedades
 * <p>
 * Clase que gestiona el archivo propiedades para el acceso a su contenido
 * </p>
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

    /**
     * Metodo para obtener propiedad requerida
     *
     * @param propiedad
     * @return String con el valor si no null
     */
    public String getProperty(String propiedad) {
        return propiedades.getProperty(propiedad);
    }
}
