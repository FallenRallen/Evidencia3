import javax.swing.*;

public class Main extends JFrame {
    public static void main(String[] args)  {
        //@Override
        SwingUtilities.invokeLater(() -> {
            // Inicia la aplicación mostrando la ventana de login
            VentanaLogin login = new VentanaLogin();
            login.setContentPane(login.MiPanel);
            login.setSize(500,300);
            login.setDefaultCloseOperation(EXIT_ON_CLOSE);
            login.setVisible(true);
        });
    }

}
