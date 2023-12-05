import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InsertarDoctor extends JFrame {
    public JPanel MiPanel;
    private JTextField txtID;
    private JLabel lblID;
    private JTextField txtNombre;
    private JLabel lblNombre;
    private JLabel lblEspecialidad;
    private JTextField txtEspecialidad;
    private JButton btnVerificar;
    private JButton btnLimpiar;
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JButton btnGenerar;
    private JButton btnInsertar;
    private JLabel lblIngresoID;
    private JTextField txtIngreso;
    private JButton btnConsultar;

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
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarDoctor();
            }
        });
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarDoctores();
            }
        });
    }

    public void limpiarCampos() {
        // Restablecer a vacío los valores en los campos de texto de la interfaz
        txtID.setText("");
        txtEspecialidad.setText("");
        txtNombre.setText("");
        txtIngreso.setText("");

        // Mostrar un mensaje de notificación indicando que la limpieza fue exitosa
        JOptionPane.showMessageDialog(this, "Limpieza Exitosa");
    }
    public class Doctor {
        private String nombre;        // Nombre del doctor
        private String id;            // Identificador único del doctor
        private String especialidad;  // Especialidad del doctor

        /**
         * Constructor de la clase Doctor.
         *
         * @param nombre       El nombre del doctor.
         * @param id           El identificador único del doctor.
         * @param especialidad La especialidad del doctor.
         */
        public Doctor(String nombre, String id, String especialidad) {
            this.nombre = nombre;
            this.id = id;
            this.especialidad = especialidad;
        }
    }

    public void VerificarCampos() {
        // Obtener los valores ingresados en los campos de la interfaz de usuario
        String nombre = txtNombre.getText();
        String id = txtID.getText();
        String especialidad = txtEspecialidad.getText();

        // Verificar si los campos de nombre, ID y especialidad no están vacíos
        if (!nombre.isEmpty() && !id.isEmpty() && !especialidad.isEmpty()) {
            // Crear una nueva instancia de la clase Doctor con los valores proporcionados
            Doctor newDoc = new Doctor(id, nombre, especialidad);

            // Mostrar un mensaje de notificación indicando que el doctor está verificado y listo para el registro
            JOptionPane.showMessageDialog(this, "Doctor verificado, Listo para el registro: " +
                    newDoc.nombre + ", " + newDoc.id + ", " + newDoc.especialidad);
        } else {
            // Mostrar un mensaje indicando que se deben ingresar todos los datos necesarios para el registro
            JOptionPane.showMessageDialog(this, "Ingrese los datos necesarios para el registro");
        }
    }

    private void InsertarDoctor(Doctor newDoc) {
        // Definir el nombre del archivo
        String Archivo = "Doctores.txt";

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(Archivo, true))) {
            // Escribir la información del nuevo doctor en el archivo
            escritor.write(newDoc.nombre + ", " + newDoc.id + ", " + newDoc.especialidad + "\n");
        } catch (IOException e) {
            // Imprimir la traza de la excepción en caso de error durante la escritura
            e.printStackTrace();
        }
    }

    /**
     * Genera un número aleatorio de cuatro dígitos y lo devuelve.
     *
     * Este método utiliza la clase Random para generar un número aleatorio de cuatro dígitos.
     * Retorna el número generado, que se utilizará para construir un ID único para un nuevo doctor.
     *
     * @return Un número aleatorio de cuatro dígitos.
     */
    private int GenerarNumRandom() {
        Random random = new Random();
        return random.nextInt(9000) + 1000;
    }

    /**
     * Genera un ID único utilizando un número aleatorio y lo muestra en el campo de texto de la interfaz de usuario.
     *
     * Este método llama a GenerarNumRandom para obtener un número aleatorio de cuatro dígitos,
     * lo concatena con la letra "D" y establece el resultado como el texto del campo de texto "txtID".
     */
    private void InsertarIDEnTextPanel() {
        // Generar un número aleatorio de cuatro dígitos
        int numran = GenerarNumRandom();

        // Construir el ID agregando la letra "D" al número aleatorio
        String idRan = "D" + numran;

        // Establecer el ID generado como texto en el campo de texto "txtID"
        txtID.setText(idRan);
    }


    private void VerificarEInsertarEnArchivo() {
        // Obtener los valores ingresados en los campos de la interfaz de usuario
        String nombre = txtNombre.getText();
        String idTxt = txtID.getText();
        String especialidad = txtEspecialidad.getText();

        // Verificar si los campos de nombre, ID y especialidad no están vacíos
        if (!nombre.isEmpty() && !idTxt.isEmpty() && !especialidad.isEmpty()) {
            // Crear una nueva instancia de la clase Doctor con los valores proporcionados
            Doctor newDoc = new Doctor(nombre, idTxt, especialidad);

            // Insertar el nuevo doctor en el archivo utilizando el método InsertarDoctor
            InsertarDoctor(newDoc);

            // Mostrar un mensaje de notificación indicando que el doctor ha sido insertado en el archivo
            JOptionPane.showMessageDialog(this, "Doctor Insertado en el Archivo:\n" +
                    "Nombre: " + newDoc.nombre + "\n" +
                    "ID: " + newDoc.id + "\n" +
                    "Especialidad: " + newDoc.especialidad);
        }
    }

    private Doctor buscarDoctorPorID(String id) {
        // Definir el nombre del archivo
        String Archivo = "Doctores.txt";

        try (BufferedReader lector = new BufferedReader(new FileReader(Archivo))) {
            String linea;

            // Leer el archivo línea por línea
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(", ");

                // Verificar si la línea tiene el formato esperado (tres partes separadas por comas y espacio)
                if (partes.length == 3) {
                    String idDoctor = partes[1].trim();

                    // Verificar si el ID del doctor en la línea coincide con el ID proporcionado
                    if (idDoctor.equals(id)) {
                        // Crear un nuevo objeto Doctor con los datos de la línea y retornarlo
                        return new Doctor(partes[0], idDoctor, partes[2]);
                    }
                }
            }

        } catch (IOException e) {
            // Imprimir la traza de la excepción en caso de error durante la lectura
            e.printStackTrace();
        }

        // Retornar null si no se encuentra un doctor con el ID proporcionado
        return null;
    }
    private void buscarPorID() {
        // Obtener el ID de búsqueda desde el componente de interfaz de usuario
        String idBusqueda = txtIngreso.getText();

        // Verificar si el ID de búsqueda no está vacío
        if (!idBusqueda.isEmpty()) {
            // Llamar a la función auxiliar para buscar el doctor por ID
            Doctor encontrado = buscarDoctorPorID(idBusqueda);

            // Verificar si se encontró el doctor
            if (encontrado != null) {
                // Mostrar un mensaje con la información del doctor encontrado
                JOptionPane.showMessageDialog(this, "Doctor encontrado:\n" +
                        "Nombre: " + encontrado.nombre + "\n" +
                        "ID: " + encontrado.id + "\n" +
                        "Especialidad: " + encontrado.especialidad);
            } else {
                // Mostrar un mensaje si no se encontró un doctor con ese ID
                JOptionPane.showMessageDialog(this, "No se encontró un doctor con ese ID.");
            }
        } else {
            // Mostrar un mensaje si el ID de búsqueda está vacío
            JOptionPane.showMessageDialog(this, "Ingrese un ID para realizar la búsqueda.");
        }
    }
    //El metodo de eliminarDoctor, sirve como su nombre lo dice, Eliminar un doctor de la base de datos que se tiene...
    private void eliminarDoctor() {
        // Obtener el ID ingresado
        String idEliminar = txtIngreso.getText();

        // Verificar si el ID ingresado no está vacío
        if (!idEliminar.isEmpty()) {
            // Definir el nombre del archivo
            String archivo = "Doctores.txt";

            // Inicializar una lista para almacenar las líneas del archivo
            List<String> lineas = new ArrayList<>();

            try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
                String linea; // Variable que lee línea por línea el archivo
                boolean doctorEncontrado = false; // Variable para verificar si el doctor ingresado existe

                // Leer el archivo línea por línea
                while ((linea = lector.readLine()) != null) {
                    String[] partes = linea.split(", ");
                    if (partes.length == 3) {
                        String idDoctor = partes[1].trim();

                        // Verificar si el ID del doctor no coincide con el ID a eliminar
                        if (!idDoctor.equals(idEliminar)) {
                            lineas.add(linea); // Agregar la línea al listado
                        } else {
                            doctorEncontrado = true;
                        }
                    }
                }

                // Verificar si se encontró el doctor con el ID proporcionado
                if (!doctorEncontrado) {
                    JOptionPane.showMessageDialog(this, "No se encontró un doctor con ese ID.");
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo))) {
                // Escribir las líneas actualizadas de vuelta al archivo
                for (String linea : lineas) {
                    escritor.write(linea + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al intentar eliminar al doctor.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Doctor eliminado con éxito.");
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido para eliminar.");
        }
    }

    private void consultarDoctores() {
        // Definir el nombre del archivo
        String archivo = "Doctores.txt";

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            // Construir un StringBuilder para almacenar los datos de los doctores
            StringBuilder datosDoctores = new StringBuilder();
            String linea;

            // Leer el archivo línea por línea y agregar cada línea al StringBuilder
            while ((linea = lector.readLine()) != null) {
                datosDoctores.append(linea).append("\n");
            }

            // Verificar si hay datos de doctores almacenados
            if (datosDoctores.length() > 0) {
                // Mostrar un mensaje de notificación con los datos de los doctores
                JOptionPane.showMessageDialog(this, "Datos de los Doctores:\n" + datosDoctores.toString());
            } else {
                // Mostrar un mensaje indicando que no hay datos de doctores almacenados
                JOptionPane.showMessageDialog(this, "No hay datos de doctores almacenados.");
            }
        } catch (IOException e) {
            // Imprimir la traza de la excepción en caso de error durante la lectura
            e.printStackTrace();

            // Mostrar un mensaje de error en caso de problemas durante la consulta de datos de doctores
            JOptionPane.showMessageDialog(this, "Error al intentar consultar los datos de los doctores.");
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
