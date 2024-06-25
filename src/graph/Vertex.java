package graph;

public abstract class Vertex {

    private String name;

    public Vertex() {
        this.name = "";
    }

    public Vertex(final String s) {
        this.name = s;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
