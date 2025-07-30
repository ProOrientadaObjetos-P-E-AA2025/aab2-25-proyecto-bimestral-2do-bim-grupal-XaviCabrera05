package dao;

import modelo.DispositivoMovil;
import conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DispositivoMovilDAO {

    public void insertarDispositivo(DispositivoMovil dispositivo) {
        String sql = "INSERT INTO dispositivos_moviles(id_cliente, marca, modelo, numero_celular) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dispositivo.getIdCliente());
            pstmt.setString(2, dispositivo.getMarca());
            pstmt.setString(3, dispositivo.getModelo());
            pstmt.setString(4, dispositivo.getNumeroCelular());
            pstmt.executeUpdate();
            System.out.println("Dispositivo insertado correctamente para cliente ID: " + dispositivo.getIdCliente());
        } catch (SQLException e) {
            System.err.println("Error al insertar dispositivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<DispositivoMovil> obtenerDispositivosPorCliente(int idCliente) {
        List<DispositivoMovil> dispositivos = new ArrayList<>();
        String sql = "SELECT id_dispositivo, id_cliente, marca, modelo, numero_celular FROM dispositivos_moviles WHERE id_cliente = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dispositivos.add(new DispositivoMovil(
                    rs.getInt("id_dispositivo"),
                    rs.getInt("id_cliente"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getString("numero_celular")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener dispositivos por cliente: " + e.getMessage());
            e.printStackTrace();
        }
        return dispositivos;
    }
    
    public DispositivoMovil buscarDispositivoPorId(int idDispositivo) {
        String sql = "SELECT id_dispositivo, id_cliente, marca, modelo, numero_celular FROM dispositivos_moviles WHERE id_dispositivo = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idDispositivo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new DispositivoMovil(
                    rs.getInt("id_dispositivo"),
                    rs.getInt("id_cliente"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getString("numero_celular")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar dispositivo por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void actualizarDispositivo(DispositivoMovil dispositivo) {
        String sql = "UPDATE dispositivos_moviles SET marca = ?, modelo = ?, numero_celular = ? WHERE id_dispositivo = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dispositivo.getMarca());
            pstmt.setString(2, dispositivo.getModelo());
            pstmt.setString(3, dispositivo.getNumeroCelular());
            pstmt.setInt(4, dispositivo.getIdDispositivo());
            pstmt.executeUpdate();
            System.out.println("Dispositivo actualizado correctamente ID: " + dispositivo.getIdDispositivo());
        } catch (SQLException e) {
            System.err.println("Error al actualizar dispositivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminarDispositivo(int idDispositivo) {
        String sql = "DELETE FROM dispositivos_moviles WHERE id_dispositivo = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idDispositivo);
            pstmt.executeUpdate();
            System.out.println("Dispositivo eliminado correctamente ID: " + idDispositivo);
        } catch (SQLException e) {
            System.err.println("Error al eliminar dispositivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}