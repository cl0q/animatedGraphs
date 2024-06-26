package graph;

import graph.marking.EdgeMarking;
import graph.marking.MarkedEdge;
import graph.marking.MarkedVertex;
import graph.marking.VertexMarking;

import java.util.Vector;

// TODO: Implement logic for methods
public abstract class Graph<T extends VertexMarking, U extends EdgeMarking>  {

    private String name;
    private final Vector<MarkedVertex<T>> vertexes;
    private final Vector<MarkedEdge<U>> edges;

    public Graph() {
        this.vertexes = new Vector<>();
        this.edges = new Vector<>();
    }

    public Graph(String s) {
        this();
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
        return vertexes.size();
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
        return edges.contains(e);
    }

    public boolean hasEdge(String s) {
        return edges.stream().anyMatch(e -> e.getName().equals(s));
    }

    public boolean hasEdge(MarkedVertex<T> n1, MarkedVertex<T> n2) {
        return edges.stream().anyMatch(e -> e.getSource().equals(n1) && e.getDestination().equals(n2));
    }

    public boolean hasEdge(String s1, String s2) {
        MarkedVertex<T> n1 = getVertex(s1);
        MarkedVertex<T> n2 = getVertex(s2);
        if (n1 != null && n2 != null) {
            return hasEdge(n1, n2);
        }
        return false;
    }

    public MarkedVertex<T> getVertex(String name) {
        return vertexes.stream().filter(v -> v.getName().equals(name)).findFirst().orElse(null);
    }

    public boolean hasLoop(MarkedVertex<T> n) {
        return hasEdge(n, n);
    }

    public boolean hasLoop(String s) {
        MarkedVertex<T> n = getVertex(s);
        if (n != null) {
            return hasLoop(n);
        }
        return false;
    }

    public boolean hasVertex(MarkedVertex<T> n) {
        return vertexes.contains(n);
    }

    public boolean hasVertex(String s) {
        return vertexes.stream().anyMatch(v -> v.getName().equals(s));
    }

    public int numberOfEdges() {
        return edges.size();
    }

    public int numberOfVertexes() {
        return vertexes.size();
    }

    public boolean removeEdge(MarkedEdge<U> e) {
        return edges.remove(e);
    }

    public boolean removeEdge(String s) {
        return edges.removeIf(e -> e.getName().equals(s));
    }

    public boolean removeVertex(MarkedVertex<T> n) {
        return vertexes.remove(n);
    }

    public boolean removeVertex(String s) {
        return vertexes.removeIf(v -> v.getName().equals(s));
    }

    public void setName(String s) {
        this.name = s;
    }

    public String toString() {
        return "Graph{" +
                "name='" + name + '\'' +
                ", vertexes=" + vertexes +
                ", edges=" + edges +
                '}';
    }
}
