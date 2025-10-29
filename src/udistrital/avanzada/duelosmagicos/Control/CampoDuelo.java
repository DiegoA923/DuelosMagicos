package udistrital.avanzada.duelosmagicos.Control;

public class CampoDuelo {

    private MagoHilo mago1;
    private MagoHilo mago2;
    private int puntajeMax;
    private ControlPrincipal controlPrincipal; // referencia para actualizar GUI

    public CampoDuelo(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.mago1 = null;
        this.mago2 = null;
        this.puntajeMax = 0;
    }

    /**
     * Configura los magos que participarán en el duelo
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
     * Sincroniza el lanzamiento de hechizos entre ambos magos.
     * Se ejecuta desde los hilos de cada mago.
     */
    public synchronized void lanzarHechizo(MagoHilo magoHilo) {
        if (magoHilo.estaAturdido()) {
            controlPrincipal.mostrarAccion(magoHilo.getNombreMago() + " está aturdido 🌀");
            return;
        }

        // Ejecutar lanzamiento
        String[] hechizoLanzado = magoHilo.lanzarHechizo();
        if (hechizoLanzado[0] == null) {
            return; // no se pudo lanzar hechizo
        }

        String nombreHechizo = hechizoLanzado[0];
        int puntosHechizo = Integer.parseInt(hechizoLanzado[1]);

        // Determinar qué mago lanzó el hechizo (1 o 2)
        int indice = (magoHilo == mago1) ? 1 : 2;

        // 🔹 Actualizar GUI con hechizo, puntos y cantidad de lanzamientos
        controlPrincipal.actualizarVista(
                indice,
                nombreHechizo,
                magoHilo.getPuntos(),
                magoHilo.getHechizosLanzados()
        );

        // Mostrar acción en el panel o log
        controlPrincipal.mostrarAccion(
                magoHilo.getNombreMago() + " lanzó " + nombreHechizo + " (" + puntosHechizo + " pts)"
        );

        // Actualizar el puntaje máximo alcanzado en el duelo
        if (magoHilo.getPuntos() > puntajeMax) {
            puntajeMax = magoHilo.getPuntos();
        }

        // Verificar si alguien ganó
        if (puntajeMax >= 250) {
            String nombre = magoHilo.getNombreMago();
            String casa = magoHilo.getCasaMago();
            int puntos = magoHilo.getPuntos();
            int hechizos = magoHilo.getHechizosLanzados();

            // 🔹 Mostrar al ganador en la interfaz
            controlPrincipal.mostrarGanador(nombre, casa, puntos);

            // También puedes mostrarlo en la consola para depuración
            System.out.println("🏆 Gana " + nombre + " (" + casa + ") con " + puntos + " puntos y "
                    + hechizos + " hechizos lanzados.");
        }
    }

    /**
     * Devuelve el mago con más puntos.
     */
    public MagoHilo getGanador() {
        if (mago1 == null || mago2 == null) return null;
        return (mago1.getPuntos() > mago2.getPuntos()) ? mago1 : mago2;
    }

    public int getPuntajeMax() {
        return puntajeMax;
    }
}
