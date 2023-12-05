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
    private JLabel lblCorreo;
    private JLabel lblPassword;

    private static final String NOMBRE_LABEL = "Nombre:";
    private static final String EMAIL_LABEL = "Email:";
    private static final String EDAD_LABEL = "Edad:";
    private static final String USUARIO_LABEL = "Tipo de Usuario:";
    private static final String CONTRASEÑA_LABEL = "Contraseña:";

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


    private void abrirVentanaFuncionesDoctor(){
        // Crear e mostrar la ventana de funciones para doctores
        VentanaMenu ventanaMenu = new VentanaMenu(); // Asegúrate de tener una clase VentanaFuncionesDoctor
        JFrame frame = new JFrame("Funciones para Doctores");
        frame.setContentPane(ventanaMenu.MiPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Ajusta según tus necesidades
        frame.pack();
        frame.setVisible(true);

        // Cerrar la ventana actual de inicio de sesión
        this.dispose();
    }

    private void verificarCredencialeseIniciarSesion() {
        // Obtener el correo ingresado y la contraseña como cadena
        String correoIngresado = txtCorreo.getText().trim();
        char[] contraseñaIngresada = pswContraseña.getPassword();
        String contraseñaIngresadaStr = new String(contraseñaIngresada).trim();

        // Verificar si tanto el correo como la contraseña están completos
        if (correoIngresado.isEmpty() || contraseñaIngresadaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese correo y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader("Registro.txt"))) {
            String line;
            boolean credencialesCorrectas = false;
            String correoEnRegistro = "";
            String contraseñaEnRegistro = "";
            String tipoUsuarioEnRegistro = "";

            // Leer el archivo de registros línea por línea
            while ((line = br.readLine()) != null) {
                // Imprimir la línea leída en la consola (puede ser útil para depuración)
                System.out.println("Línea leída: " + line);

                // Analizar las líneas que contienen información sobre el usuario
                if (line.startsWith("Email:")) {
                    correoEnRegistro = obtenerValorDespuesDeCadena(line, "Email:").trim();
                } else if (line.startsWith("Contraseña:")) {
                    contraseñaEnRegistro = obtenerValorDespuesDeCadena(line, "Contraseña:").trim();
                } else if (line.startsWith("Tipo de Usuario:")) {
                    tipoUsuarioEnRegistro = obtenerValorDespuesDeCadena(line, "Tipo de Usuario:").trim();
                } else if (line.trim().isEmpty()) {
                    // Se encontró una línea en blanco, verifica las credenciales
                    if (correoIngresado.equals(correoEnRegistro) && contraseñaIngresadaStr.equals(contraseñaEnRegistro)) {
                        // Abre la ventana correspondiente según el tipo de usuario
                        if ("Doctor".equalsIgnoreCase(tipoUsuarioEnRegistro)) {
                            abrirVentanaFuncionesDoctor();
                        } else if ("Paciente".equalsIgnoreCase(tipoUsuarioEnRegistro)) {
                            abrirVentanaPaciente();
                        }

                        // Establece la bandera de credenciales correctas y sale del bucle
                        credencialesCorrectas = true;
                        break;
                    }

                    // Restablece las variables para la siguiente iteración
                    correoEnRegistro = "";
                    contraseñaEnRegistro = "";
                    tipoUsuarioEnRegistro = "";
                }
            }

            // Verifica las credenciales después de salir del bucle (para el último usuario en el archivo)
            if (!credencialesCorrectas && correoIngresado.equals(correoEnRegistro) && contraseñaIngresadaStr.equals(contraseñaEnRegistro)) {
                // Abre la ventana correspondiente según el tipo de usuario
                if ("Doctor".equalsIgnoreCase(tipoUsuarioEnRegistro)) {
                    abrirVentanaFuncionesDoctor();
                } else if ("Paciente".equalsIgnoreCase(tipoUsuarioEnRegistro)) {
                    abrirVentanaPaciente();
                }

                // Establece la bandera de credenciales correctas
                credencialesCorrectas = true;
            }

            // Muestra un mensaje de error si las credenciales son incorrectas
            if (!credencialesCorrectas) {
                JOptionPane.showMessageDialog(this, "La contraseña es incorrecta o el usuario no existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            // Imprimir la traza de la excepción en caso de error durante la lectura
            ex.printStackTrace();

            // Mostrar un mensaje de error en caso de problemas durante la verificación de credenciales
            JOptionPane.showMessageDialog(this, "Error al verificar credenciales", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private String obtenerValorDespuesDeCadena(String linea, String cadena) {
        int indiceCadena = linea.indexOf(cadena);
        if (indiceCadena != -1) {
            // Si la cadena se encuentra, devolver la parte después de la cadena
            return linea.substring(indiceCadena + cadena.length());
        } else {
            // Si la cadena no se encuentra, devolver una cadena vacía o manejar el caso según sea necesario
            return "";
        }
    }

    private void abrirVentanaPaciente(){
        VentanaMenuPacientes ventanaMenuPacientes = new VentanaMenuPacientes();
        JFrame frame = new JFrame("Funciones para Pacientes");
        frame.setContentPane(ventanaMenuPacientes.MiPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        // Cerrar la ventana actual de inicio de sesión
        this.dispose();
    }


    public static void main(String[] args) {
        VentanaLogin login = new VentanaLogin();
        login.setContentPane(login.MiPanel);
        login.setSize(500,300);
        login.setDefaultCloseOperation(EXIT_ON_CLOSE);
        login.setVisible(true);
    }
}

