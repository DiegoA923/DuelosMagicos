package udistrital.avanzada.duelosmagicos.Modelo;

import java.util.Random;
import udistrital.avanzada.duelosmagicos.Control.CampoDuelo;

/**
 * Clase Mago.
 * <p>
 * Descripción:
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-25
 */
public class Mago implements Runnable {

    private String nombre;
    private String casa;
    private int puntos;
    private boolean aturdido;
    private Mago rival;
    //cantidad de hechizos lanzados
    private int hechizosLanzados;
    private CampoDuelo campo;

    /**
     * Constructor vacio
     */
    public Mago() {
    }

    public Mago(String nombre, String casa) {
        this.nombre = nombre;
        this.casa = casa;
        this.puntos = 0;
        this.aturdido = false;
        this.rival = null;
        this.hechizosLanzados = 0;
        this.campo = null;
    }

    /**
     *
     * @param puntos
     */
    public void lanzarHechizo(int puntos) {
        this.puntos += puntos;
        rival.setAturdido(true);
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

    @Override
    public void run() {
        while (puntos < 250) {
            if (aturdido) {
                try {
                    Thread.sleep(new Random().nextInt(250));
                } catch (InterruptedException ie) {
                }
                aturdido = false;
            } else {
                campo.lanzarHechizo(this);
                try {
                    Thread.sleep(new Random().nextInt(500));
                } catch (InterruptedException ie) {
                }
            }
        }
    }
}
