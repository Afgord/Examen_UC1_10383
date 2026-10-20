
package modelo;

import dto.DetalleCompraDTO;
import dto.ProductoDTO;
import dto.TarjetaDTO;

import java.util.List;

/**
 * Define las operaciones de consulta que el modelo
 * pone a disposición de la vista.
 *
 * Esta interfaz forma parte del mecanismo Observer Pull.
 * Cuando la vista recibe una notificación, consulta mediante
 * estas operaciones la información que necesita mostrar.
 *
 * No permite modificar directamente los datos de la compra.
 */
public interface IConsultaModelo {

    /**
     * Obtiene los productos disponibles en el catálogo.
     *
     * @return Lista de productos representados mediante DTO.
     */
    List<ProductoDTO> obtenerProductos();

    /**
     * Obtiene los productos seleccionados para la compra.
     *
     * @return Lista de detalles con cantidades y subtotales.
     */
    List<DetalleCompraDTO> obtenerDetallesSeleccionados();

    /**
     * Obtiene la información de la tarjeta consultada.
     *
     * @return Datos de la tarjeta o null si aún no se ha consultado.
     */
    TarjetaDTO obtenerDatosTarjeta();

    /**
     * Consulta el importe total de la compra.
     *
     * @return Suma de los subtotales seleccionados.
     */
    double obtenerTotal();

    /**
     * Indica si el cliente confirmó la compra.
     *
     * @return true si la compra fue confirmada; false en caso contrario.
     */
    boolean estaConfirmadaCompra();
}
