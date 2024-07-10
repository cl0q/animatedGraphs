package graph.structure.graph;

import graph.marking.edge.EdgeMarking;
import graph.marking.edge.MarkedEdge;
import graph.marking.vertex.MarkedVertex;
import graph.marking.vertex.VertexMarking;

import java.util.*;

/**
 * Implementation eines gerichteten Graphen.
 *
 * @param <T> implementierende Klasse der Knotenmarkierung
 * @param <U> implementierende Klasse der Kantenmarkierung
 */
public class DirectedGraph<T extends VertexMarking, U extends EdgeMarking> extends Graph<T, U> {

    /**
     * Erzeugt einen gerichteten Graphen.
     */
    public DirectedGraph() {
        super();
    }

    /**
     * Erzeugt einen gerichteten Graphen mit einem Namen.
     *
     * @param s der Name des Graphen
     */
    public DirectedGraph(final String s) {
        super(s);
    }

    @Override
    public boolean areAdjacent(MarkedVertex<T> n1, MarkedVertex<T> n2) {
        return getAllEdges()
                .stream()
                .anyMatch(e -> e.getSource().equals(n1) && e.getDestination().equals(n2));
    }

    @Override
    public boolean areAdjacent(String s1, String s2) {
        MarkedVertex<T> n1 = getVertex(s1);
        MarkedVertex<T> n2 = getVertex(s2);
        if (n1 != null && n2 != null) {
            return areAdjacent(n1, n2);
        }
        return false;
    }

    /**
     * Überprüft, ob zwei Knoten stark benachbart sind.
     *
     * @param n1 der erste Knoten
     * @param n2 der zweite Knoten
     * @return true, wenn die Knoten stark benachbart sind, andernfalls false
     */
    public boolean areStrongAdjacent(MarkedVertex<T> n1, MarkedVertex<T> n2) {
        return areAdjacent(n1, n2) && areAdjacent(n2, n1);
    }

    /**
     * Überprüft, ob zwei Knoten mit den angegebenen Namen stark benachbart sind.
     *
     * @param s1 Name des ersten Knotens
     * @param s2 Name des zweiten Knotens
     * @return true, wenn die Knoten stark benachbart sind, andernfalls false
     */
    public boolean areStrongAdjacent(String s1, String s2) {
        return areAdjacent(s1, s2) && areAdjacent(s2, s1);
    }

    /**
     * Gibt die Vorgänger eines Knotens zurück.
     *
     * @param n der Knoten
     * @return eine Liste der Vorgänger
     */
    public Vector<MarkedVertex<T>> getPredecessors(final MarkedVertex<T> n) {
        Vector<MarkedVertex<T>> predecessors = new Vector<>();
        for (MarkedEdge<U> edge : getAllEdges()) {
            if (edge.getDestination().equals(n)) {
                predecessors.add((MarkedVertex<T>) edge.getSource());
            }
        }
        return predecessors;
    }

    /**
     * Gibt die Nachfolger eines Knotens zurück.
     *
     * @param n der Knoten
     * @return eine Liste der Nachfolger
     */
    public Vector<MarkedVertex<T>> getSuccessors(final MarkedVertex<T> n) {
        Vector<MarkedVertex<T>> successors = new Vector<>();
        for (MarkedEdge<U> edge : getAllEdges()) {
            if (edge.getSource().equals(n)) {
                successors.add((MarkedVertex<T>) edge.getDestination());
            }
        }
        return successors;
    }

    /**
     * Gibt den Eingangsgrad eines Knotens zurück.
     *
     * @param n der Knoten
     * @return der Eingangsgrad
     */
    public int inDegree(MarkedVertex<T> n) {
        return getPredecessors(n).size();
    }

    /**
     * Gibt den Ausgangsgrad eines Knotens zurück.
     *
     * @param n der Knoten
     * @return der Ausgangsgrad
     */
    public int outDegree(MarkedVertex<T> n) {
        return getSuccessors(n).size();
    }

    /**
     * Gibt den Eingangsgrad eines Knotens mit angegebenen Namen zurück.
     *
     * @param s Namen des Knotens
     * @return der Eingangsgrad
     */
    public int inDegree(String s) {
        MarkedVertex<T> vertex = getVertex(s);
        if (vertex != null) {
            return inDegree(vertex);
        }
        return 0;
    }

    /**
     * Gibt den Ausgangsgrad eines Knotens mit angegebenen Namen zurück.
     *
     * @param s Namen des Knotens
     * @return der Ausgangsgrad
     */
    public int outDegree(String s) {
        MarkedVertex<T> vertex = getVertex(s);
        if (vertex != null) {
            return outDegree(vertex);
        }
        return 0;
    }
}
