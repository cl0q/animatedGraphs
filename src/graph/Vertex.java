package graph;

/**
 * Implementation eines Knoten.
 */
public abstract class Vertex {

    private String name;
    private int x;
    private int y;

    /**
     * Erzeugt einen neuen Knoten.
     */
    public Vertex() {
        this.name = "";
        this.x = 0;
        this.y = 0;
    }

    /**
     * Erzeugt einen neuen Knoten mit einem Namen.
     * @param s der Name des Knotens
     */
    public Vertex(String s) {
        this.name = s;
        this.x = 0;
        this.y = 0;
    }

    /**
     * Erzeugt einen neuen Knoten mit einem Namen.
     * @param name der Name des Knotens
     * @param x Koordinate des Knotens im Graphen
     * @param y Koordinate des Knotens im Graphen
     */
    public Vertex(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    /**
     * @return den Namen des Knotens
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen des Knotens
     * @param name der neue Name des Knotens
     */
    public void setName(final String name) {
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
