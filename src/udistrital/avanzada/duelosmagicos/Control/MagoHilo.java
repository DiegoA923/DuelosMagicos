package udistrital.avanzada.duelosmagicos.Control;

import java.util.Random;
import udistrital.avanzada.duelosmagicos.Control.CampoDuelo

/**
 * Clase MagoHilo.
 * <p>
 * Clase envoltorio para la clase Mago con objeto de tener concurrencia en la aplicacion
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-10-27
 */
public class MagoHilo implements Runnable {
    private int puntos;
    private boolean aturdido;
    private MagoHilo rival;
    //cantidad de hechizos lanzados
    private int hechizosLanzados;
    private CampoDuelo campo;
    private Mago yo;
    private ArrayList<Hechizo> hechizos;

    /**
     * Constructor vacio
     */
    public MagoHilo() {
    }

    public MagoHilo(mago) {
        this.mago = mago;
        this.puntos = 0;
        this.aturdido = false;
        this.rival = null;
        this.hechizosLanzados = 0;
        this.campo = null;
    }

    public Hechizo lanzarHechizo() {
        Hechizo hechizo = getHechizoRandom();
        if (hechizo != null) {
            this.puntos += puntos;
            rival.setAturdido(true);
        }
        return hechizo;
    }

    public boolean estaAturdido() {
        return aturdido;
    }

    public void setAturdido(boolean aturdido) {
        this.aturdido = aturdido;
    }

    public void setCampo(CampoDuelo campo) {
        this.campo = campo;
    }

    public Mago getRival() {
        return rival;
    }

    public void setRival(Mago rival) {
        this.rival = rival;
    }

    public void aturdirRival() {
        this.rival.setAturdido(true);
    }

    /**
     * Método para obtener hechizo aleatorio
     * 
     * @return Hechizo
     */
    private Hechizo getHechizoRandom() {
        if(hechizos.isEmpty()){
            return null;
        }
        int indice = new Random().nextInt(hechizos.size());        
        return hechizos.get(indice);
    }

    @Override
    public void run() {
        while (puntos < 250) {
            campo.habilitarLanzar(this);
        }
    }
}
