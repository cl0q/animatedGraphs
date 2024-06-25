package graph.marking;

import graph.Vertex;

import java.util.ArrayList;
import java.util.List;

public abstract class VertexMarking extends Marking {

    private final List<Vertex> markedVertexes = new ArrayList<>();

    public final void mark(final Vertex vertex) {
        markedVertexes.add(vertex);
    }

    public final void unmark(final Vertex vertex) {
        markedVertexes.remove(vertex);
    }

    public final boolean isMarked(final Vertex vertex) {
        return markedVertexes.contains(vertex);
    }

    public final List<Vertex> getMarked() {
        return markedVertexes;
    }

    public String toString() {
        return "VertexMarking{" +
                "marked='" + getMarked() + '\'' +
                '}';
    }
}
