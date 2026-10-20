
package controlador;

import modelo.IOperacionesModelo;

/**
 * Representa el controlador del caso de uso Comprar producto.
 *
 * Recibe las solicitudes de la vista y las delega al modelo
 * mediante la interfaz IOperacionesModelo.
 *
 * No administra directamente los productos, las tarjetas
 * ni los cálculos de la compra, ya que esas responsabilidades
 * corresponden al modelo y a las clases del dominio.
 */
public class ControladorCompraProducto {

    private final IOperacionesModelo modelo;

    /**
     * Inicializa el controlador con la interfaz de operaciones
     * que utilizará para comunicarse con el modelo.
     *
     * @param modelo Interfaz que permite modificar el estado de la compra.
     */
    public ControladorCompraProducto(IOperacionesModelo modelo) {
        this.modelo = modelo;
    }

    /**
     * Solicita agregar un producto a la compra actual.
     *
     * @param idProducto Identificador del producto seleccionado.
     * @param cantidad Cantidad de unidades solicitadas.
     */
    public void agregarProducto(int idProducto, int cantidad) {
        modelo.agregarProducto(idProducto, cantidad);
    }

    /**
     * Solicita consultar la información de una tarjeta ficticia.
     *
     * @param numero Número de la tarjeta que se desea consultar.
     */
    public void consultarTarjeta(String numero) {
        modelo.consultarTarjeta(numero);
    }

    /**
     * Solicita confirmar la compra actual.
     *
     * La validación y confirmación corresponden al modelo.
     */
    public void realizarPago() {
        modelo.realizarPago();
    }
}
