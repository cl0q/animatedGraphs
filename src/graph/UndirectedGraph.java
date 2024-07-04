package graph;

import graph.marking.EdgeMarking;
import graph.marking.MarkedEdge;
import graph.marking.MarkedVertex;
import graph.marking.VertexMarking;

import java.util.Stack;
import java.util.Vector;

/**
 * Implementation eines ungerichteten Graphen.
 *
 * @param <T> der Typ der Markierung, die mit den Knoten verbunden ist
 * @param <U> der Typ der Markierung, die mit den Kanten verbunden ist
 */
public class UndirectedGraph<T extends VertexMarking, U extends EdgeMarking> extends Graph<T, U> {

    private String name;

    public UndirectedGraph() {
        super();
    }

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
     * @param s Name des Knotens
     * @return der Grad des Knotens
     */
    public int degree(String s) {
        MarkedVertex<T> vertex = getVertex(s);
        if (vertex != null) {
            return degree(vertex);
        }
        return 0;
    }

    /**
     * Gibt die Nachbarn eines Knotens zurück.
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

    public String toString() {
        return super.toString();
    }

    /**
     * Führt eine Tiefensuche rekursiv durch.
     * @param start der Startknoten
     * @return eine Liste der besuchten Knoten
     */
    public Vector<MarkedVertex<T>> depthSearchRecursive(MarkedVertex<T> start) {
        Vector<MarkedVertex<T>> visited = new Vector<>();
        Stack<MarkedVertex<T>> stack = new Stack<>();
        stack.push(start); // Startknoten auf den Stack legen
        depthSearchRecursive(stack, visited); // Rekursive Tiefensuche starten
        return visited;
    }

    /**
     * Hilfsmethode für die rekursive Tiefensuche.
     * @param stack der Stack mit den zu besuchenden Knoten
     * @param visited die Liste der besuchten Knoten
     */
    private void depthSearchRecursive(Stack<MarkedVertex<T>> stack, Vector<MarkedVertex<T>> visited) {
        if (!stack.isEmpty()) { // Solange der Stack nicht leer ist
            MarkedVertex<T> vertex = stack.pop(); // Knoten vom Stack nehmen
            if (!visited.contains(vertex)) { // Wenn der Knoten noch nicht besucht wurde
                visited.add(vertex); // Knoten als besucht markieren
                for (MarkedVertex<T> neighbor : getNeighbours(vertex)) { // Alle Nachbarn des Knotens durchlaufen
                    if (!visited.contains(neighbor)) { // Wenn der Nachbar noch nicht besucht wurde
                        stack.push(neighbor); // Nachbar auf den Stack legen
                    }
                }
                depthSearchRecursive(stack, visited); // Rekursiver Aufruf für den nächsten Knoten
            }
        }
    }
}
