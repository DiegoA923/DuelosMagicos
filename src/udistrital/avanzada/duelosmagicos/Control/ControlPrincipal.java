package udistrital.avanzada.duelosmagicos.Control;

import java.io.File;
import java.util.ArrayList;
import udistrital.avanzada.duelosmagicos.Modelo.Hechizo;
import udistrital.avanzada.duelosmagicos.Modelo.Mago;

public class ControlPrincipal {

    private ControlVentana cVentana;
    private GestorArchivoPropiedades gArchivoProps;
    private CampoDuelo campoDuelo;
    private ArrayList<Mago> magos;
    private int dueloActual;
    private int maxDuelos;

    public ControlPrincipal() {
        this.cVentana = new ControlVentana(this);
        this.campoDuelo = new CampoDuelo(this);
        this.dueloActual = 1;
        this.gArchivoProps = new GestorArchivoPropiedades();
        precarga();
    }

    public void precarga() {
        ArrayList<Mago> magos = null;
        ArrayList<Hechizo> hechizos = null;
        File archivo = cVentana.obtenerArchivoPropiedades(
                "specs/data",
                "Eliga Archivo de propiedades válido con configuración base"
        );
        if (archivo == null) return;

        gArchivoProps.setArchivo(archivo);
        boolean carga = gArchivoProps.cargar();
        if (!carga) {
            cVentana.mostrarMensaje("No se pudo cargar el archivo");
            return;
        }

        magos = gArchivoProps.getMagos();
        hechizos = gArchivoProps.getHechizos();

        if (magos.size() < 2 || hechizos.size() < 2) {
            cVentana.mostrarMensaje("Archivo no contiene los elementos necesarios");
            return;
        }

        for (Mago mago : magos) {
            ArrayList<Hechizo> hechizosMago = gArchivoProps.getHechizos();
            mago.setHechizos(hechizosMago);
        }

        this.magos = magos;
        gArchivoProps.cerrarArchivo();
        this.maxDuelos = magos.size() - 1;

        // Configurar los dos primeros magos en la interfaz
        cVentana.setDatosMago(1, magos.get(0).getNombre(), magos.get(0).getCasa());
        cVentana.setDatosMago(2, magos.get(1).getNombre(), magos.get(1).getCasa());

        cVentana.mostrarVentanaPrincipal();
    }

    public void iniciarDuelo() {
        if (dueloActual > magos.size() - 1) return;

        MagoHilo mago1, mago2;
        MagoHilo ganador = campoDuelo.getGanador();

        if (ganador != null) {
            mago1 = new MagoHilo(ganador.getMago());
            mago2 = new MagoHilo(magos.get(dueloActual));
        } else {
            mago1 = new MagoHilo(this.magos.get(0));
            mago2 = new MagoHilo(this.magos.get(1));
        }

        campoDuelo.setMagos(mago1, mago2);
        mago1.setName("mago1");
        mago2.setName("mago2");

        mago1.start();
        mago2.start();

        dueloActual++;
    }

    // 🔹 Métodos para comunicación desde CampoDuelo
    public void actualizarVista(int indice, String hechizo, int puntos, int lanzados) {
        cVentana.actualizarMago(indice, hechizo, puntos, lanzados);
    }

    public void mostrarAccion(String texto) {
        cVentana.mostrarAccion(texto);
    }

    public void mostrarGanador(String nombre, String casa, int puntos) {
        cVentana.mostrarGanador(nombre, casa, puntos);
    }
}
