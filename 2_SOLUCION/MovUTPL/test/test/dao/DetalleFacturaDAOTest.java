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

public class DetalleFacturaDAOTest extends BaseTest {

    private ClienteDAO clienteDAO;
    private PlanDAO planDAO;
    private FacturaDAO facturaDAO;
    private DetalleFacturaDAO detalleFacturaDAO;

    private int idClienteTest;
    private String clienteCedulaTest;
    private int idFacturaTest;
    private int idPlanTest;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        clienteDAO = new ClienteDAO();
        planDAO = new PlanDAO();
        facturaDAO = new FacturaDAO();
        detalleFacturaDAO = new DetalleFacturaDAO();

        clienteCedulaTest = UUID.randomUUID().toString().substring(0, 10);
        Cliente cliente = new Cliente("Cliente Detalle Test", clienteCedulaTest, "Loja", "detalle@test.com", "Dir Detalle");
        try {
            clienteDAO.insertarCliente(cliente);
        } catch (SQLException e) {
            fail("Error fatal en setUp: No se pudo insertar el cliente de prueba debido a SQLException: " + e.getMessage());
        }
        Cliente clienteDB = clienteDAO.buscarClientePorCedula(clienteCedulaTest);
        assertNotNull(clienteDB);
        idClienteTest = clienteDB.getIdCliente();

        PlanPostPagoMinutosMegasEconomico plan = new PlanPostPagoMinutosMegasEconomico(idClienteTest, 100, 0.10, 5.0, 2.0, 10.0);
        planDAO.insertarPlan(plan);
        idPlanTest = plan.getIdPlan();

        Factura factura = new Factura(idClienteTest, LocalDate.now(), 0.0, "Pendiente");
        idFacturaTest = facturaDAO.insertarFactura(factura);
        assertTrue(idFacturaTest > 0);
    }

    @Test
    public void testInsertarDetalleFactura() {
        DetalleFactura detalle = new DetalleFactura(idFacturaTest, idPlanTest, 15.0);
        detalleFacturaDAO.insertarDetalleFactura(detalle);

        List<DetalleFactura> detalles = detalleFacturaDAO.obtenerDetallesPorFactura(idFacturaTest);
        assertNotNull(detalles);
        assertEquals(1, detalles.size());
        assertEquals(idPlanTest, detalles.get(0).getIdPlan());
        assertEquals(15.0, detalles.get(0).getCostoPlanFacturado(), 0.01);
    }

    @Test
    public void testObtenerDetallesPorFactura() {
        PlanPostPagoMinutos plan2 = new PlanPostPagoMinutos(idClienteTest, 200, 0.15, 50, 0.50);
        planDAO.insertarPlan(plan2);
        int idPlanTest2 = plan2.getIdPlan();

        DetalleFactura detalle1 = new DetalleFactura(idFacturaTest, idPlanTest, 15.0);
        DetalleFactura detalle2 = new DetalleFactura(idFacturaTest, idPlanTest2, 25.0);
        detalleFacturaDAO.insertarDetalleFactura(detalle1);
        detalleFacturaDAO.insertarDetalleFactura(detalle2);

        List<DetalleFactura> detalles = detalleFacturaDAO.obtenerDetallesPorFactura(idFacturaTest);
        assertNotNull(detalles);
        assertEquals(2, detalles.size());
        
        assertTrue(detalles.stream().anyMatch(d -> d.getIdPlan() == idPlanTest && d.getTipoPlanAsociado().equals("Economico")));
        assertTrue(detalles.stream().anyMatch(d -> d.getIdPlan() == idPlanTest2 && d.getTipoPlanAsociado().equals("Minutos")));
    }

    @Test
    public void testActualizarDetalleFactura() {
        DetalleFactura detalle = new DetalleFactura(idFacturaTest, idPlanTest, 15.0);
        detalleFacturaDAO.insertarDetalleFactura(detalle);

        List<DetalleFactura> detalles = detalleFacturaDAO.obtenerDetallesPorFactura(idFacturaTest);
        assertEquals(1, detalles.size());
        DetalleFactura detalleRecuperado = detalles.get(0);

        detalleRecuperado.setCostoPlanFacturado(18.5);
        detalleFacturaDAO.actualizarDetalleFactura(detalleRecuperado);

        DetalleFactura detalleActualizado = detalleFacturaDAO.obtenerDetallesPorFactura(idFacturaTest).get(0);
        assertEquals(18.5, detalleActualizado.getCostoPlanFacturado(), 0.01);
    }

    @Test
    public void testEliminarDetalleFactura() {
        DetalleFactura detalle = new DetalleFactura(idFacturaTest, idPlanTest, 15.0);
        detalleFacturaDAO.insertarDetalleFactura(detalle);

        List<DetalleFactura> detalles = detalleFacturaDAO.obtenerDetallesPorFactura(idFacturaTest);
        assertEquals(1, detalles.size());
        int idDetalle = detalles.get(0).getIdDetalle();

        detalleFacturaDAO.eliminarDetalleFactura(idDetalle);
        List<DetalleFactura> detallesDespuesDeEliminar = detalleFacturaDAO.obtenerDetallesPorFactura(idFacturaTest);
        assertTrue(detallesDespuesDeEliminar.isEmpty());
    }
}
