package graph.marking;

import graph.Vertex;

import java.awt.*;
import java.util.*;

// TODO: Refactor logic
public class VertexColorMarking extends VertexMarking {

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

    public void resetMarkedVertexes() {
        markedVertexes.clear();
    }
}
