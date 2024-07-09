package animate;

import graph.marking.MarkedVertex;
import graph.marking.VertexMarking;
import visualizationElements.Vertex;

public class VertexLogElement<T extends VertexMarking> extends logging.LogElement{
    protected long value;

    protected final MarkedVertex<T> vertex;

    public VertexLogElement() {
        super();
        value = 0;
        vertex = new MarkedVertex<>();
    }

    public VertexLogElement(int step, String description, long value, MarkedVertex<T> vertex){
        super(step, description);
        this.value=value;
        this.vertex = vertex;
    }

    public long getValue(){
        return value;
    }

    public MarkedVertex<T> getMarkedVertex(){
        return vertex;
    }

    public Vertex getVertex() {
        return new Vertex(vertex.getX(), vertex.getY(), vertex.getMarking().getColor(vertex));
    }
}
