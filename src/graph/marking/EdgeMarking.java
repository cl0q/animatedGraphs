package graph.marking;

import graph.Edge;

import java.awt.*;
import java.util.Set;

/**
 * Abstrakte Klasse, die eine Kante in einem Graphen markiert.
 */
public abstract class EdgeMarking extends Marking {

    /**
     * Markiert die angegebene Kante mit der angegebenen Farbe.
     *
     * @param edge die zu markierende Kante
     * @param color die Farbe, mit der die Kante markiert werden soll
     */
    public abstract void markEdge(final Edge edge, final Color color);

    /**
     * Entfernt die Markierung der angegebenen Kante.
     *
     * @param edge die Kante dessen Markierung entfernt werden soll
     */
    public abstract void unmarkEdge(final Edge edge);

    /**
     * Überprüft, ob die angegebene Kante markiert ist.
     *
     * @param edge die zu überprüfende Kante
     * @return true, wenn die Kante markiert ist, andernfalls false
     */
    public abstract boolean isEdgeMarked(final Edge edge);

    /**
     * @return ein Set aller markierten Kanten
     */
    public abstract Set<Edge> getMarkedEdges();

    public String toString() {
        return "EdgeMarking{" +
                "marked='" + getMarkedEdges() + '\'' +
                '}';
    }
}
