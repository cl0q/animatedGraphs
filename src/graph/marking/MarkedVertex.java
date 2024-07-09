package graph.marking;

import graph.Edge;
import graph.Vertex;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasse, die einen markierten Knoten in einem Graphen darstellt.
 *
 * @param <T> der Typ der Markierung, die mit dem Knoten verbunden ist
 */
public final class MarkedVertex<T extends VertexMarking> extends Vertex implements Cloneable {

    // Die mit dem Knoten verbundene implementierung der Markierung
    private T marking;
    private final List<Edge> edges = new ArrayList<>();  // List to store edges connected to this vertex

    /**
     * Erzeugt einen neuen markierten Knoten.
     */
    public MarkedVertex() {
        super();
    }

    /**
     * Erzeugt einen neuen markierten Knoten mit Namen und Markierung.
     *
     * @param s der Name des Knotens
     * @param t die mit dem Knoten verbundene Markierung
     */
    public MarkedVertex(String s, T t) {
        super(s);
        this.marking = t;
    }

    /**
     * Erzeugt einen neuen markierten Knoten mit Namen, x-Koordinate, y-Koordinate und Markierung.
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

    /**
     * Gibt eine Kopie des markierten Knotens zurück, welche die gleichen Attribute hat
     * und für den dieselbe Markierung gespeichert wurde.
     *
     * @return eine Kopie des markierten Knotens
     */
    @Override
    public MarkedVertex<T> clone() {
        MarkedVertex<T> clonedMarkedVertex = new MarkedVertex<>(getName(), getX(), getY(), marking);
        marking.markVertex(clonedMarkedVertex, marking.getColor(this));
        return clonedMarkedVertex;
    }

    /**
     * Zeichnet den markierten Knoten.
     *
     * @param g Graphics-Objekt zur Zeichnung
     */
    public List<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    public void drawHere(Graphics g) {
        g.setColor(marking.getColor(this));
        g.fillOval(getX() - 10, getY() - 10, 20, 20);
        g.setColor(Color.WHITE);
        g.drawString(getName(), getX() - 10 + 4, getY() + 4);
    }
}
