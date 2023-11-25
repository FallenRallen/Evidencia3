import javax.print.Doc;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Random;

public class InsertarDoctor extends JFrame {
    private JPanel MiPanel;
    private JTextField txtID;
    private JLabel lblID;
    private JTextField txtNombre;
    private JLabel lblNombre;
    private JLabel lblEspecialidad;
    private JTextField txtEspecialidad;
    private JButton btnVerificar;
    private JButton btnLimpiar;
    private JButton btnBuscar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnGenerar;
    private JButton btnInsertar;
    private JLabel lblIngresoID;
    private JTextField txtIngreso;

    public InsertarDoctor() {
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
        btnGenerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InsertarIDEnTextPanel();
            }
        });
        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VerificarEInsertarEnArchivo();
            }
        });
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPorID();
            }
        });
    }

    public void limpiarCampos(){
        txtID.setText("");
        txtEspecialidad.setText("");
        txtNombre.setText("");
        txtIngreso.setText("");
        JOptionPane.showMessageDialog(this, "Limpieza Exitosa");
    }
    public class Doctor{
        private String nombre;
        private String id;
        private String especialidad;

        public Doctor(String nombre, String id, String especialidad){
            this.nombre = nombre;
            this.id = id;
            this.especialidad = especialidad;
        }
    }

    public void VerificarCampos(){
        String nombre = txtNombre.getText();
        String id = txtID.getText();
        String especialidad = txtEspecialidad.getText();

        if(!nombre.isEmpty() && !id.isEmpty() && !especialidad.isEmpty()){
            Doctor newDoc = new Doctor(id, nombre, especialidad);
            JOptionPane.showMessageDialog(this, "Doctor verificado, Listo para el registro " + newDoc.nombre +", " + newDoc.id + ", " + newDoc.especialidad);
        }else{
            JOptionPane.showMessageDialog(this, "Ingrese los datos necesarios para el registro");
        }
    }

    private void InsertarDoctor(Doctor newDoc){
        String Archivo = "Doctores.txt";

        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(Archivo, true))){
            escritor.write(newDoc.nombre + ", " + newDoc.id + ", " + newDoc.especialidad + "\n");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private int GenerarNumRandom(){
        Random random = new Random();
        return random.nextInt(9000)+1000;
    }
    private void InsertarIDEnTextPanel() {
        int numran = GenerarNumRandom();
        String idRan = "D" + numran;
        txtID.setText(idRan);
    }

    private void VerificarEInsertarEnArchivo(){
        String nombre = txtNombre.getText();
        String idTxt = txtID.getText();
        String especialidad = txtEspecialidad.getText();

        if(!nombre.isEmpty() && !idTxt.isEmpty() && !especialidad.isEmpty()){
                String id = txtID.getText();
                Doctor newDoc = new Doctor(nombre, String.valueOf(id),especialidad);
                InsertarDoctor(newDoc);
                JOptionPane.showMessageDialog(this, "Doctor Insertado en el Archivo:\n " + "Nombre: " + newDoc.nombre +"\n"+ "ID: " + newDoc.id + "\n" + "Especialidad: " + newDoc.especialidad);
        }
    }

    private Doctor buscarDoctorPorID(String id){
        String Archivo = "Doctores.txt";
        try(BufferedReader lector = new BufferedReader(new FileReader(Archivo))){
            String linea;
            while((linea = lector.readLine()) != null){
                String[] partes = linea.split(", ");
                if(partes.length == 3){
                    String idDoctor = partes[1].trim();
                    if(idDoctor.equals(id)){
                        return new Doctor(partes[0], idDoctor,partes[2]);
                    }
                }
            }

        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private void buscarPorID(){
        String idBusqueda = txtIngreso.getText();

        if(!idBusqueda.isEmpty()){
                Doctor finded = buscarDoctorPorID(idBusqueda);
                if(finded != null){
                    JOptionPane.showMessageDialog(this, "Doctor encontrado:\n" +
                            "Nombre: " + finded.nombre + "\n" +
                            "ID: " + finded.id + "\n" +
                            "Especialidad: " + finded.especialidad);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró un doctor con ese ID.");
                }
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese un ID para realizar la búsqueda.");
        }
    }



    public static void main(String[] args){
        InsertarDoctor doc = new InsertarDoctor();
        doc.setContentPane(doc.MiPanel);
        doc.setSize(500,500);
        doc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        doc.setVisible(true);
    }
}
