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
    /**
 * Metodo sincronizado para lanzar hechizo
 *
 * @return Arreglo de String: [0] = nombre del hechizo, [1] = puntaje del hechizo
 */
public synchronized String[] lanzarHechizo() {
    String[] resultado = new String[2];
    if (mago.getCantHechizos() > 0) {
        int indice = new Random().nextInt(mago.getCantHechizos());
        int puntosHechizo = mago.getHechizoPuntos(indice);
        String nombreHechizo = mago.getHechizoNombre(indice);

        // actualizar estadísticas
        this.puntos += puntosHechizo;
        this.hechizosLanzados++;
        aturdirRival();

        // devolver datos del hechizo
        resultado[0] = nombreHechizo;
        resultado[1] = String.valueOf(puntosHechizo);
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
