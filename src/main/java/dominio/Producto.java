
package dominio;

/**
 * Representa un producto disponible en el catálogo de la tienda.
 *
 * Cada producto cuenta con un identificador, nombre y precio unitario.
 * Estos datos serán utilizados al momento de seleccionar los productos
 * que formarán parte de una compra.
 */
public class Producto {

    private final int id;
    private final String nombre;
    private final double precioUnitario;

    /**
     * Crea un producto con los datos recibidos.
     *
     * @param id Identificador del producto.
     * @param nombre Nombre del producto.
     * @param precioUnitario Precio por unidad del producto.
     */
    public Producto(int id, String nombre, double precioUnitario) {
        this.id = id;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
    }

    /**
     * @return Identificador del producto.
     */
    public int getId() {
        return id;
    }

    /**
     * @return Nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return Precio unitario del producto.
     */
    public double getPrecioUnitario() {
        return precioUnitario;
    }
}
