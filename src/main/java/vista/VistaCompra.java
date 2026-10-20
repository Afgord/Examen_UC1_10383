
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

    private final JList<ProductoDTO> selectorProductos;
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

        // Panel central: catálogo a la izquierda y compra a la derecha.
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 12, 0));
        panelCentral.setBorder(
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        );

        // Zona izquierda: catálogo de productos.

        // Zona izquierda: catálogo interactivo de productos.
        JPanel panelIzquierdo = new JPanel(new BorderLayout(5, 10));
        panelIzquierdo.setBorder(
                BorderFactory.createTitledBorder("Catálogo de productos")
        );

        // El cliente selecciona directamente un producto de la lista.
        selectorProductos = new JList<>();
        selectorProductos.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        selectorProductos.setVisibleRowCount(6);

        panelIzquierdo.add(
                new JScrollPane(selectorProductos),
                BorderLayout.CENTER
        );

        // Controles para indicar cuántas unidades se desean comprar.
        JPanel panelSeleccion = new JPanel(new FlowLayout(FlowLayout.LEFT));

        selectorCantidad = new JSpinner(
                new SpinnerNumberModel(1, 1, 100, 1)
        );

        JButton botonAgregar = new JButton("Agregar producto");

        panelSeleccion.add(new JLabel("Cantidad:"));
        panelSeleccion.add(selectorCantidad);
        panelSeleccion.add(botonAgregar);

        panelIzquierdo.add(panelSeleccion, BorderLayout.SOUTH);

        // Zona derecha: productos seleccionados.
        JPanel panelDerecho = new JPanel(new BorderLayout(5, 5));
        panelDerecho.setBorder(
                BorderFactory.createTitledBorder("Productos seleccionados")
        );

        areaCompra = new JTextArea();
        areaCompra.setEditable(false);

        panelDerecho.add(
                new JScrollPane(areaCompra),
                BorderLayout.CENTER
        );

        panelCentral.add(panelIzquierdo);
        panelCentral.add(panelDerecho);

        add(panelCentral, BorderLayout.CENTER);

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
     * Obtiene los productos del modelo y los presenta
     * en una lista que permite seleccionar uno de ellos.
     */
    private void cargarCatalogo() {

        DefaultListModel<ProductoDTO> listaProductos =
                new DefaultListModel<>();

        for (ProductoDTO producto : modelo.obtenerProductos()) {
            listaProductos.addElement(producto);
        }

        selectorProductos.setModel(listaProductos);

        // Mostramos el nombre y precio en lugar del record completo.
        selectorProductos.setCellRenderer(new DefaultListCellRenderer() {
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

        // Seleccionamos el primer producto al iniciar la ventana.
        if (!listaProductos.isEmpty()) {
            selectorProductos.setSelectedIndex(0);
        }
    }

    /**
     * Envía al controlador el producto y cantidad seleccionados.
     */
    private void agregarProducto() {
        try {
            ProductoDTO producto = selectorProductos.getSelectedValue();

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
                    "Ingresa el número de tarjeta para consultar sus datos."
            );
        }

        areaResultado.setText(resultado.toString());
    }

}
