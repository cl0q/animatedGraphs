package graph.structure;

/**
 * Implementation eines Knoten.
 */
public abstract class Vertex {

    private String name;

    /**
     * Erzeugt einen Knoten.
     */
    public Vertex() {
        this.name = "";
    }

    /**
     * Erzeugt einen Knoten mit einem Namen.
     *
     * @param s der Name des Knotens
     */
    public Vertex(String s) {
        this.name = s;
    }

    /**
     * @return den Namen des Knotens
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen des Knotens.
     *
     * @param name der neue Name des Knotens
     */
    public void setName(final String name) {
        this.name = name;
    }
}
