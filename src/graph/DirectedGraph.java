package graph;

import graph.marking.EdgeMarking;
import graph.marking.MarkedEdge;
import graph.marking.MarkedVertex;
import graph.marking.VertexMarking;

import java.util.Vector;

// TODO: Implement logic for methods
public class DirectedGraph<T extends VertexMarking, U extends EdgeMarking> extends Graph<T, U> {

    public DirectedGraph() {
        super();
    }

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

    public boolean areStrongAdjacent(MarkedVertex<T> n1, MarkedVertex<T> n2) {
        return areAdjacent(n1, n2) && areAdjacent(n2, n1);
    }

    public boolean areStrongAdjacent(String s1, String s2) {
        return areAdjacent(s1, s2);
    }

    // TODO: Check if logic works correctly with typecast
    public Vector<MarkedVertex<T>> getPredecessors(final MarkedVertex<T> n) {
        Vector<MarkedVertex<T>> predecessors = new Vector<>();
        for (MarkedEdge<U> edge : getAllEdges()) {
            if (edge.getDestination().equals(n)) {
                predecessors.add((MarkedVertex<T>) edge.getSource());
            }
        }
        return predecessors;
    }

    // TODO: Check if logic works correctly with typecast
    public Vector<MarkedVertex<T>> getSuccessors(final MarkedVertex<T> n) {
        Vector<MarkedVertex<T>> successors = new Vector<>();
        for (MarkedEdge<U> edge : getAllEdges()) {
            if (edge.getSource().equals(n)) {
                successors.add((MarkedVertex<T>) edge.getDestination());
            }
        }
        return successors;
    }

    public int inDegree(MarkedVertex<T> n) {
        return getPredecessors(n).size();
    }

    public int outDegree(MarkedVertex<T> n) {
        return getSuccessors(n).size();
    }

    public int inDegree(String s){
        MarkedVertex<T> vertex = getAllVertexes()
                .stream()
                .filter(v -> v.getName().equals(s))
                .findFirst()
                .orElse(null);
        if(vertex != null) {
            return inDegree(vertex);
        }
        return 0;
    }

    public int outDegree(String s) {
        MarkedVertex<T> vertex = getAllVertexes()
                .stream()
                .filter(v -> v.getName().equals(s))
                .findFirst()
                .orElse(null);
        if (vertex != null) {
            return outDegree(vertex);
        }
        return 0;
    }

}
