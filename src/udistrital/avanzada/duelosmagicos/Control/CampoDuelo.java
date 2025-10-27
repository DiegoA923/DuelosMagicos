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
    private MagoHilo mago1;
    private MagoHilo mago2;

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
    public void setMagos(MagoHilo mago1, MagoHilo mago2) {
        mago1.setCampo(this);
        mago1.setRival(mago2);
        mago2.setCampo(this);
        mago2.setRival(mago1);        
        this.mago1 = mago1;
        this.mago2 = mago2;        
    }
    /**
     * Metodo sincronizado para actualizar interfaz
     * 
     * @param mago el mago en turno
     */
    public synchronized void habilitarLanzar(MagoHilo magoHilo) {
        if(magoHilo.estaAturdido()) {     
            magoHilo.setAturdido(false);
            //actualizar ui para mostrar que esta aturdido
            //bloquear boton lanzar hechizo
            magoHilo.wait(new Random().nextInt(250));
        } else {
            //habilitar boton lanzar para hilo actual
        }
    }     
    //i es 0 o 1, 0 para el primer jugador, 1 para el segundo 
    public void lanzarHechizo(int i) {
        MagoHilo mh;
        if(i == 0) {
            mh = mago1;
        } else {
            mh = mago2;
        }
        Hechizo hechizo mh.lanzarHechizo();
        mh.aturdirRival();
        // mostrar en interfaz hechizo lanzado
    }

    public void setHechizos(ArrayList<Hechizo> hechizos) {
        this.hechizos = hechizos;
    }    
}
