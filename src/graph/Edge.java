package graph;

public abstract class Edge {

    private String name;
    private Vertex source;
    private Vertex destination;

    public Edge() {
        this.name = "";
        this.source = null;
        this.destination = null;
    }

    public Edge(final String name, final Vertex n1, final Vertex n2) {
        this.name = name;
        this.source = n1;
        this.destination = n2;
    }

    public Vertex getDestination() {
        return destination;
    }

    public String getName() {
        return name;
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex setDestination(final Vertex n) {
        return this.destination = n;
    }

    public void setName(final String s) {
        this.name = s;
    }

    public void setSource(final Vertex n) {
        this.source = n;
    }
}
