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
import java.util.UUID; // Importar UUID para generar cédulas únicas

public class ClienteDAOTest extends BaseTest {

    private ClienteDAO clienteDAO;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        clienteDAO = new ClienteDAO();
    }

    @Test
    public void testInsertarCliente() {
        // Generar una cédula única para esta prueba
        String uniqueCedula = UUID.randomUUID().toString().substring(0, 10);
        Cliente cliente = new Cliente("Juan Perez", uniqueCedula, "Loja", "juan@example.com", "Calle Principal 123");
        try {
            clienteDAO.insertarCliente(cliente);
        } catch (SQLException e) {
            fail("No se esperaba SQLException al insertar cliente válido: " + e.getMessage());
        }

        List<Cliente> clientes = clienteDAO.obtenerTodosClientes();
        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        assertEquals("Juan Perez", clientes.get(0).getNombres());
        assertEquals(uniqueCedula, clientes.get(0).getPasaporteCedula());
    }

    @Test
    public void testBuscarClientePorCedula() {
        // Generar una cédula única para esta prueba
        String uniqueCedula = UUID.randomUUID().toString().substring(0, 10);
        Cliente cliente1 = new Cliente("Ana Garcia", uniqueCedula, "Quito", "ana@example.com", "Av. Amazonas");
        try {
            clienteDAO.insertarCliente(cliente1);
        } catch (SQLException e) {
            fail("No se esperaba SQLException al insertar cliente válido: " + e.getMessage());
        }

        Cliente clienteEncontrado = clienteDAO.buscarClientePorCedula(uniqueCedula);
        assertNotNull(clienteEncontrado);
        assertEquals("Ana Garcia", clienteEncontrado.getNombres());
        assertEquals(uniqueCedula, clienteEncontrado.getPasaporteCedula());

        Cliente clienteNoEncontrado = clienteDAO.buscarClientePorCedula("9999999999");
        assertNull(clienteNoEncontrado);
    }

    @Test
    public void testActualizarCliente() {
        // Generar una cédula única para esta prueba
        String uniqueCedula = UUID.randomUUID().toString().substring(0, 10);
        Cliente cliente = new Cliente("Pedro Lopez", uniqueCedula, "Cuenca", "pedro@example.com", "Centro Historico");
        try {
            clienteDAO.insertarCliente(cliente);
        } catch (SQLException e) {
            fail("No se esperaba SQLException al insertar cliente válido: " + e.getMessage());
        }

        cliente.setNombres("Pedro Lopez Actualizado");
        cliente.setCiudad("Guayaquil");
        clienteDAO.actualizarCliente(cliente);

        Cliente clienteActualizado = clienteDAO.buscarClientePorCedula(uniqueCedula);
        assertNotNull(clienteActualizado);
        assertEquals("Pedro Lopez Actualizado", clienteActualizado.getNombres());
        assertEquals("Guayaquil", clienteActualizado.getCiudad());
    }

    @Test
    public void testEliminarCliente() {
        // Generar una cédula única para esta prueba
        String uniqueCedula = UUID.randomUUID().toString().substring(0, 10);
        Cliente cliente = new Cliente("Maria Sol", uniqueCedula, "Manta", "maria@example.com", "Playa Murcielago");
        try {
            clienteDAO.insertarCliente(cliente);
        } catch (SQLException e) {
            fail("No se esperaba SQLException al insertar cliente válido: " + e.getMessage());
        }

        clienteDAO.eliminarCliente(uniqueCedula);
        Cliente clienteEliminado = clienteDAO.buscarClientePorCedula(uniqueCedula);
        assertNull(clienteEliminado);

        List<Cliente> clientes = clienteDAO.obtenerTodosClientes();
        assertEquals(0, clientes.size());
    }

    @Test(expected = SQLException.class)
    public void testInsertarClienteCedulaDuplicada() throws SQLException {
        // Generar una cédula única para esta prueba
        String uniqueCedulaForDuplicateTest = UUID.randomUUID().toString().substring(0, 10);

        Cliente cliente1 = new Cliente("Cliente A", uniqueCedulaForDuplicateTest, "Ciudad A", "a@example.com", "Dir A");
        clienteDAO.insertarCliente(cliente1); // Primera inserción (debe ser exitosa)

        Cliente cliente2 = new Cliente("Cliente B", uniqueCedulaForDuplicateTest, "Ciudad B", "b@example.com", "Dir B");
        clienteDAO.insertarCliente(cliente2); // Segunda inserción, esta es la que se espera que lance SQLException
    }
}
