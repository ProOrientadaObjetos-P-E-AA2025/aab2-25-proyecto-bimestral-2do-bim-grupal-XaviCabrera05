package modelo;

public class PlanPostPagoMinutosMegas extends PlanMovil {
    private double minutos;
    private double costoMinutos;
    private double megasGigas;
    private double costoPorGiga;

    public PlanPostPagoMinutosMegas(int idCliente, double minutos, double costoMinutos, double megasGigas, double costoPorGiga) {
        super(idCliente, "MinutosMegas");
        this.minutos = minutos;
        this.costoMinutos = costoMinutos;
        this.megasGigas = megasGigas;
        this.costoPorGiga = costoPorGiga;
    }

    // Constructor para cuando se recupera de la BD (con ID)
    public PlanPostPagoMinutosMegas(int idPlan, int idCliente, double minutos, double costoMinutos, double megasGigas, double costoPorGiga) {
        super(idPlan, idCliente, "MinutosMegas");
        this.minutos = minutos;
        this.costoMinutos = costoMinutos;
        this.megasGigas = megasGigas;
        this.costoPorGiga = costoPorGiga;
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

    @Override
    public double calcularPagoMensual() {
        return (minutos * costoMinutos) + (megasGigas * costoPorGiga);
    }
}
