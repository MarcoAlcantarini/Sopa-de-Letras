package sopadeletras.visualizacion;

import java.util.List;
import java.util.Map;
import sopadeletras.modelo.NodoGrafo;

/**
 * Clase que encapsula el resultado de una búsqueda BFS en el grafo del tablero.
 * Contiene información sobre si se encontró la palabra, el recorrido realizado,
 * el mapa de padres para reconstruir el árbol, y el nodo inicial de la búsqueda.
 */
public class ResultadoBFS {
    /** Indica si la palabra fue encontrada. */
    private boolean encontrado;

    /** Lista de nodos que forman el recorrido BFS hasta encontrar la palabra. */
    private List<NodoGrafo> recorrido;

    /** Mapa que relaciona cada nodo con su nodo padre en el recorrido BFS. */
    private Map<NodoGrafo, NodoGrafo> padres;

    /** Nodo inicial desde donde comenzó la búsqueda BFS. */
    private NodoGrafo inicio;

    /**
     * Construye un objeto ResultadoBFS con toda la información de la búsqueda.
     *
     * @param encontrado true si la palabra fue encontrada, false en caso contrario.
     * @param recorrido Lista de nodos del recorrido BFS.
     * @param padres Mapa de nodos a sus padres para reconstruir el árbol BFS.
     * @param inicio Nodo inicial de la búsqueda.
     */
    public ResultadoBFS(boolean encontrado, List<NodoGrafo> recorrido, 
                       Map<NodoGrafo, NodoGrafo> padres, NodoGrafo inicio) {
        this.encontrado = encontrado;
        this.recorrido = recorrido;
        this.padres = padres;
        this.inicio = inicio;
    }

    /**
     * Versión simplificada del constructor sin el nodo inicial.
     *
     * @param encontrado true si la palabra fue encontrada.
     * @param recorrido Lista de nodos del recorrido.
     * @param padres Mapa de relaciones padre-hijo.
     */
    public ResultadoBFS(boolean encontrado, List<NodoGrafo> recorrido, 
                       Map<NodoGrafo, NodoGrafo> padres) {
        this(encontrado, recorrido, padres, null);
    }

    /**
     * Indica si la palabra fue encontrada en la búsqueda.
     *
     * @return true si la palabra fue encontrada, false si no.
     */
    public boolean fueEncontrado() {
        return encontrado;
    }

    /**
     * Obtiene la lista de nodos que forman el recorrido BFS.
     *
     * @return Lista de nodos recorrido.
     */
    public List<NodoGrafo> getRecorrido() {
        return recorrido;
    }

    /**
     * Obtiene el mapa de nodos y sus padres en el recorrido BFS.
     *
     * @return Mapa de relaciones padre-hijo.
     */
    public Map<NodoGrafo, NodoGrafo> getPadres() {
        return padres;
    }

    /**
     * Obtiene el nodo inicial de la búsqueda BFS.
     *
     * @return Nodo inicial.
     */
    public NodoGrafo getInicio() {
        return inicio;
    }
}