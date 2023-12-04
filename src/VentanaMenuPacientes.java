import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VentanaMenuPacientes extends JFrame {
    private JButton btnDoctores;
    private JButton btnPacientes;
    private JButton btnCitas;
    private JButton btnSalir;
    public JPanel MiPanel;

    public VentanaMenuPacientes() {
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CerrarSesion();
            }
        });
        btnDoctores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirArchivo("Doctores.txt");
            }
        });
        btnPacientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirArchivo("Pacientes.txt");
            }
        });
        btnCitas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirArchivo("Citas.txt");
            }
        });
    }

    private void abrirArchivo(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            StringBuilder contenido = new StringBuilder();
            String linea;

            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }

            // Puedes mostrar el contenido en un cuadro de diálogo o realizar otras acciones según tus necesidades
            JOptionPane.showMessageDialog(this, "Contenido de " + nombreArchivo + ":\n" + contenido.toString(), "Archivo Abierto", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al abrir el archivo " + nombreArchivo, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void CerrarSesion(){
        VentanaLogin ventanaLogin = new VentanaLogin();
        JFrame frame = new JFrame("VentanaLogin");
        frame.setContentPane(ventanaLogin.MiPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        VentanaMenuPacientes menuPacientes = new VentanaMenuPacientes();
        menuPacientes.setContentPane(menuPacientes.MiPanel);
        menuPacientes.setSize(500,500);
        menuPacientes.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        menuPacientes.setVisible(true);
    }
}
