package edge;

public class Edge {
    private String name;
    private Vertex source;
    private Vertex destination;

    public Edge(){
        this.name = "";
        this.source = null;
        this.destination = null;
    }

    public Edge(String name, Vertex n1, Vertex n2){
        this.name = name;
        this.source = n1;
        this.destination = n2;
    }

    public Vertex getDestination() {
        return destination;
    }
    //TODO ist das String oder Vertex?
    public String getName() {
        return name;
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex setDestination(Vertex n) {
        return this.destination = n;
    }
    //TODO ist das void oder Vertex?
    public void setName(String s) {
        this.name = s;
    }
    //TODO ist das void oder Vertex?
    public void setSource(Vertex n) {
        this.source = n;
    }
}
