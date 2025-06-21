package sopadeletras.logica;

import java.util.*;
import sopadeletras.modelo.Tablero;
import sopadeletras.modelo.NodoGrafo;
import sopadeletras.visualizacion.ResultadoBFS;

/**
 * Controlador que implementa los algoritmos de búsqueda en el tablero de la sopa de letras.
 * Incluye búsqueda DFS y BFS con y sin visualización.
 */
public class ControladorBusqueda {
    /** Tablero que contiene la matriz de nodos con letras. */
    private Tablero tablero;

    /**
     * Construye un controlador con el tablero dado.
     * @param tablero el tablero donde se realizará la búsqueda.
     */
    public ControladorBusqueda(Tablero tablero) {
        this.tablero = tablero;
    }

    /**
     * Busca una palabra en el tablero usando búsqueda DFS.
     *
     * @param palabra La palabra a buscar.
     * @return true si la palabra se encuentra, false en caso contrario.
     */
    public boolean buscarPalabraDFS(String palabra) {
        if (palabra.length() < 3) return false;

        NodoGrafo[][] matriz = tablero.getMatriz();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                if (matriz[i][j].getLetra() == palabra.charAt(0)) {
                    Set<NodoGrafo> visitados = new HashSet<>();
                    if (dfs(matriz[i][j], palabra, 0, visitados)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Busca una palabra en el tablero usando búsqueda DFS y retorna el camino encontrado.
     *
     * @param palabra La palabra a buscar.
     * @return Lista con los nodos que forman la palabra, o null si no se encuentra.
     */
    public List<NodoGrafo> buscarPalabraDFSConCamino(String palabra) {
        NodoGrafo[][] matriz = tablero.getMatriz();

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                if (matriz[i][j].getLetra() == palabra.charAt(0)) {
                    List<NodoGrafo> camino = new ArrayList<>();
                    Set<NodoGrafo> visitados = new HashSet<>();
                    if (dfs(matriz[i][j], palabra, 0, visitados, camino)) {
                        return camino;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Implementación recursiva de DFS que verifica la existencia de la palabra.
     *
     * @param actual Nodo actual en el recorrido.
     * @param palabra Palabra que se busca.
     * @param indice Índice actual dentro de la palabra.
     * @param visitados Conjunto de nodos visitados para evitar ciclos.
     * @return true si la palabra es encontrada desde el nodo actual.
     */
    private boolean dfs(NodoGrafo actual, String palabra, int indice, Set<NodoGrafo> visitados) {
        if (indice >= palabra.length()) return true;
        if (actual.getLetra() != palabra.charAt(indice)) return false;

        visitados.add(actual);

        if (indice == palabra.length() - 1) return true;

        for (NodoGrafo vecino : actual.getAdyacentes()) {
            if (!visitados.contains(vecino)) {
                if (dfs(vecino, palabra, indice + 1, visitados)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Implementación recursiva de DFS que construye el camino completo.
     *
     * @param actual Nodo actual en el recorrido.
     * @param palabra Palabra que se busca.
     * @param indice Índice actual dentro de la palabra.
     * @param visitados Conjunto de nodos visitados para evitar ciclos.
     * @param camino Lista que almacena el camino recorrido.
     * @return true si la palabra es encontrada desde el nodo actual.
     */
    private boolean dfs(NodoGrafo actual, String palabra, int indice, Set<NodoGrafo> visitados, List<NodoGrafo> camino) {
        if (indice >= palabra.length()) return true;
        if (actual.getLetra() != palabra.charAt(indice)) return false;

        visitados.add(actual);
        camino.add(actual);

        if (indice == palabra.length() - 1) return true;

        for (NodoGrafo vecino : actual.getAdyacentes()) {
            if (!visitados.contains(vecino)) {
                if (dfs(vecino, palabra, indice + 1, visitados, camino)) {
                    return true;
                }
            }
        }

        camino.remove(camino.size() - 1);
        return false;
    }

    /**
     * Busca una palabra en el tablero usando búsqueda BFS.
     *
     * @param palabra La palabra a buscar.
     * @return true si la palabra se encuentra, false en caso contrario.
     */
    public boolean buscarPalabraBFS(String palabra) {
        if (palabra.length() < 3) return false;

        NodoGrafo[][] matriz = tablero.getMatriz();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                if (matriz[i][j].getLetra() == palabra.charAt(0)) {
                    if (bfs(matriz[i][j], palabra)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Implementación de BFS que verifica la existencia de la palabra.
     *
     * @param inicio Nodo inicial para la búsqueda.
     * @param palabra Palabra que se busca.
     * @return true si la palabra es encontrada.
     */
    private boolean bfs(NodoGrafo inicio, String palabra) {
        Queue<NodoEstado> cola = new LinkedList<>();
        cola.add(new NodoEstado(inicio, 0, new HashSet<>()));

        while (!cola.isEmpty()) {
            NodoEstado estado = cola.poll();
            NodoGrafo actual = estado.nodo;
            int indice = estado.indice;
            Set<NodoGrafo> visitados = estado.visitados;

            if (actual.getLetra() != palabra.charAt(indice)) continue;

            visitados.add(actual);

            if (indice == palabra.length() - 1) return true;

            for (NodoGrafo vecino : actual.getAdyacentes()) {
                if (!visitados.contains(vecino)) {
                    Set<NodoGrafo> copia = new HashSet<>(visitados);
                    cola.add(new NodoEstado(vecino, indice + 1, copia));
                }
            }
        }
        return false;
    }

    /**
     * Clase interna para representar el estado en la búsqueda BFS.
     */
    private static class NodoEstado {
        /** Nodo actual en la búsqueda. */
        NodoGrafo nodo;
        /** Índice actual en la palabra buscada. */
        int indice;
        /** Conjunto de nodos visitados. */
        Set<NodoGrafo> visitados;

        /**
         * Constructor de NodoEstado.
         * @param nodo Nodo actual.
         * @param indice Índice de la palabra.
         * @param visitados Conjunto de nodos visitados.
         */
        NodoEstado(NodoGrafo nodo, int indice, Set<NodoGrafo> visitados) {
            this.nodo = nodo;
            this.indice = indice;
            this.visitados = visitados;
        }
    }

    /**
     * Busca una palabra usando BFS y devuelve un objeto ResultadoBFS con el camino para visualización.
     *
     * @param palabra La palabra a buscar.
     * @return ResultadoBFS con información del recorrido o null si no se encuentra.
     */
    public ResultadoBFS buscarConVisualizacion(String palabra) {
        if (palabra.length() < 3) return null;

        NodoGrafo[][] matriz = tablero.getMatriz();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                if (matriz[i][j].getLetra() == palabra.charAt(0)) {
                    ResultadoBFS resultado = bfsVisual(matriz[i][j], palabra);
                    if (resultado.fueEncontrado()) {
                        System.out.println("Palabra '" + palabra + "' encontrada con visualización.");
                        return resultado;
                    }
                }
            }
        }
        System.out.println("Palabra '" + palabra + "' NO encontrada con visualización.");
        return null;
    }

    /**
     * Implementación de BFS que devuelve un ResultadoBFS con el camino recorrido y padres para visualización.
     *
     * @param inicio Nodo inicial.
     * @param palabra Palabra a buscar.
     * @
