package dao;

import modelo.Cliente;
import conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes(nombres, pasaporte_cedula, ciudad, email, direccion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getNombres());
            pstmt.setString(2, cliente.getPasaporteCedula());
            pstmt.setString(3, cliente.getCiudad());
            pstmt.setString(4, cliente.getEmail());
            pstmt.setString(5, cliente.getDireccion());
            pstmt.executeUpdate();
            System.out.println("Cliente insertado correctamente: " + cliente.getNombres());
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Cliente> obtenerTodosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nombres, pasaporte_cedula, ciudad, email, direccion FROM clientes";
        try (Connection conn = Conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clientes.add(new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nombres"),
                    rs.getString("pasaporte_cedula"),
                    rs.getString("ciudad"),
                    rs.getString("email"),
                    rs.getString("direccion")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los clientes: " + e.getMessage());
            e.printStackTrace();
        }
        return clientes;
    }

    public Cliente buscarClientePorCedula(String pasaporteCedula) {
        String sql = "SELECT id_cliente, nombres, pasaporte_cedula, ciudad, email, direccion FROM clientes WHERE pasaporte_cedula = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pasaporteCedula);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getString("nombres"),
                    rs.getString("pasaporte_cedula"),
                    rs.getString("ciudad"),
                    rs.getString("email"),
                    rs.getString("direccion")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por cédula/pasaporte: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void actualizarCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nombres = ?, ciudad = ?, email = ?, direccion = ? WHERE pasaporte_cedula = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getNombres());
            pstmt.setString(2, cliente.getCiudad());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setString(4, cliente.getDireccion());
            pstmt.setString(5, cliente.getPasaporteCedula());
            pstmt.executeUpdate();
            System.out.println("Cliente actualizado correctamente: " + cliente.getNombres());
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminarCliente(String pasaporteCedula) {
        String sql = "DELETE FROM clientes WHERE pasaporte_cedula = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pasaporteCedula);
            pstmt.executeUpdate();
            System.out.println("Cliente eliminado correctamente con cédula/pasaporte: " + pasaporteCedula);
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
