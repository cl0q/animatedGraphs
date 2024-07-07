package graph.marking;

import graph.Edge;
import graph.Vertex;

public class MarkedEdge<T extends EdgeMarking> extends Edge {

    private T marking;

    public MarkedEdge() {
        super();
    }

    public MarkedEdge(String s, Vertex n1, Vertex n2, T t) {
        super(s, n1, n2);
        this.marking = t;
    }

    public MarkedEdge(String s, Vertex n1, Vertex n2, boolean isDirected, T t) {
        super(s, n1, n2, isDirected);
        this.marking = t;
    }

    public T getMarking() {
        return marking;
    }

    public void setMarking(T marking) {
        this.marking = marking;
    }

    public String toString() {
        return "";
    }
}
