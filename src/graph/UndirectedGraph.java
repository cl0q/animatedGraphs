package graph;

import graph.marking.EdgeMarking;
import graph.marking.MarkedEdge;
import graph.marking.MarkedVertex;
import graph.marking.VertexMarking;
import jdk.jshell.spi.ExecutionControl;

import java.util.Vector;

// TODO: Implement logic for methods
public class UndirectedGraph <T extends VertexMarking, U extends EdgeMarking> extends Graph<T, U> {

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
        MarkedVertex<T> n1 = getAllVertexes()
                .stream()
                .filter(n -> n.getName().equals(s1))
                .findFirst()
                .orElse(null);
        MarkedVertex<T> n2 = getAllVertexes()
                .stream()
                .filter(n -> n.getName().equals(s2))
                .findFirst()
                .orElse(null);
        if(n1 != null && n2 != null) {
            return areAdjacent(n1, n2);
        }
        return false;
    }

    public int degree(MarkedVertex<T> n) {
        int degree = 0;
        for(MarkedEdge<U> e : getAllEdges()) {
            if(e.getSource().equals(n) || e.getDestination().equals(n)) {
                degree++;
            }
        }
        return degree;
    }

    public int degree(String s) {
        MarkedVertex<T> vertex = getAllVertexes()
                .stream()
                .filter(n -> n.getName().equals(s))
                .findFirst()
                .orElse(null);
        if (vertex != null) {
            return degree(vertex);
        }
        return 0;
    }

    // TODO: Check if logic works correctly with typecast
    public Vector<MarkedVertex<T>> getNeighbors(MarkedVertex<T> n) {
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
        return "";
    }

    public Vector<MarkedVertex<T>> depthSearchRecursive() {
        return null;
    }
}
