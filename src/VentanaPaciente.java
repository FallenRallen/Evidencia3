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
    public class Paciente {
        private String nombre;    // Nombre del paciente
        private String id;        // Identificación del paciente
        private int edad;         // Edad del paciente
        private String telefono;  // Número de teléfono del paciente
        private String sexo;      // Género del paciente

        /**
         * Constructor para crear instancias de la clase Paciente con valores específicos.
         *
         * @param nombre    Nombre del paciente.
         * @param id        Identificación del paciente.
         * @param edad      Edad del paciente.
         * @param telefono  Número de teléfono del paciente.
         * @param sexo      Género del paciente.
         */
        public Paciente(String nombre, String id, int edad, String telefono, String sexo) {
            this.nombre = nombre;
            this.id = id;
            this.edad = edad;
            this.telefono = telefono;
            this.sexo = sexo;
        }
    }

    /**
     * Genera un número aleatorio de cuatro dígitos.
     *
     * Este método utiliza la clase Random para generar un número aleatorio entre 1000 y 9999
     * (cuatro dígitos). Retorna el número generado.
     *
     * @return Número aleatorio de cuatro dígitos.
     */
    private int GenerarNumRandom() {
        Random random = new Random();
        return random.nextInt(9000) + 1000;
    }

    /**
     * Genera un número aleatorio y lo inserta en un campo de texto en la interfaz de usuario.
     *
     * Este método llama al método GenerarNumRandom para obtener un número aleatorio de cuatro dígitos.
     * Luego, concatena la letra "P" al inicio del número y establece el resultado como texto en un campo
     * de texto llamado "txtID" en la interfaz de usuario.
     */
    private void InsertarIDEnTextPanel() {
        // Generar un número aleatorio de cuatro dígitos
        int numran = GenerarNumRandom();

        // Concatenar la letra "P" al inicio del número aleatorio
        String idRan = "P" + numran;

        // Establecer el resultado como texto en el campo de texto "txtID" en la interfaz de usuario
        txtID.setText(idRan);
    }

    private void VerificarEInsertarEnArchivo() {
        // Obtener información desde campos de texto y cuadro combinado en la interfaz de usuario
        String nombre = txtNombre.getText();
        String idTxt = txtID.getText();
        String TxTtelefono = txtTelefono.getText();
        String edad = txtEdad.getText();
        String sexo = (String) cbxSexo.getSelectedItem();

        // Convertir la edad a un valor numérico
        int edadnum = Integer.parseInt(edad);

        // Verificar que los campos obligatorios no estén vacíos
        if (!nombre.isEmpty() && !idTxt.isEmpty() && !edad.isEmpty() && !TxTtelefono.isEmpty() && !sexo.isEmpty()) {
            // Crear una instancia de la clase Paciente con los datos proporcionados
            Paciente newPac = new Paciente(nombre, String.valueOf(idTxt), edadnum, TxTtelefono, sexo);

            // Insertar el nuevo paciente en el archivo "Pacientes.txt"
            InsertarPaciente(newPac);

            // Mostrar un mensaje con la información del paciente insertado
            JOptionPane.showMessageDialog(this, "Paciente Insertado en el Archivo:\n " +
                    "Nombre: " + newPac.nombre + "\n" +
                    "ID: " + newPac.id + "\n" +
                    "Edad: " + newPac.edad + "\n" +
                    "Telefono: " + newPac.telefono + "\n" +
                    "Sexo: " + newPac.sexo);
        }
    }
    private void InsertarPaciente(Paciente newPac) {
        // Nombre del archivo donde se guardarán los datos de los pacientes
        String Archivo = "Pacientes.txt";

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(Archivo, true))) {
            // Escribir la información del nuevo paciente en una nueva línea del archivo
            escritor.write(newPac.nombre + ", " + newPac.id + ", " + newPac.edad + ", " + newPac.telefono + ", " + newPac.sexo + "\n");
        } catch (IOException e) {
            // Imprimir la traza de la excepción en caso de error durante la escritura
            e.printStackTrace();
        }
    }

    private void buscarPorID() {
        // Obtener el ID de búsqueda desde el campo de texto en la interfaz de usuario
        String idBusqueda = txtIngreso.getText();

        // Verificar que el ID de búsqueda no esté vacío
        if (!idBusqueda.isEmpty()) {
            // Llamar al método buscarPacientePorID con el ID de búsqueda
            Paciente finded = buscarPacientePorID(idBusqueda);

            // Verificar si se encontró un paciente con el ID especificado
            if (finded != null) {
                // Mostrar un mensaje con la información del paciente encontrado
                JOptionPane.showMessageDialog(this, "Paciente encontrado:\n" +
                        "Nombre: " + finded.nombre + "\n" +
                        "ID: " + finded.id + "\n" +
                        "Edad: " + finded.edad + "\n" +
                        "Telefono: " + finded.telefono + "\n" +
                        "Sexo: " + finded.sexo);
            } else {
                // Mostrar un mensaje si no se encontró un paciente con el ID especificado
                JOptionPane.showMessageDialog(this, "No se encontró un Paciente con ese ID.");
            }
        } else {
            // Mostrar un mensaje si el campo de ID está vacío
            JOptionPane.showMessageDialog(this, "Ingrese un ID para realizar la búsqueda.");
        }
    }

    private Paciente buscarPacientePorID(String id) {
        // Nombre del archivo que contiene los datos de los pacientes
        String Archivo = "Pacientes.txt";

        try (BufferedReader lector = new BufferedReader(new FileReader(Archivo))) {
            String linea;

            // Leer cada línea del archivo
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(", ");

                // Verificar si la línea contiene los datos de un paciente
                if (partes.length == 5) {
                    String idPaciente = partes[1].trim();

                    // Comparar el ID con el ID buscado
                    if (idPaciente.equals(id)) {
                        // Crear una instancia de la clase Paciente con los datos encontrados
                        return new Paciente(partes[0], idPaciente, Integer.parseInt(partes[2]), partes[3], partes[4]);
                    }
                }
            }
        } catch (IOException e) {
            // Imprimir la traza de la excepción en caso de error durante la lectura
            e.printStackTrace();
        }

        // Devolver null si no se encuentra un paciente con el ID especificado
        return null;
    }

    public void limpiarCampos() {
        // Establecer valores predeterminados o vacíos en los campos de la interfaz
        txtNombre.setText("");
        txtEdad.setText("");
        txtTelefono.setText("");
        txtID.setText("");
        cbxSexo.setSelectedItem("Ninguno");
        txtIngreso.setText("");

        // Mostrar un mensaje indicando que la limpieza de campos fue exitosa
        JOptionPane.showMessageDialog(this, "Limpieza Exitosa");
    }
    public void VerificarCampos() {
        // Obtener valores de los campos desde la interfaz de usuario
        String nombre = txtNombre.getText();
        String id = txtID.getText();
        String edadTxt = txtEdad.getText();
        String telefono = txtTelefono.getText();
        String sexo = (String) cbxSexo.getSelectedItem();

        // Verificar que los campos obligatorios no estén vacíos
        if (!nombre.isEmpty() && !edadTxt.isEmpty() && !telefono.isEmpty()) {
            try {
                // Intentar convertir la edad a un número entero
                int edadnum = Integer.parseInt(edadTxt);

                // Crear una instancia de la clase Paciente con los datos verificados
                Paciente newPac = new Paciente(nombre, id, edadnum, telefono, sexo);

                // Mostrar un mensaje de éxito con la información del paciente verificado
                JOptionPane.showMessageDialog(this, "Paciente verificado: " + newPac.nombre + ", " + newPac.id + ", " + newPac.edad + ", " + newPac.telefono + ", " + newPac.sexo);
            } catch (NumberFormatException e) {
                // Mostrar un mensaje si la conversión de la edad falla
                JOptionPane.showMessageDialog(this, "Ingrese una edad válida");
            }
        } else {
            // Mostrar un mensaje si algún campo obligatorio está vacío
            JOptionPane.showMessageDialog(this, "Por favor, no deje campos en blanco y rellénelos con los datos necesarios");
        }
    }

    private void eliminarPaciente() {
        // Obtener el ID del paciente a eliminar desde el campo de texto
        String idEliminar = txtIngreso.getText();

        // Verificar que el ID no esté vacío
        if (!idEliminar.isEmpty()) {
            // Nombre del archivo que contiene los datos de los pacientes
            String archivo = "Pacientes.txt";

            // Lista para almacenar las líneas del archivo excluyendo la del paciente a eliminar
            List<String> lineas = new ArrayList<>();

            try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
                String linea;
                boolean pacienteEncontrado = false;

                // Leer cada línea del archivo y agregarla a la lista, excluyendo al paciente a eliminar
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

                // Mostrar un mensaje si el paciente no fue encontrado
                if (!pacienteEncontrado) {
                    JOptionPane.showMessageDialog(this, "No se encontró un paciente con ese ID.");
                    return;
                }
            } catch (IOException e) {
                // Imprimir la traza de la excepción en caso de error durante la lectura
                e.printStackTrace();
            }

            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo))) {
                // Escribir las líneas en el archivo excluyendo la del paciente a eliminar
                for (String linea : lineas) {
                    escritor.write(linea + "\n");
                }
            } catch (IOException e) {
                // Imprimir la traza de la excepción en caso de error durante la escritura
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al intentar eliminar al paciente.");
                return;
            }

            // Mostrar un mensaje de éxito si la operación se realizó correctamente
            JOptionPane.showMessageDialog(this, "Paciente eliminado con éxito.");
        } else {
            // Mostrar un mensaje si el campo de texto del ID está vacío
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido para eliminar.");
        }
    }

    private void consultarPacientes() {
        // Nombre del archivo que contiene los datos de los pacientes
        String archivo = "Pacientes.txt";

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            // StringBuilder para almacenar los datos de los pacientes
            StringBuilder datosPacientes = new StringBuilder();
            String linea;

            // Leer cada línea del archivo y concatenarla en el StringBuilder
            while ((linea = lector.readLine()) != null) {
                datosPacientes.append(linea).append("\n");
            }

            // Mostrar los datos en un cuadro de diálogo si hay información
            if (datosPacientes.length() > 0) {
                JOptionPane.showMessageDialog(this, "Datos de los Pacientes:\n" + datosPacientes.toString());
            } else {
                // Mostrar un mensaje si no hay datos de pacientes almacenados
                JOptionPane.showMessageDialog(this, "No hay datos de pacientes almacenados.");
            }
        } catch (IOException e) {
            // Imprimir la traza de la excepción en caso de error durante la lectura
            e.printStackTrace();

            // Mostrar un mensaje de error en caso de problemas durante la consulta de datos
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
