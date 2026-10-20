
package dto;

/**
 * Contiene la información de un producto seleccionado
 * que será presentada en la interfaz de compra.
 *
 * Permite consultar la cantidad, el precio unitario y el subtotal
 * sin que la vista tenga que acceder directamente a DetalleCompra.
 *
 * @param nombreProducto Nombre del producto seleccionado.
 * @param cantidad Cantidad de unidades solicitadas.
 * @param precioUnitario Precio por unidad del producto.
 * @param subtotal Importe correspondiente al detalle.
 */
public record DetalleCompraDTO(
        String nombreProducto,
        int cantidad,
        double precioUnitario,
        double subtotal
) {
}
