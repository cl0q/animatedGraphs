package graph.marking;

import graph.Vertex;

import java.awt.*;
import java.util.Set;

/**
 * Abstrakte Klasse, die einen Knoten in einem Graphen markiert.
 */
public abstract class VertexMarking extends Marking {

    /**
     * Markiert den angegebenen Knoten mit der angegebenen Farbe.
     *
     * @param vertex der zu markierende Knoten
     * @param color die Farbe, mit der der Knoten markiert werden soll
     */
    public abstract void markVertex(final Vertex vertex, final Color color);

    /**
     * Entfernt die Markierung des angegebenen Knotens.
     *
     * @param vertex der Knoten dessen Markierung entfernt werden soll
     */
    public abstract void unmarkVertex(final Vertex vertex);

    /**
     * Überprüft, ob der angegebene Knoten markiert ist.
     *
     * @param vertex der zu überprüfende Knoten
     * @return true, wenn der Knoten markiert ist, andernfalls false
     */
    public abstract boolean isVertexMarked(final Vertex vertex);

    /**
     * @return ein Set aller markierten Knoten
     */
    public abstract Set<Vertex> getMarkedVertexes();

    public String toString() {
        return "VertexMarking{" +
                "marked='" + getMarkedVertexes() + '\'' +
                '}';
    }
}
