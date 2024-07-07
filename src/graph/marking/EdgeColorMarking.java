package graph.marking;

import graph.Edge;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EdgeColorMarking extends EdgeMarking {

    private final Set<Edge> markedEdges;

    public EdgeColorMarking() {
        this.markedEdges = new HashSet<>();
    }

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
}
