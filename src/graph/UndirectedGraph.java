package graph;

public class UndirectedGraph <T extends VertexMarking, U extends EdgeMarking> {
    String name;

    public UndirectedGraph() {
        this.name = "";
    }

    public UndirectedGraph(String s) {
        this.name = s;
    }

    public int degree(MarkedVertex<T> n) {
        
    }
}
