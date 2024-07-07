package graph.marking;

import graph.Vertex;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VertexColorMarking extends VertexMarking {

    private final List<Vertex> markedVertexes;

    public VertexColorMarking() {
        this.markedVertexes = new ArrayList<>();
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
    public List<Vertex> getMarkedVertexes() {
        return markedVertexes;
    }
}
