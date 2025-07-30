import dao.TablaCreator;
import vista.VentanaPrincipal; 

public class Main {
    public static void main(String[] args) {
        TablaCreator tablaCreator = new TablaCreator();
        tablaCreator.crearTablas();

        javax.swing.SwingUtilities.invokeLater(VentanaPrincipal::new);
    }
}
