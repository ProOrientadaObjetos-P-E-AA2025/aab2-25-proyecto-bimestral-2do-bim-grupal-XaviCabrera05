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
import java.time.LocalDate;
import java.util.List;

public class BaseTest {

    protected TablaCreator tablaCreator = new TablaCreator();

    @Before
    public void setUp() {
        try (Connection conn = Conexion.conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS detalle_factura");
            stmt.execute("DROP TABLE IF EXISTS facturas");
            stmt.execute("DROP TABLE IF EXISTS planes");
            stmt.execute("DROP TABLE IF EXISTS dispositivos_moviles");
            stmt.execute("DROP TABLE IF EXISTS clientes");
            System.out.println("Tablas eliminadas para limpieza de prueba.");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Error al limpiar la base de datos antes de la prueba: " + e.getMessage());
        }
        tablaCreator.crearTablas();
    }

    @After
    public void tearDown() {
    }
}