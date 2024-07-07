package graph.marking;

import graph.Vertex;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VertexColorMarking extends VertexMarking {

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
}
