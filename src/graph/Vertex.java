package graph;

public class Vertex {
    private String name;

    public Vertex() {
        this.name = "";
    }

    public Vertex(String label) {
        this.name = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String label) {
        this.name = label;
    }
}
