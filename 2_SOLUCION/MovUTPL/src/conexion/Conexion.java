package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:sqlite:movutpl.db";

    public static Connection conectar() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Error de conexión a la base de datos: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC de SQLite no encontrado: " + e.getMessage());
            return null;
        }
    }
}
