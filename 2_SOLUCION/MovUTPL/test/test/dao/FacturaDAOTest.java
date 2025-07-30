package test.dao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import conexion.Conexion;
import dao.*;
import modelo.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class FacturaDAOTest extends BaseTest {

    private ClienteDAO clienteDAO;
    private FacturaDAO facturaDAO;
    private int idClienteTest;
    private String clienteCedulaTest;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        clienteDAO = new ClienteDAO();
        facturaDAO = new FacturaDAO();

        clienteCedulaTest = UUID.randomUUID().toString().substring(0, 10);
        Cliente cliente = new Cliente("Cliente Factura Test", clienteCedulaTest, "Loja", "factura@test.com", "Dir Factura");
        try {
            clienteDAO.insertarCliente(cliente);
        } catch (SQLException e) {
            fail("Error fatal en setUp: No se pudo insertar el cliente de prueba debido a SQLException: " + e.getMessage());
        }

        Cliente clienteDB = clienteDAO.buscarClientePorCedula(clienteCedulaTest);
        assertNotNull(clienteDB);
        idClienteTest = clienteDB.getIdCliente();
    }

    @Test
    public void testInsertarFactura() {
        Factura factura = new Factura(idClienteTest, LocalDate.now(), 50.0, "Pendiente");
        int idFactura = facturaDAO.insertarFactura(factura);

        assertTrue(idFactura > 0);
        Factura facturaEncontrada = facturaDAO.buscarFacturaPorId(idFactura);
        assertNotNull(facturaEncontrada);
        assertEquals(50.0, facturaEncontrada.getTotalPagar(), 0.01);
        assertEquals("Pendiente", facturaEncontrada.getEstado());
        assertNull(facturaEncontrada.getNombreCliente());
    }

    @Test
    public void testObtenerFacturasPorCliente() {
        Factura factura1 = new Factura(idClienteTest, LocalDate.now(), 50.0, "Pendiente");
        Factura factura2 = new Factura(idClienteTest, LocalDate.now().minusDays(1), 75.0, "Pagada");
        facturaDAO.insertarFactura(factura1);
        facturaDAO.insertarFactura(factura2);

        List<Factura> facturas = facturaDAO.obtenerFacturasPorCliente(idClienteTest);
        assertNotNull(facturas);
        assertEquals(2, facturas.size());
        assertEquals("Cliente Factura Test", facturas.get(0).getNombreCliente());
        assertEquals("Cliente Factura Test", facturas.get(1).getNombreCliente());
    }

    @Test
    public void testBuscarFacturaPorId() {
        Factura factura = new Factura(idClienteTest, LocalDate.now(), 100.0, "Pendiente");
        int idFactura = facturaDAO.insertarFactura(factura);

        Factura facturaEncontrada = facturaDAO.buscarFacturaPorId(idFactura);
        assertNotNull(facturaEncontrada);
        assertEquals(100.0, facturaEncontrada.getTotalPagar(), 0.01);
        assertEquals("Cliente Factura Test", facturaEncontrada.getNombreCliente());
    }

    @Test
    public void testActualizarFactura() {
        Factura factura = new Factura(idClienteTest, LocalDate.now(), 120.0, "Pendiente");
        int idFactura = facturaDAO.insertarFactura(factura);

        factura.setIdFactura(idFactura);
        factura.setEstado("Pagada");
        factura.setTotalPagar(125.0);
        facturaDAO.actualizarFactura(factura);

        Factura facturaActualizada = facturaDAO.buscarFacturaPorId(idFactura);
        assertNotNull(facturaActualizada);
        assertEquals("Pagada", facturaActualizada.getEstado());
        assertEquals(125.0, facturaActualizada.getTotalPagar(), 0.01);
    }

    @Test
    public void testEliminarFactura() {
        Factura factura = new Factura(idClienteTest, LocalDate.now(), 200.0, "Pendiente");
        int idFactura = facturaDAO.insertarFactura(factura);

        facturaDAO.eliminarFactura(idFactura);
        Factura facturaEliminada = facturaDAO.buscarFacturaPorId(idFactura);
        assertNull(facturaEliminada);
    }
}
