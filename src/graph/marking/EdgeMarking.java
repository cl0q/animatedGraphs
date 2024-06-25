package graph.marking;

import graph.Edge;

import java.util.ArrayList;
import java.util.List;

public abstract class EdgeMarking extends Marking {

    private final List<Edge> markedEdges = new ArrayList<>();

    public final void mark(final Edge edge) {
        markedEdges.add(edge);
    }
    
    public final void unmark(final Edge edge) {
        markedEdges.remove(edge);
    }
    
    public final boolean isMarked(final Edge edge) {
        return markedEdges.contains(edge);
    }

    public final List<Edge> getMarked() {
        return markedEdges;
    }

    public String toString() {
        return "EdgeMarking{" +
                "marked='" + getMarked() + '\'' +
                '}';
    }
}
