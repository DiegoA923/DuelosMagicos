package udistrital.avanzada.duelosmagicos.Control;

import java.io.File;

/**
 * Clase ControlPrincipal.
 * <p>
 * Descripción:
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-25
 */
public class ControlPrincipal implements IDueloListener {

    private ControlVentana cVentana;
    private GestorArchivoPropiedades gArchivoProps;
    private CampoDuelo campoDuelo;
    private ControlMago cMago;
    private ControlHechizo cHechizo;
    private int dueloActual;
    private int maxDuelos;

    public ControlPrincipal() {
        this.cVentana = new ControlVentana(this);
        this.campoDuelo = new CampoDuelo(this);
        this.dueloActual = 1;
        this.gArchivoProps = new GestorArchivoPropiedades();
        this.cHechizo = new ControlHechizo();
        this.cMago = new ControlMago();
        precarga();
        //probar sin boton
        //siguienteDuelo();
        //iniciarDuelo();
    }

    public void precarga() {
        //restablecemos listas para cargar nuevos datos
        cMago.vaciarLista();
        cHechizo.vaciarLista();
        File archivo = cVentana.obtenerArchivoPropiedades(
                "specs/data",
                "Eliga Archivo de propiedades valido con configuracion base"
        );
        if (archivo == null) {
            return;
        }
        gArchivoProps.setArchivo(archivo);
        boolean carga = gArchivoProps.cargar();
        if (!carga) {
            gArchivoProps.cerrarArchivo();
            cVentana.mostrarMensaje("No se pudo cargar el archivo");
            return;
        }
        int nHechizos;
        int nMagos;
        cHechizo = new ControlHechizo();
        try {
            nHechizos = Integer.parseInt(gArchivoProps.getProperty("nHechizos"));
            nMagos = Integer.parseInt(gArchivoProps.getProperty("nMagos"));
            //obtener hechizos
            for (int i = 1; i < nHechizos + 1; i++) {
                String nombre = gArchivoProps.getProperty("hechizo" + i + ".nombre");
                int puntos;
                try {
                    puntos = Integer.parseInt(gArchivoProps.getProperty("hechizo" + i + ".puntos"));
                } catch (NumberFormatException e) {
                    // No hay puntos validos entonces continuar a la siguiente iteracion
                    continue;
                }
                // si las propiedades son validas crear hechizo
                if ((puntos >= 5 && puntos <= 25) && (nombre != null && !nombre.isEmpty())) {
                    cHechizo.crearHechizo(nombre, puntos);
                }
            }
            //obtener magos
            for (int i = 1; i < nMagos + 1; i++) {
                String nombre = gArchivoProps.getProperty("mago" + i + ".nombre");
                String casa = gArchivoProps.getProperty("mago" + i + ".casa");
                // si las propiedades son validas crear mago
                if ((casa != null && !casa.isEmpty()) && (nombre != null && !nombre.isEmpty())) {
                    //Simulamos que cada mago tiene una lista diferente de hechizos
                    cMago.crearMago(nombre, casa, cHechizo.getHechizos());
                }
            }
        } catch (Exception e) {

        } finally {
            gArchivoProps.cerrarArchivo();
        }
        // verificar tamaño de lista
        if (cMago.getSize() < 2 && cHechizo.getSize() < 2) {
            //Vaciar lista para solicitara de nuevo
            cMago.vaciarLista();
            cHechizo.vaciarLista();
            cVentana.mostrarMensaje("Archivo no contiene los elementos necesarios");
            return;
        }
        this.maxDuelos = cMago.getSize() - 1;
        cVentana.mostrarVentanaPrincipal();
    }

    // antes de llamar metodo debe comprobar que hilos mago han cumplido su ciclo de vida
    public void iniciarDuelo() {
        if (cMago.getSize() < 2 || campoDuelo.estaEnDuelo()) {
            return;
        }
        // Ya no se pueden hacer más duelos
        if (dueloActual > cMago.getSize() - 1) {
            return;
        }
        campoDuelo.iniciarDuelo();
    }

    public void siguienteDuelo() {
        if (cMago.getSize() < 2 || campoDuelo.estaEnDuelo()) {
            return;
        }
        // Ya no se pueden hacer más duelos
        if (dueloActual > maxDuelos) {
            return;
        }
        MagoHilo ganador = campoDuelo.getGanador();
        campoDuelo.setMagos((ganador != null) ? ganador.getMago() : cMago.getMago(0), cMago.getMago(dueloActual));
        String[] mago1 = campoDuelo.getDatosMago(0);
        String[] mago2 = campoDuelo.getDatosMago(1);
        // Siguiente duelo
        dueloActual++;
    }
  
    @Override
    public void onGanador(String nombre, String casa, int hechizosLanzados, int puntajeActual) {
        String gano = "Gano";
        if (dueloActual == maxDuelos) {
            gano = "Torneo lo gano ";
        }
        cVentana.mostrarMensaje(
                gano
                + nombre
                + " de la casa "
                + casa
                + " con "
                + puntajeActual
                + " puntos y "
                + hechizosLanzados
                + " hechizos lanzados"
        );
    }
    
    @Override
    public void onLanzarHechizo(int indice, String nombreHechizo, int puntaje) {
        //delegar a control ventana
    }

    @Override
    public void onAturdir(int indice) {
        //delegar a control ventana
    }

    @Override
    public void onDespertar(int indice) {
        //delegar a control ventana
    }
}
