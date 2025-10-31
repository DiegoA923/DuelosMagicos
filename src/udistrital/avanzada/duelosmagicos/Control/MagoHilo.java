package udistrital.avanzada.duelosmagicos.Control;

import java.util.Random;
import udistrital.avanzada.duelosmagicos.Modelo.Mago;

/**
 * Clase MagoHilo.
 * <p>
 * Clase envoltorio para la clase {@link Mago} para la concurrencia
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-10-27
 */
public class MagoHilo extends Thread {

    private int puntos;
    private boolean aturdido;
    private MagoHilo rival;
    //cantidad de hechizos lanzados
    private int hechizosLanzados;
    private CampoDuelo campo;
    private Mago mago;
   
    /**
     * Contructor con los parametros
     *
     * @param mago la clase modelo que envuelve
     */
    public MagoHilo(Mago mago) {
        this.mago = mago;
        this.puntos = 0;
        this.aturdido = false;
        this.rival = null;
        this.hechizosLanzados = 0;
        this.campo = null;
    }

    /**
     * Metodo sincornizado para lanzar hechizo
     *
     * @return Arreglo de String posicion cero nombre del hechizo, posicion 1
     * puntaje del hechizo
     */
    public synchronized String[] lanzarHechizo() {
        String[] resultado = new String[2];
        int indice = new Random().nextInt(mago.getCantHechizos());
        if (mago.getCantHechizos() > 0) {
            this.puntos += mago.getHechizoPuntos(indice);
            this.hechizosLanzados++;
            resultado[0] = mago.getHechizoNombre(indice);
            resultado[1] = String.valueOf(mago.getCantHechizos());
            aturdirRival();
        }
        return resultado;
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

    public MagoHilo getRival() {
        return rival;
    }

    public void setRival(MagoHilo rival) {
        this.rival = rival;
    }

    public synchronized void aturdirRival() {
        this.rival.setAturdido(true);
    }

    public int getPuntos() {
        return puntos;
    }

    public Mago getMago() {
        return mago;
    }

    public int getHechizosLanzados() {
        return hechizosLanzados;
    }

    public String getNombreMago() {
        return mago.getNombre();
    }

    public String getCasaMago() {
        return mago.getCasa();
    }
    
    @Override
    public void run() {
        //Si se alcanzo el puntaje maximo terminar
        while (campo.getPuntajeMax() < 250) {
            if (aturdido) {
                try {
                    //lanzar hechizo comprueba estado aturdido
                    campo.lanzarHechizo(this);
                    aturdido = false;
                    Thread.sleep(new Random().nextInt(250));
                } catch (InterruptedException ex) {
                }
            } else {
                try {
                    campo.lanzarHechizo(this);
                    Thread.sleep(new Random().nextInt(500));
                } catch (InterruptedException ex) {
                }
            }

        }
    }
}
