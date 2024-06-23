package graph;

import graph.marking.EdgeMarking;
import graph.marking.MarkedEdge;
import graph.marking.MarkedVertex;
import graph.marking.VertexMarking;

import java.util.Vector;

// TODO: Check why some method are abstract when UML-Diagram says no abstract class
// TODO: Implement logic for methods
public class Graph<T extends VertexMarking, U extends EdgeMarking>  {

    private String name;
    private Vector<MarkedVertex<T>> vertexes;
    private Vector<MarkedEdge<U>> edges;

    public Graph() {

    }

    public Graph(String s) {
        this.name = s;
    }

    public void addEdge(MarkedEdge<U> e) {
        this.edges.add(e);
    }

    public void addVertex(MarkedVertex<T> n) {
        this.vertexes.add(n);
    }


    public abstract boolean areAdjacent(MarkedVertex<T> n1, MarkedVertex<T> n2);

    public abstract boolean areAdjacent(String s1, String s2);

    public int degree() {
        return -1;
    }

    public Vector<MarkedEdge<U>> getAllEdges() {
        return edges;
    }

    public Vector<MarkedVertex<T>> getAllVertexes() {
        return vertexes;
    }

    public String getName() {
        return name;
    }

    public boolean hasEdge(MarkedEdge<U> e) {
        return false;
    }

    public boolean hasEdge(String s) {
        return false;
    }

    public boolean hasEdge(MarkedVertex<T> n1, MarkedVertex<T> n2) {
        return false;
    }

    public boolean hasEdge(String s1, String s2) {
        return false;
    }

    public boolean hasLoop(MarkedVertex<T> n) {
        return false;
    }

    public boolean hasLoop(String s) {
        return false;
    }

    public boolean hasVertex(MarkedVertex<T> n) {
        return false;
    }

    public boolean hasVertex(String s) {
        return false;
    }

    public int numberOfEdges() {
        return -1;
    }

    public int numberOfVertexes() {
        return -1;
    }

    public boolean removeEdge(MarkedEdge<U> e) {
        return false;
    }

    public boolean removeEdge(String s) {
        return false;
    }

    public boolean removeVertex(MarkedVertex<T> n) {
        return false;
    }

    public boolean removeVertex(String s) {
        return false;
    }

    public void setName(String s) {
        this.name = s;
    }

    public String toString() {
        return "";
    }
}
