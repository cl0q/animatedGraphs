package graph;

import java.awt.*;

public class Edge {

    private String name;
    private Vertex source;
    private Vertex destination;
    private boolean directed;

    public Edge(String name, Vertex source, Vertex destination, boolean directed) {
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.directed = directed;
    }

    public Edge() {
        
    }

    public Edge(String s, Vertex n1, Vertex n2) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vertex getSource() {
        return source;
    }

    public void setSource(Vertex source) {
        this.source = source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
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
