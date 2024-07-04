package graph.marking;

import graph.Vertex;

import java.awt.*;
import java.util.*;

/**
 * Implementation einer Knotenmarkierung.
 */
public class VertexColorMarking extends VertexMarking {

    // Liste der markierten Knoten
    private final Map<Vertex, Color> markedVertexes = new HashMap<>();

    @Override
    public void markVertex(Vertex vertex, Color color) {
        markedVertexes.put(vertex, color);
    }

    @Override
    public void unmarkVertex(Vertex vertex) {
        markedVertexes.remove(vertex);
    }

    @Override
    public boolean isVertexMarked(Vertex vertex) {
        return markedVertexes.containsKey(vertex);
    }

    @Override
    public Set<Vertex> getMarkedVertexes() {
        return markedVertexes.keySet();
    }

    @Override
    public Color getColor(final Object vertex) {
        if(vertex instanceof Vertex) {
            return markedVertexes.get(vertex);
        }
        return null;
    }

    @Override
    public void setColor(final Object vertex, Color color) {
        if(vertex instanceof Vertex) {
            markedVertexes.put((Vertex) vertex, color);
        }
    }

    /**
     * Leert die Liste der markierten Knoten.
     */
    public void resetMarkedVertexes() {
        markedVertexes.clear();
    }
}
