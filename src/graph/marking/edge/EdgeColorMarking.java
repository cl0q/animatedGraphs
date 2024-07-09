package graph.marking.edge;

import graph.marking.Marking;
import graph.structure.Edge;

import java.awt.*;
import java.util.*;

/**
 * Implementation einer Kantenmarkierung.
 */
public final class EdgeColorMarking extends EdgeMarking {

    private final Set<Edge> markedEdges = new HashSet<>(); // Set den markierten Kanten

    @Override
    public void markEdge(Edge edge, Color color) {
        setColor(edge, color);
        markedEdges.add(edge);
    }

    @Override
    public void unmarkEdge(Edge edge) {
        setColor(edge, Marking.DEFAULT_COLOR);
        markedEdges.remove(edge);
    }

    @Override
    public boolean isEdgeMarked(Edge edge) {
        return markedEdges.contains(edge);
    }

    @Override
    public Set<Edge> getMarkedEdges() {
        return markedEdges;
    }

    /**
     * Leert die Liste der markierten Kanten.
     */
    public void resetMarkedEdges() {
        markedEdges.clear();
    }
}
