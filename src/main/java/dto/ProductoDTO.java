
package dto;

/**
 * Representa los datos de un producto que serán enviados
 * desde el modelo hacia la vista.
 *
 * Permite mostrar el catálogo sin que la interfaz gráfica
 * necesite trabajar directamente con las entidades del dominio.
 *
 * @param id Identificador del producto.
 * @param nombre Nombre del producto.
 * @param precioUnitario Precio por unidad.
 */
public record ProductoDTO(
        int id,
        String nombre,
        double precioUnitario
) {
}
