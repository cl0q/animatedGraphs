package graph.structure;

/**
 * Implementation einer Kante.
 */
public abstract class Edge {

    private String name;
    private Vertex source;
    private Vertex destination;

    /**
     * Erzeugt eine Kante.
     */
    public Edge() {
        this.name = "";
        this.source = null;
        this.destination = null;
    }

    /**
     * Erzeugt eine Kante mit Namen und Knoten.
     *
     * @param name der Name der Kante
     * @param n1 der erste Knoten
     * @param n2 der zweite Knoten
     */
    public Edge(final String name, final Vertex n1, final Vertex n2) {
        this.name = name;
        this.source = n1;
        this.destination = n2;
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
     *
     * @param n der neue Zielknoten
     * @return den Zielknoten
     */
    public Vertex setDestination(final Vertex n) {
        return this.destination = n;
    }

    /**
     * Setzt den Namen der Kante.
     *
     * @param s der neue Name
     */
    public void setName(final String s) {
        this.name = s;
    }

    /**
     * Setzt den Startknoten der Kante.
     *
     * @param n der neue Startknoten
     */
    public void setSource(final Vertex n) {
        this.source = n;
    }
}
