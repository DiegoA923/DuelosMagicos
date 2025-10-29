package udistrital.avanzada.duelosmagicos.Modelo;

/**
 * Clase que representa los hechizos del torneo con su nombre y nivel de puntos.
 * Esta clase será usada durante los duelos para asignar hechizos aleatorios a
 * los magos.
 *
 * @author Mauricio
 * @since 2025-25-10
 */
public class Hechizo {

    private String nombre;
    private int puntos;

    /**
     * Contructor vacio
     */
    public Hechizo() {
    }

    /**
     * Constructor con los parametros
     *
     * @param nombre
     * @param puntos cantidad de puntos
     */
    public Hechizo(String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    //Getter y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    
    @Override
    public String toString() {
       return "nombre: "+nombre+", puntos: "+puntos;
    }        
}
