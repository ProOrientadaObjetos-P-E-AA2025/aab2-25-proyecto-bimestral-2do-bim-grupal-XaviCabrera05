package dao;

import modelo.Factura;
import conexion.Conexion;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO {

    public int insertarFactura(Factura factura) {
        String sql = "INSERT INTO facturas(id_cliente, fecha_emision, total_pagar, estado) VALUES (?, ?, ?, ?)";
        int idFacturaGenerada = -1;
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, factura.getIdCliente());
            pstmt.setString(2, factura.getFechaEmision().toString());
            pstmt.setDouble(3, factura.getTotalPagar());
            pstmt.setString(4, factura.getEstado());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                idFacturaGenerada = rs.getInt(1);
                factura.setIdFactura(idFacturaGenerada); 
            }
            System.out.println("Factura insertada correctamente para cliente ID: " + factura.getIdCliente() + " con ID: " + idFacturaGenerada);
        } catch (SQLException e) {
            System.err.println("Error al insertar factura: " + e.getMessage());
            e.printStackTrace();
        }
        return idFacturaGenerada;
    }

    public List<Factura> obtenerFacturasPorCliente(int idCliente) {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT id_factura, id_cliente, fecha_emision, total_pagar, estado FROM facturas WHERE id_cliente = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                facturas.add(new Factura(
                    rs.getInt("id_factura"),
                    rs.getInt("id_cliente"),
                    LocalDate.parse(rs.getString("fecha_emision")), 
                    rs.getDouble("total_pagar"),
                    rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener facturas por cliente: " + e.getMessage());
            e.printStackTrace();
        }
        return facturas;
    }
    
    public Factura buscarFacturaPorId(int idFactura) {
        String sql = "SELECT id_factura, id_cliente, fecha_emision, total_pagar, estado FROM facturas WHERE id_factura = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idFactura);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Factura(
                    rs.getInt("id_factura"),
                    rs.getInt("id_cliente"),
                    LocalDate.parse(rs.getString("fecha_emision")),
                    rs.getDouble("total_pagar"),
                    rs.getString("estado")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar factura por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void actualizarFactura(Factura factura) {
        String sql = "UPDATE facturas SET id_cliente = ?, fecha_emision = ?, total_pagar = ?, estado = ? WHERE id_factura = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, factura.getIdCliente());
            pstmt.setString(2, factura.getFechaEmision().toString());
            pstmt.setDouble(3, factura.getTotalPagar());
            pstmt.setString(4, factura.getEstado());
            pstmt.setInt(5, factura.getIdFactura());
            pstmt.executeUpdate();
            System.out.println("Factura actualizada correctamente ID: " + factura.getIdFactura());
        } catch (SQLException e) {
            System.err.println("Error al actualizar factura: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminarFactura(int idFactura) {
        String sql = "DELETE FROM facturas WHERE id_factura = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idFactura);
            pstmt.executeUpdate();
            System.out.println("Factura eliminada correctamente ID: " + idFactura);
        } catch (SQLException e) {
            System.err.println("Error al eliminar factura: " + e.getMessage());
            e.printStackTrace();
        }
    }
}