package udistrital.avanzada.duelosmagicos.Control;

import java.util.ArrayList;
import java.util.Random;
import udistrital.avanzada.duelosmagicos.Modelo.Hechizo;
import udistrital.avanzada.duelosmagicos.Modelo.Mago;

/**
 * Campo de duelo sincroinza el lanzamiento de hechizos de los magos
 * 
 * @author Mauricio
 * @version 1.0
 * @since 2025-10-27
 */
public class CampoDuelo {
    private ArrayList<Hechizo> hechizos;
    private Mago mago1;
    private Mago mago2;

    public CampoDuelo() {
        mago1 = null;
        mago2 = null;
        hechizos = new ArrayList<>();
    }
    /**
     * Configurar quienes van a batirse en duelo
     * 
     * @param mago1
     * @param mago2 
     */
    public void setMagos(Mago mago1, Mago mago2) {
        mago1.setCampo(this);
        mago1.setRival(mago2);
        mago2.setCampo(this);
        mago2.setRival(mago1);        
        this.mago1 = mago1;
        this.mago2 = mago2;        
    }
    /**
     * Metodo sincronizado para lanzar hechizo y actualizar interfaz
     * 
     * @param mago el mago en turno
     */
    public synchronized void lanzarHechizo(Mago mago) {
        Hechizo hechizo = obtenerHechizoRandom();
        mago.lanzarHechizo(hechizo.getPuntos());
        //llamar metodo para actualizar la interfaz grafica
    }
     
    /**
     * Método para obtener hechizo aleatorio
     * 
     * @return Hechizo
     */
    private Hechizo obtenerHechizoRandom() {
        if(hechizos.isEmpty()){
            return null;
        }
        int indice = new Random().nextInt(hechizos.size());        
        return hechizos.get(indice);
    }

    public void setHechizos(ArrayList<Hechizo> hechizos) {
        this.hechizos = hechizos;
    }    
}
