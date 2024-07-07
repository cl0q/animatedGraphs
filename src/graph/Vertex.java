package graph;

import java.awt.*;

public class Vertex {

    private String name;
    private int x;
    private int y;

    public Vertex(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public Vertex(String name) {
        this.name = name;
        this.x = 0;
        this.y = 0;
    }

    public Vertex() {
        this.name = "";
        this.x = 0;
        this.y = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean contains(int px, int py) {
        int radius = 20 / 2;  // Assuming a fixed size for the vertex
        return Math.pow(px - x, 2) + Math.pow(py - y, 2) <= Math.pow(radius, 2);
    }
}
