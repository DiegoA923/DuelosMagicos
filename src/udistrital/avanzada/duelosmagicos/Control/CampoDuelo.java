package udistrital.avanzada.duelosmagicos.Control;

import udistrital.avanzada.duelosmagicos.Modelo.Mago;

/**
 * Campo de duelo sincroinza el lanzamiento de hechizos de los magos
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-10-27
 */
public class CampoDuelo {

    private MagoHilo magoHilo1;
    private MagoHilo magoHilo2;
    private int puntajeMax;
    private IDueloListener dueloListener;

    public CampoDuelo(IDueloListener dueloListener) {
        this.magoHilo1 = null;
        this.magoHilo2 = null;
        this.puntajeMax = 0;
        this.dueloListener = dueloListener;
    }

    /**
     * Configurar quienes van a batirse en duelo
     *
     * @param mago1
     * @param mago2
     */
    public void setMagos(Mago mago1, Mago mago2) {
        if (estaEnDuelo()) {
            return;
        }
        magoHilo1 = new MagoHilo(mago1);
        magoHilo2 = new MagoHilo(mago1);
        magoHilo1.setName("mago1");
        magoHilo2.setName("mago2");
        magoHilo1.setCampo(this);
        magoHilo1.setRival(magoHilo2);
        magoHilo2.setCampo(this);
        magoHilo2.setRival(magoHilo1);
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
        int indice = hiloActual.equalsIgnoreCase("mago1") ? 1 : 2;
        if (magoHilo.estaAturdido()) {
            //animacion aturdido
            dueloListener.onAturdir(indice);
            System.out.println("Aturdido " + hiloActual);
            System.out.println("------------------");
            return;
        }
        //animacion despertar mago si esta aturdido
        dueloListener.onDespertar(indice);
        if (puntajeMax < 250) {
            System.out.println("despertar " + hiloActual);
            String[] hechizoLanzado = magoHilo.lanzarHechizo();
            String nombreHechizo = hechizoLanzado[0];
            int puntajeHechizo = Integer.parseInt(hechizoLanzado[1]);
            //TODO animacion de lanzar hechizo y mostrar hechizo  
            dueloListener.onLanzarHechizo(indice, nombreHechizo, puntajeHechizo);
            //Animacion aturdido para mago rival
            System.out.println("hechizo " + nombreHechizo + " " + hiloActual);
            System.out.println("puntos " + magoHilo.getPuntos());
        }
        if (magoHilo.getPuntos() > puntajeMax) {
            puntajeMax = magoHilo.getPuntos();
        }
        if (puntajeMax >= 250) {
            String nombre = magoHilo.getNombreMago();
            String casa = magoHilo.getCasaMago();
            int cantH = magoHilo.getHechizosLanzados();
            dueloListener.onGanador(nombre, casa, cantH, puntajeMax);
            System.out.println("gana " + nombre + "con " + magoHilo.getPuntos() + " " + cantH);
            dueloListener.onDespertar(1);
            dueloListener.onDespertar(2);
            //Despertar a ambos en UI
            //Mostrar ganador
        }
        System.out.println("------------------");
    }

    /**
     * Obtener ganador de duelo
     *
     * @return MagoHilo que gano si no null
     */
    public MagoHilo getGanador() {
        //si hilos no exiten o siguen vivos retornar null porque aun no hay ganador        
        if (magoHilo1 == null || magoHilo2 == null || magoHilo1.isAlive() || magoHilo2.isAlive()) {
            return null;
        }
        if (magoHilo1.getPuntos() > magoHilo2.getPuntos()) {
            return magoHilo1;
        } else {
            return magoHilo2;
        }
    }

    /**
     * metodo para iniciar los hilos
     */
    public void iniciarDuelo() {
        //si hilos no exiten o siguen vivos retornar
        if (magoHilo1 == null || magoHilo2 == null || magoHilo1.isAlive() || magoHilo2.isAlive()) {
            return;
        }
        puntajeMax = 0;
        magoHilo1.start();
        magoHilo2.start();
    }

    /**
     * Comprueba que hay un duelo activo
     *
     * @return true si hay duelo sino false
     */
    public boolean estaEnDuelo() {
        if (magoHilo1 != null && magoHilo2 != null) {
            return !(!magoHilo1.isAlive() && !magoHilo2.isAlive());
        }
        return false;
    }

    /**
     * Metodo para obtener el puntaje maximo de la partida
     *
     * @return
     */
    public int getPuntajeMax() {
        return puntajeMax;
    }

    /**
     * Metodo para obtener datos de un mago con un indice
     *
     * @param indice 0 para el mago1 y 1 para el mago2
     * @return array de string posicion 0 con el nombre, posicion 1 con la casa
     * a la que pertenece
     */
    public String[] getDatosMago(int indice) {
        String[] aux = new String[2];
        if (indice == 0) {
            aux[0] = magoHilo1.getNombreMago();
            aux[1] = magoHilo1.getCasaMago();
        } else {
            aux[0] = magoHilo2.getNombreMago();
            aux[1] = magoHilo2.getCasaMago();
        }
        return aux;
    }
}
