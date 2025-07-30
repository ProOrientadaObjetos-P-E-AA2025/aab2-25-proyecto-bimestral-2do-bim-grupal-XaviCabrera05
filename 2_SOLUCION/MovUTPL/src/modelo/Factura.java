package modelo;

import java.time.LocalDate;

public class Factura {
    private int idFactura;
    private int idCliente;
    private LocalDate fechaEmision;
    private double totalPagar;
    private String estado;

    public Factura(int idFactura, int idCliente, LocalDate fechaEmision, double totalPagar, String estado) {
        this.idFactura = idFactura;
        this.idCliente = idCliente;
        this.fechaEmision = fechaEmision;
        this.totalPagar = totalPagar;
        this.estado = estado;
    }

    public Factura(int idCliente, LocalDate fechaEmision, double totalPagar, String estado) {
        this.idCliente = idCliente;
        this.fechaEmision = fechaEmision;
        this.totalPagar = totalPagar;
        this.estado = estado;
    }

    public int getIdFactura() { return idFactura; }
    public int getIdCliente() { return idCliente; }
    public LocalDate getFechaEmision() { return fechaEmision; }
    public double getTotalPagar() { return totalPagar; }
    public String getEstado() { return estado; }

    public void setIdFactura(int idFactura) { this.idFactura = idFactura; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }
    public void setTotalPagar(double totalPagar) { this.totalPagar = totalPagar; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Factura{" +
               "idFactura=" + idFactura +
               ", idCliente=" + idCliente +
               ", fechaEmision=" + fechaEmision +
               ", totalPagar=" + String.format("%.2f", totalPagar) +
               ", estado='" + estado + '\'' +
               '}';
    }
}
