package graph.marking.vertex;

import graph.structure.Edge;
import graph.structure.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse, die einen markierten Knoten in einem Graphen darstellt.
 *
 * @param <T> die Implementation der Markierung, die mit dem Knoten verbunden ist
 */
public final class MarkedVertex<T extends VertexMarking> extends Vertex {

    // Die mit dem Knoten verbundene implementierung der Markierung
    private T marking;
    private final List<Edge> edges = new ArrayList<>();  // List to store edges connected to this vertex

    /**
     * Erzeugt einen markierten Knoten.
     */
    public MarkedVertex() {
        super();
    }

    /**
     * Erzeugt einen markierten Knoten mit Namen und Markierung.
     *
     * @param s der Name des Knotens
     * @param t die mit dem Knoten verbundene Markierung
     */
    public MarkedVertex(String s, T t) {
        super(s);
        this.marking = t;
    }

    /**
     * Erzeugt einen markierten Knoten mit Namen, x-Koordinate, y-Koordinate und Markierung.
     *
     * @param name der Name des Knotens
     * @param x Koordinate des Knotens im Graphen
     * @param y Koordinate des Knotens im Graphen
     * @param t die mit dem Knoten verbundene Markierung
     */
    public MarkedVertex(String name, int x, int y, T t) {
        super(name, x, y);
        this.marking = t;
    }

    public MarkedVertex(String name){
        super(name);
    }

    /**
     * @return die mit dem Knoten verbundene Markierung
     */
    public T getMarking() {
        return marking;
    }

    /**
     * Setzt die mit dem Knoten verbundene Markierung.
     *
     * @param marking die mit dem Knoten zu verbindende Markierung
     */
    public void setMarking(T marking) {
        this.marking = marking;
    }
}
