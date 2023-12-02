import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaMenu extends JFrame{
    public JPanel MiPanel;
    private JButton btnDoctores;
    private JButton btnPacientes;
    private JButton btnCita;
    private JButton btnRegreso;
    
    public VentanaMenu() {
        btnDoctores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irDoctores();
            }
        });
        btnPacientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irPacientes();
            }
        });
        btnRegreso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
    }

    private void irDoctores(){
            // Cerrar la ventana actual
            this.dispose();

            // Crear e mostrar la ventana de registro
            InsertarDoctor insertarDoctor = new InsertarDoctor();
            JFrame frame = new JFrame("InsertarDoctor");
            frame.setContentPane(insertarDoctor.MiPanel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Ajusta según tus necesidades
            frame.pack();
            frame.setVisible(true);
    }

    private void irPacientes(){
        // Cerrar la ventana actual
        this.dispose();

        // Crear e mostrar la ventana de registro
        VentanaPaciente ventanaPaciente = new VentanaPaciente();
        JFrame frame = new JFrame("VentanaPaciente");
        frame.setContentPane(ventanaPaciente.MiPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Ajusta según tus necesidades
        frame.pack();
        frame.setVisible(true);
    }

    private void cerrarSesion(){
        this.dispose();
        VentanaLogin ventanaLogin = new VentanaLogin();
        JFrame frame = new JFrame("VentanaLogin");
        frame.setContentPane(ventanaLogin.MiPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Ajusta según tus necesidades
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        VentanaMenu menu = new VentanaMenu();
        menu.setContentPane(menu.MiPanel);
        menu.setSize(500,500);
        menu.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        menu.setVisible(true);
    }
}
