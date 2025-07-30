package modelo;

public class Cliente {
    private int idCliente;
    private String nombres;
    private String pasaporteCedula;
    private String ciudad;
    private String email;
    private String direccion;

    public Cliente(int idCliente, String nombres, String pasaporteCedula, String ciudad, String email, String direccion) {
        this.idCliente = idCliente;
        this.nombres = nombres;
        this.pasaporteCedula = pasaporteCedula;
        this.ciudad = ciudad;
        this.email = email;
        this.direccion = direccion;
    }

    public Cliente(String nombres, String pasaporteCedula, String ciudad, String email, String direccion) {
        this.nombres = nombres;
        this.pasaporteCedula = pasaporteCedula;
        this.ciudad = ciudad;
        this.email = email;
        this.direccion = direccion;
    }

    public int getIdCliente() { return idCliente; }
    public String getNombres() { return nombres; }
    public String getPasaporteCedula() { return pasaporteCedula; }
    public String getCiudad() { return ciudad; }
    public String getEmail() { return email; }
    public String getDireccion() { return direccion; }

    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public void setPasaporteCedula(String pasaporteCedula) { this.pasaporteCedula = pasaporteCedula; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public void setEmail(String email) { this.email = email; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    @Override
    public String toString() {
        return "Cliente{" +
               "idCliente=" + idCliente +
               ", nombres='" + nombres + '\'' +
               ", pasaporteCedula='" + pasaporteCedula + '\'' +
               '}';
    }
}