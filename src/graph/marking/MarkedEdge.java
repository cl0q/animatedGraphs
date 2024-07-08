package graph.marking;

import graph.Edge;
import graph.Vertex;

import java.awt.*;
import java.util.ArrayList;

public class MarkedEdge<T extends EdgeMarking> extends Edge {
/**
 * Klasse, die eine markierte Kante in einem Graphen darstellt.
 *
 * @param <T> der Typ der Markierung, die mit der Kante verbunden ist
 */
public final class MarkedEdge<T extends EdgeMarking> extends Edge implements Cloneable {

    private T marking;

    public MarkedEdge() {
        super();
    }

    /**
     * Erzeugt eine neue markierte Kante mit Namen, Ursprungsknoten, Zielknoten und Markierung.
     *
     * @param s der Name der Kante
     * @param n1 der erste Knoten
     * @param n2 der zweite Knoten
     * @param t die mit der Kante verbundene Markierung
     */
    public MarkedEdge(String s, Vertex n1, Vertex n2, T t) {
        super(s, n1, n2);
        this.marking = t;
        ((MarkedVertex<VertexColorMarking>) n1).addEdge(this);
        ((MarkedVertex<VertexColorMarking>) n2).addEdge(this);
    }

    /**
     * Erzeugt eine neue markierte Kante mit Namen, Ursprungsknoten, Zielknoten und Markierung, die gerichtet sein kann.
     *
     * @param s der Name der Kante
     * @param n1 der erste Knoten
     * @param n2 der zweite Knoten
     * @param isDirected ob die Kante gerichtet ist
     * @param t die mit der Kante verbundene Markierung
     */
    public MarkedEdge(String s, Vertex n1, Vertex n2, boolean isDirected, T t) {
        super(s, n1, n2, isDirected);
        this.marking = t;
        ((MarkedVertex<VertexColorMarking>) n1).addEdge(this);
        ((MarkedVertex<VertexColorMarking>) n2).addEdge(this);
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

    /**
     * @return eine Kopie der markierten Kante
     */
    @Override
    public MarkedEdge<T> clone() {
        MarkedEdge<T> cloneObj = new MarkedEdge<>(getName(), getSource(), getDestination(), isDirected(), marking);
        marking.markEdge(cloneObj, marking.getColor(this));
        return cloneObj;
    }

    public void drawHere(Graphics g) {
        g.setColor(marking.getColor(this));
        if (getSource() == getDestination()) {
            drawLoop(g);
        } else {
            drawEdge(g);
        }
    }

    private void drawEdge(Graphics g) {
        int sourceX = getSource().getX();
        int sourceY = getSource().getY();
        int destX = getDestination().getX();
        int destY = getDestination().getY();

        int radius = 10; // Adjust the radius according to the vertex size
        double angle = Math.atan2(destY - sourceY, destX - sourceX);
        destX -= radius * Math.cos(angle);
        destY -= radius * Math.sin(angle);

        g.drawLine(sourceX, sourceY, destX, destY);
        int midX = (sourceX + destX) / 2;
        int midY = (sourceY + destY) / 2;
        g.drawString(getName(), midX, midY);

        if (isDirected()) {
            drawArrow(g, sourceX, sourceY, destX, destY);
        }
    }

    private void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
        int arrowSize = 10;
        double angle = Math.atan2(y2 - y1, x2 - x1);

        int x0 = (int) (x2 - arrowSize * Math.cos(angle));
        int y0 = (int) (y2 - arrowSize * Math.sin(angle));

        int arrowX1 = (int) (x0 - arrowSize * Math.cos(angle - Math.PI / 6));
        int arrowY1 = (int) (y0 - arrowSize * Math.sin(angle - Math.PI / 6));
        int arrowX2 = (int) (x0 - arrowSize * Math.cos(angle + Math.PI / 6));
        int arrowY2 = (int) (y0 - arrowSize * Math.sin(angle + Math.PI / 6));

        g.drawLine(x2, y2, arrowX1, arrowY1);
        g.drawLine(x2, y2, arrowX2, arrowY2);
    }

    private void drawLoop(Graphics g) {
        int loopSize = 30;
        int loopX = getSource().getX() - 10;
        int loopY = getSource().getY() - loopSize;

        ArrayList<Edge> loops = new ArrayList<>();
        for (Edge edge : ((MarkedVertex<VertexColorMarking>) getSource()).getEdges()) {
            if (edge != this && edge.getSource() == edge.getDestination()) {
                loops.add(edge);
            }
        }

        if (!loops.isEmpty()) {
            boolean placeOnOppositeSide = (loops.size() % 2 != 0);
            if (placeOnOppositeSide) {
                loopX = getSource().getX() + 10;
                loopY = getSource().getY() + 10;
            }
        }

        g.drawOval(loopX, loopY, loopSize, loopSize);
        g.drawString(getName(), loopX + 5, loopY - 5);

        if (isDirected()) {
            drawLoopArrow(g, loopX, loopY, loopSize);
        }
    }

    private void drawLoopArrow(Graphics g, int loopX, int loopY, int loopSize) {
        int arrowSize = 10;
        double angle = 190;

        int arrowX = loopX + loopSize / 2 + 14; // Offset to the right
        int arrowY = loopY + loopSize / 2;

        int arrowX1 = (int) (arrowX + arrowSize * Math.cos(angle - Math.PI / 6));
        int arrowY1 = (int) (arrowY - arrowSize * Math.sin(angle - Math.PI / 6));
        int arrowX2 = (int) (arrowX + arrowSize * Math.cos(angle + Math.PI / 6));
        int arrowY2 = (int) (arrowY - arrowSize * Math.sin(angle + Math.PI / 6));

        g.drawLine(arrowX, arrowY, arrowX1, arrowY1);
        g.drawLine(arrowX, arrowY, arrowX2, arrowY2);
    }
}
