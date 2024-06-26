package graph.marking;

import graph.Edge;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EdgeColorMarking extends EdgeMarking {

    private final List<Edge> markedEdges = new ArrayList<>();

    public EdgeColorMarking(Color color) {
        setColor(color);
    }

    @Override
    public void markEdge(Edge edge, Color color) {
        setColor(color);
        markedEdges.add(edge);
    }

    @Override
    public void unmarkEdge(Edge edge) {
        markedEdges.remove(edge);
    }

    @Override
    public boolean isEdgeMarked(Edge edge) {
        return markedEdges.contains(edge);
    }

    @Override
    public List<Edge> getMarkedEdges() {
        return markedEdges;
    }
}
