package graph.marking;

import graph.Vertex;

public class MarkedVertex<T extends VertexMarking> extends Vertex {

    private T marking;

    public MarkedVertex() {
        super();
    }

    public MarkedVertex(String s, T t) {
        super(s);
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
