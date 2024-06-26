package graph.marking;

import graph.Edge;

import java.awt.*;
import java.util.List;

public abstract class EdgeMarking extends Marking {

    public abstract void markEdge(final Edge edge, final Color color);
    
    public abstract void unmarkEdge(final Edge edge);
    
    public abstract boolean isEdgeMarked(final Edge edge);

    public abstract List<Edge> getMarkedEdges();

    public String toString() {
        return "EdgeMarking{" +
                "marked='" + getMarkedEdges() + '\'' +
                '}';
    }
}
