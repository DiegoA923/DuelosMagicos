package udistrital.avanzada.duelosmagicos.Control;

import java.io.File;

/**
 * Clase ControlPrincipal.
 * <p>
 * Punto central de inicialización de la aplicación. Se encarga de: - Cargar los
 * datos base (magos y hechizos) desde un archivo .properties. - Inicializar los
 * controladores y la ventana principal. - Coordinar el inicio del torneo, pero
 * sin manejar duelos directamente.
 * </p>
 *
 * <p>
 * Principios SOLID: - SRP: Solo inicializa, organiza los controladores y delega. - DIP:
 * No depende de vistas ni modelos concretos, sino de sus controladores.
 * </p>
 *
 * @author Diego
 * @version 2.0
 * @since 2025-10-31
 */
public class ControlPrincipal {

    private final ControlVentana cVentana;
    private final GestorArchivoPropiedades gArchivoProps;
    private final ControlMago cMago;
    private final ControlHechizo cHechizo;
    private ControlDuelo cDuelo; // Controlador del duelo actual
    /**
     * Constructor principal del sistema. Inicializa los controladores base y
     * carga los datos iniciales.
     */
    public ControlPrincipal() {
        this.cVentana = new ControlVentana(this);
        this.gArchivoProps = new GestorArchivoPropiedades();
        this.cHechizo = new ControlHechizo();
        this.cMago = new ControlMago();
        
        precargarDatos();
    }

    /**
     * Carga los magos y hechizos desde un archivo .properties y luego muestra
     * la ventana principal.
     */
    private void precargarDatos() {
        cMago.vaciarLista();
        cHechizo.vaciarLista();

        File archivo = cVentana.obtenerArchivoPropiedades(
                "specs/data",
                "Seleccione el archivo de propiedades con la configuración base del torneo"
        );

        if (archivo == null) {
            return;
        }

        gArchivoProps.setArchivo(archivo);

        if (!gArchivoProps.cargar()) {
            gArchivoProps.cerrarArchivo();
            cVentana.mostrarMensaje("Informacion", "❌ No se pudo cargar el archivo de propiedades.");
            return;
        }

        try {
            int nHechizos = Integer.parseInt(gArchivoProps.getProperty("nHechizos"));
            int nMagos = Integer.parseInt(gArchivoProps.getProperty("nMagos"));

            // Cargar hechizos
            for (int i = 1; i <= nHechizos; i++) {
                String nombre = gArchivoProps.getProperty("hechizo" + i + ".nombre");
                int puntos;
                try {
                    puntos = Integer.parseInt(gArchivoProps.getProperty("hechizo" + i + ".puntos"));
                } catch (NumberFormatException e) {
                    continue;
                }
                if ((puntos >= 5 && puntos <= 25) && nombre != null && !nombre.isEmpty()) {
                    cHechizo.crearHechizo(nombre, puntos);
                }
            }

            // Cargar magos
            for (int i = 1; i <= nMagos; i++) {
                String nombre = gArchivoProps.getProperty("mago" + i + ".nombre");
                String casa = gArchivoProps.getProperty("mago" + i + ".casa");
                if (nombre != null && !nombre.isEmpty() && casa != null && !casa.isEmpty()) {
                    cMago.crearMago(nombre, casa, cHechizo.getHechizos());
                }
            }

        } catch (Exception e) {
            cVentana.mostrarMensaje("Informacion", "⚠️ Error al procesar el archivo de propiedades.");
            return;
        } finally {
            gArchivoProps.cerrarArchivo();            
        }

        if (cMago.getSize() < 2 || cHechizo.getSize() < 1) {
            cMago.vaciarLista();
            cHechizo.vaciarLista();
            cVentana.mostrarMensaje("Informacion", "⚠️ El archivo no contiene suficientes magos o hechizos.");
            return;
        }        
        
        // Mostrar Ventana Principal
        cVentana.mostrarVentanaPrincipal();

        //Crear controlador del duelo y conectar la vista y los modelos
        this.cDuelo = new ControlDuelo(cVentana, cMago);

        // Preparar el primer enfrentamiento
        cDuelo.setMaxDuelos(cMago.getSize() - 1);
        cDuelo.prepararSiguienteDuelo();        
    }
    
    public void prepararSiguienteDuelo() {
        //delegar a cDuelo
        if(cDuelo != null) {
            cDuelo.prepararSiguienteDuelo();
        } else {
            cVentana.mostrarErrorConsola("⚠️ ControlDuelo no está conectado aún.");
        }
    }
    
    public void iniciarDuelo() {
        //delegar a cDuelo
        if(cDuelo != null) {
            cDuelo.iniciarDuelo();
        } else {
            cVentana.mostrarErrorConsola("⚠️ ControlDuelo no está conectado aún.");
        }        
    }
}
