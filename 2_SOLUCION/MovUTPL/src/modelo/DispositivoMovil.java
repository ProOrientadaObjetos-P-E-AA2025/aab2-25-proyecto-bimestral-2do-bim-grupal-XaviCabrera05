package modelo;

public class DispositivoMovil {
    private int idDispositivo;
    private int idCliente;
    private String marca;
    private String modelo;
    private String numeroCelular;

    public DispositivoMovil(int idDispositivo, int idCliente, String marca, String modelo, String numeroCelular) {
        this.idDispositivo = idDispositivo;
        this.idCliente = idCliente;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroCelular = numeroCelular;
    }

    public DispositivoMovil(int idCliente, String marca, String modelo, String numeroCelular) {
        this.idCliente = idCliente;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroCelular = numeroCelular;
    }

    public int getIdDispositivo() { return idDispositivo; }
    public int getIdCliente() { return idCliente; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getNumeroCelular() { return numeroCelular; }

    public void setIdDispositivo(int idDispositivo) { this.idDispositivo = idDispositivo; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setNumeroCelular(String numeroCelular) { this.numeroCelular = numeroCelular; }

    @Override
    public String toString() {
        return "DispositivoMovil{" +
               "idDispositivo=" + idDispositivo +
               ", idCliente=" + idCliente +
               ", marca='" + marca + '\'' +
               ", modelo='" + modelo + '\'' +
               ", numeroCelular='" + numeroCelular + '\'' +
               '}';
    }
}
