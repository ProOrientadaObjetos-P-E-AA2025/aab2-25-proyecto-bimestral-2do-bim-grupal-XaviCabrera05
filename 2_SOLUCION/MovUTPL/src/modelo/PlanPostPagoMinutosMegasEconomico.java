package modelo;

public class PlanPostPagoMinutosMegasEconomico extends PlanMovil {
    private double minutos;
    private double costoMinutos;
    private double megasGigas;
    private double costoPorGiga;
    private double porcentajeDescuento;

    public PlanPostPagoMinutosMegasEconomico(int idCliente, double minutos, double costoMinutos, double megasGigas, double costoPorGiga, double porcentajeDescuento) {
        super(idCliente, "Economico");
        this.minutos = minutos;
        this.costoMinutos = costoMinutos;
        this.megasGigas = megasGigas;
        this.costoPorGiga = costoPorGiga;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    // Constructor para cuando se recupera de la BD (con ID)
    public PlanPostPagoMinutosMegasEconomico(int idPlan, int idCliente, double minutos, double costoMinutos, double megasGigas, double costoPorGiga, double porcentajeDescuento) {
        super(idPlan, idCliente, "Economico");
        this.minutos = minutos;
        this.costoMinutos = costoMinutos;
        this.megasGigas = megasGigas;
        this.costoPorGiga = costoPorGiga;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    // Getters y Setters
    public double getMinutos() { return minutos; }
    public void setMinutos(double minutos) { this.minutos = minutos; }
    public double getCostoMinutos() { return costoMinutos; }
    public void setCostoMinutos(double costoMinutos) { this.costoMinutos = costoMinutos; }
    public double getMegasGigas() { return megasGigas; }
    public void setMegasGigas(double megasGigas) { this.megasGigas = megasGigas; }
    public double getCostoPorGiga() { return costoPorGiga; }
    public void setCostoPorGiga(double costoPorGiga) { this.costoPorGiga = costoPorGiga; }
    public double getPorcentajeDescuento() { return porcentajeDescuento; }
    public void setPorcentajeDescuento(double porcentajeDescuento) { this.porcentajeDescuento = porcentajeDescuento; }

    @Override
    public double calcularPagoMensual() {
        double subtotal = (minutos * costoMinutos) + (megasGigas * costoPorGiga);
        return subtotal * (1 - (porcentajeDescuento / 100));
    }
}
