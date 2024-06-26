package graph;

import graph.marking.EdgeMarking;
import graph.marking.MarkedEdge;
import graph.marking.MarkedVertex;
import graph.marking.VertexMarking;

import java.util.*;

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
        MarkedVertex<T> n1 = getVertex(s1);
        MarkedVertex<T> n2 = getVertex(s2);
        if(n1 != null && n2 != null) {
            return areAdjacent(n1, n2);
        }
        return false;
    }

    public boolean areStrongAdjacent(MarkedVertex<T> n1, MarkedVertex<T> n2) {
        return areAdjacent(n1, n2) && areAdjacent(n2, n1);
    }

    public boolean areStrongAdjacent(String s1, String s2) {
        return areAdjacent(s1, s2) && areAdjacent(s2, s1);
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

    public List<MarkedEdge<U>> getOutgoingEdges(MarkedVertex<T> vertex) {
        List<MarkedEdge<U>> outgoingEdges = new ArrayList<>();
        for (MarkedEdge<U> edge : getAllEdges()) {
            if (edge.getSource().equals(vertex)) {
                outgoingEdges.add(edge);
            }
        }
        return outgoingEdges;
    }

    public List<MarkedEdge<U>> getIncomingEdges(MarkedVertex<T> vertex) {
        List<MarkedEdge<U>> incomingEdges = new ArrayList<>();
        for (MarkedEdge<U> edge : getAllEdges()) {
            if (edge.getDestination().equals(vertex)) {
                incomingEdges.add(edge);
            }
        }
        return incomingEdges;
    }

    public int inDegree(MarkedVertex<T> n) {
        return getPredecessors(n).size();
    }

    public int outDegree(MarkedVertex<T> n) {
        return getSuccessors(n).size();
    }

    public int inDegree(String s) {
        MarkedVertex<T> vertex = getVertex(s);
        if(vertex != null) {
            return inDegree(vertex);
        }
        return 0;
    }

    public int outDegree(String s) {
        MarkedVertex<T> vertex = getVertex(s);
        if (vertex != null) {
            return outDegree(vertex);
        }
        return 0;
    }

    // TODO: Add marking logic
    public List<MarkedVertex<T>> topSort() {
        List<MarkedVertex<T>> sortedList = new ArrayList<>();
        Set<MarkedVertex<T>> visited = new HashSet<>();
        Set<MarkedVertex<T>> stack = new HashSet<>();

        for (MarkedVertex<T> vertex : getAllVertexes()) {
            if (!visited.contains(vertex)) {
                if (topologicalSortAlgorithm(vertex, visited, stack, sortedList)) {
                    return null;
                }
            }
        }

        Collections.reverse(sortedList);
        return sortedList;
    }

    private boolean topologicalSortAlgorithm(MarkedVertex<T> vertex, Set<MarkedVertex<T>> visited, Set<MarkedVertex<T>> stack, List<MarkedVertex<T>> sortedList) {
        visited.add(vertex);
        stack.add(vertex);

        for (MarkedEdge<U> edge : getOutgoingEdges(vertex)) {
            MarkedVertex<T> neighbor = (MarkedVertex<T>) edge.getDestination();
            if (stack.contains(neighbor)) {
                return true;
            }
            if (!visited.contains(neighbor)) {
                if (topologicalSortAlgorithm(neighbor, visited, stack, sortedList)) {
                    return true;
                }
            }
        }

        stack.remove(vertex);
        sortedList.add(vertex);
        return false;
    }

    public boolean hasCycle() {
        return topSort() == null;
    }
}
