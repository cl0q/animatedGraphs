package graph;

import graph.marking.EdgeMarking;
import graph.marking.MarkedVertex;
import graph.marking.VertexMarking;
import jdk.jshell.spi.ExecutionControl;

import java.util.Vector;

// TODO: Implement logic for methods
public class UndirectedGraph <T extends VertexMarking, U extends EdgeMarking> extends Graph<T, U> {

    private String name;

    public UndirectedGraph() {

    }

    public UndirectedGraph(String s) {
        this.name = s;
    }

    @Override
    public boolean areAdjacent(MarkedVertex<T> n1, MarkedVertex<T> n2) {
        return false;
    }

    @Override
    public boolean areAdjacent(String s1, String s2) {
        return false;
    }

    public int degree(MarkedVertex<T> n) {
        return -1;
    }

    public int degree(String s) {
        return -1;
    }

    public Vector<MarkedVertex<T>> getNeighbors(MarkedVertex<T> n) {
        return null;
    }

    public String toString() {
        return "";
    }

    public Vector<MarkedVertex<T>> depthSearchRecursive() {
        return null;
    }
}
