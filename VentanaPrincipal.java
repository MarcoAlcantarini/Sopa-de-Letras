package sopadeletras.vista;

import sopadeletras.estructuras.ListaPalabras;
import sopadeletras.logica.ControladorBusqueda;
import sopadeletras.modelo.NodoGrafo;
import sopadeletras.modelo.Tablero;
import sopadeletras.visualizacion.PanelBFST;
import sopadeletras.visualizacion.ResultadoBFS;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Ventana principal de la aplicación Sopa de Letras.
 * Permite cargar archivos, buscar palabras usando DFS o BFS,
 * y visualizar resultados y recorridos BFS.
 */
public class VentanaPrincipal extends JFrame {
    private JTextArea areaTablero;
    private JTextArea areaDiccionario;
    private JTextArea areaResultados;
    private JTextField campoPalabra;
    private JRadioButton radioDFS, radioBFS;
    private JButton botonBuscarTodas, botonBuscarUna, botonCargar;
    private PanelBFST panelBFST;

    private ListaPalabras diccionario;
    private Tablero tablero;

    /**
     * Constructor que inicializa la interfaz gráfica.
     */
    public VentanaPrincipal() {
        setTitle("Sopa de Letras");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        // Panel superior con botón para cargar archivo
        JPanel panelSuperior = new JPanel();
        botonCargar = new JButton("Cargar archivo");
        panelSuperior.add(botonCargar);
        add(panelSuperior, BorderLayout.NORTH);

        // Panel para visualizar recorrido BFS
        panelBFST = new PanelBFST();
        panelBFST.setPreferredSize(new Dimension(600, 200));
        add(panelBFST, BorderLayout.EAST);

        // Panel central con áreas de texto para tablero y diccionario
        JPanel panelCentro = new JPanel(new GridLayout(1, 2));
        areaTablero = new JTextArea(8, 20);
        areaDiccionario = new JTextArea(8, 20);
        panelCentro.add(new JScrollPane(areaTablero));
        panelCentro.add(new JScrollPane(areaDiccionario));
        add(panelCentro, BorderLayout.CENTER);

        // Panel inferior con controles de búsqueda y resultados
        JPanel panelInferior = new JPanel(new GridLayout(4, 1));

        campoPalabra = new JTextField(20);
        botonBuscarUna = new JButton("Buscar palabra específica");

        JPanel panelBusqueda = new JPanel();
        panelBusqueda.add(new JLabel("Palabra:"));
        panelBusqueda.add(campoPalabra);
        panelBusqueda.add(botonBuscarUna);
        panelInferior.add(panelBusqueda);

        radioDFS = new JRadioButton("DFS", true);
        radioBFS = new JRadioButton("BFS");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(radioDFS);
        grupo.add(radioBFS);
        JPanel panelMetodos = new JPanel();
        panelMetodos.add(new JLabel("Método:"));
        panelMetodos.add(radioDFS);
        panelMetodos.add(radioBFS);
        panelInferior.add(panelMetodos);

        botonBuscarTodas = new JButton("Buscar todas las palabras");
        panelInferior.add(botonBuscarTodas);

        areaResultados = new JTextArea(5, 50);
        areaResultados.setEditable(false);
        panelInferior.add(new JScrollPane(areaResultados));

        add(panelInferior, BorderLayout.SOUTH);

        agregarEventos();
    }

    /**
     * Agrega los listeners de eventos a los componentes.
     */
    private void agregarEventos() {
        botonCargar.addActionListener(e -> cargarArchivo());

        botonBuscarTodas.addActionListener(e -> {
            if (tablero == null || diccionario == null) return;
            ControladorBusqueda buscador = new ControladorBusqueda(tablero);
            areaResultados.setText("");
            for (String palabra : diccionario.aArreglo()) {
                boolean encontrada = radioDFS.isSelected()
                    ? buscador.buscarPalabraDFS(palabra)
                    : buscador.buscarPalabraBFS(palabra);
                if (encontrada) {
                    areaResultados.append(palabra + " encontrada\n");
                }
            }
        });

        botonBuscarUna.addActionListener(e -> {
            String palabra = campoPalabra.getText().trim().toUpperCase();
            if (palabra.length() < 3 || tablero == null) return;
            ControladorBusqueda buscador = new ControladorBusqueda(tablero);
            ResultadoBFS resultado = buscador.buscarConVisualizacion(palabra);
            if (resultado != null && resultado.fueEncontrado()) {
                areaResultados.setText("La palabra '" + palabra + "' fue encontrada.");
                diccionario.agregar(palabra);
                actualizarDiccionario();
                panelBFST.setResultado(resultado);
            } else {
                areaResultados.setText("La palabra '" + palabra + "' NO fue encontrada.");
                panelBFST.setResultado(null);
            }
        });
    }

    /**
     * Carga un archivo de texto con el formato esperado para sopa de letras y diccionario.
     * Actualiza la vista con los datos cargados.
     */
    private void cargarArchivo() {
        JFileChooser chooser = new JFileChooser();
        int resultado = chooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                diccionario = new ListaPalabras();
                char[][] letras = new char[4][4];
                String linea;
                boolean enDic = false, enTab = false;
                int fila = 0;

                while ((linea = br.readLine()) != null) {
                    if (linea.equalsIgnoreCase("dic")) {
                        enDic = true;
                    } else if (linea.equalsIgnoreCase("/dic")) {
                        enDic = false;
                    } else if (linea.equalsIgnoreCase("tab")) {
                        enTab = true;
                    } else if (linea.equalsIgnoreCase("/tab")) {
                        enTab = false;
                    } else if (enDic) {
                        diccionario.agregar(linea.trim().toUpperCase());
                    } else if (enTab) {
                        String[] letrasLinea = linea.split(",");
                        for (int i = 0; i < 4; i++) {
                            letras[fila][i] = letrasLinea[i].trim().charAt(0);
                        }
                        fila++;
                    }
                }

                tablero = new Tablero(letras);
                actualizarTablero(letras);
                actualizarDiccionario();
                areaResultados.setText("Archivo cargado correctamente.");

            } catch (Exception ex) {
                areaResultados.setText("Error al leer el archivo.");
            }
        }
    }

    /**
     * Actualiza el área de texto que muestra el tablero con las letras cargadas.
     *
     * @param letras Matriz de letras del tablero.
     */
    private void actualizarTablero(char[][] letras) {
        areaTablero.setText("");
        for (int i = 0; i < letras.length; i++) {
            for (int j = 0; j < letras[i].length; j++) {
                areaTablero.append(letras[i][j] + " ");
            }
            areaTablero.append("\n");
        }
    }

    /**
     * Actualiza el área de texto que muestra el diccionario con las palabras almacenadas.
     */
    private void actualizarDiccionario() {
        areaDiccionario.setText("");
        for (String palabra : diccionario.aArreglo()) {
            areaDiccionario.append(palabra + "\n");
        }
    }

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}

