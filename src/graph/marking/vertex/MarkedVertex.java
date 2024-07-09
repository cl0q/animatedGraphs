package graph.marking.vertex;

import graph.structure.Edge;
import graph.structure.Vertex;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasse, die einen markierten Knoten in einem Graphen darstellt.
 *
 * @param <T> die Implementation der Markierung, die mit dem Knoten verbunden ist
 */
public final class MarkedVertex<T extends VertexMarking> extends Vertex implements Cloneable {

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

    /**
     * Erzeugt eine Kopie des markierten Knotens, welche die gleichen Attribute hat
     * und für den dieselbe Markierung gespeichert wurde.
     *
     * @return eine Kopie des markierten Knotens
     */
    @Override
    public MarkedVertex<T> clone() {
        MarkedVertex<T> clonedMarkedVertex = (MarkedVertex<T>)super.clone();
        clonedMarkedVertex.setMarking(marking);
        marking.markVertex(clonedMarkedVertex, marking.getColor(this));
        return clonedMarkedVertex;
    }

    /**
     * @return eine Liste der Kanten, die mit dem Knoten verbunden sind
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Fügt eine Kante zu den Kanten hinzu, die mit dem Knoten verbunden sind.
     * @param edge die Kante, welche hinzugefügt werden soll
     */
    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    /**
     * Entfernt eine Kante aus den Kanten, die mit dem Knoten verbunden sind.
     * @param edge die Kante, welche entfernt werden soll
     */
    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    /**
     * Zeichnet den markierten Knoten.
     * @param g Graphics-Objekt zur Zeichnung
     */
    public void drawHere(Graphics g) {
        g.setColor(marking.getColor(this));
        g.fillOval(getX() - 10, getY() - 10, 20, 20);
        g.setColor(Color.WHITE);
        g.drawString(getName(), getX() - 10 + 4, getY() + 4);
    }
}
