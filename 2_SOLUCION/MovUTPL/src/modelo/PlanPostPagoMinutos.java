package modelo;

public class PlanPostPagoMinutos extends PlanMovil {
    private double minutosNacionales;
    private double costoMinutoNacional;
    private double minutosInternacionales;
    private double costoMinutoInternacional;

    public PlanPostPagoMinutos(int idCliente, double minutosNacionales, double costoMinutoNacional, double minutosInternacionales, double costoMinutoInternacional) {
        super(idCliente, "Minutos");
        this.minutosNacionales = minutosNacionales;
        this.costoMinutoNacional = costoMinutoNacional;
        this.minutosInternacionales = minutosInternacionales;
        this.costoMinutoInternacional = costoMinutoInternacional;
    }

    // Constructor para cuando se recupera de la BD (con ID)
    public PlanPostPagoMinutos(int idPlan, int idCliente, double minutosNacionales, double costoMinutoNacional, double minutosInternacionales, double costoMinutoInternacional) {
        super(idPlan, idCliente, "Minutos");
        this.minutosNacionales = minutosNacionales;
        this.costoMinutoNacional = costoMinutoNacional;
        this.minutosInternacionales = minutosInternacionales;
        this.costoMinutoInternacional = costoMinutoInternacional;
    }

    // Getters y Setters
    public double getMinutosNacionales() { return minutosNacionales; }
    public void setMinutosNacionales(double minutosNacionales) { this.minutosNacionales = minutosNacionales; }
    public double getCostoMinutoNacional() { return costoMinutoNacional; }
    public void setCostoMinutoNacional(double costoMinutoNacional) { this.costoMinutoNacional = costoMinutoNacional; }
    public double getMinutosInternacionales() { return minutosInternacionales; }
    public void setMinutosInternacionales(double minutosInternacionales) { this.minutosInternacionales = minutosInternacionales; }
    public double getCostoMinutoInternacional() { return costoMinutoInternacional; }
    public void setCostoMinutoInternacional(double costoMinutoInternacional) { this.costoMinutoInternacional = costoMinutoInternacional; }

    @Override
    public double calcularPagoMensual() {
        return (minutosNacionales * costoMinutoNacional) + (minutosInternacionales * costoMinutoInternacional);
    }
}
  