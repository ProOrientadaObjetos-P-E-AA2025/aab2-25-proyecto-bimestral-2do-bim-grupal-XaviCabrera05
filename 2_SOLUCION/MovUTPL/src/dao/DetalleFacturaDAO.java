package dao;

import modelo.DetalleFactura;
import conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleFacturaDAO {

    public void insertarDetalleFactura(DetalleFactura detalle) {
        String sql = "INSERT INTO detalle_factura(id_factura, id_plan, costo_plan_facturado) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, detalle.getIdFactura());
            pstmt.setInt(2, detalle.getIdPlan());
            pstmt.setDouble(3, detalle.getCostoPlanFacturado());
            pstmt.executeUpdate();
            System.out.println("Detalle de factura insertado correctamente para factura ID: " + detalle.getIdFactura());
        } catch (SQLException e) {
            System.err.println("Error al insertar detalle de factura: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<DetalleFactura> obtenerDetallesPorFactura(int idFactura) {
        List<DetalleFactura> detalles = new ArrayList<>();
        String sql = "SELECT id_detalle, id_factura, id_plan, costo_plan_facturado FROM detalle_factura WHERE id_factura = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idFactura);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                detalles.add(new DetalleFactura(
                    rs.getInt("id_detalle"),
                    rs.getInt("id_factura"),
                    rs.getInt("id_plan"),
                    rs.getDouble("costo_plan_facturado")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener detalles por factura: " + e.getMessage());
            e.printStackTrace();
        }
        return detalles;
    }

    public void actualizarDetalleFactura(DetalleFactura detalle) {
        String sql = "UPDATE detalle_factura SET id_factura = ?, id_plan = ?, costo_plan_facturado = ? WHERE id_detalle = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, detalle.getIdFactura());
            pstmt.setInt(2, detalle.getIdPlan());
            pstmt.setDouble(3, detalle.getCostoPlanFacturado());
            pstmt.setInt(4, detalle.getIdDetalle());
            pstmt.executeUpdate();
            System.out.println("Detalle de factura actualizado correctamente ID: " + detalle.getIdDetalle());
        } catch (SQLException e) {
            System.err.println("Error al actualizar detalle de factura: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminarDetalleFactura(int idDetalle) {
        String sql = "DELETE FROM detalle_factura WHERE id_detalle = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idDetalle);
            pstmt.executeUpdate();
            System.out.println("Detalle de factura eliminado correctamente ID: " + idDetalle);
        } catch (SQLException e) {
            System.err.println("Error al eliminar detalle de factura: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
