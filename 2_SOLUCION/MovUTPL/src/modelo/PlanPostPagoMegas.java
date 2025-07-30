package modelo;

public class PlanPostPagoMegas extends PlanMovil {
    private double megasGigas;
    private double costoPorGiga;
    private double tarifaBase;

    public PlanPostPagoMegas(int idCliente, double megasGigas, double costoPorGiga, double tarifaBase) {
        super(idCliente, "Megas");
        this.megasGigas = megasGigas;
        this.costoPorGiga = costoPorGiga;
        this.tarifaBase = tarifaBase;
    }

    // Constructor para cuando se recupera de la BD (con ID)
    public PlanPostPagoMegas(int idPlan, int idCliente, double megasGigas, double costoPorGiga, double tarifaBase) {
        super(idPlan, idCliente, "Megas");
        this.megasGigas = megasGigas;
        this.costoPorGiga = costoPorGiga;
        this.tarifaBase = tarifaBase;
    }

    // Getters y Setters
    public double getMegasGigas() { return megasGigas; }
    public void setMegasGigas(double megasGigas) { this.megasGigas = megasGigas; }
    public double getCostoPorGiga() { return costoPorGiga; }
    public void setCostoPorGiga(double costoPorGiga) { this.costoPorGiga = costoPorGiga; }
    public double getTarifaBase() { return tarifaBase; }
    public void setTarifaBase(double tarifaBase) { this.tarifaBase = tarifaBase; }

    @Override
    public double calcularPagoMensual() {
        return (megasGigas * costoPorGiga) + tarifaBase;
    }
}
