package graph.marking;

import graph.Edge;

import java.awt.*;
import java.util.*;

/**
 * Implementation einer Kantenmarkierung.
 */
public final class EdgeColorMarking extends EdgeMarking {

    // Set den markierten Kanten
    private final Set<Edge> markedEdges = new HashSet<>();

    @Override
    public void markEdge(Edge edge, Color color) {
        setColor(edge, color);
        markedEdges.add(edge);
    }

    @Override
    public void unmarkEdge(Edge edge) {
        setColor(edge, DEFAULT_COLOR);
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
