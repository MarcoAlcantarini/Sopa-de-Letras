package sopadeletras.estructuras;

/**
 * Lista enlazada simple para almacenar palabras de forma única.
 * Permite agregar, eliminar, buscar y obtener un arreglo de las palabras almacenadas.
 */
public class ListaPalabras {
    /** Nodo cabeza de la lista. */
    private NodoPalabra cabeza;

    /**
     * Constructor que inicializa la lista vacía.
     */
    public ListaPalabras() {
        this.cabeza = null;
    }

    /**
     * Agrega una palabra a la lista si no existe ya.
     * La palabra se añade al inicio de la lista.
     *
     * @param palabra Palabra a agregar.
     */
    public void agregar(String palabra) {
        if (!contiene(palabra)) {
            NodoPalabra nuevo = new NodoPalabra(palabra);
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
        }
    }

    /**
     * Verifica si la lista contiene una palabra (sin distinguir mayúsculas/minúsculas).
     *
     * @param palabra Palabra a buscar.
     * @return true si la palabra existe en la lista, false si no.
     */
    public boolean contiene(String palabra) {
        NodoPalabra actual = cabeza;
        while (actual != null) {
            if (actual.palabra.equalsIgnoreCase(palabra)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    /**
     * Elimina la primera ocurrencia de una palabra en la lista.
     *
     * @param palabra Palabra a eliminar.
     * @return true si se eliminó la palabra, false si no se encontró.
     */
    public boolean eliminar(String palabra) {
        NodoPalabra actual = cabeza;
        NodoPalabra anterior = null;
        while (actual != null) {
            if (actual.palabra.equalsIgnoreCase(palabra)) {
                if (anterior == null) {
                    cabeza = actual.siguiente;
                } else {
                    anterior.siguiente = actual.siguiente;
                }
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
        return false;
    }

    /**
     * Imprime todas las palabras almacenadas en la lista en consola.
     */
    public void imprimir() {
        NodoPalabra actual = cabeza;
        while (actual != null) {
            System.out.println(actual.palabra);
            actual = actual.siguiente;
        }
    }

    /**
     * Convierte la lista de palabras en un arreglo de strings.
     *
     * @return Arreglo con las palabras almacenadas en la lista.
     */
    public String[] aArreglo() {
        int conteo = contar();
        String[] arreglo = new String[conteo];
        NodoPalabra actual = cabeza;
        int i = 0;
        while (actual != null) {
            arreglo[i++] = actual.palabra;
            actual = actual.siguiente;
        }
        return arreglo;
    }

    /**
     * Cuenta la cantidad de palabras almacenadas en la lista.
     *
     * @return Número de palabras en la lista.
     */
    public int contar() {
        int contador = 0;
        NodoPalabra actual = cabeza;
        while (actual != null) {
            contador++;
            actual = actual.siguiente;
        }
        return contador;
    }
}
