package animate;

import graph.Vertex;
import graph.marking.EdgeMarking;
import graph.marking.MarkedEdge;
import visualizationElements.Edge;

public class EdgeLogElement<U extends EdgeMarking> extends logging.LogElement{
    protected long value;

    protected final MarkedEdge<U> edge;

    public EdgeLogElement() {
        super();
        value = 0;
        edge = new MarkedEdge<>();
    }

    public EdgeLogElement(int step, String description, long value, MarkedEdge<U> edge){
        super(step, description);
        this.value = value;
        this.edge = edge;
    }

    public long getValue(){
        return value;
    }

    public MarkedEdge<U> getMarkedEdge(){
        return edge;
    }

    public Edge getEdge() {
        return new Edge(convertVertex(edge.getSource()), convertVertex(edge.getDestination()), edge.getName(), edge.getMarking().getColor(edge));
    }

    private visualizationElements.Vertex convertVertex(Vertex vertex) {
        return new visualizationElements.Vertex(vertex.getX(), vertex.getY(), vertex.getName());
    }
}
