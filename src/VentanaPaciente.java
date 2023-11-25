import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPaciente extends JFrame {
    private JTextField txtNombre;
    private JTextField Txttelefono;
    private JTextField txtEdad;
    private JLabel lblNombre;
    private JLabel LblSexo;
    private JComboBox cbxSexo;
    private JLabel lblTelefono;
    private JLabel lblEdad;
    private JButton btnInsertar;
    private JButton btnLimpiar;
    private JPanel miPanel;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton bntBuscar;
    private JButton btnVerificar;

    public VentanaPaciente() {
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        btnVerificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VerificarCampos();
            }
        });
    }
    public class Paciente{
        private String nombre;
        private int edad;
        private String telefono;

        public Paciente(){
            nombre = "";
            edad = 0;
            telefono = "";
        }

        public Paciente(String nombre, int edad, String telefono){
            this.nombre = nombre;
            this.edad = edad;
            this.telefono = telefono;
        }
    }

    public static void main(String[] args){
        VentanaPaciente pac = new VentanaPaciente();
        pac.setContentPane(pac.miPanel);
        pac.setSize(500,500);
        pac.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pac.setVisible(true);
    }
    public void limpiarCampos(){
        txtNombre.setText("");
        txtEdad.setText("");
        Txttelefono.setText("");
        JOptionPane.showMessageDialog(this, "Limpieza Exitosa");
    }
    public void VerificarCampos(){
        String nombre = txtNombre.getText();
        String edadTxt = txtEdad.getText();
        String telefono = Txttelefono.getText();

        if(!nombre.isEmpty() && !edadTxt.isEmpty() && !telefono.isEmpty()){

            try{
            int edad = Integer.parseInt(edadTxt);
            Paciente NewPac = new Paciente(nombre,edad, telefono);

            JOptionPane.showMessageDialog(this, "Paciente verificado: " + NewPac.nombre +", "+ NewPac.edad +", "+ NewPac.telefono);
            } catch(NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Ingrese una edad valida");
            }
        }else{
            JOptionPane.showMessageDialog(this, "Por favor no deje campos en blanco y rellenelos con los datos necesarios");
        }
    }
}
