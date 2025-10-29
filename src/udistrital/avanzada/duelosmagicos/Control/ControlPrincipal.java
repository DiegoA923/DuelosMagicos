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
        
        if (magos.size() < 2 && hechizos.size() < 2) {                   
            cVentana.mostrarMensaje("Archivo no contiene los elementos necesarios");            
            gArchivoProps.cerrarArchivo();
            return;
        }        
        //Simulamos que cada mago tiene hechizos diferentes       
        for (Mago mago : magos) {
            ArrayList<Hechizo> hechizosMago = gArchivoProps.getHechizos();
            mago.setHechizos(hechizosMago);
        }
        this.magos = magos;
        gArchivoProps.cerrarArchivo();
        this.maxDuelos = magos.size()-1;
        cVentana.mostrarVentanaPrincipal();    
    }
    
    // antes de llamar metodo debe comprobar que hilos mago han cumplido su ciclo de vida
    public void iniciarDuelo() {
        // Ya no se pueden hacer más duelos
        MagoHilo mago1 = null;
        MagoHilo mago2 = null;       
        if (dueloActual > magos.size()-1) {
            return;
        }
        MagoHilo ganador = campoDuelo.getGanador();        
        if (ganador != null) {
            mago1 = new MagoHilo(ganador.getMago());
            mago2 = new MagoHilo(magos.get(dueloActual));            
        } else {
            mago1 = new MagoHilo(this.magos.get(0));
            mago2 = new MagoHilo(this.magos.get(1));
        }
        campoDuelo.setMagos(mago1, mago2);
        //nombres de los hilos para identificarlos
        mago1.setName("mago1");
        mago1.setName("mago2");
        //TODO pintar en lista nombre y casa de mago        
        //Iniciar hilos
        mago1.start();
        mago2.start();
        // Siguiente duelo
        dueloActual++;
    }
    
    public void repintarMago(int indice){
        
    }    
}

