import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Random;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentanaCitas extends JFrame {
    private JTextField txtID;
    private JTextField txtFecha;
    private JTextField txtHora;
    private JTextField txtPaciente;
    private JButton btnBuscarPaciente;
    private JTextField txtDatosPaciente;
    private JButton btnGenerar;
    private JButton btnLimpiar;
    private JButton btnCreatCita;
    private JTextArea txtAreaObservaciones;
    public JPanel MiPanel;

    public VentanaCitas() {
        btnGenerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InsertarIDEnTextPanel();
            }
        });
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        btnCreatCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VerificarEInsertarEnArchivo();
            }
        });
        btnBuscarPaciente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtenemos el ID del campo txtPaciente
                String idPaciente = txtPaciente.getText();

                // Verificamos si se proporcionó un ID
                if (!idPaciente.isEmpty()) {
                    // Llamamos al método buscarPacientePorID con el ID proporcionado
                    buscarPacientePorID(idPaciente);
                } else {
                    JOptionPane.showMessageDialog(VentanaCitas.this, "Por favor, ingrese un ID de paciente", "Error", JOptionPane.ERROR_MESSAGE);
                    txtDatosPaciente.setText("");
                }
            }
        });
    }

    public class Citas {
        // Atributos de la clase Citas
        private String paciente;
        private String id;
        private String fecha;
        private String hora;
        private String datos;
        private String observaciones;

        /**
         * Constructor de la clase Citas que inicializa sus atributos con los valores proporcionados.
         *
         * @param paciente      El nombre del paciente asociado a la cita.
         * @param id            El identificador único de la cita.
         * @param fecha         La fecha de la cita en formato de cadena.
         * @param hora          La hora de la cita en formato de cadena.
         * @param datos         Información adicional sobre la cita o el paciente.
         * @param observaciones Observaciones asociadas a la cita médica.
         */
        public Citas(String paciente, String id, String fecha, String hora, String datos, String observaciones) {
            // Inicializar los atributos con los valores proporcionados en el constructor
            this.paciente = paciente;
            this.id = id;
            this.fecha = fecha;
            this.hora = hora;
            this.datos = datos;
            this.observaciones = observaciones;
        }
    }
    public void limpiarCampos() {
        // Establecer el texto de los campos de texto y el área de observaciones como una cadena vacía
        txtID.setText("");
        txtFecha.setText("");
        txtHora.setText("");
        txtPaciente.setText("");
        txtAreaObservaciones.setText("");
        txtDatosPaciente.setText("");

        // Mostrar un mensaje de notificación indicando que la limpieza fue exitosa
        JOptionPane.showMessageDialog(this, "Limpieza Exitosa");
    }
    private void VerificarEInsertarEnArchivo() {
        // Obtener los datos de la cita desde los campos de texto
        String idTxt = txtID.getText();
        String fecha = txtFecha.getText();
        String hora = txtHora.getText();
        String paciente = txtPaciente.getText();
        String datos = txtDatosPaciente.getText();
        String observaciones = txtAreaObservaciones.getText();

        // Verificar que las fechas y horas tengan el formato correcto y sean válidas
        if (verificarFormatoFecha(fecha) && verificarFormatoHora(hora) &&
                !idTxt.isEmpty() && !paciente.isEmpty() && !datos.isEmpty() && !observaciones.isEmpty()) {
            // Crear un objeto de la clase Citas con los datos proporcionados
            Citas newCita = new Citas(paciente, idTxt, fecha, hora, datos, observaciones);

            // Insertar la cita en el archivo "Citas.txt"
            InsertarCita(newCita);

            // Mostrar un mensaje indicando que la cita se insertó correctamente
            JOptionPane.showMessageDialog(this, "Cita Insertada en el Archivo:\n" +
                    "Paciente: " + newCita.paciente + "\n" +
                    "ID: " + newCita.id + "\n" +
                    "Fecha: " + newCita.fecha + "\n" +
                    "Hora: " + newCita.hora + "\n" +
                    "Datos: " + newCita.datos + "\n" +
                    "Observaciones: " + newCita.observaciones);
        } else {
            // Mostrar un mensaje de error si los datos no son válidos o algún campo está vacío
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos correctamente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarPacientePorID(String id) {
        // Definir el nombre del archivo de pacientes
        String archivoPacientes = "Pacientes.txt";

        // Bandera para indicar si se encontró el paciente
        boolean pacienteEncontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(archivoPacientes))) {
            String line;

            // Leer el archivo de pacientes línea por línea
            while ((line = br.readLine()) != null) {
                // Asumiendo que las líneas en el archivo tienen el formato "Nombre, ID, Edad, Teléfono, Género"
                String[] partes = line.split(", ");

                // Verificar si la línea contiene el ID buscado
                if (partes.length >= 2) {
                    String idEnArchivo = partes[1].trim();

                    // Se encontró el paciente con el ID especificado
                    if (idEnArchivo.equals(id)) {
                        // Formatear los datos del paciente
                        String datosPaciente = obtenerDatosPacienteDesdeLinea(line);

                        // Mostrar los datos del paciente en la interfaz
                        txtDatosPaciente.setText(datosPaciente);
                        JOptionPane.showMessageDialog(this, datosPaciente);

                        // Establecer la bandera de paciente encontrado y salir del bucle
                        pacienteEncontrado = true;
                        break;
                    }
                }
            }

            // Verificar si se encontró el paciente
            if (!pacienteEncontrado) {
                JOptionPane.showMessageDialog(this, "Paciente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);

                // Limpiar el campo de texto de datos del paciente
                txtDatosPaciente.setText("");
            }
        } catch (IOException e) {
            // Imprimir la traza de la excepción en caso de error durante la lectura
            e.printStackTrace();

            // Mostrar un mensaje de error en caso de problemas durante la búsqueda del paciente
            JOptionPane.showMessageDialog(this, "Error al buscar paciente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private String obtenerDatosPacienteDesdeLinea(String linea) {
        // Asumiendo que las líneas en el archivo tienen el formato "Nombre, ID, Edad, Teléfono, Género"
        String[] partes = linea.split(", ");

        // Extraer cada parte de la línea
        String nombre = partes[0].trim();
        String id = partes[1].trim();
        String edad = partes[2].trim();
        String telefono = partes[3].trim();
        String genero = partes[4].trim();

        // Formatear los datos según tus necesidades
        return "Nombre: " + nombre + "\nID: " + id + "\nEdad: " + edad + "\nTeléfono: " + telefono + "\nGénero: " + genero;
    }

    private boolean verificarFormatoFecha(String fecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        try {
            // Intentar parsear la cadena como fecha
            Date parsedDate = dateFormat.parse(fecha);
            return true;
        } catch (ParseException e) {
            // Capturar excepción en caso de error durante el parseo
            return false;
        }
    }

    private boolean verificarFormatoHora(String hora) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        timeFormat.setLenient(false);

        try {
            // Intentar parsear la cadena como hora
            Date parsedTime = timeFormat.parse(hora);
            return true;
        } catch (ParseException e) {
            // Capturar excepción en caso de error durante el parseo
            return false;
        }
    }
    private void InsertarCita(Citas newCita) {
        // Definir el nombre del archivo
        String Archivo = "Citas.txt";

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(Archivo, true))) {
            // Escribir la información de la nueva cita en el archivo
            escritor.write(newCita.paciente + ", " + newCita.id + ", " + newCita.fecha + ", "
                    + newCita.hora + ", " + newCita.datos + ", " + newCita.observaciones + "\n");
        } catch (IOException e) {
            // Imprimir la traza de la excepción en caso de error durante la escritura
            e.printStackTrace();
        }
    }

    /**
     * Genera un número aleatorio de cuatro dígitos y lo devuelve.
     *
     * Este método utiliza la clase Random para generar un número aleatorio de cuatro dígitos,
     * que se utiliza para construir un ID único para algún propósito específico.
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
     * lo concatena con la letra "C" y establece el resultado como el texto del campo de texto "txtID".
     */
    private void InsertarIDEnTextPanel() {
        // Generar un número aleatorio de cuatro dígitos
        int numran = GenerarNumRandom();

        // Construir el ID agregando la letra "C" al número aleatorio
        String idRan = "C" + numran;

        // Establecer el ID generado como texto en el campo de texto "txtID"
        txtID.setText(idRan);
    }

    public static void main(String[] args) {
        VentanaCitas citas = new VentanaCitas();
        citas.setContentPane(citas.MiPanel);
        citas.setSize(800,300);
        citas.setDefaultCloseOperation(EXIT_ON_CLOSE);
        citas.setVisible(true);
    }
}
