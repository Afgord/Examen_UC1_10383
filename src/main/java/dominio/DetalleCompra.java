
package dominio;

/**
 * Representa el detalle de un producto seleccionado para una compra.
 *
 * Guarda una referencia al producto y la cantidad solicitada.
 * También se encarga de calcular el subtotal correspondiente,
 * multiplicando el precio unitario por la cantidad seleccionada.
 */
public class DetalleCompra {

    private final Producto producto;
    private int cantidad;

    /**
     * Crea un detalle de compra con el producto y la cantidad indicada.
     *
     * @param producto Producto que se desea comprar.
     * @param cantidad Cantidad de unidades seleccionadas.
     */
    public DetalleCompra(Producto producto, int cantidad) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        this.producto = producto;
        this.cantidad = cantidad;
    }

    /**
     * Calcula el importe correspondiente a este detalle.
     *
     * @return Subtotal del producto seleccionado.
     */
    public double calcularSubtotal() {
        return producto.getPrecioUnitario() * cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    /**
     * Incrementa la cantidad del producto seleccionado.
     * Se utilizará cuando el cliente agregue nuevamente
     * un producto que ya se encuentra en la compra.
     *
     * @param cantidad Cantidad adicional de unidades.
     */
    public void aumentarCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad adicional debe ser mayor que cero.");
        }

        this.cantidad += cantidad;
    }
}
