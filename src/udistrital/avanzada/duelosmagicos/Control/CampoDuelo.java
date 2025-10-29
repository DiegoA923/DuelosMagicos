package udistrital.avanzada.duelosmagicos.Control;

/**
 * Campo de duelo sincroinza el lanzamiento de hechizos de los magos
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-10-27
 */
public class CampoDuelo {

    private MagoHilo mago1;
    private MagoHilo mago2;
    private int puntajeMax;

    public CampoDuelo() {
        this.mago1 = null;
        this.mago2 = null;
        this.puntajeMax = 0;
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
        puntajeMax = 0;
    }

    /**
     * Metodo sincronizado para actualizar interfaz y lanzar hechizo
     *
     * @param magoHilo el mago en turno
     */
    public synchronized void lanzarHechizo(MagoHilo magoHilo) {

        // Saber que hilo fue para actualizar en UI
        String hiloActual = Thread.currentThread().getName();
        //TODO actualizar mago actual si esta aturdido
        String[] hechizoLanzado = magoHilo.lanzarHechizo();
        String nombreHechizo = hechizoLanzado[0];
        String puntajeHechizo = hechizoLanzado[1];
        //TODO animacion de lanzar hechizo y mostrar hechizo
        //Animacion aturdido para mago rival
        if (magoHilo.getPuntos() > puntajeMax) {
            puntajeMax = magoHilo.getPuntos();
        }
        if (puntajeMax >= 250) {
            String nombre = magoHilo.getNombreMago();
            String casa = magoHilo.getCasaMago();
            int cantH = magoHilo.getHechizosLanzados();
            //Despertar a ambos en UI
            //Mostrar ganador
        }
    }

    /**
     * Obtener ganador de duelo
     *
     * @return MagoHilo que gano
     */
    public MagoHilo getGanador() {
        if (mago1 == null || mago2 == null) {
            return null;
        }
        if (mago1.getPuntos() > mago2.getPuntos()) {
            return mago1;
        } else {
            return mago2;
        }
    }

    public int getPuntajeMax() {
        return puntajeMax;
    }
}
