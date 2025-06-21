package sopadeletras.visualizacion;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import sopadeletras.modelo.NodoGrafo;
import java.util.List;

/**
 * Panel para visualizar el recorrido BFS realizado sobre el tablero de la sopa de letras.
 * Dibuja los nodos visitados y las conexiones entre ellos según el resultado de la búsqueda.
 */
public class PanelBFST extends JPanel {
    /** Resultado de la búsqueda BFS que se va a visualizar. */
    private ResultadoBFS resultado;

    /**
     * Establece el resultado de la búsqueda para visualizarlo.
     * Llama a repaint() para refrescar el panel.
     *
     * @param resultado ResultadoBFS que contiene el recorrido y estructura para mostrar.
     */
    public void setResultado(ResultadoBFS resultado) {
        this.resultado = resultado;
        repaint();
    }

    /**
     * Dibuja el recorrido BFS en el panel.
     * Los nodos se posicionan según su fila y columna escaladas al tamaño del panel.
     * Se dibujan líneas azules entre nodos y sus padres para mostrar la estructura del recorrido.
     * Los nodos se dibujan como círculos cyan con la letra en negro centrada.
     *
     * @param g Objeto Graphics para dibujar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (resultado == null || !resultado.fueEncontrado()) {
            // No hay nada que dibujar
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g.setFont(new Font("Arial", Font.BOLD, 16));

        List<NodoGrafo> recorrido = resultado.getRecorrido();
        Map<NodoGrafo, NodoGrafo> padres = resultado.getPadres();

        int diametro = 40;

        // Calcular máximo de fila y columna para escala
        int maxCol = 0, maxFila = 0;
        for (NodoGrafo nodo : recorrido) {
            if (nodo.getColumna() > maxCol) maxCol = nodo.getColumna();
            if (nodo.getFila() > maxFila) maxFila = nodo.getFila();
        }

        int anchoPanel = getWidth() - diametro;
        int altoPanel = getHeight() - diametro;

        double escalaX = (double) anchoPanel / (maxCol + 1);
        double escalaY = (double) altoPanel / (maxFila + 1);

        Map<NodoGrafo, Point> posiciones = new HashMap<>();

        // Asignar posición en panel según fila y columna del nodo
        for (NodoGrafo nodo : recorrido) {
            int x = (int) (nodo.getColumna() * escalaX + diametro / 2);
            int y = (int) (nodo.getFila() * escalaY + diametro / 2);
            posiciones.put(nodo, new Point(x, y));
        }

        // Dibujar conexiones entre nodos y sus padres
        g2.setColor(Color.BLUE);
        for (int i = 1; i < recorrido.size(); i++) {
            NodoGrafo actual = recorrido.get(i);
            NodoGrafo padre = padres.get(actual);
            if (padre != null) {
                Point pActual = posiciones.get(actual);
                Point pPadre = posiciones.get(padre);
                g2.drawLine(pPadre.x, pPadre.y, pActual.x, pActual.y);
            }
        }

        // Dibujar nodos con letras
        for (NodoGrafo nodo : recorrido) {
            Point p = posiciones.get(nodo);
            g2.setColor(Color.CYAN);
            g2.fillOval(p.x - diametro / 2, p.y - diametro / 2, diametro, diametro);
            g2.setColor(Color.BLACK);
            g2.drawOval(p.x - diametro / 2, p.y - diametro / 2, diametro, diametro);

            String letra = String.valueOf(nodo.getLetra());
            FontMetrics fm = g2.getFontMetrics();
            int anchoLetra = fm.stringWidth(letra);
            int altoLetra = fm.getAscent();
            g2.drawString(letra, p.x - anchoLetra / 2, p.y + altoLetra / 4);
        }
    }
}
