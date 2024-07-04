package graph.marking;

import graph.Edge;
import graph.Vertex;

/**
 * Klasse, die eine markierte Kante in einem Graphen darstellt.
 *
 * @param <T> der Typ der Markierung, die mit der Kante verbunden ist
 */
public class MarkedEdge<T extends EdgeMarking> extends Edge {

    private T marking;

    public MarkedEdge() {
        super();
    }

    /**
     * @param s der Name der Kante
     * @param n1 der erste Knoten
     * @param n2 der zweite Knoten
     * @param t die mit der Kante verbundene Markierung
     */
    public MarkedEdge(String s, Vertex n1, Vertex n2, T t) {
        super(s, n1, n2);
        this.marking = t;
    }

    /**
     * @return die mit der Kante verbundene Markierung
     */
    public T getMarking() {
        return marking;
    }

    /**
     * Setzt die mit der Kante verbundene Markierung.
     *
     * @param marking die mit der Kante zu verbindende Markierung
     */
    public void setMarking(T marking) {
        this.marking = marking;
    }

    public String toString() {
        return "";
    }
}
