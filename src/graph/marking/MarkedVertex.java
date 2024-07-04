package graph.marking;

import graph.Vertex;

/**
 * Klasse, die einen markierten Knoten in einem Graphen darstellt.
 *
 * @param <T> der Typ der Markierung, die mit dem Knoten verbunden ist
 */
public class MarkedVertex<T extends VertexMarking> extends Vertex {

    private T marking;

    public MarkedVertex() {
        super();
    }

    /**
     * @param s der Name des Scheitelpunkts
     * @param t die mit dem Scheitelpunkt verbundene Markierung
     */
    public MarkedVertex(String s, T t) {
        super(s);
        this.marking = t;
    }

    /**
     * @return die mit dem Scheitelpunkt verbundene Markierung
     */
    public T getMarking() {
        return marking;
    }

    /**
     * Setzt die mit dem Knoten verbundene Markierung.
     *
     * @param marking die mit dem Scheitelpunkt zu verbindende Markierung
     */
    public void setMarking(T marking) {
        this.marking = marking;
    }

    public String toString() {
        return "";
    }
}
