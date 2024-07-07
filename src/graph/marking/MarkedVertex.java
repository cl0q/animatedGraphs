package graph.marking;

import graph.Vertex;

import java.awt.*;

public class MarkedVertex<T extends VertexMarking> extends Vertex {

    private T marking;

    public MarkedVertex() {
        super();
    }

    public MarkedVertex(String s, T t) {
        super(s);
        this.marking = t;
    }

    public MarkedVertex(String name, int x, int y, T t) {
        super(name, x, y);
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

    public void drawHere(Graphics g) {
        g.setColor(marking.getColor(this)); // Convert string color to Color
        g.fillOval(getX() - 10, getY() - 10, 20, 20);  // Assuming a fixed size for the vertex
        g.setColor(Color.WHITE);
        g.drawString(getName(), getX() - 10 + 4, getY() + 4);
    }
}
