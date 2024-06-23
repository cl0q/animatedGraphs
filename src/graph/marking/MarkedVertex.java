package graph.marking;

import graph.Vertex;

public class MarkedVertex<T extends VertexMarking> extends Vertex {

    private T marking;

    public MarkedVertex() {

    }

    public MarkedVertex(String s, T t) {
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
