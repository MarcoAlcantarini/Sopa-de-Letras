package sopadeletras.estructuras;

/**
 * Nodo simple para almacenar una palabra en una lista enlazada.
 * Contiene la palabra y referencia al siguiente nodo en la lista.
 */
public class NodoPalabra {
    /** Palabra almacenada en el nodo. */
    public String palabra;

    /** Referencia al siguiente nodo en la lista. */
    public NodoPalabra siguiente;

    /**
     * Construye un nodo con la palabra dada y sin siguiente nodo.
     *
     * @param palabra Palabra a almacenar en el nodo.
     */
    public NodoPalabra(String palabra) {
        this.palabra = palabra;
        this.siguiente = null;
    }
}
