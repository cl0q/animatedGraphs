package graph.structure.graph;

import graph.marking.edge.EdgeMarking;
import graph.marking.edge.MarkedEdge;
import graph.marking.vertex.MarkedVertex;
import graph.marking.vertex.VertexMarking;

import java.util.*;

/**
 * Implementation eines ungerichteten Graphen.
 *
 * @param <T> implementierende Klasse der Knotenmarkierung
 * @param <U> implementierende Klasse der Kantenmarkierung
 */
public class UndirectedGraph<T extends VertexMarking, U extends EdgeMarking> extends Graph<T, U> {

    private String name;

    /**
     * Erzeugt einen ungerichteten Graphen.
     */
    public UndirectedGraph() {
        super();
    }

    /**
     * Erzeugt einen ungerichteten Graphen mit einem Namen.
     *
     * @param s der Name des Graphen
     */
    public UndirectedGraph(String s) {
        super(s);
        this.name = s;
    }

    @Override
    public boolean areAdjacent(MarkedVertex<T> n1, MarkedVertex<T> n2) {
        return getAllEdges()
                .stream()
                .anyMatch(e -> e.getSource().equals(n1) && e.getDestination().equals(n2)
                        || e.getSource().equals(n2) && e.getDestination().equals(n1));
    }

    @Override
    public boolean areAdjacent(String s1, String s2) {
        MarkedVertex<T> n1 = getVertex(s1);
        MarkedVertex<T> n2 = getVertex(s2);
        if(n1 != null && n2 != null) {
            return areAdjacent(n1, n2);
        }
        return false;
    }

    /**
     * Gibt den Grad eines Knotens zurück.
     *
     * @param n der Knoten
     * @return der Grad des Knotens
     */
    public int degree(MarkedVertex<T> n) {
        int degree = 0;
        for(MarkedEdge<U> e : getAllEdges()) {
            if(e.getSource().equals(n) || e.getDestination().equals(n)) {
                degree++;
            }
        }
        return degree;
    }

    /**
     * Gibt den Grad eines Knotens mit angegebenen Namen zurück.
     *
     * @param s Name des Knotens
     * @return der Grad des Knotens
     */
    public int degree(String s) {
        MarkedVertex<T> vertex = getVertex(s);
        return vertex != null ? degree(vertex) : 0;
    }

    /**
     * Gibt die Nachbarn eines Knotens zurück.
     *
     * @param n der Knoten
     * @return eine Liste der Nachbarn
     */
    public Vector<MarkedVertex<T>> getNeighbours(MarkedVertex<T> n) {
        Vector<MarkedVertex<T>> neighbours = new Vector<>();
        for (MarkedEdge<U> edge : getAllEdges()) {
            if (edge.getSource().equals(n)) {
                neighbours.add((MarkedVertex<T>) edge.getDestination());
            } else if (edge.getDestination().equals(n)) {
                neighbours.add((MarkedVertex<T>) edge.getSource());
            }
        }
        return neighbours;
    }
}
