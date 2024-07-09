package graph.marking.vertex;

import graph.structure.Vertex;

import java.awt.*;
import java.util.*;

/**
 * Implementation einer Knotenmarkierung.
 */
public final class VertexColorMarking extends VertexMarking {

    // Liste der markierten Knoten
    private final Set<Vertex> markedVertexes;

    public VertexColorMarking() {
        this.markedVertexes = new HashSet<>();
    }

    @Override
    public void markVertex(Vertex vertex, Color color) {
        setColor(vertex, color);
        markedVertexes.add(vertex);
    }

    @Override
    public void unmarkVertex(Vertex vertex) {
        setColor(vertex, DEFAULT_COLOR);
        markedVertexes.remove(vertex);
    }

    @Override
    public boolean isVertexMarked(Vertex vertex) {
        return markedVertexes.contains(vertex);
    }

    @Override
    public Set<Vertex> getMarkedVertexes() {
        return markedVertexes;
    }

    /**
     * Leert die Liste der markierten Knoten.
     */
    public void resetMarkedVertexes() {
        markedVertexes.clear();
    }
}
