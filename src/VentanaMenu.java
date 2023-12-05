import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaMenu extends JFrame{
    public JPanel MiPanel;
    private JButton btnDoctores;
    private JButton btnPacientes;
    private JButton btnCita;
    private JButton btnRegreso;
    private JButton btnConsultas;

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
        btnCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IrCitas();
            }
        });
        btnConsultas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IrConsultas();
            }
        });
    }

    private void irDoctores() {
        // Crear e mostrar la ventana de registro
        InsertarDoctor insertarDoctor = new InsertarDoctor();
        JFrame frame = new JFrame("InsertarDoctor");
        frame.setContentPane(insertarDoctor.MiPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        // Cerrar la ventana actual
        this.dispose();
    }

    private void irPacientes(){
        // Crear e mostrar la ventana de registro
        VentanaPaciente ventanaPaciente = new VentanaPaciente();
        JFrame frame = new JFrame("VentanaPaciente");
        frame.setContentPane(ventanaPaciente.MiPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.dispose();
    }
    private void IrCitas(){
        VentanaCitas ventanaCitas = new VentanaCitas();
        JFrame frame = new JFrame("VentanaCitas");
        frame.setContentPane(ventanaCitas.MiPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.dispose();
    }

    private void IrConsultas(){
        VentanaMenuPacientes ventanaConsultas = new VentanaMenuPacientes();
        JFrame frame = new JFrame("VentanaCOnsultas");
        frame.setContentPane(ventanaConsultas.MiPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.dispose();
    }

    private void cerrarSesion(){
        VentanaLogin ventanaLogin = new VentanaLogin();
        JFrame frame = new JFrame("VentanaLogin");
        frame.setContentPane(ventanaLogin.MiPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        VentanaMenu menu = new VentanaMenu();
        menu.setContentPane(menu.MiPanel);
        menu.setSize(500,500);
        menu.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        menu.setVisible(true);
    }
}
