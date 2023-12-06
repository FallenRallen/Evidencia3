// Importa la clase JFrame del paquete javax.swing
import javax.swing.*;

// Clase principal que extiende JFrame para representar la ventana principal de la aplicación
public class Main extends JFrame {

    // Método principal que sirve como punto de entrada para la aplicación
    public static void main(String[] args)  {

        //@Override
        // Utiliza SwingUtilities para ejecutar la creación de la GUI en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> {

            // Crea una instancia de la ventana de login (VentanaLogin)
            VentanaLogin login = new VentanaLogin();

            // Establece el panel de contenido de la ventana como el panel personalizado de la ventana de login
            login.setContentPane(login.MiPanel);

            // Establece las dimensiones de la ventana
            login.setSize(500, 300);

            // Establece la operación predeterminada al cerrar la ventana
            login.setDefaultCloseOperation(EXIT_ON_CLOSE);

            // Hace visible la ventana de login
            login.setVisible(true);
        });
    }
}