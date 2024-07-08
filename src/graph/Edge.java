package graph;

import java.awt.*;

/**
 * Implementation einer Kante.
 */
public abstract class Edge {

    private String name;
    private Vertex source;
    private Vertex destination;
    private boolean directed;

    /**
     * Erzeugt eine neue Kante.
     */
    public Edge() {
        this.name = "";
        this.source = null;
        this.destination = null;
    }

    /**
     * Erzeugt eine neue Kante mit Namen und Knoten.
     * @param name der Name der Kante
     * @param n1 der erste Knoten
     * @param n2 der zweite Knoten
     */
    public Edge(final String name, final Vertex n1, final Vertex n2) {
        this.name = name;
        this.source = n1;
        this.destination = n2;
        this.directed = false;
    }

    /**
     * Erzeugt eine neue Kante mit Namen und Knoten die gerichtet sein kann.
     * @param name der Name der Kante
     * @param source der erste Knoten
     * @param destination der zweite Knoten
     * @param directed ob die Kante gerichtet ist
     */
    public Edge(String name, Vertex source, Vertex destination, boolean directed) {
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.directed = directed;
    }

    /**
     * @return den Zielknoten der Kante
     */
    public Vertex getDestination() {
        return destination;
    }

    /**
     * @return den Namen der Kante
     */
    public String getName() {
        return name;
    }

    /**
     * @return den Startknoten der Kante
     */
    public Vertex getSource() {
        return source;
    }

    /**
     * Setzt den Zielknoten der Kante.
     * @param n der neue Zielknoten
     * @return den Zielknoten
     */
    public Vertex setDestination(final Vertex n) {
        return this.destination = n;
    }

    /**
     * Setzt den Namen der Kante.
     * @param s der neue Name
     */
    public void setName(final String s) {
        this.name = s;
    }

    /**
     * Setzt den Startknoten der Kante.
     * @param n der neue Startknoten
     */
    public void setSource(final Vertex n) {
        this.source = n;
    }

    public boolean isDirected() {
        return directed;
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    public boolean contains(int px, int py) {
        int x1 = source.getX();
        int y1 = source.getY();
        int x2 = destination.getX();
        int y2 = destination.getY();

        double distance = Math.abs((y2 - y1) * px - (x2 - x1) * py + x2 * y1 - y2 * x1) / Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
        return distance < 5;
    }

    public void drawHere(Graphics g) {
        g.setColor(Color.BLACK);
        int adjustedX1 = adjustCoordinateForVertex(source.getX(), destination.getX());
        int adjustedY1 = adjustCoordinateForVertex(source.getY(), destination.getY());
        int adjustedX2 = adjustCoordinateForVertex(destination.getX(), source.getX());
        int adjustedY2 = adjustCoordinateForVertex(destination.getY(), source.getY());
        g.drawLine(adjustedX1, adjustedY1, adjustedX2, adjustedY2);
        int mx = (adjustedX1 + adjustedX2) / 2;
        int my = (adjustedY1 + adjustedY2) / 2;
        g.setColor(Color.BLACK);
        g.drawString(name, mx, my);
        if (directed) {
            drawArrow(g, adjustedX1, adjustedY1, adjustedX2, adjustedY2);
        }
    }

    private void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
        int arrowSize = 10;
        double angle = Math.atan2(y2 - y1, x2 - x1);

        int arrowX1 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
        int arrowY1 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));
        int arrowX2 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
        int arrowY2 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));

        g.drawLine(x2, y2, arrowX1, arrowY1);
        g.drawLine(x2, y2, arrowX2, arrowY2);
    }

    private int adjustCoordinateForVertex(int from, int to) {
        double theta = Math.atan2(to - from, to - from);
        return from + (int) (10 * Math.cos(theta));
    }
}
