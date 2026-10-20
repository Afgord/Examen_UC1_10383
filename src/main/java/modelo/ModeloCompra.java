
package modelo;

import dominio.Compra;
import dominio.DetalleCompra;
import dominio.FachadaCompras;
import dominio.Producto;
import dominio.Tarjeta;
import dto.DetalleCompraDTO;
import dto.ProductoDTO;
import dto.TarjetaDTO;
import utilerias.SujetoObservado;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementa el modelo de nuestra aplicación MVC.
 *
 * Coordina las operaciones de compra mediante FachadaCompras,
 * proporciona los datos que necesita la vista mediante DTO
 * y mantiene el estado de la tarjeta y la confirmación del pago.
 *
 * También hereda de SujetoObservado para notificar los cambios
 * utilizando el patrón Observer Pull.
 */
public class ModeloCompra extends SujetoObservado
        implements IConsultaModelo, IOperacionesModelo {

    private final FachadaCompras fachada;
    private TarjetaDTO tarjetaActual;
    private boolean compraConfirmada;

    /**
     * Inicializa el modelo utilizando la fachada del dominio.
     *
     * @param fachada Fachada encargada de las operaciones de compra.
     */
    public ModeloCompra(FachadaCompras fachada) {
        this.fachada = fachada;
        this.tarjetaActual = null;
        this.compraConfirmada = false;
    }

    /**
     * Agrega un producto a la compra y notifica a los observadores
     * para que consulten los detalles actualizados.
     *
     * @param idProducto Identificador del producto.
     * @param cantidad Cantidad que se desea agregar.
     */
    @Override
    public void agregarProducto(int idProducto, int cantidad) {
        fachada.agregarProducto(idProducto, cantidad);

        // Si cambia el carrito, la compra debe confirmarse nuevamente.
        compraConfirmada = false;

        notificarObservadores();
    }

    /**
     * Consulta una tarjeta ficticia mediante la fachada.
     *
     * Convierte la entidad Tarjeta en un DTO y notifica
     * a la vista para que presente sus datos.
     *
     * @param numero Número de la tarjeta solicitada.
     */
    @Override
    public void consultarTarjeta(String numero) {
        Tarjeta tarjeta = fachada.buscarTarjeta(numero);

        tarjetaActual = new TarjetaDTO(
                tarjeta.getNumero(),
                tarjeta.getBancoEmisor(),
                tarjeta.getCiudad()
        );

        compraConfirmada = false;
        notificarObservadores();
    }

    /**
     * Confirma la compra actual.
     *
     * Valida que existan productos seleccionados y una tarjeta
     * consultada antes de actualizar el estado de la compra.
     *
     * Esta operación simula el pago; no realiza ninguna
     * transacción con instituciones bancarias.
     */
    @Override
    public void realizarPago() {
        if (fachada.obtenerCompra().obtenerDetalles().isEmpty()) {
            throw new IllegalStateException(
                    "Debes seleccionar al menos un producto.");
        }

        if (tarjetaActual == null) {
            throw new IllegalStateException(
                    "Debes consultar una tarjeta antes de pagar.");
        }

        compraConfirmada = true;
        notificarObservadores();
    }

    /**
     * Recupera el catálogo y convierte sus productos a DTO.
     *
     * @return Lista de productos disponibles para la vista.
     */
    @Override
    public List<ProductoDTO> obtenerProductos() {
        List<ProductoDTO> productosDTO = new ArrayList<>();

        for (Producto producto : fachada.obtenerProductos()) {
            productosDTO.add(new ProductoDTO(
                    producto.getId(),
                    producto.getNombre(),
                    producto.getPrecioUnitario()
            ));
        }

        return productosDTO;
    }

    /**
     * Recupera los detalles de la compra y los convierte a DTO.
     *
     * @return Lista de detalles con cantidades y subtotales.
     */
    @Override
    public List<DetalleCompraDTO> obtenerDetallesSeleccionados() {
        Compra compra = fachada.obtenerCompra();
        List<DetalleCompraDTO> detallesDTO = new ArrayList<>();

        for (DetalleCompra detalle : compra.obtenerDetalles()) {
            Producto producto = detalle.getProducto();

            detallesDTO.add(new DetalleCompraDTO(
                    producto.getNombre(),
                    detalle.getCantidad(),
                    producto.getPrecioUnitario(),
                    detalle.calcularSubtotal()
            ));
        }

        return detallesDTO;
    }

    /**
     * Obtiene los datos de la tarjeta consultada.
     *
     * @return TarjetaDTO o null si no se ha consultado una tarjeta.
     */
    @Override
    public TarjetaDTO obtenerDatosTarjeta() {
        return tarjetaActual;
    }

    /**
     * Consulta el importe total calculado por el dominio.
     *
     * @return Total de la compra.
     */
    @Override
    public double obtenerTotal() {
        return fachada.obtenerCompra().calcularTotal();
    }

    /**
     * Consulta si la compra fue confirmada.
     *
     * @return true cuando el pago simulado fue confirmado.
     */
    @Override
    public boolean estaConfirmadaCompra() {
        return compraConfirmada;
    }
}
