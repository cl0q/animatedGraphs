package graph.marking;

import graph.Vertex;

import java.awt.*;

/**
 * Klasse, die einen markierten Knoten in einem Graphen darstellt.
 *
 * @param <T> der Typ der Markierung, die mit dem Knoten verbunden ist
 */
public final class MarkedVertex<T extends VertexMarking> extends Vertex implements Cloneable {

    private T marking;

    public MarkedVertex() {
        super();
    }

    /**
     * @param s der Name des Knotens
     * @param t die mit dem Knoten verbundene Markierung
     */
    public MarkedVertex(String s, T t) {
        super(s);
        this.marking = t;
    }

    /**
     * @param name der Name des Knotens
     * @param x Koordinate des Knotens im Graphen
     * @param y Koordinate des Knotens im Graphen
     * @param t die mit dem Knoten verbundene Markierung
     */
    public MarkedVertex(String name, int x, int y, T t) {
        super(name, x, y);
        this.marking = t;
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
     * @return eine Kopie des markierten Knotens
     */
    @Override
    public MarkedVertex<T> clone() {
        MarkedVertex<T> clonedMarkedVertex = new MarkedVertex<T>(getName(), getX(), getY(), marking);
        marking.markVertex(clonedMarkedVertex, marking.getColor(this));
        return clonedMarkedVertex;
    }

    public void drawHere(Graphics g) {
        g.setColor(marking.getColor(this));
        g.fillOval(getX() - 10, getY() - 10, 20, 20);
        g.setColor(Color.WHITE);
        g.drawString(getName(), getX() - 10 + 4, getY() + 4);
    }
}
