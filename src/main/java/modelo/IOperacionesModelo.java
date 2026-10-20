
package modelo;

/**
 * Define las operaciones que el controlador puede solicitar
 * al modelo durante el proceso de compra.
 *
 * Permite agregar productos, consultar la información de una tarjeta
 * y confirmar la compra realizada por el cliente.
 *
 * De esta manera, el controlador no necesita conocer directamente
 * la implementación interna de ModeloCompra.
 */
public interface IOperacionesModelo {

    /**
     * Solicita agregar un producto a la compra actual.
     *
     * @param idProducto Identificador del producto seleccionado.
     * @param cantidad Cantidad de unidades que se desean agregar.
     */
    void agregarProducto(int idProducto, int cantidad);

    /**
     * Solicita consultar los datos de una tarjeta ficticia.
     *
     * @param numero Número de la tarjeta que se desea consultar.
     */
    void consultarTarjeta(String numero);

    /**
     * Confirma la compra utilizando los productos seleccionados
     * y la tarjeta consultada previamente.
     *
     * Actualiza el estado de la compra para que la vista pueda mostrar
     * el comprobante correspondiente.
     */
    void realizarPago();
}
