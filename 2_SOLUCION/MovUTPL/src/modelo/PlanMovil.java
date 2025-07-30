package modelo;

public abstract class PlanMovil {
    protected int idPlan;
    protected int idCliente;
    protected String tipoPlan; 

    public PlanMovil(int idCliente, String tipoPlan) {
        this.idCliente = idCliente;
        this.tipoPlan = tipoPlan;
    }

    // Constructor para cuando se recupera de la BD (con ID)
    public PlanMovil(int idPlan, int idCliente, String tipoPlan) {
        this.idPlan = idPlan;
        this.idCliente = idCliente;
        this.tipoPlan = tipoPlan;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipoPlan() {
        return tipoPlan;
    }

    public void setTipoPlan(String tipoPlan) {
        this.tipoPlan = tipoPlan;
    }

    // Método abstracto para calcular el pago mensual, a ser implementado por subclases
    public abstract double calcularPagoMensual();
}