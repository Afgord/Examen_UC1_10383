
package vista;

import controlador.ControladorCompraProducto;
import dto.DetalleCompraDTO;
import dto.ProductoDTO;
import dto.TarjetaDTO;
import modelo.IConsultaModelo;
import utilerias.Observador;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Representa la interfaz gráfica del caso de uso Comprar producto.
 *
 * Permite al cliente consultar el catálogo, seleccionar productos,
 * ingresar una tarjeta ficticia y confirmar su compra.
 *
 * Implementa Observador para actualizar la información presentada
 * cada vez que el modelo notifica un cambio.
 */
public class VistaCompra extends JFrame implements Observador {

    private final IConsultaModelo modelo;
    private final ControladorCompraProducto controlador;

    private final JComboBox<ProductoDTO> selectorProductos;
    private final JSpinner selectorCantidad;
    private final JTextArea areaCompra;
    private final JTextField campoTarjeta;
    private final JTextArea areaResultado;

    /**
     * Construye la ventana principal y sus componentes gráficos.
     *
     * @param modelo Interfaz utilizada para consultar los datos.
     * @param controlador Controlador que recibe las acciones del cliente.
     */
    public VistaCompra(IConsultaModelo modelo,
                       ControladorCompraProducto controlador) {

        this.modelo = modelo;
        this.controlador = controlador;

        setTitle("Comprar producto - Examen UC-1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel superior: catálogo y selección de productos.
        JPanel panelCatalogo = new JPanel(new FlowLayout());

        selectorProductos = new JComboBox<>();
        selectorProductos.setPreferredSize(new Dimension(240, 30));

        selectorCantidad = new JSpinner(
                new SpinnerNumberModel(1, 1, 100, 1)
        );

        JButton botonAgregar = new JButton("Agregar producto");

        panelCatalogo.add(new JLabel("Producto:"));
        panelCatalogo.add(selectorProductos);
        panelCatalogo.add(new JLabel("Cantidad:"));
        panelCatalogo.add(selectorCantidad);
        panelCatalogo.add(botonAgregar);

        add(panelCatalogo, BorderLayout.NORTH);

        // Panel central: productos seleccionados.
        areaCompra = new JTextArea();
        areaCompra.setEditable(false);

        add(new JScrollPane(areaCompra), BorderLayout.CENTER);

        // Panel inferior: consulta de tarjeta y comprobante.
        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));

        JPanel panelTarjeta = new JPanel(new FlowLayout());

        campoTarjeta = new JTextField(18);

        JButton botonConsultar = new JButton("Consultar tarjeta");
        JButton botonPagar = new JButton("Pagar");

        panelTarjeta.add(new JLabel("Número de tarjeta:"));
        panelTarjeta.add(campoTarjeta);
        panelTarjeta.add(botonConsultar);
        panelTarjeta.add(botonPagar);

        panelInferior.add(panelTarjeta, BorderLayout.NORTH);

        areaResultado = new JTextArea(8, 40);
        areaResultado.setEditable(false);

        panelInferior.add(
                new JScrollPane(areaResultado),
                BorderLayout.CENTER
        );

        add(panelInferior, BorderLayout.SOUTH);

        // Eventos que comunican las acciones al controlador.
        botonAgregar.addActionListener(e -> agregarProducto());
        botonConsultar.addActionListener(e -> consultarTarjeta());
        botonPagar.addActionListener(e -> realizarPago());

        // Consulta inicial del catálogo.
        cargarCatalogo();
        actualizar();
    }

    /**
     * Muestra la ventana principal de la aplicación.
     */
    public void mostrarVentana() {
        setVisible(true);
    }

    /**
     * Obtiene los productos disponibles y llena el selector.
     */
    private void cargarCatalogo() {
        for (ProductoDTO producto : modelo.obtenerProductos()) {
            selectorProductos.addItem(producto);
        }

        selectorProductos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> lista, Object valor, int indice,
                    boolean seleccionado, boolean tieneFoco) {

                if (valor instanceof ProductoDTO producto) {
                    valor = String.format(
                            "%s - $%.2f",
                            producto.nombre(),
                            producto.precioUnitario()
                    );
                }

                return super.getListCellRendererComponent(
                        lista, valor, indice, seleccionado, tieneFoco
                );
            }
        });
    }

    /**
     * Envía al controlador el producto y cantidad seleccionados.
     */
    private void agregarProducto() {
        try {
            ProductoDTO producto =
                    (ProductoDTO) selectorProductos.getSelectedItem();

            if (producto == null) {
                return;
            }

            int cantidad = (Integer) selectorCantidad.getValue();

            controlador.agregarProducto(producto.id(), cantidad);

        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }
    }

    /**
     * Solicita consultar la información de la tarjeta ingresada.
     */
    private void consultarTarjeta() {
        try {
            controlador.consultarTarjeta(campoTarjeta.getText().trim());

        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }
    }

    /**
     * Solicita confirmar la compra.
     */
    private void realizarPago() {
        try {
            controlador.realizarPago();

        } catch (IllegalStateException ex) {
            mostrarError(ex.getMessage());
        }
    }

    /**
     * Presenta un mensaje cuando ocurre un problema
     * durante alguna operación de compra.
     *
     * @param mensaje Descripción del problema.
     */
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Operación no realizada",
                JOptionPane.WARNING_MESSAGE
        );
    }


    /**
     * Recibe una notificación del modelo y consulta
     * los datos actualizados mediante IConsultaModelo.
     *
     * Actualiza la lista de productos seleccionados y presenta
     * los datos de la tarjeta o el comprobante de compra,
     * dependiendo del estado actual del modelo.
     */
    @Override
    public void actualizar() {

        // Recuperamos los productos seleccionados.
        List<DetalleCompraDTO> detalles =
                modelo.obtenerDetallesSeleccionados();

        double total = modelo.obtenerTotal();

        // Construimos el resumen de la compra.
        StringBuilder resumen = new StringBuilder();

        resumen.append("PRODUCTOS SELECCIONADOS\n");
        resumen.append("----------------------------------------\n");

        if (detalles.isEmpty()) {
            resumen.append("No se han seleccionado productos.\n");
        } else {
            for (DetalleCompraDTO detalle : detalles) {
                resumen.append(String.format(
                        "%s | %d x $%.2f = $%.2f%n",
                        detalle.nombreProducto(),
                        detalle.cantidad(),
                        detalle.precioUnitario(),
                        detalle.subtotal()
                ));
            }
        }

        resumen.append("----------------------------------------\n");
        resumen.append(String.format("TOTAL: $%.2f%n", total));

        areaCompra.setText(resumen.toString());

        // Consultamos los datos de la tarjeta seleccionada.
        TarjetaDTO tarjeta = modelo.obtenerDatosTarjeta();

        StringBuilder resultado = new StringBuilder();

        if (modelo.estaConfirmadaCompra()) {

            // La compra ya fue confirmada: mostramos el ticket.
            resultado.append("COMPROBANTE DE COMPRA\n");
            resultado.append("========================================\n");

            for (DetalleCompraDTO detalle : detalles) {
                resultado.append(String.format(
                        "%s | Cantidad: %d | Subtotal: $%.2f%n",
                        detalle.nombreProducto(),
                        detalle.cantidad(),
                        detalle.subtotal()
                ));
            }

            resultado.append("----------------------------------------\n");
            resultado.append(String.format("TOTAL PAGADO: $%.2f%n", total));

            if (tarjeta != null) {
                resultado.append("\nDATOS DE LA TARJETA\n");
                resultado.append("Banco: ")
                        .append(tarjeta.bancoEmisor()).append("\n");
                resultado.append("Ciudad: ")
                        .append(tarjeta.ciudad()).append("\n");
                resultado.append("Número: ")
                        .append(tarjeta.numero()).append("\n");
            }

            resultado.append("\nCompra confirmada correctamente.\n");

        } else if (tarjeta != null) {

            // Todavía no se paga: mostramos la tarjeta consultada.
            resultado.append("DATOS DE LA TARJETA\n");
            resultado.append("========================================\n");
            resultado.append("Número: ")
                    .append(tarjeta.numero()).append("\n");
            resultado.append("Banco emisor: ")
                    .append(tarjeta.bancoEmisor()).append("\n");
            resultado.append("Ciudad: ")
                    .append(tarjeta.ciudad()).append("\n");

        } else {
            resultado.append(
                    "Ingresa una tarjeta ficticia para consultar sus datos."
            );
        }

        areaResultado.setText(resultado.toString());
    }

}
