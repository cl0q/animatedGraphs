package animate;

import graph.Edge;

public class EdgeLogElement extends logging.LogElement{
    protected long value;

    protected final Edge edge;

    public EdgeLogElement() {
        super();
        value = 0;
        edge = null;
    }

    public EdgeLogElement(int step, String description, long value, Edge edge){
        this.step=step;
        this.description=description;
        this.value=value;
        this.edge = edge;
    }

    public long getValue(){
        return value;
    }

    public Edge getVertex(){
        return edge;
    }
}
