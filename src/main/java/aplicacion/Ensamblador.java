
package aplicacion;

import controlador.ControladorCompraProducto;
import dominio.FachadaCompras;
import modelo.ModeloCompra;
import vista.VistaCompra;

import javax.swing.SwingUtilities;

/**
 * Se encarga de construir y conectar los componentes
 * de nuestra aplicación MVC.
 *
 * Inicializa el dominio, el modelo, el controlador y la vista.
 * También registra la vista como observadora del modelo
 * para implementar el mecanismo Observer Pull.
 */
public class Ensamblador {

    /**
     * Inicializa los componentes y muestra la ventana de compra.
     */
    public void iniciar() {

        // Creamos la fachada que administra las operaciones del dominio.
        FachadaCompras fachada = new FachadaCompras();

        // El modelo utiliza la fachada para realizar las operaciones.
        ModeloCompra modelo = new ModeloCompra(fachada);

        // El controlador modifica el modelo mediante IOperacionesModelo.
        ControladorCompraProducto controlador =
                new ControladorCompraProducto(modelo);

        // La vista consulta el modelo mediante IConsultaModelo.
        VistaCompra vista = new VistaCompra(modelo, controlador);

        // Registramos la vista para recibir notificaciones.
        modelo.agregarObservador(vista);

        // Mostramos la ventana principal.
        vista.mostrarVentana();
    }

    /**
     * Punto de entrada de nuestra aplicación.
     *
     * @param args Argumentos de ejecución.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Ensamblador ensamblador = new Ensamblador();
            ensamblador.iniciar();
        });
    }
}
