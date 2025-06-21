package sopadeletras.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un nodo dentro del grafo del tablero de sopa de letras.
 * Cada nodo contiene una letra y referencias a sus nodos adyacentes.
 */
public class NodoGrafo {
    /** Letra almacenada en el nodo. */
    private char letra;

    /** Fila donde se encuentra el nodo en el tablero. */
    private int fila;

    /** Columna donde se encuentra el nodo en el tablero. */
    private int columna;

    /** Lista de nodos adyacentes conectados a este nodo. */
    private List<NodoGrafo> adyacentes;

    /**
     * Construye un nodo con la letra y posición dada.
     *
     * @param letra Letra contenida en el nodo.
     * @param fila Fila del nodo en el tablero.
     * @param columna Columna del nodo en el tablero.
     */
    public NodoGrafo(char letra, int fila, int columna) {
        this.letra = letra;
        this.fila = fila;
        this.columna = columna;
        this.adyacentes = new ArrayList<>();
    }

    /**
     * Obtiene la letra almacenada en el nodo.
     *
     * @return Letra del nodo.
     */
    public char getLetra() {
        return letra;
    }

    /**
     * Obtiene la fila del nodo en el tablero.
     *
     * @return Fila del nodo.
     */
    public int getFila() {
        return fila;
    }

    /**
     * Obtiene la columna del nodo en el tablero.
     *
     * @return Columna del nodo.
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Obtiene la lista de nodos adyacentes conectados a este nodo.
     *
     * @return Lista de nodos adyacentes.
     */
    public List<NodoGrafo> getAdyacentes() {
        return adyacentes;
    }

    /**
     * Agrega un nodo adyacente a la lista, si no está ya presente.
     *
     * @param nodo Nodo adyacente a agregar.
     */
    public void agregarAdyacente(NodoGrafo nodo) {
        if (!adyacentes.contains(nodo)) {
            adyacentes.add(nodo);
        }
    }

    /**
     * Representación en cadena del nodo, mostrando la letra y su posición.
     *
     * @return Cadena con la letra y posición del nodo.
     */
    @Override
    public String toString() {
        return letra + "(" + fila + "," + columna + ")";
    }

    /**
     * Compara si dos nodos son iguales basándose en su posición (fila y columna).
     *
     * @param obj Objeto a comparar.
     * @return true si el objeto es un NodoGrafo con la misma fila y columna.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NodoGrafo) {
            NodoGrafo otro = (NodoGrafo) obj;
            return this.fila == otro.fila && this.columna == otro.columna;
        }
        return false;
    }

    /**
     * Genera el código hash basado en la posición del nodo.
     *
     * @return Código hash del nodo.
     */
    @Override
    public int hashCode() {
        return fila * 10 + columna;
    }
}
