package sopadeletras.modelo;

/**
 * Clase que representa el tablero de la sopa de letras como una matriz de nodos (grafo).
 * Cada nodo contiene una letra y referencias a sus nodos adyacentes (hasta 8 direcciones).
 */
public class Tablero {
    /** Matriz de nodos que forman el tablero. */
    private NodoGrafo[][] matriz;

    /** Dimensión del tablero (número de filas y columnas). */
    private int dimension;

    /**
     * Construye un tablero a partir de una matriz de letras.
     * Crea los nodos correspondientes y los conecta con sus adyacentes.
     *
     * @param letras Matriz de caracteres que representan el tablero.
     */
    public Tablero(char[][] letras) {
        this.dimension = letras.length;
        this.matriz = new NodoGrafo[dimension][dimension];
        construirNodos(letras);
        conectarAdyacentes();
    }

    /**
     * Construye los nodos grafo para cada letra en la matriz de entrada.
     *
     * @param letras Matriz de caracteres que representan las letras del tablero.
     */
    private void construirNodos(char[][] letras) {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                matriz[i][j] = new NodoGrafo(letras[i][j], i, j);
            }
        }
    }

    /**
     * Conecta cada nodo con sus adyacentes válidos (hasta 8 direcciones).
     * Se consideran vecinos horizontales, verticales y diagonales.
     */
    private void conectarAdyacentes() {
        // Direcciones relativas para los 8 vecinos posibles
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                for (int dir = 0; dir < 8; dir++) {
                    int ni = i + dx[dir];
                    int nj = j + dy[dir];
                    if (esValido(ni, nj)) {
                        matriz[i][j].agregarAdyacente(matriz[ni][nj]);
                    }
                }
            }
        }
    }

    /**
     * Verifica si las coordenadas dadas están dentro de los límites válidos del tablero.
     *
     * @param i Fila.
     * @param j Columna.
     * @return true si la posición es válida dentro del tablero, false de lo contrario.
     */
    private boolean esValido(int i, int j) {
        return i >= 0 && j >= 0 && i < dimension && j < dimension;
    }

    /**
     * Obtiene la matriz completa de nodos del tablero.
     *
     * @return Matriz bidimensional de objetos NodoGrafo.
     */
    public NodoGrafo[][] getMatriz() {
        return matriz;
    }

    /**
     * Obtiene un nodo específico dado su fila y columna.
     *
     * @param fila Fila del nodo.
     * @param columna Columna del nodo.
     * @return NodoGrafo ubicado en la fila y columna indicadas.
     */
    public NodoGrafo getNodo(int fila, int columna) {
        return matriz[fila][columna];
    }
}
