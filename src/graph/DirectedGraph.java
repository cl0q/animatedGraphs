package graph;

import graph.marking.EdgeMarking;
import graph.marking.MarkedVertex;
import graph.marking.VertexMarking;

import java.util.Vector;

// TODO: Implement logic for methods
public class DirectedGraph<T extends VertexMarking, U extends EdgeMarking> extends Graph<T, U> {

    public DirectedGraph() {
    }

    public DirectedGraph(final String s) {
        super(s);
    }

    @Override
    public boolean areAdjacent(MarkedVertex<T> n1, MarkedVertex<T> n2) {
        return false;
    }

    @Override
    public boolean areAdjacent(String s1, String s2) {
        return false;
    }

    public boolean areStrongAdjacent(final MarkedVertex<T> n1, MarkedVertex<T> n2) {
        return false;
    }

    public Vector<MarkedVertex<T>> getPredecessors(final MarkedVertex<T> n) {
        return null;
    }

    public Vector<MarkedVertex<T>> getSuccessors(final MarkedVertex<T> n) {
        return null;
    }

    public int inDegree(MarkedVertex<T> n) {
        return 0;
    }

    public int outDegree(MarkedVertex<T> n) {
        return 0;
    }

    public int outDegree(String s) {
        return 0;
    }

}
