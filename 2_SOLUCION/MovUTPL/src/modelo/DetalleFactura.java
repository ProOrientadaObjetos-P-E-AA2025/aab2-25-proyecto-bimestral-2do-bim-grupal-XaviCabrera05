package modelo;

public class DetalleFactura {
    private int idDetalle;
    private int idFactura;
    private int idPlan;
    private double costoPlanFacturado;

    public DetalleFactura(int idDetalle, int idFactura, int idPlan, double costoPlanFacturado) {
        this.idDetalle = idDetalle;
        this.idFactura = idFactura;
        this.idPlan = idPlan;
        this.costoPlanFacturado = costoPlanFacturado;
    }

    public DetalleFactura(int idFactura, int idPlan, double costoPlanFacturado) {
        this.idFactura = idFactura;
        this.idPlan = idPlan;
        this.costoPlanFacturado = costoPlanFacturado;
    }

    public int getIdDetalle() { return idDetalle; }
    public int getIdFactura() { return idFactura; }
    public int getIdPlan() { return idPlan; }
    public double getCostoPlanFacturado() { return costoPlanFacturado; }

    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }
    public void setIdFactura(int idFactura) { this.idFactura = idFactura; }
    public void setIdPlan(int idPlan) { this.idPlan = idPlan; }
    public void setCostoPlanFacturado(double costoPlanFacturado) { this.costoPlanFacturado = costoPlanFacturado; }

    @Override
    public String toString() {
        return "DetalleFactura{" +
               "idDetalle=" + idDetalle +
               ", idFactura=" + idFactura +
               ", idPlan=" + idPlan +
               ", costoPlanFacturado=" + String.format("%.2f", costoPlanFacturado) +
               '}';
    }
}