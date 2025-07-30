package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TablaCreator {

    public void crearTablas() {
        String sqlClientes = "CREATE TABLE IF NOT EXISTS clientes ("
                + "id_cliente INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombres TEXT NOT NULL,"
                + "pasaporte_cedula TEXT NOT NULL UNIQUE,"
                + "ciudad TEXT NOT NULL,"
                + "email TEXT,"
                + "direccion TEXT"
                + ");";

        String sqlDispositivos = "CREATE TABLE IF NOT EXISTS dispositivos_moviles ("
                + "id_dispositivo INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "id_cliente INTEGER NOT NULL,"
                + "marca TEXT NOT NULL,"
                + "modelo TEXT NOT NULL,"
                + "numero_celular TEXT NOT NULL UNIQUE,"
                + "FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente) ON DELETE CASCADE"
                + ");";

        String sqlPlanes = "CREATE TABLE IF NOT EXISTS planes ("
                + "id_plan INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "id_cliente INTEGER NOT NULL,"
                + "tipo_plan TEXT NOT NULL,"
                + "minutos REAL,"
                + "costo_minutos REAL,"
                + "megas_gigas REAL,"
                + "costo_por_giga REAL,"
                + "porcentaje_descuento REAL,"
                + "minutos_nacionales REAL,"
                + "costo_minuto_nacional REAL,"
                + "minutos_internacionales REAL,"
                + "costo_minuto_internacional REAL,"
                + "tarifa_base REAL,"
                + "FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente) ON DELETE CASCADE"
                + ");";
        
        String sqlFacturas = "CREATE TABLE IF NOT EXISTS facturas ("
                + "id_factura INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "id_cliente INTEGER NOT NULL,"
                + "fecha_emision TEXT NOT NULL,"
                + "total_pagar REAL NOT NULL,"
                + "estado TEXT NOT NULL,"
                + "FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente) ON DELETE CASCADE"
                + ");";

        String sqlDetalleFactura = "CREATE TABLE IF NOT EXISTS detalle_factura ("
                + "id_detalle INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "id_factura INTEGER NOT NULL,"
                + "id_plan INTEGER NOT NULL,"
                + "costo_plan_facturado REAL NOT NULL,"
                + "FOREIGN KEY (id_factura) REFERENCES facturas(id_factura) ON DELETE CASCADE,"
                + "FOREIGN KEY (id_plan) REFERENCES planes(id_plan) ON DELETE CASCADE"
                + ");";

        try (Connection conn = Conexion.conectar();
             Statement stmt = conn.createStatement()) {
            if (conn != null) {
                stmt.execute(sqlClientes);
                System.out.println("Tabla 'clientes' creada o verificada.");
                stmt.execute(sqlDispositivos);
                System.out.println("Tabla 'dispositivos_moviles' creada o verificada.");
                stmt.execute(sqlPlanes);
                System.out.println("Tabla 'planes' creada o verificada.");
                stmt.execute(sqlFacturas);
                System.out.println("Tabla 'facturas' creada o verificada.");
                stmt.execute(sqlDetalleFactura);
                System.out.println("Tabla 'detalle_factura' creada o verificada.");
            } else {
                System.err.println("No se pudo establecer la conexión a la base de datos para crear las tablas.");
            }
        } catch (SQLException e) {
            System.err.println("Error al crear las tablas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
