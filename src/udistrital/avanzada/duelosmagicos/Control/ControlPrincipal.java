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
public class ControlPrincipal {

    private ControlVentana cVentana;
    private GestorArchivoPropiedades gArchivoProps;
    private CampoDuelo campoDuelo;
    private ControlMago cMago;
    private ControlHechizo cHechizo;
    private int dueloActual;
    private int maxDuelos;    

    public ControlPrincipal() {
        this.cVentana = new ControlVentana(this);
        this.campoDuelo = new CampoDuelo();
        this.dueloActual = 1;
        this.gArchivoProps = new GestorArchivoPropiedades();
        this.cHechizo = new ControlHechizo();
        this.cMago = new ControlMago();
        precarga();
        //probar sin boton
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
        if (cMago.getSize() < 2) {
            return;
        }
        MagoHilo mago1 = null;
        MagoHilo mago2 = null;
        // Ya no se pueden hacer más duelos
        if (dueloActual > cMago.getSize() - 1) {
            return;
        }
        MagoHilo ganador = campoDuelo.getGanador();
        if (ganador != null) {
            mago1 = new MagoHilo(ganador.getMago());
            mago2 = new MagoHilo(cMago.getMago(dueloActual));
        } else {
            mago1 = new MagoHilo(cMago.getMago(0));
            mago2 = new MagoHilo(cMago.getMago(1));
        }
        campoDuelo.setMagos(mago1, mago2);
        //nombres de los hilos para identificarlos
        mago1.setName("mago1");
        mago2.setName("mago2");
        //TODO pintar en lista nombre y casa de mago        
        //Iniciar hilos
        mago1.start();
        mago2.start();
        // Siguiente duelo
        dueloActual++;
    }

    public void repintarMago(int indice) {

    }
}

