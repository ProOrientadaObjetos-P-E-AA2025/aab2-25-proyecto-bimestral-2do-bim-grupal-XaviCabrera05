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

public class PlanDAOTest extends BaseTest {

    private ClienteDAO clienteDAO;
    private PlanDAO planDAO;
    private int idClienteTest;
    private String clienteCedulaTest;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        clienteDAO = new ClienteDAO();
        planDAO = new PlanDAO();

        clienteCedulaTest = UUID.randomUUID().toString().substring(0, 10);
        Cliente cliente = new Cliente("Cliente Plan Test", clienteCedulaTest, "Loja", "plan@test.com", "Dir Plan");
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
    public void testInsertarPlanPostPagoMinutosMegasEconomico() {
        PlanPostPagoMinutosMegasEconomico plan = new PlanPostPagoMinutosMegasEconomico(
            idClienteTest, 100, 0.10, 5.0, 2.0, 10.0);
        planDAO.insertarPlan(plan);

        List<PlanMovil> planes = planDAO.obtenerPlanesPorCliente(idClienteTest);
        assertNotNull(planes);
        assertEquals(1, planes.size());
        assertTrue(planes.get(0) instanceof PlanPostPagoMinutosMegasEconomico);
        assertEquals(plan.calcularPagoMensual(), planes.get(0).calcularPagoMensual(), 0.01);
    }

    @Test
    public void testInsertarPlanPostPagoMinutos() {
        PlanPostPagoMinutos plan = new PlanPostPagoMinutos(
            idClienteTest, 200, 0.15, 50, 0.50);
        planDAO.insertarPlan(plan);

        List<PlanMovil> planes = planDAO.obtenerPlanesPorCliente(idClienteTest);
        assertNotNull(planes);
        assertEquals(1, planes.size());
        assertTrue(planes.get(0) instanceof PlanPostPagoMinutos);
        assertEquals(plan.calcularPagoMensual(), planes.get(0).calcularPagoMensual(), 0.01);
    }

    @Test
    public void testInsertarPlanPostPagoMegas() {
        PlanPostPagoMegas plan = new PlanPostPagoMegas(
            idClienteTest, 10.0, 1.5, 15.0);
        planDAO.insertarPlan(plan);

        List<PlanMovil> planes = planDAO.obtenerPlanesPorCliente(idClienteTest);
        assertNotNull(planes);
        assertEquals(1, planes.size());
        assertTrue(planes.get(0) instanceof PlanPostPagoMegas);
        assertEquals(plan.calcularPagoMensual(), planes.get(0).calcularPagoMensual(), 0.01);
    }

    @Test
    public void testInsertarPlanPostPagoMinutosMegas() {
        PlanPostPagoMinutosMegas plan = new PlanPostPagoMinutosMegas(
            idClienteTest, 300, 0.08, 8.0, 1.8);
        planDAO.insertarPlan(plan);

        List<PlanMovil> planes = planDAO.obtenerPlanesPorCliente(idClienteTest);
        assertNotNull(planes);
        assertEquals(1, planes.size());
        assertTrue(planes.get(0) instanceof PlanPostPagoMinutosMegas);
        assertEquals(plan.calcularPagoMensual(), planes.get(0).calcularPagoMensual(), 0.01);
    }

    @Test
    public void testObtenerPlanesPorCliente() {
        PlanPostPagoMinutosMegasEconomico plan1 = new PlanPostPagoMinutosMegasEconomico(idClienteTest, 100, 0.1, 5.0, 2.0, 10.0);
        PlanPostPagoMinutos plan2 = new PlanPostPagoMinutos(idClienteTest, 200, 0.15, 50, 0.50);
        planDAO.insertarPlan(plan1);
        planDAO.insertarPlan(plan2);

        List<PlanMovil> planes = planDAO.obtenerPlanesPorCliente(idClienteTest);
        assertNotNull(planes);
        assertEquals(2, planes.size());
        assertTrue(planes.stream().anyMatch(p -> p instanceof PlanPostPagoMinutosMegasEconomico));
        assertTrue(planes.stream().anyMatch(p -> p instanceof PlanPostPagoMinutos));
    }

    @Test
    public void testBuscarPlanPorId() {
        PlanPostPagoMegas plan = new PlanPostPagoMegas(idClienteTest, 10.0, 1.5, 15.0);
        planDAO.insertarPlan(plan);

        PlanMovil planEncontrado = planDAO.buscarPlanPorId(plan.getIdPlan());
        assertNotNull(planEncontrado);
        assertTrue(planEncontrado instanceof PlanPostPagoMegas);
        assertEquals(plan.calcularPagoMensual(), planEncontrado.calcularPagoMensual(), 0.01);
    }

    @Test
    public void testActualizarPlan() {
        PlanPostPagoMinutosMegas planOriginal = new PlanPostPagoMinutosMegas(idClienteTest, 300, 0.08, 8.0, 1.8);
        planDAO.insertarPlan(planOriginal);

        PlanPostPagoMinutosMegas planActualizado = new PlanPostPagoMinutosMegas(
            planOriginal.getIdPlan(), idClienteTest, 350, 0.07, 9.0, 1.7);
        planDAO.actualizarPlan(planActualizado);

        PlanMovil planRecuperado = planDAO.buscarPlanPorId(planOriginal.getIdPlan());
        assertNotNull(planRecuperado);
        assertTrue(planRecuperado instanceof PlanPostPagoMinutosMegas);
        PlanPostPagoMinutosMegas p = (PlanPostPagoMinutosMegas) planRecuperado;
        assertEquals(350, p.getMinutos(), 0.01);
        assertEquals(0.07, p.getCostoMinutos(), 0.01);
    }

    @Test
    public void testEliminarPlan() {
        PlanPostPagoMinutosMegasEconomico plan = new PlanPostPagoMinutosMegasEconomico(idClienteTest, 100, 0.1, 5.0, 2.0, 10.0);
        planDAO.insertarPlan(plan);

        planDAO.eliminarPlan(plan.getIdPlan());
        PlanMovil planEliminado = planDAO.buscarPlanPorId(plan.getIdPlan());
        assertNull(planEliminado);
    }
}
