package animate;

import graph.Vertex;

public class VertexLogElement extends logging.LogElement{
    protected long value;

    protected final Vertex vertex;

    public VertexLogElement() {
        super();
        value = 0;
        vertex = null;
    }

    public VertexLogElement(int step, String description, long value, Vertex vertex){
        this.step=step;
        this.description=description;
        this.value=value;
        this.vertex = vertex;
    }

    public long getValue(){
        return value;
    }

    public Vertex getVertex(){
        return vertex;
    }
}
