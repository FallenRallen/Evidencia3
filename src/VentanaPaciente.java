import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VentanaPaciente extends JFrame {
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtEdad;
    private JLabel lblNombre;
    private JLabel LblSexo;
    private JComboBox cbxSexo;
    private JLabel lblTelefono;
    private JLabel lblEdad;
    private JButton btnInsertar;
    private JButton btnLimpiar;
    public JPanel MiPanel;

    private JButton btnEliminar;
    private JButton bntBuscar;
    private JButton btnVerificar;
    private JLabel lblID;
    private JButton btnGenerar;
    private JTextField txtID;
    private JLabel txtBusqueda;
    private JTextField txtIngreso;
    private JButton btnConsultar;

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
        btnGenerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InsertarIDEnTextPanel();
            }
        });
        cbxSexo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sexoSeleccionado = (String) cbxSexo.getSelectedItem();
            }
        });
        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VerificarEInsertarEnArchivo();
            }
        });
        bntBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPorID();
            }
        });
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarPaciente();
            }
        });
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarPacientes();
            }
        });
    }
    public class Paciente{
        private String nombre;
        private String id;
        private int edad;
        private String telefono;
        private String sexo;

        public Paciente(String nombre, String id, int edad, String telefono, String sexo){
            this.nombre = nombre;
            this.id = id;
            this.edad = edad;
            this.telefono = telefono;
            this.sexo = sexo;
        }
    }

    private int GenerarNumRandom(){
        Random random = new Random();
        return random.nextInt(9000)+1000;
    }
    private void InsertarIDEnTextPanel() {
        int numran = GenerarNumRandom();
        String idRan = "P" + numran;
        txtID.setText(idRan);
    }

    private void VerificarEInsertarEnArchivo(){
        String nombre = txtNombre.getText();
        String idTxt = txtID.getText();
        String TxTtelefono = txtTelefono.getText();
        String edad = txtEdad.getText();
        String sexo = (String)cbxSexo.getSelectedItem();
        edad = String.valueOf(edad);

        if(!nombre.isEmpty() && !idTxt.isEmpty() && !edad.isEmpty() && !TxTtelefono.isEmpty() && !sexo.isEmpty()){
            String id = txtID.getText();
            int edadnum = Integer.parseInt(edad);
            Paciente newPac = new Paciente(nombre, String.valueOf(id), edadnum,TxTtelefono, sexo);
            InsertarPaciente(newPac);
            JOptionPane.showMessageDialog(this, "Paciente Insertado en el Archivo:\n " + "Nombre: " + newPac.nombre +"\n"+ "ID: " + newPac.id + "\n" + "Edad: " + newPac.edad + "\n" +"Telefono: "+ newPac.telefono + "\n" +"Sexo: "+ newPac.sexo);
        }
    }
    private void InsertarPaciente(Paciente newPac){
        String Archivo = "Pacientes.txt";

        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(Archivo, true))){
            escritor.write(newPac.nombre + ", " + newPac.id + ", " + newPac.edad +", "+ newPac.telefono+ ", "+ newPac.sexo+"\n");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void buscarPorID(){
        String idBusqueda = txtIngreso.getText();

        if(!idBusqueda.isEmpty()){
            Paciente finded = buscarPacientePorID(idBusqueda);
            if(finded != null){
                JOptionPane.showMessageDialog(this, "Paciente encontrado:\n" +
                        "Nombre: " + finded.nombre + "\n" +
                        "ID: " + finded.id + "\n" +
                        "Edad: " + finded.edad + "\n"+
                        "Telefono: " + finded.telefono + "\n" +
                        "Sexo: " + finded.sexo);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró un Paciente con ese ID.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese un ID para realizar la búsqueda.");
        }
    }

    private Paciente buscarPacientePorID(String id){
        String Archivo = "Pacientes.txt";
        try(BufferedReader lector = new BufferedReader(new FileReader(Archivo))){
            String linea;
            while((linea = lector.readLine()) != null){
                String[] partes = linea.split(", ");
                if(partes.length == 5){
                    String idPaciente = partes[1].trim();
                    if(idPaciente.equals(id)){
                        return new Paciente(partes[0], idPaciente,Integer.parseInt(partes[2]),partes[3], partes[4]);
                    }
                }
            }

        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public void limpiarCampos(){
        txtNombre.setText("");
        txtEdad.setText("");
        txtTelefono.setText("");
        txtID.setText("");
        cbxSexo.setSelectedItem("Ninguno");
        txtIngreso.setText("");
        JOptionPane.showMessageDialog(this, "Limpieza Exitosa");
    }
    public void VerificarCampos(){
        String nombre = txtNombre.getText();
        String id = txtID.getText();
        String edadTxt = txtEdad.getText();
        String telefono = txtTelefono.getText();
        String sexo = (String)cbxSexo.getSelectedItem();

        if(!nombre.isEmpty() && !edadTxt.isEmpty() && !telefono.isEmpty()){

            try{
                int edadnum = Integer.parseInt(edadTxt);
                Paciente newPac = new Paciente(nombre, id, edadnum, telefono, sexo);

                JOptionPane.showMessageDialog(this, "Paciente verificado: " + newPac.nombre +", "+newPac.id +", "+ newPac.edad +", "+ newPac.telefono + ", " + newPac.sexo);
            } catch(NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Ingrese una edad valida");
            }
        }else{
            JOptionPane.showMessageDialog(this, "Por favor no deje campos en blanco y rellenelos con los datos necesarios");
        }
    }

    private void eliminarPaciente() {
        String idEliminar = txtIngreso.getText();

        if (!idEliminar.isEmpty()) {
            String archivo = "Pacientes.txt";
            List<String> lineas = new ArrayList<>();

            try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
                String linea;
                boolean pacienteEncontrado = false;

                while ((linea = lector.readLine()) != null) {
                    String[] partes = linea.split(", ");
                    if (partes.length == 5) {
                        String idPaciente = partes[1].trim();
                        if (!idPaciente.equals(idEliminar)) {
                            lineas.add(linea);
                        } else {
                            pacienteEncontrado = true;
                        }
                    }
                }

                if (!pacienteEncontrado) {
                    JOptionPane.showMessageDialog(this, "No se encontró un paciente con ese ID.");
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo))) {
                for (String linea : lineas) {
                    escritor.write(linea + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al intentar eliminar al paciente.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Paciente eliminado con éxito.");
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido para eliminar.");
        }
    }

    private void consultarPacientes() {
        String archivo = "Pacientes.txt";

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            StringBuilder datosPacientes = new StringBuilder();
            String linea;

            while ((linea = lector.readLine()) != null) {
                datosPacientes.append(linea).append("\n");
            }

            if (datosPacientes.length() > 0) {
                JOptionPane.showMessageDialog(this, "Datos de los Pacientes:\n" + datosPacientes.toString());
            } else {
                JOptionPane.showMessageDialog(this, "No hay datos de pacientes almacenados.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al intentar consultar los datos de los pacientes.");
        }
    }
    public static void main(String[] args){
        VentanaPaciente pac = new VentanaPaciente();
        pac.setContentPane(pac.MiPanel);
        pac.setSize(500,500);
        pac.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pac.setVisible(true);
    }
}
