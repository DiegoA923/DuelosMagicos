package udistrital.avanzada.duelosmagicos.Control;

import java.io.File;
import java.util.ArrayList;
import udistrital.avanzada.duelosmagicos.Modelo.Hechizo;
import udistrital.avanzada.duelosmagicos.Modelo.Mago;


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
    private ArrayList<Mago> magos;
    private int dueloActual;
    private int maxDuelos;

    public ControlPrincipal() {
        this.cVentana = new ControlVentana(this);        
        this.campoDuelo = new CampoDuelo();
        this.dueloActual = 1;
        this.gArchivoProps = new GestorArchivoPropiedades();
        precarga();
    }
    
    public void precarga(){   
        ArrayList<Mago> magos = null;
        ArrayList<Hechizo> hechizos = null;
        //bandera para saber si el archivo es valido
        boolean valido = false;   
        File archivo = cVentana.obtenerArchivoPropiedades(
            "specs/data",
            "Eliga Archivo de propiedades valido con configuracion base"
        );
        if(archivo == null) {
            return;
        }
        gArchivoProps.setArchivo(archivo);
        boolean carga = gArchivoProps.cargar();
        if (!carga) {
            gArchivoProps.cerrarArchivo();
            cVentana.mostrarMensaje("No se pudo cargar el archivo");
            return;
        }
        magos = gArchivoProps.getMagos();
        hechizos = gArchivoProps.getHechizos();
        gArchivoProps.cerrarArchivo();
        if (magos.size() < 2 && hechizos.size() < 2) {                   
            cVentana.mostrarMensaje("Archivo no contiene los elementos necesarios");
            return;
        }        
        campoDuelo.setHechizos(hechizos);
        this.magos = magos;
        this.maxDuelos = magos.size()-1;
        cVentana.mostrarVentanaPrincipal();        
    }
    
    public void iniciarDuelo() {
//        // Ya no se pueden hacer más duelos
//        if (dueloActual > magos.size()-1) {
//            return;
//        }
//        Mago magoActual = campoDuelo.getMagoActual();
//        if (magoActual == null) {
//            
//        } else {
//        
//        }       
//        // Siguiente duelo
//        dueloActual++;
    }
}

