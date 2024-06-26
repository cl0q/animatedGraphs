package graph.marking;

import graph.Edge;

import java.awt.*;
import java.util.*;

public class EdgeColorMarking extends EdgeMarking {

    private final Map<Edge, Color> markedEdges = new HashMap<>();

    @Override
    public void markEdge(Edge edge, Color color) {
        markedEdges.put(edge, color);
    }

    @Override
    public void unmarkEdge(Edge edge) {
        markedEdges.remove(edge);
    }

    @Override
    public boolean isEdgeMarked(Edge edge) {
        return markedEdges.containsKey(edge);
    }

    @Override
    public Set<Edge> getMarkedEdges() {
        return markedEdges.keySet();
    }

    @Override
    public Color getColor(final Object edge) {
        if(edge instanceof Edge) {
            return markedEdges.get(edge);
        }
        return null;
    }

    @Override
    public void setColor(Object edge, Color color) {
        if(edge instanceof Edge) {
            markedEdges.put((Edge) edge, color);
        }
    }

    public void resetMarkedEdges() {
        markedEdges.clear();
    }
}
