package graph;

public abstract class Vertex {
    private String name;

    public Vertex() {

    }

    public Vertex(String s) {
        this.name = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String label) {
        this.name = label;
    }
}
