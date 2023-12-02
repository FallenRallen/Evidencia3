import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VentanaLogin extends JFrame {
    private JPasswordField pswContraseña;
    private JTextField txtCorreo;
    private JButton btnNuevo;
    private JButton btnIngresar;
    public JPanel MiPanel;

    public VentanaLogin() {
        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaRegistro();
            }
        });
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarCredencialeseIniciarSesion();
            }
        });
    }

    private void abrirVentanaRegistro() {
        // Cerrar la ventana actual
        this.dispose();

        // Crear e mostrar la ventana de registro
        VentanaRegistro ventanaRegistro = new VentanaRegistro();
        JFrame frame = new JFrame("VentanaRegistro");
        frame.setContentPane(ventanaRegistro.MiPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Ajusta según tus necesidades
        frame.pack();
        frame.setVisible(true);
    }

    private void verificarCredencialeseIniciarSesion() {
        String correoIngresado = txtCorreo.getText();
        char[] contraseñaIngresada = pswContraseña.getPassword();
        String contraseñaIngresadaStr = new String(contraseñaIngresada);

        try (BufferedReader br = new BufferedReader(new FileReader("Registro.txt"))) {
            String line;
            boolean credencialesCorrectas = false;
            String correoEnRegistro = "";
            String contraseñaEnRegistro = "";

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Email:")) {
                    // Extraer el correo de la línea
                    correoEnRegistro = obtenerValorDespuesDeCadena(line, "Email:");
                } else if (line.startsWith("Contraseña:")) {
                    // Extraer la contraseña de la línea
                    contraseñaEnRegistro = obtenerValorDespuesDeCadena(line, "Contraseña:");

                    if (correoIngresado.trim().equals(correoEnRegistro.trim()) && contraseñaIngresadaStr.trim().equals(contraseñaEnRegistro.trim())) {
                        // Las credenciales coinciden
                        credencialesCorrectas = true;
                        break;
                    }
                }
            }


            if (credencialesCorrectas) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                VentanaMenu ventanaMenu = new VentanaMenu();
                JFrame frame = new JFrame("VentanaMenu");
                frame.setContentPane(ventanaMenu.MiPanel);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Ajusta según tus necesidades
                frame.pack();
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "La contraseña es incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al verificar credenciales", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obtenerValorDespuesDeCadena(String linea, String cadena) {
        int indiceCadena = linea.indexOf(cadena);
        if (indiceCadena != -1) {
            // Si la cadena se encuentra, devolver la parte después de la cadena
            return linea.substring(indiceCadena + cadena.length()).trim();
        } else {
            // Si la cadena no se encuentra, devolver una cadena vacía o manejar el caso según sea necesario
            return "";
        }
    }
    public static void main(String[] args) {
        VentanaLogin login = new VentanaLogin();
        login.setContentPane(login.MiPanel);
        login.setSize(500,300);
        login.setDefaultCloseOperation(EXIT_ON_CLOSE);
        login.setVisible(true);
    }
}

