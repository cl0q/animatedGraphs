package graph.marking;

import graph.Edge;
import graph.Vertex;

public class MarkedEdge<T extends EdgeMarking> extends Edge {

    private T marking;

    public MarkedEdge() {

    }

    public MarkedEdge(String s, Vertex n1, Vertex n2, T t) {
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
