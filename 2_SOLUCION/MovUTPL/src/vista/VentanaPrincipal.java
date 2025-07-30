package vista;

import modelo.*;
import dao.*;
import conexion.Conexion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame {
    // DAOs
    private ClienteDAO clienteDAO = new ClienteDAO();
    private DispositivoMovilDAO dispositivoDAO = new DispositivoMovilDAO();
    private PlanDAO planDAO = new PlanDAO();
    private FacturaDAO facturaDAO = new FacturaDAO();
    private DetalleFacturaDAO detalleFacturaDAO = new DetalleFacturaDAO();

    // Atributos para gestionar el cliente actualmente seleccionado
    private Cliente clienteSeleccionado = null;
    private DispositivoMovil dispositivoSeleccionado = null;
    private PlanMovil planSeleccionado = null;
    private Factura facturaSeleccionada = null;

    // --- Componentes de la Interfaz Gráfica ---
    private JTabbedPane tabbedPane;

    // Pestaña de Clientes
    private JTextField txtClienteNombres, txtClientePasaporteCedula, txtClienteCiudad, txtClienteEmail, txtClienteDireccion;
    private JButton btnGuardarCliente, btnBuscarCliente, btnModificarCliente, btnEliminarCliente, btnLimpiarCliente;
    private JTable tblClientes;
    private DefaultTableModel modeloTablaClientes;

    // Pestaña de Dispositivos y Planes
    private JComboBox<Cliente> cmbClientesParaPlanes;
    private JButton btnCargarClientePlanes;

    // Sub-panel de Dispositivos
    private JTextField txtDispositivoMarca, txtDispositivoModelo, txtDispositivoNumero;
    private JButton btnGuardarDispositivo, btnModificarDispositivo, btnEliminarDispositivo, btnLimpiarDispositivo;
    private JTable tblDispositivos;
    private DefaultTableModel modeloTablaDispositivos;

    // Sub-panel de Planes
    private JComboBox<String> cmbTipoPlan;
    private JTextField txtPlanMinutos, txtPlanCostoMinutos, txtPlanMegasGigas, txtPlanCostoPorGiga,
                       txtPlanPorcentajeDescuento, txtPlanMinutosNacionales, txtPlanCostoMinutoNacional,
                       txtPlanMinutosInternacionales, txtPlanCostoMinutoInternacional, txtPlanTarifaBase;
    private JButton btnGuardarPlan, btnModificarPlan, btnEliminarPlan, btnLimpiarPlan;
    private JTable tblPlanes;
    private DefaultTableModel modeloTablaPlanes;
    private JPanel panelCamposPlanes; // Panel para campos dinámicos de planes

    // Etiquetas para los campos de planes (necesarias para hacerlas visibles/invisibles)
    private JLabel lblPlanMinutos, lblPlanCostoMinutos, lblPlanMegasGigas, lblPlanCostoPorGiga,
                   lblPlanPorcentajeDescuento, lblPlanMinutosNacionales, lblPlanCostoMinutoNacional,
                   lblPlanMinutosInternacionales, lblPlanCostoMinutoInternacional, lblPlanTarifaBase;


    // Pestaña de Facturación
    private JComboBox<Cliente> cmbClientesParaFacturas;
    private JButton btnCargarClienteFacturas, btnGenerarFactura, btnVerDetalleFactura, btnMarcarPagada;
    private JTable tblFacturas;
    private DefaultTableModel modeloTablaFacturas;
    private JTextArea txtDetalleFactura;

    public VentanaPrincipal() {
        setTitle("Mov-UTPL: Sistema de Gestión de Telefonía Móvil Estudiantil");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Configuración del JTabbedPane ---
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // --- Pestaña 1: Gestión de Clientes ---
        JPanel panelClientes = new JPanel(new BorderLayout(10, 10));
        panelClientes.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelClientes.setBackground(new Color(240, 248, 255));

        // Panel de entrada de datos de Cliente
        JPanel inputPanelCliente = new JPanel(new GridBagLayout());
        inputPanelCliente.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Datos del Cliente"));
        inputPanelCliente.setBackground(Color.WHITE);
        GridBagConstraints gbcCliente = new GridBagConstraints();
        gbcCliente.insets = new Insets(5, 5, 5, 5);
        gbcCliente.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbcCliente.gridx = 0; gbcCliente.gridy = row; gbcCliente.anchor = GridBagConstraints.EAST; inputPanelCliente.add(new JLabel("Nombres:"), gbcCliente);
        gbcCliente.gridx = 1; gbcCliente.gridy = row; gbcCliente.weightx = 1.0; txtClienteNombres = new JTextField(25); inputPanelCliente.add(txtClienteNombres, gbcCliente); row++;
        gbcCliente.gridx = 0; gbcCliente.gridy = row; gbcCliente.anchor = GridBagConstraints.EAST; inputPanelCliente.add(new JLabel("Pasaporte/Cédula:"), gbcCliente);
        gbcCliente.gridx = 1; gbcCliente.gridy = row; gbcCliente.weightx = 1.0; txtClientePasaporteCedula = new JTextField(25); inputPanelCliente.add(txtClientePasaporteCedula, gbcCliente); row++;
        gbcCliente.gridx = 0; gbcCliente.gridy = row; gbcCliente.anchor = GridBagConstraints.EAST; inputPanelCliente.add(new JLabel("Ciudad:"), gbcCliente);
        gbcCliente.gridx = 1; gbcCliente.gridy = row; gbcCliente.weightx = 1.0; txtClienteCiudad = new JTextField(25); inputPanelCliente.add(txtClienteCiudad, gbcCliente); row++;
        gbcCliente.gridx = 0; gbcCliente.gridy = row; gbcCliente.anchor = GridBagConstraints.EAST; inputPanelCliente.add(new JLabel("Email:"), gbcCliente);
        gbcCliente.gridx = 1; gbcCliente.gridy = row; gbcCliente.weightx = 1.0; txtClienteEmail = new JTextField(25); inputPanelCliente.add(txtClienteEmail, gbcCliente); row++;
        gbcCliente.gridx = 0; gbcCliente.gridy = row; gbcCliente.anchor = GridBagConstraints.EAST; inputPanelCliente.add(new JLabel("Dirección:"), gbcCliente);
        gbcCliente.gridx = 1; gbcCliente.gridy = row; gbcCliente.weightx = 1.0; txtClienteDireccion = new JTextField(25); inputPanelCliente.add(txtClienteDireccion, gbcCliente); row++;

        // Panel de botones de Cliente
        JPanel buttonPanelCliente = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanelCliente.setBackground(new Color(240, 248, 255));
        btnGuardarCliente = createStyledButton("Guardar", new Color(70, 130, 180));
        btnBuscarCliente = createStyledButton("Buscar", new Color(255, 165, 0));
        btnModificarCliente = createStyledButton("Modificar", new Color(255, 99, 71));
        btnEliminarCliente = createStyledButton("Eliminar", new Color(220, 20, 60));
        btnLimpiarCliente = createStyledButton("Limpiar", new Color(100, 149, 237));
        buttonPanelCliente.add(btnGuardarCliente);
        buttonPanelCliente.add(btnBuscarCliente);
        buttonPanelCliente.add(btnModificarCliente);
        buttonPanelCliente.add(btnEliminarCliente);
        buttonPanelCliente.add(btnLimpiarCliente);

        // Tabla de Clientes
        modeloTablaClientes = new DefaultTableModel(new Object[]{"ID", "Nombres", "Cédula", "Ciudad", "Email", "Dirección"}, 0);
        tblClientes = new JTable(modeloTablaClientes);
        tblClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblClientes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblClientes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblClientes.setRowHeight(25);
        JScrollPane scrollClientes = new JScrollPane(tblClientes);
        scrollClientes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Listado de Clientes"));

        panelClientes.add(inputPanelCliente, BorderLayout.NORTH);
        panelClientes.add(buttonPanelCliente, BorderLayout.CENTER);
        panelClientes.add(scrollClientes, BorderLayout.SOUTH);

        tabbedPane.addTab("Clientes", panelClientes);

        // --- Pestaña 2: Gestión de Dispositivos y Planes ---
        JPanel panelDispositivosPlanes = new JPanel(new BorderLayout(10, 10));
        panelDispositivosPlanes.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelDispositivosPlanes.setBackground(new Color(240, 248, 255));

        // Panel de selección de cliente para dispositivos/planes
        JPanel panelSeleccionClienteDP = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelSeleccionClienteDP.setBackground(new Color(220, 230, 240));
        panelSeleccionClienteDP.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Seleccionar Cliente"));
        cmbClientesParaPlanes = new JComboBox<>();
        cmbClientesParaPlanes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbClientesParaPlanes.setPreferredSize(new Dimension(300, 30));
        btnCargarClientePlanes = createStyledButton("Cargar Cliente", new Color(100, 180, 100));
        panelSeleccionClienteDP.add(new JLabel("Cliente:"));
        panelSeleccionClienteDP.add(cmbClientesParaPlanes);
        panelSeleccionClienteDP.add(btnCargarClientePlanes);
        
        panelDispositivosPlanes.add(panelSeleccionClienteDP, BorderLayout.NORTH);

        // Panel principal para Dispositivos y Planes (Grid para dividir)
        JSplitPane splitPaneDP = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPaneDP.setResizeWeight(0.5);

        // Sub-panel de Dispositivos
        JPanel panelDispositivos = new JPanel(new BorderLayout(5, 5));
        panelDispositivos.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Dispositivos Móviles del Cliente"));
        panelDispositivos.setBackground(Color.WHITE);

        JPanel inputPanelDispositivo = new JPanel(new GridBagLayout());
        inputPanelDispositivo.setBackground(Color.WHITE);
        GridBagConstraints gbcDispositivo = new GridBagConstraints();
        gbcDispositivo.insets = new Insets(3, 3, 3, 3);
        gbcDispositivo.fill = GridBagConstraints.HORIZONTAL;

        row = 0;
        gbcDispositivo.gridx = 0; gbcDispositivo.gridy = row; gbcDispositivo.anchor = GridBagConstraints.EAST; inputPanelDispositivo.add(new JLabel("Marca:"), gbcDispositivo);
        gbcDispositivo.gridx = 1; gbcDispositivo.gridy = row; gbcDispositivo.weightx = 1.0; txtDispositivoMarca = new JTextField(15); inputPanelDispositivo.add(txtDispositivoMarca, gbcDispositivo); row++;
        gbcDispositivo.gridx = 0; gbcDispositivo.gridy = row; gbcDispositivo.anchor = GridBagConstraints.EAST; inputPanelDispositivo.add(new JLabel("Modelo:"), gbcDispositivo);
        gbcDispositivo.gridx = 1; gbcDispositivo.gridy = row; gbcDispositivo.weightx = 1.0; txtDispositivoModelo = new JTextField(15); inputPanelDispositivo.add(txtDispositivoModelo, gbcDispositivo); row++;
        gbcDispositivo.gridx = 0; gbcDispositivo.gridy = row; gbcDispositivo.anchor = GridBagConstraints.EAST; inputPanelDispositivo.add(new JLabel("Número Celular:"), gbcDispositivo);
        gbcDispositivo.gridx = 1; gbcDispositivo.gridy = row; gbcDispositivo.weightx = 1.0; txtDispositivoNumero = new JTextField(15); inputPanelDispositivo.add(txtDispositivoNumero, gbcDispositivo); row++;

        JPanel buttonPanelDispositivo = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonPanelDispositivo.setBackground(Color.WHITE);
        btnGuardarDispositivo = createStyledButton("Guardar Dispositivo", new Color(70, 130, 180));
        btnModificarDispositivo = createStyledButton("Modificar Dispositivo", new Color(255, 99, 71));
        btnEliminarDispositivo = createStyledButton("Eliminar Dispositivo", new Color(220, 20, 60));
        btnLimpiarDispositivo = createStyledButton("Limpiar", new Color(100, 149, 237));
        buttonPanelDispositivo.add(btnGuardarDispositivo);
        buttonPanelDispositivo.add(btnModificarDispositivo);
        buttonPanelDispositivo.add(btnEliminarDispositivo);
        buttonPanelDispositivo.add(btnLimpiarDispositivo);

        modeloTablaDispositivos = new DefaultTableModel(new Object[]{"ID Dispositivo", "Marca", "Modelo", "Número Celular"}, 0);
        tblDispositivos = new JTable(modeloTablaDispositivos);
        tblDispositivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblDispositivos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        tblDispositivos.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tblDispositivos.setRowHeight(22);
        JScrollPane scrollDispositivos = new JScrollPane(tblDispositivos);

        panelDispositivos.add(inputPanelDispositivo, BorderLayout.NORTH);
        panelDispositivos.add(buttonPanelDispositivo, BorderLayout.CENTER);
        panelDispositivos.add(scrollDispositivos, BorderLayout.SOUTH);

        splitPaneDP.setTopComponent(panelDispositivos);

        // Sub-panel de Planes
        JPanel panelPlanes = new JPanel(new BorderLayout(5, 5));
        panelPlanes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Planes Móviles del Cliente"));
        panelPlanes.setBackground(Color.WHITE);

        JPanel topPanelPlanes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanelPlanes.setBackground(Color.WHITE);
        topPanelPlanes.add(new JLabel("Tipo de Plan:"));
        String[] tiposPlan = {"", "Economico", "Minutos", "Megas", "MinutosMegas"};
        cmbTipoPlan = new JComboBox<>(tiposPlan);
        cmbTipoPlan.setPreferredSize(new Dimension(150, 25));
        cmbTipoPlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCamposPlanes();
            }
        });
        topPanelPlanes.add(cmbTipoPlan);

        panelCamposPlanes = new JPanel(new GridBagLayout());
        panelCamposPlanes.setBackground(Color.WHITE);

        // Inicializar etiquetas y campos de texto para planes
        lblPlanMinutos = new JLabel("Minutos:"); txtPlanMinutos = new JTextField(10);
        lblPlanCostoMinutos = new JLabel("Costo Minutos:"); txtPlanCostoMinutos = new JTextField(10);
        lblPlanMegasGigas = new JLabel("Megas (GB):"); txtPlanMegasGigas = new JTextField(10);
        lblPlanCostoPorGiga = new JLabel("Costo por GB:"); txtPlanCostoPorGiga = new JTextField(10);
        lblPlanPorcentajeDescuento = new JLabel("Desc. (%):"); txtPlanPorcentajeDescuento = new JTextField(10);
        lblPlanMinutosNacionales = new JLabel("Min. Nacionales:"); txtPlanMinutosNacionales = new JTextField(10);
        lblPlanCostoMinutoNacional = new JLabel("Costo Min. Nacional:"); txtPlanCostoMinutoNacional = new JTextField(10);
        lblPlanMinutosInternacionales = new JLabel("Min. Internacionales:"); txtPlanMinutosInternacionales = new JTextField(10);
        lblPlanCostoMinutoInternacional = new JLabel("Costo Min. Internacional:"); txtPlanCostoMinutoInternacional = new JTextField(10);
        lblPlanTarifaBase = new JLabel("Tarifa Base:"); txtPlanTarifaBase = new JTextField(10);
        
        // Agregar los campos y sus etiquetas al panelCamposPlanes (inicialmente ocultos)
        addPlanField(panelCamposPlanes, lblPlanMinutos, txtPlanMinutos, 0);
        addPlanField(panelCamposPlanes, lblPlanCostoMinutos, txtPlanCostoMinutos, 1);
        addPlanField(panelCamposPlanes, lblPlanMegasGigas, txtPlanMegasGigas, 2);
        addPlanField(panelCamposPlanes, lblPlanCostoPorGiga, txtPlanCostoPorGiga, 3);
        addPlanField(panelCamposPlanes, lblPlanPorcentajeDescuento, txtPlanPorcentajeDescuento, 4);
        addPlanField(panelCamposPlanes, lblPlanMinutosNacionales, txtPlanMinutosNacionales, 5);
        addPlanField(panelCamposPlanes, lblPlanCostoMinutoNacional, txtPlanCostoMinutoNacional, 6);
        addPlanField(panelCamposPlanes, lblPlanMinutosInternacionales, txtPlanMinutosInternacionales, 7);
        addPlanField(panelCamposPlanes, lblPlanCostoMinutoInternacional, txtPlanCostoMinutoInternacional, 8);
        addPlanField(panelCamposPlanes, lblPlanTarifaBase, txtPlanTarifaBase, 9);
        
        actualizarCamposPlanes(); // Llamada inicial para ocultar todos los campos

        JPanel buttonPanelPlanes = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonPanelPlanes.setBackground(Color.WHITE);
        btnGuardarPlan = createStyledButton("Guardar Plan", new Color(70, 130, 180));
        btnModificarPlan = createStyledButton("Modificar Plan", new Color(255, 99, 71));
        btnEliminarPlan = createStyledButton("Eliminar Plan", new Color(220, 20, 60));
        btnLimpiarPlan = createStyledButton("Limpiar", new Color(100, 149, 237));
        buttonPanelPlanes.add(btnGuardarPlan);
        buttonPanelPlanes.add(btnModificarPlan);
        buttonPanelPlanes.add(btnEliminarPlan);
        buttonPanelPlanes.add(btnLimpiarPlan);

        modeloTablaPlanes = new DefaultTableModel(new Object[]{"ID Plan", "Tipo", "Pago Mensual"}, 0);
        tblPlanes = new JTable(modeloTablaPlanes);
        tblPlanes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblPlanes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        tblPlanes.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tblPlanes.setRowHeight(22);
        JScrollPane scrollPlanes = new JScrollPane(tblPlanes);

        panelPlanes.add(topPanelPlanes, BorderLayout.NORTH);
        panelPlanes.add(panelCamposPlanes, BorderLayout.CENTER);
        panelPlanes.add(buttonPanelPlanes, BorderLayout.SOUTH);
        panelPlanes.add(scrollPlanes, BorderLayout.EAST);

        splitPaneDP.setBottomComponent(panelPlanes);
        panelDispositivosPlanes.add(splitPaneDP, BorderLayout.CENTER);

        tabbedPane.addTab("Dispositivos y Planes", panelDispositivosPlanes);

        // --- Pestaña 3: Facturación ---
        JPanel panelFacturacion = new JPanel(new BorderLayout(10, 10));
        panelFacturacion.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelFacturacion.setBackground(new Color(240, 248, 255));

        // Panel de selección de cliente para facturas
        JPanel panelSeleccionClienteFactura = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelSeleccionClienteFactura.setBackground(new Color(220, 230, 240));
        panelSeleccionClienteFactura.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Seleccionar Cliente para Facturación"));
        cmbClientesParaFacturas = new JComboBox<>();
        cmbClientesParaFacturas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbClientesParaFacturas.setPreferredSize(new Dimension(300, 30));
        btnCargarClienteFacturas = createStyledButton("Cargar Cliente", new Color(100, 180, 100));
        panelSeleccionClienteFactura.add(new JLabel("Cliente:"));
        panelSeleccionClienteFactura.add(cmbClientesParaFacturas);
        panelSeleccionClienteFactura.add(btnCargarClienteFacturas);

        JPanel buttonPanelFacturas = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanelFacturas.setBackground(new Color(240, 248, 255));
        btnGenerarFactura = createStyledButton("Generar Factura", new Color(0, 128, 0));
        btnMarcarPagada = createStyledButton("Marcar como Pagada", new Color(30, 144, 255));
        btnVerDetalleFactura = createStyledButton("Ver Detalle", new Color(75, 0, 130));
        buttonPanelFacturas.add(btnGenerarFactura);
        buttonPanelFacturas.add(btnMarcarPagada);
        buttonPanelFacturas.add(btnVerDetalleFactura);

        modeloTablaFacturas = new DefaultTableModel(new Object[]{"ID Factura", "Fecha Emisión", "Total a Pagar", "Estado"}, 0);
        tblFacturas = new JTable(modeloTablaFacturas);
        tblFacturas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblFacturas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblFacturas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblFacturas.setRowHeight(25);
        JScrollPane scrollFacturas = new JScrollPane(tblFacturas);
        scrollFacturas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Facturas del Cliente"));

        txtDetalleFactura = new JTextArea(8, 40);
        txtDetalleFactura.setEditable(false);
        txtDetalleFactura.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtDetalleFactura.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Detalle de Factura Seleccionada"));
        JScrollPane scrollDetalleFactura = new JScrollPane(txtDetalleFactura);

        JSplitPane splitPaneFacturas = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPaneFacturas.setResizeWeight(0.7);
        splitPaneFacturas.setTopComponent(scrollFacturas);
        splitPaneFacturas.setBottomComponent(scrollDetalleFactura);

        panelFacturacion.add(panelSeleccionClienteFactura, BorderLayout.NORTH);
        panelFacturacion.add(buttonPanelFacturas, BorderLayout.CENTER);
        panelFacturacion.add(splitPaneFacturas, BorderLayout.SOUTH);

        tabbedPane.addTab("Facturación", panelFacturacion);

        // --- Añadir el JTabbedPane al JFrame principal ---
        add(tabbedPane);

        // --- Cargar datos iniciales y configurar listeners ---
        cargarClientesEnComboBoxes();
        agregarListeners();
        limpiarCamposCliente();
        cargarTablaClientes();

        setVisible(true);
    }

    // --- Métodos Auxiliares de UI ---
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void addPlanField(JPanel panel, JLabel label, JTextField textField, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.gridx = 0; gbc.gridy = row; gbc.anchor = GridBagConstraints.EAST;
        panel.add(label, gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(textField, gbc);
        label.setVisible(false); // Inicialmente ocultos
        textField.setVisible(false);
    }

    private void actualizarCamposPlanes() {
        String tipoSeleccionado = (String) cmbTipoPlan.getSelectedItem();

        // Ocultar todos los campos y sus etiquetas primero
        lblPlanMinutos.setVisible(false); txtPlanMinutos.setText(""); txtPlanMinutos.setVisible(false);
        lblPlanCostoMinutos.setVisible(false); txtPlanCostoMinutos.setText(""); txtPlanCostoMinutos.setVisible(false);
        lblPlanMegasGigas.setVisible(false); txtPlanMegasGigas.setText(""); txtPlanMegasGigas.setVisible(false);
        lblPlanCostoPorGiga.setVisible(false); txtPlanCostoPorGiga.setText(""); txtPlanCostoPorGiga.setVisible(false);
        lblPlanPorcentajeDescuento.setVisible(false); txtPlanPorcentajeDescuento.setText(""); txtPlanPorcentajeDescuento.setVisible(false);
        lblPlanMinutosNacionales.setVisible(false); txtPlanMinutosNacionales.setText(""); txtPlanMinutosNacionales.setVisible(false);
        lblPlanCostoMinutoNacional.setVisible(false); txtPlanCostoMinutoNacional.setText(""); txtPlanCostoMinutoNacional.setVisible(false);
        lblPlanMinutosInternacionales.setVisible(false); txtPlanMinutosInternacionales.setText(""); txtPlanMinutosInternacionales.setVisible(false);
        lblPlanCostoMinutoInternacional.setVisible(false); txtPlanCostoMinutoInternacional.setText(""); txtPlanCostoMinutoInternacional.setVisible(false);
        lblPlanTarifaBase.setVisible(false); txtPlanTarifaBase.setText(""); txtPlanTarifaBase.setVisible(false);

        // Mostrar campos y sus etiquetas según el tipo de plan seleccionado
        switch (tipoSeleccionado) {
            case "Economico":
                lblPlanMinutos.setVisible(true); txtPlanMinutos.setVisible(true);
                lblPlanCostoMinutos.setVisible(true); txtPlanCostoMinutos.setVisible(true);
                lblPlanMegasGigas.setVisible(true); txtPlanMegasGigas.setVisible(true);
                lblPlanCostoPorGiga.setVisible(true); txtPlanCostoPorGiga.setVisible(true);
                lblPlanPorcentajeDescuento.setVisible(true); txtPlanPorcentajeDescuento.setVisible(true);
                break;
            case "Minutos":
                lblPlanMinutosNacionales.setVisible(true); txtPlanMinutosNacionales.setVisible(true);
                lblPlanCostoMinutoNacional.setVisible(true); txtPlanCostoMinutoNacional.setVisible(true);
                lblPlanMinutosInternacionales.setVisible(true); txtPlanMinutosInternacionales.setVisible(true);
                lblPlanCostoMinutoInternacional.setVisible(true); txtPlanCostoMinutoInternacional.setVisible(true);
                break;
            case "Megas":
                lblPlanMegasGigas.setVisible(true); txtPlanMegasGigas.setVisible(true);
                lblPlanCostoPorGiga.setVisible(true); txtPlanCostoPorGiga.setVisible(true);
                lblPlanTarifaBase.setVisible(true); txtPlanTarifaBase.setVisible(true);
                break;
            case "MinutosMegas":
                lblPlanMinutos.setVisible(true); txtPlanMinutos.setVisible(true);
                lblPlanCostoMinutos.setVisible(true); txtPlanCostoMinutos.setVisible(true);
                lblPlanMegasGigas.setVisible(true); txtPlanMegasGigas.setVisible(true);
                lblPlanCostoPorGiga.setVisible(true); txtPlanCostoPorGiga.setVisible(true);
                break;
            default:
                // No mostrar nada si no hay tipo seleccionado o es desconocido
                break;
        }
        panelCamposPlanes.revalidate();
        panelCamposPlanes.repaint();
    }

    // --- Métodos de Carga y Limpieza ---
    private void cargarClientesEnComboBoxes() {
        List<Cliente> clientes = clienteDAO.obtenerTodosClientes();
        cmbClientesParaPlanes.removeAllItems();
        cmbClientesParaFacturas.removeAllItems();
        for (Cliente c : clientes) {
            cmbClientesParaPlanes.addItem(c);
            cmbClientesParaFacturas.addItem(c);
        }
    }

    private void cargarTablaClientes() {
        modeloTablaClientes.setRowCount(0);
        List<Cliente> clientes = clienteDAO.obtenerTodosClientes();
        for (Cliente c : clientes) {
            modeloTablaClientes.addRow(new Object[]{
                c.getIdCliente(),
                c.getNombres(),
                c.getPasaporteCedula(),
                c.getCiudad(),
                c.getEmail(),
                c.getDireccion()
            });
        }
    }

    private void limpiarCamposCliente() {
        txtClienteNombres.setText("");
        txtClientePasaporteCedula.setText("");
        txtClienteCiudad.setText("");
        txtClienteEmail.setText("");
        txtClienteDireccion.setText("");
        txtClientePasaporteCedula.setEditable(true);
        btnModificarCliente.setEnabled(false);
        btnEliminarCliente.setEnabled(false);
        clienteSeleccionado = null;
    }

    private void limpiarCamposDispositivo() {
        txtDispositivoMarca.setText("");
        txtDispositivoModelo.setText("");
        txtDispositivoNumero.setText("");
        dispositivoSeleccionado = null;
        btnModificarDispositivo.setEnabled(false);
        btnEliminarDispositivo.setEnabled(false);
    }

    private void limpiarCamposPlan() {
        cmbTipoPlan.setSelectedIndex(0);
        actualizarCamposPlanes();
        planSeleccionado = null;
        btnModificarPlan.setEnabled(false);
        btnEliminarPlan.setEnabled(false);
    }

    private void cargarDispositivosYPlanesDelCliente(Cliente cliente) {
        if (cliente == null) {
            modeloTablaDispositivos.setRowCount(0);
            modeloTablaPlanes.setRowCount(0);
            limpiarCamposDispositivo();
            limpiarCamposPlan();
            return;
        }
        
        modeloTablaDispositivos.setRowCount(0);
        List<DispositivoMovil> dispositivos = dispositivoDAO.obtenerDispositivosPorCliente(cliente.getIdCliente());
        for (DispositivoMovil dm : dispositivos) {
            modeloTablaDispositivos.addRow(new Object[]{
                dm.getIdDispositivo(),
                dm.getMarca(),
                dm.getModelo(),
                dm.getNumeroCelular()
            });
        }

        modeloTablaPlanes.setRowCount(0);
        List<PlanMovil> planes = planDAO.obtenerPlanesPorCliente(cliente.getIdCliente());
        for (PlanMovil pm : planes) {
            modeloTablaPlanes.addRow(new Object[]{
                pm.getIdPlan(),
                pm.getTipoPlan(),
                String.format("%.2f", pm.calcularPagoMensual())
            });
        }
        limpiarCamposDispositivo();
        limpiarCamposPlan();
    }

    private void cargarFacturasDelCliente(Cliente cliente) {
        modeloTablaFacturas.setRowCount(0);
        txtDetalleFactura.setText("");
        if (cliente == null) {
            return;
        }
        List<Factura> facturas = facturaDAO.obtenerFacturasPorCliente(cliente.getIdCliente());
        for (Factura f : facturas) {
            modeloTablaFacturas.addRow(new Object[]{
                f.getIdFactura(),
                f.getFechaEmision(),
                String.format("%.2f", f.getTotalPagar()),
                f.getEstado()
            });
        }
        btnMarcarPagada.setEnabled(false);
        btnVerDetalleFactura.setEnabled(false);
        facturaSeleccionada = null;
    }

    // --- Métodos de Acción (Listeners) ---
    private void agregarListeners() {
        // Listeners para Clientes
        btnGuardarCliente.addActionListener(this::guardarCliente);
        btnBuscarCliente.addActionListener(this::buscarCliente);
        btnModificarCliente.addActionListener(this::modificarCliente);
        btnEliminarCliente.addActionListener(this::eliminarCliente);
        btnLimpiarCliente.addActionListener(e -> limpiarCamposCliente());
        tblClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblClientes.getSelectedRow() != -1) {
                cargarClienteSeleccionadoDesdeTabla();
            }
        });

        // Listeners para Dispositivos y Planes
        btnCargarClientePlanes.addActionListener(e -> {
            clienteSeleccionado = (Cliente) cmbClientesParaPlanes.getSelectedItem();
            cargarDispositivosYPlanesDelCliente(clienteSeleccionado);
            limpiarCamposDispositivo();
            limpiarCamposPlan();
        });

        // Listeners para Dispositivos
        btnGuardarDispositivo.addActionListener(this::guardarDispositivo);
        btnModificarDispositivo.addActionListener(this::modificarDispositivo);
        btnEliminarDispositivo.addActionListener(this::eliminarDispositivo);
        btnLimpiarDispositivo.addActionListener(e -> limpiarCamposDispositivo());
        tblDispositivos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblDispositivos.getSelectedRow() != -1) {
                cargarDispositivoSeleccionadoDesdeTabla();
            }
        });

        // Listeners para Planes
        btnGuardarPlan.addActionListener(this::guardarPlan);
        btnModificarPlan.addActionListener(this::modificarPlan);
        btnEliminarPlan.addActionListener(this::eliminarPlan);
        btnLimpiarPlan.addActionListener(e -> limpiarCamposPlan());
        tblPlanes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblPlanes.getSelectedRow() != -1) {
                cargarPlanSeleccionadoDesdeTabla();
            }
        });

        // Listeners para Facturación
        btnCargarClienteFacturas.addActionListener(e -> {
            clienteSeleccionado = (Cliente) cmbClientesParaFacturas.getSelectedItem();
            cargarFacturasDelCliente(clienteSeleccionado);
        });
        btnGenerarFactura.addActionListener(this::generarFactura);
        btnMarcarPagada.addActionListener(this::marcarFacturaPagada);
        tblFacturas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblFacturas.getSelectedRow() != -1) {
                cargarFacturaSeleccionadaDesdeTabla();
            }
        });
        btnVerDetalleFactura.addActionListener(this::verDetalleFactura);
    }

    // --- Lógica de Clientes ---
    private void cargarClienteSeleccionadoDesdeTabla() {
        int selectedRow = tblClientes.getSelectedRow();
        if (selectedRow != -1) {
            String cedula = (String) modeloTablaClientes.getValueAt(selectedRow, 2);
            clienteSeleccionado = clienteDAO.buscarClientePorCedula(cedula);
            if (clienteSeleccionado != null) {
                txtClienteNombres.setText(clienteSeleccionado.getNombres());
                txtClientePasaporteCedula.setText(clienteSeleccionado.getPasaporteCedula());
                txtClienteCiudad.setText(clienteSeleccionado.getCiudad());
                txtClienteEmail.setText(clienteSeleccionado.getEmail());
                txtClienteDireccion.setText(clienteSeleccionado.getDireccion());
                txtClientePasaporteCedula.setEditable(false);
                btnModificarCliente.setEnabled(true);
                btnEliminarCliente.setEnabled(true);
            }
        }
    }

    private void guardarCliente(ActionEvent e) {
        try {
            String nombres = txtClienteNombres.getText().trim();
            String pasaporteCedula = txtClientePasaporteCedula.getText().trim();
            String ciudad = txtClienteCiudad.getText().trim();
            String email = txtClienteEmail.getText().trim();
            String direccion = txtClienteDireccion.getText().trim();

            if (nombres.isEmpty() || pasaporteCedula.isEmpty() || ciudad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombres, Cédula/Pasaporte y Ciudad son campos obligatorios.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Cliente nuevoCliente = new Cliente(nombres, pasaporteCedula, ciudad, email, direccion);
            clienteDAO.insertarCliente(nuevoCliente);
            JOptionPane.showMessageDialog(this, "Cliente guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCamposCliente();
            cargarTablaClientes();
            cargarClientesEnComboBoxes();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado al guardar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void buscarCliente(ActionEvent e) {
        String cedulaBuscada = JOptionPane.showInputDialog(this, "Ingrese la Cédula/Pasaporte del cliente a buscar:");
        if (cedulaBuscada != null && !cedulaBuscada.trim().isEmpty()) {
            clienteSeleccionado = clienteDAO.buscarClientePorCedula(cedulaBuscada.trim());
            if (clienteSeleccionado != null) {
                txtClienteNombres.setText(clienteSeleccionado.getNombres());
                txtClientePasaporteCedula.setText(clienteSeleccionado.getPasaporteCedula());
                txtClienteCiudad.setText(clienteSeleccionado.getCiudad());
                txtClienteEmail.setText(clienteSeleccionado.getEmail());
                txtClienteDireccion.setText(clienteSeleccionado.getDireccion());
                txtClientePasaporteCedula.setEditable(false);
                btnModificarCliente.setEnabled(true);
                btnEliminarCliente.setEnabled(true);
            } else {
                limpiarCamposCliente();
                JOptionPane.showMessageDialog(this, "Cliente no encontrado.", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (cedulaBuscada != null) {
            JOptionPane.showMessageDialog(this, "La Cédula/Pasaporte no puede estar vacía.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void modificarCliente(ActionEvent e) {
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Primero debe buscar un cliente para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            clienteSeleccionado.setNombres(txtClienteNombres.getText().trim());
            clienteSeleccionado.setCiudad(txtClienteCiudad.getText().trim());
            clienteSeleccionado.setEmail(txtClienteEmail.getText().trim());
            clienteSeleccionado.setDireccion(txtClienteDireccion.getText().trim());

            clienteDAO.actualizarCliente(clienteSeleccionado);
            JOptionPane.showMessageDialog(this, "Cliente modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCamposCliente();
            cargarTablaClientes();
            cargarClientesEnComboBoxes();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al modificar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void eliminarCliente(ActionEvent e) {
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Primero debe buscar un cliente para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar al cliente " + clienteSeleccionado.getNombres() + "? Esto también eliminará sus dispositivos, planes y facturas asociadas.", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                clienteDAO.eliminarCliente(clienteSeleccionado.getPasaporteCedula());
                JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCamposCliente();
                cargarTablaClientes();
                cargarClientesEnComboBoxes();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ocurrió un error al eliminar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    // --- Lógica de Dispositivos ---
    private void cargarDispositivoSeleccionadoDesdeTabla() {
        int selectedRow = tblDispositivos.getSelectedRow();
        if (selectedRow != -1) {
            int idDispositivo = (int) modeloTablaDispositivos.getValueAt(selectedRow, 0);
            dispositivoSeleccionado = dispositivoDAO.buscarDispositivoPorId(idDispositivo);
            if (dispositivoSeleccionado != null) {
                txtDispositivoMarca.setText(dispositivoSeleccionado.getMarca());
                txtDispositivoModelo.setText(dispositivoSeleccionado.getModelo());
                txtDispositivoNumero.setText(dispositivoSeleccionado.getNumeroCelular());
                btnModificarDispositivo.setEnabled(true);
                btnEliminarDispositivo.setEnabled(true);
            }
        }
    }

    private void guardarDispositivo(ActionEvent e) {
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente primero para guardar un dispositivo.", "Cliente No Seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String marca = txtDispositivoMarca.getText().trim();
            String modelo = txtDispositivoModelo.getText().trim();
            String numero = txtDispositivoNumero.getText().trim();

            if (marca.isEmpty() || modelo.isEmpty() || numero.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Marca, Modelo y Número Celular son obligatorios para el dispositivo.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            DispositivoMovil nuevoDispositivo = new DispositivoMovil(clienteSeleccionado.getIdCliente(), marca, modelo, numero);
            dispositivoDAO.insertarDispositivo(nuevoDispositivo);
            JOptionPane.showMessageDialog(this, "Dispositivo guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDispositivosYPlanesDelCliente(clienteSeleccionado);
            limpiarCamposDispositivo();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado al guardar el dispositivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void modificarDispositivo(ActionEvent e) {
        if (dispositivoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Primero debe seleccionar un dispositivo para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            dispositivoSeleccionado.setMarca(txtDispositivoMarca.getText().trim());
            dispositivoSeleccionado.setModelo(txtDispositivoModelo.getText().trim());
            dispositivoSeleccionado.setNumeroCelular(txtDispositivoNumero.getText().trim());

            dispositivoDAO.actualizarDispositivo(dispositivoSeleccionado);
            JOptionPane.showMessageDialog(this, "Dispositivo modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDispositivosYPlanesDelCliente(clienteSeleccionado);
            limpiarCamposDispositivo();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al modificar el dispositivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void eliminarDispositivo(ActionEvent e) {
        if (dispositivoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Primero debe seleccionar un dispositivo para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar el dispositivo " + dispositivoSeleccionado.getNumeroCelular() + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                dispositivoDAO.eliminarDispositivo(dispositivoSeleccionado.getIdDispositivo());
                JOptionPane.showMessageDialog(this, "Dispositivo eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDispositivosYPlanesDelCliente(clienteSeleccionado);
                limpiarCamposDispositivo();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ocurrió un error al eliminar el dispositivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    // --- Lógica de Planes ---
    private void cargarPlanSeleccionadoDesdeTabla() {
        int selectedRow = tblPlanes.getSelectedRow();
        if (selectedRow != -1) {
            int idPlan = (int) modeloTablaPlanes.getValueAt(selectedRow, 0);
            planSeleccionado = planDAO.buscarPlanPorId(idPlan);
            if (planSeleccionado != null) {
                cmbTipoPlan.setSelectedItem(planSeleccionado.getTipoPlan());
                // Rellenar campos específicos del plan
                if (planSeleccionado instanceof PlanPostPagoMinutosMegasEconomico) {
                    PlanPostPagoMinutosMegasEconomico p = (PlanPostPagoMinutosMegasEconomico) planSeleccionado;
                    txtPlanMinutos.setText(String.valueOf(p.getMinutos()));
                    txtPlanCostoMinutos.setText(String.valueOf(p.getCostoMinutos()));
                    txtPlanMegasGigas.setText(String.valueOf(p.getMegasGigas()));
                    txtPlanCostoPorGiga.setText(String.valueOf(p.getCostoPorGiga()));
                    txtPlanPorcentajeDescuento.setText(String.valueOf(p.getPorcentajeDescuento()));
                } else if (planSeleccionado instanceof PlanPostPagoMinutos) {
                    PlanPostPagoMinutos p = (PlanPostPagoMinutos) planSeleccionado;
                    txtPlanMinutosNacionales.setText(String.valueOf(p.getMinutosNacionales()));
                    txtPlanCostoMinutoNacional.setText(String.valueOf(p.getCostoMinutoNacional()));
                    txtPlanMinutosInternacionales.setText(String.valueOf(p.getMinutosInternacionales()));
                    txtPlanCostoMinutoInternacional.setText(String.valueOf(p.getCostoMinutoInternacional()));
                } else if (planSeleccionado instanceof PlanPostPagoMegas) {
                    PlanPostPagoMegas p = (PlanPostPagoMegas) planSeleccionado;
                    txtPlanMegasGigas.setText(String.valueOf(p.getMegasGigas()));
                    txtPlanCostoPorGiga.setText(String.valueOf(p.getCostoPorGiga()));
                    txtPlanTarifaBase.setText(String.valueOf(p.getTarifaBase()));
                } else if (planSeleccionado instanceof PlanPostPagoMinutosMegas) {
                    PlanPostPagoMinutosMegas p = (PlanPostPagoMinutosMegas) planSeleccionado;
                    txtPlanMinutos.setText(String.valueOf(p.getMinutos()));
                    txtPlanCostoMinutos.setText(String.valueOf(p.getCostoMinutos()));
                    txtPlanMegasGigas.setText(String.valueOf(p.getMegasGigas()));
                    txtPlanCostoPorGiga.setText(String.valueOf(p.getCostoPorGiga()));
                }
                btnModificarPlan.setEnabled(true);
                btnEliminarPlan.setEnabled(true);
            }
        }
    }

    private void guardarPlan(ActionEvent e) {
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente primero para guardar un plan.", "Cliente No Seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        List<PlanMovil> planesActuales = planDAO.obtenerPlanesPorCliente(clienteSeleccionado.getIdCliente());
        if (planesActuales.size() >= 2) {
            JOptionPane.showMessageDialog(this, "El cliente ya tiene 2 planes asignados. No puede agregar más.", "Límite de Planes", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tipo = (String) cmbTipoPlan.getSelectedItem();
        if (tipo == null || tipo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un tipo de plan.", "Tipo de Plan Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            PlanMovil nuevoPlan = null;
            switch (tipo) {
                case "Economico":
                    nuevoPlan = new PlanPostPagoMinutosMegasEconomico(
                        clienteSeleccionado.getIdCliente(),
                        parseDouble(txtPlanMinutos.getText()), parseDouble(txtPlanCostoMinutos.getText()),
                        parseDouble(txtPlanMegasGigas.getText()), parseDouble(txtPlanCostoPorGiga.getText()),
                        parseDouble(txtPlanPorcentajeDescuento.getText())
                    );
                    break;
                case "Minutos":
                    nuevoPlan = new PlanPostPagoMinutos(
                        clienteSeleccionado.getIdCliente(),
                        parseDouble(txtPlanMinutosNacionales.getText()), parseDouble(txtPlanCostoMinutoNacional.getText()),
                        parseDouble(txtPlanMinutosInternacionales.getText()), parseDouble(txtPlanCostoMinutoInternacional.getText())
                    );
                    break;
                case "Megas":
                    nuevoPlan = new PlanPostPagoMegas(
                        clienteSeleccionado.getIdCliente(),
                        parseDouble(txtPlanMegasGigas.getText()), parseDouble(txtPlanCostoPorGiga.getText()),
                        parseDouble(txtPlanTarifaBase.getText())
                    );
                    break;
                case "MinutosMegas":
                    nuevoPlan = new PlanPostPagoMinutosMegas(
                        clienteSeleccionado.getIdCliente(),
                        parseDouble(txtPlanMinutos.getText()), parseDouble(txtPlanCostoMinutos.getText()),
                        parseDouble(txtPlanMegasGigas.getText()), parseDouble(txtPlanCostoPorGiga.getText())
                    );
                    break;
            }

            if (nuevoPlan != null) {
                planDAO.insertarPlan(nuevoPlan);
                JOptionPane.showMessageDialog(this, "Plan guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDispositivosYPlanesDelCliente(clienteSeleccionado);
                limpiarCamposPlan();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error de formato numérico en los campos del plan. Verifique los valores.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado al guardar el plan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void modificarPlan(ActionEvent e) {
        if (planSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Primero debe seleccionar un plan para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String tipo = (String) cmbTipoPlan.getSelectedItem();
        if (tipo == null || tipo.isEmpty() || !tipo.equals(planSeleccionado.getTipoPlan())) {
            JOptionPane.showMessageDialog(this, "No se puede cambiar el tipo de plan al modificar. Seleccione el tipo original.", "Error de Tipo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            PlanMovil planModificado = null;
            switch (tipo) {
                case "Economico":
                    planModificado = new PlanPostPagoMinutosMegasEconomico(
                        planSeleccionado.getIdPlan(), planSeleccionado.getIdCliente(),
                        parseDouble(txtPlanMinutos.getText()), parseDouble(txtPlanCostoMinutos.getText()),
                        parseDouble(txtPlanMegasGigas.getText()), parseDouble(txtPlanCostoPorGiga.getText()),
                        parseDouble(txtPlanPorcentajeDescuento.getText())
                    );
                    break;
                case "Minutos":
                    planModificado = new PlanPostPagoMinutos(
                        planSeleccionado.getIdPlan(), planSeleccionado.getIdCliente(),
                        parseDouble(txtPlanMinutosNacionales.getText()), parseDouble(txtPlanCostoMinutoNacional.getText()),
                        parseDouble(txtPlanMinutosInternacionales.getText()), parseDouble(txtPlanCostoMinutoInternacional.getText())
                    );
                    break;
                case "Megas":
                    planModificado = new PlanPostPagoMegas(
                        planSeleccionado.getIdPlan(), planSeleccionado.getIdCliente(),
                        parseDouble(txtPlanMegasGigas.getText()), parseDouble(txtPlanCostoPorGiga.getText()),
                        parseDouble(txtPlanTarifaBase.getText())
                    );
                    break;
                case "MinutosMegas":
                    planModificado = new PlanPostPagoMinutosMegas(
                        planSeleccionado.getIdPlan(), planSeleccionado.getIdCliente(),
                        parseDouble(txtPlanMinutos.getText()), parseDouble(txtPlanCostoMinutos.getText()),
                        parseDouble(txtPlanMegasGigas.getText()), parseDouble(txtPlanCostoPorGiga.getText())
                    );
                    break;
            }

            if (planModificado != null) {
                planDAO.actualizarPlan(planModificado);
                JOptionPane.showMessageDialog(this, "Plan modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDispositivosYPlanesDelCliente(clienteSeleccionado);
                limpiarCamposPlan();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error de formato numérico en los campos del plan. Verifique los valores.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado al modificar el plan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void eliminarPlan(ActionEvent e) {
        if (planSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Primero debe seleccionar un plan para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar el plan de tipo " + planSeleccionado.getTipoPlan() + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                planDAO.eliminarPlan(planSeleccionado.getIdPlan());
                JOptionPane.showMessageDialog(this, "Plan eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDispositivosYPlanesDelCliente(clienteSeleccionado);
                limpiarCamposPlan();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ocurrió un error al eliminar el plan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    // Método auxiliar para parsear double, maneja campos vacíos como 0.0
    private double parseDouble(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0.0;
        }
        return Double.parseDouble(text.trim());
    }

    // --- Lógica de Facturación ---
    private void cargarFacturaSeleccionadaDesdeTabla() {
        int selectedRow = tblFacturas.getSelectedRow();
        if (selectedRow != -1) {
            int idFactura = (int) modeloTablaFacturas.getValueAt(selectedRow, 0);
            facturaSeleccionada = facturaDAO.buscarFacturaPorId(idFactura);
            if (facturaSeleccionada != null) {
                btnMarcarPagada.setEnabled(!facturaSeleccionada.getEstado().equalsIgnoreCase("Pagada"));
                btnVerDetalleFactura.setEnabled(true);
            }
        }
    }

    private void generarFactura(ActionEvent e) {
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente para generar una factura.", "Cliente No Seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<PlanMovil> planesCliente = planDAO.obtenerPlanesPorCliente(clienteSeleccionado.getIdCliente());
        if (planesCliente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El cliente no tiene planes activos para facturar.", "Sin Planes", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double totalFactura = 0.0;
        for (PlanMovil plan : planesCliente) {
            totalFactura += plan.calcularPagoMensual();
        }

        // Crear la factura
        Factura nuevaFactura = new Factura(clienteSeleccionado.getIdCliente(), LocalDate.now(), totalFactura, "Pendiente");
        try {
            int idFacturaGenerada = facturaDAO.insertarFactura(nuevaFactura);

            // Crear los detalles de la factura
            for (PlanMovil plan : planesCliente) {
                DetalleFactura detalle = new DetalleFactura(idFacturaGenerada, plan.getIdPlan(), plan.calcularPagoMensual());
                detalleFacturaDAO.insertarDetalleFactura(detalle);
            }
            JOptionPane.showMessageDialog(this, "Factura generada correctamente para " + clienteSeleccionado.getNombres() + ".\nTotal: $" + String.format("%.2f", totalFactura), "Factura Generada", JOptionPane.INFORMATION_MESSAGE);
            cargarFacturasDelCliente(clienteSeleccionado);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al generar la factura: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void marcarFacturaPagada(ActionEvent e) {
        if (facturaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una factura para marcar como pagada.", "Factura No Seleccionada", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (facturaSeleccionada.getEstado().equalsIgnoreCase("Pagada")) {
            JOptionPane.showMessageDialog(this, "Esta factura ya está marcada como pagada.", "Estado Actual", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea marcar esta factura como PAGADA?", "Confirmar Pago", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                facturaSeleccionada.setEstado("Pagada");
                facturaDAO.actualizarFactura(facturaSeleccionada);
                JOptionPane.showMessageDialog(this, "Factura marcada como pagada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarFacturasDelCliente(clienteSeleccionado);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al marcar factura como pagada: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void verDetalleFactura(ActionEvent e) {
        if (facturaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una factura para ver su detalle.", "Factura No Seleccionada", JOptionPane.WARNING_MESSAGE);
            return;
        }
        txtDetalleFactura.setText("");
        txtDetalleFactura.append("--- Detalle de Factura ID: " + facturaSeleccionada.getIdFactura() + " ---\n");
        txtDetalleFactura.append("Fecha Emisión: " + facturaSeleccionada.getFechaEmision() + "\n");
        txtDetalleFactura.append("Estado: " + facturaSeleccionada.getEstado() + "\n");
        txtDetalleFactura.append("---------------------------------------\n");
        txtDetalleFactura.append("Planes incluidos:\n");

        List<DetalleFactura> detalles = detalleFacturaDAO.obtenerDetallesPorFactura(facturaSeleccionada.getIdFactura());
        if (detalles.isEmpty()) {
            txtDetalleFactura.append("   No hay detalles de planes para esta factura.\n");
        } else {
            for (DetalleFactura det : detalles) {
                PlanMovil plan = planDAO.buscarPlanPorId(det.getIdPlan());
                if (plan != null) {
                    txtDetalleFactura.append(String.format("   - Plan ID %d (%s): $%.2f (Facturado: $%.2f)\n",
                        plan.getIdPlan(), plan.getTipoPlan(), plan.calcularPagoMensual(), det.getCostoPlanFacturado()));
                } else {
                    txtDetalleFactura.append(String.format("   - Plan ID %d (Desconocido): $%.2f (Facturado: $%.2f)\n",
                        det.getIdPlan(), det.getCostoPlanFacturado()));
                }
            }
        }
        txtDetalleFactura.append("---------------------------------------\n");
        txtDetalleFactura.append("TOTAL A PAGAR: $" + String.format("%.2f", facturaSeleccionada.getTotalPagar()) + "\n");
    }
}
