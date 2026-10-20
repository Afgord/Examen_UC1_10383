
package dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa la compra que está realizando el cliente.
 *
 * Administra los productos seleccionados junto con sus cantidades
 * y permite calcular el importe total de la compra.
 *
 * Cada producto seleccionado se almacena como un DetalleCompra.
 */
public class Compra {

    private final List<DetalleCompra> detalles;

    /**
     * Inicializa una compra sin productos seleccionados.
     */
    public Compra() {
        this.detalles = new ArrayList<>();
    }

    /**
     * Agrega un producto a la compra.
     *
     * Si el producto ya fue seleccionado anteriormente, se incrementa
     * su cantidad. De lo contrario, se crea un nuevo detalle de compra.
     *
     * @param producto Producto que se desea agregar.
     * @param cantidad Cantidad de unidades solicitadas.
     */
    public void agregarProducto(Producto producto, int cantidad) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        // Buscamos si el producto ya forma parte de la compra.
        for (DetalleCompra detalle : detalles) {
            if (detalle.getProducto().getId() == producto.getId()) {
                detalle.aumentarCantidad(cantidad);
                return;
            }
        }

        // Si no existe, creamos un nuevo detalle de compra.
        detalles.add(new DetalleCompra(producto, cantidad));
    }

    /**
     * Calcula el importe total de todos los productos seleccionados.
     *
     * @return Suma de los subtotales de la compra.
     */
    public double calcularTotal() {
        double total = 0;

        for (DetalleCompra detalle : detalles) {
            total += detalle.calcularSubtotal();
        }

        return total;
    }

    /**
     * Permite consultar los detalles seleccionados sin modificar
     * directamente la lista que administra la compra.
     *
     * @return Lista de detalles de la compra.
     */
    public List<DetalleCompra> obtenerDetalles() {
        return Collections.unmodifiableList(detalles);
    }
}
