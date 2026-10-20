
package dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Proporciona un punto de acceso a las operaciones de compra.
 *
 * Administra el catálogo de productos, las tarjetas
 * y la compra que está realizando el cliente.
 *
 * De esta manera, el modelo MVC puede utilizar el dominio
 * sin encargarse directamente de sus operaciones internas.
 */
public class FachadaCompras {

    private final List<Producto> productos;
    private final List<Tarjeta> tarjetas;
    private final Compra compra;

    /**
     * Inicializa los datos necesarios.
     */
    public FachadaCompras() {
        productos = new ArrayList<>();
        tarjetas = new ArrayList<>();
        compra = new Compra();

        // Productos del catálogo utilizados en el storyboard.
        productos.add(new Producto(1, "Cuaderno", 45.00));
        productos.add(new Producto(2, "Lápiz", 12.00));
        productos.add(new Producto(3, "Borrador", 10.00));
        productos.add(new Producto(4, "Pluma", 18.00));

        // Tarjetas bancarias (no reales ¬¬) para consultar sus datos.
        tarjetas.add(new Tarjeta("1234567890123456",
                "Banco del Norte", "Ciudad Obregón"));

        tarjetas.add(new Tarjeta("9876543210987654",
                "Banco del Pacífico", "Hermosillo"));
    }

    /**
     * Obtiene los productos disponibles en el catálogo.
     *
     * @return Lista de productos registrados.
     */
    public List<Producto> obtenerProductos() {
        return List.copyOf(productos);
    }

    /**
     * Agrega un producto del catálogo a la compra actual.
     *
     * @param idProducto Identificador del producto seleccionado.
     * @param cantidad Cantidad solicitada.
     */
    public void agregarProducto(int idProducto, int cantidad) {
        for (Producto producto : productos) {
            if (producto.getId() == idProducto) {
                compra.agregarProducto(producto, cantidad);
                return;
            }
        }

        throw new IllegalArgumentException(
                "No se encontró el producto solicitado.");
    }

    /**
     * Busca una tarjeta NO REAL ¬¬ mediante su número.
     *
     * @param numero Número de tarjeta que se desea consultar.
     * @return Tarjeta encontrada.
     */
    public Tarjeta buscarTarjeta(String numero) {
        for (Tarjeta tarjeta : tarjetas) {
            if (tarjeta.getNumero().equals(numero)) {
                return tarjeta;
            }
        }

        throw new IllegalArgumentException(
                "No se encontró la tarjeta solicitada.");
    }

    /**
     * Permite acceder a la compra actual.
     *
     * @return Compra que está realizando el cliente.
     */
    public Compra obtenerCompra() {
        return compra;
    }
}
