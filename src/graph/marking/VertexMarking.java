package graph.marking;

import graph.Vertex;

import java.awt.*;
import java.util.Set;

public abstract class VertexMarking extends Marking {

    public abstract void markVertex(final Vertex vertex, final Color color);

    public abstract void unmarkVertex(final Vertex vertex);

    public abstract boolean isVertexMarked(final Vertex vertex);

    public abstract Set<Vertex> getMarkedVertexes();

    public String toString() {
        return "VertexMarking{" +
                "marked='" + getMarkedVertexes() + '\'' +
                '}';
    }
}
