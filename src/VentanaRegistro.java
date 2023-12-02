import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class VentanaRegistro extends JFrame{
    private JTextField txtEdad;
    private JTextField txtEmail;
    private JTextField txtNombre;
    private JComboBox cbxTipo;
    private JButton btnCrear;
    private JPasswordField pswContraseña;
    private JPasswordField pswConfirmar;
    public JPanel MiPanel;
    private JButton btnRegresar;

    public VentanaRegistro() {
        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDatos();
            }
        });
        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regresarAVentanaLogin();
            }
        });
    }

    private void guardarDatos() {
        String nombre = txtNombre.getText();
        String email = txtEmail.getText();
        String edadStr = txtEdad.getText();
        String tipoUsuario = (String) cbxTipo.getSelectedItem(); // Obtener el tipo de usuario seleccionado
        String contraseña = new String(pswContraseña.getPassword());

        // Verificar si las contraseñas coinciden antes de guardar
        String confirmarContraseña = new String(pswConfirmar.getPassword());
        if (!contraseña.equals(confirmarContraseña)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si la edad es un número
        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese una edad válida", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Guardar datos en el archivo Registro.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Registro.txt", true))) {
            // Agregar datos al archivo
            writer.write("Nombre:" + nombre + "\n");
            writer.write("Email:" + email + "\n");
            writer.write("Edad:" + edad + "\n");
            writer.write("Tipo de Usuario:" + tipoUsuario + "\n");
            writer.write("Contraseña:" + contraseña + "\n");
            writer.write("\n");  // Separador entre registros

            // Mensaje de éxito
            JOptionPane.showMessageDialog(this, "Datos guardados con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            // Manejar errores de escritura de archivos
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void regresarAVentanaLogin() {
        // Cerrar la ventana actual
        this.dispose();

        // Crear e mostrar la ventana de login
        VentanaLogin ventanaLogin = new VentanaLogin();
        JFrame frame = new JFrame("VentanaLogin");
        frame.setContentPane(ventanaLogin.MiPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Ajusta según tus necesidades
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        VentanaRegistro registro = new VentanaRegistro();
        registro.setContentPane(registro.MiPanel);
        registro.setSize(500,500);
        registro.setDefaultCloseOperation(EXIT_ON_CLOSE);
        registro.setVisible(true);
    }
}
