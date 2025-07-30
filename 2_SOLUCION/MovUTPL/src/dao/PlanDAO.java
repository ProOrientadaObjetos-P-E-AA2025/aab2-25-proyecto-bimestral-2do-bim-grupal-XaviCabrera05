package dao;

import modelo.*; // Importamos todos los modelos de planes
import conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO {

    public void insertarPlan(PlanMovil plan) {

        String sql = "INSERT INTO planes(id_cliente, tipo_plan, minutos, costo_minutos, megas_gigas, costo_por_giga, " +
                     "porcentaje_descuento, minutos_nacionales, costo_minuto_nacional, minutos_internacionales, " +
                     "costo_minuto_internacional, tarifa_base) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, plan.getIdCliente());
            pstmt.setString(2, plan.getTipoPlan());


            for (int i = 3; i <= 12; i++) {
                pstmt.setNull(i, Types.REAL); // Usamos Types.REAL para tipos numéricos (double)
            }

            if (plan instanceof PlanPostPagoMinutosMegasEconomico) {
                PlanPostPagoMinutosMegasEconomico p = (PlanPostPagoMinutosMegasEconomico) plan;
                pstmt.setDouble(3, p.getMinutos()); // minutos
                pstmt.setDouble(4, p.getCostoMinutos()); // costo_minutos
                pstmt.setDouble(5, p.getMegasGigas()); // megas_gigas
                pstmt.setDouble(6, p.getCostoPorGiga()); // costo_por_giga
                pstmt.setDouble(7, p.getPorcentajeDescuento()); // porcentaje_descuento
            } else if (plan instanceof PlanPostPagoMinutos) {
                PlanPostPagoMinutos p = (PlanPostPagoMinutos) plan;
                pstmt.setDouble(8, p.getMinutosNacionales()); // minutos_nacionales
                pstmt.setDouble(9, p.getCostoMinutoNacional()); // costo_minuto_nacional
                pstmt.setDouble(10, p.getMinutosInternacionales()); // minutos_internacionales
                pstmt.setDouble(11, p.getCostoMinutoInternacional()); // costo_minuto_internacional
            } else if (plan instanceof PlanPostPagoMegas) {
                PlanPostPagoMegas p = (PlanPostPagoMegas) plan;
                pstmt.setDouble(5, p.getMegasGigas()); // megas_gigas (índice 5, coincide con la posición en la SQL)
                pstmt.setDouble(6, p.getCostoPorGiga()); // costo_por_giga (índice 6)
                pstmt.setDouble(12, p.getTarifaBase()); // tarifa_base (índice 12)
            } else if (plan instanceof PlanPostPagoMinutosMegas) {
                PlanPostPagoMinutosMegas p = (PlanPostPagoMinutosMegas) plan;
                pstmt.setDouble(3, p.getMinutos()); // minutos
                pstmt.setDouble(4, p.getCostoMinutos()); // costo_minutos
                pstmt.setDouble(5, p.getMegasGigas()); // megas_gigas
                pstmt.setDouble(6, p.getCostoPorGiga()); // costo_por_giga
            }
            
            pstmt.executeUpdate(); 

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                plan.setIdPlan(generatedKeys.getInt(1)); 
            }
            System.out.println("Plan insertado correctamente para cliente ID: " + plan.getIdCliente() + " Tipo: " + plan.getTipoPlan() + " con ID de Plan: " + plan.getIdPlan());

        } catch (SQLException e) {
            System.err.println("Error al insertar plan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<PlanMovil> obtenerPlanesPorCliente(int idCliente) {
        List<PlanMovil> planes = new ArrayList<>();
        String sql = "SELECT * FROM planes WHERE id_cliente = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PlanMovil plan = crearPlanDesdeResultSet(rs);
                if (plan != null) {
                    planes.add(plan);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener planes por cliente: " + e.getMessage());
            e.printStackTrace();
        }
        return planes;
    }

    public PlanMovil buscarPlanPorId(int idPlan) {
        String sql = "SELECT * FROM planes WHERE id_plan = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPlan);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return crearPlanDesdeResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar plan por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void actualizarPlan(PlanMovil plan) {

        String sql = "UPDATE planes SET tipo_plan = ?, minutos = ?, costo_minutos = ?, megas_gigas = ?, costo_por_giga = ?, " +
                     "porcentaje_descuento = ?, minutos_nacionales = ?, costo_minuto_nacional, minutos_internacionales = ?, " +
                     "costo_minuto_internacional = ?, tarifa_base = ? WHERE id_plan = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, plan.getTipoPlan());

            for (int i = 2; i <= 11; i++) { // Columnas para datos de planes específicos
                pstmt.setNull(i, Types.REAL);
            }

            if (plan instanceof PlanPostPagoMinutosMegasEconomico) {
                PlanPostPagoMinutosMegasEconomico p = (PlanPostPagoMinutosMegasEconomico) plan;
                pstmt.setDouble(2, p.getMinutos()); // minutos
                pstmt.setDouble(3, p.getCostoMinutos()); // costo_minutos
                pstmt.setDouble(4, p.getMegasGigas()); // megas_gigas
                pstmt.setDouble(5, p.getCostoPorGiga()); // costo_por_giga
                pstmt.setDouble(6, p.getPorcentajeDescuento()); // porcentaje_descuento
            } else if (plan instanceof PlanPostPagoMinutos) {
                PlanPostPagoMinutos p = (PlanPostPagoMinutos) plan;
                pstmt.setDouble(7, p.getMinutosNacionales()); // minutos_nacionales
                pstmt.setDouble(8, p.getCostoMinutoNacional()); // costo_minuto_nacional
                pstmt.setDouble(9, p.getMinutosInternacionales()); // minutos_internacionales
                pstmt.setDouble(10, p.getCostoMinutoInternacional()); // costo_minuto_internacional
            } else if (plan instanceof PlanPostPagoMegas) {
                PlanPostPagoMegas p = (PlanPostPagoMegas) plan;
                pstmt.setDouble(4, p.getMegasGigas()); // megas_gigas (índice 4)
                pstmt.setDouble(5, p.getCostoPorGiga()); // costo_por_giga (índice 5)
                pstmt.setDouble(11, p.getTarifaBase()); // tarifa_base (índice 11)
            } else if (plan instanceof PlanPostPagoMinutosMegas) {
                PlanPostPagoMinutosMegas p = (PlanPostPagoMinutosMegas) plan;
                pstmt.setDouble(2, p.getMinutos()); // minutos
                pstmt.setDouble(3, p.getCostoMinutos()); // costo_minutos
                pstmt.setDouble(4, p.getMegasGigas()); // megas_gigas
                pstmt.setDouble(5, p.getCostoPorGiga()); // costo_por_giga
            }
            
            pstmt.setInt(12, plan.getIdPlan()); 
            pstmt.executeUpdate();
            System.out.println("Plan actualizado correctamente ID: " + plan.getIdPlan() + " Tipo: " + plan.getTipoPlan());

        } catch (SQLException e) {
            System.err.println("Error al actualizar plan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminarPlan(int idPlan) {
        String sql = "DELETE FROM planes WHERE id_plan = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPlan);
            pstmt.executeUpdate();
            System.out.println("Plan eliminado correctamente ID: " + idPlan);
        } catch (SQLException e) {
            System.err.println("Error al eliminar plan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método auxiliar para crear el objeto PlanMovil correcto desde un ResultSet
    private PlanMovil crearPlanDesdeResultSet(ResultSet rs) throws SQLException {
        int idPlan = rs.getInt("id_plan");
        int idCliente = rs.getInt("id_cliente");
        String tipoPlan = rs.getString("tipo_plan");

        switch (tipoPlan) {
            case "Economico":
                return new PlanPostPagoMinutosMegasEconomico(
                    idPlan,
                    idCliente,
                    rs.getDouble("minutos"),
                    rs.getDouble("costo_minutos"),
                    rs.getDouble("megas_gigas"),
                    rs.getDouble("costo_por_giga"),
                    rs.getDouble("porcentaje_descuento")
                );
            case "Minutos":
                return new PlanPostPagoMinutos(
                    idPlan,
                    idCliente,
                    rs.getDouble("minutos_nacionales"),
                    rs.getDouble("costo_minuto_nacional"),
                    rs.getDouble("minutos_internacionales"),
                    rs.getDouble("costo_minuto_internacional")
                );
            case "Megas":
                return new PlanPostPagoMegas(
                    idPlan,
                    idCliente,
                    rs.getDouble("megas_gigas"),
                    rs.getDouble("costo_por_giga"),
                    rs.getDouble("tarifa_base")
                );
            case "MinutosMegas":
                return new PlanPostPagoMinutosMegas(
                    idPlan,
                    idCliente,
                    rs.getDouble("minutos"),
                    rs.getDouble("costo_minutos"),
                    rs.getDouble("megas_gigas"),
                    rs.getDouble("costo_por_giga")
                );
            default:
                System.err.println("Tipo de plan desconocido: " + tipoPlan);
                return null;
        }
    }
}