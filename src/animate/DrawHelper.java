package animate;

import graph.Edge;
import graph.Vertex;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

public class DrawHelper {

    private final GraphDrawer graphDrawer;

    public DrawHelper(GraphDrawer graphDrawer) {
        this.graphDrawer = graphDrawer;
    }

    public boolean setGraph(Vector<visualizationElements.Vertex> vertexes, Vector<visualizationElements.Edge> edges) {
        // Map to track corresponding visualization vertices
        Map<String, visualizationElements.Vertex> vertexMap = new HashMap<>();

        // Convert graph vertices to visualization vertices and populate the map
        for (Vertex vertex : graphDrawer.getMarkedVertices()) {
            visualizationElements.Vertex visVertex = new visualizationElements.Vertex(vertex.getX(), vertex.getY(), vertex.getName(), Color.BLACK);
            vertexes.add(visVertex);
            vertexMap.put(vertex.getName(), visVertex);
            System.out.println("Vertex " + vertex.getName() + " added" + " at " + vertex.getX() + " " + vertex.getY());
        }

        // Convert graph edges to visualization edges
        for (Edge edge : graphDrawer.getMarkedEdges()) {
            visualizationElements.Vertex source = vertexMap.get(edge.getSource().getName());
            visualizationElements.Vertex destination = vertexMap.get(edge.getDestination().getName());

            if (source != null && destination != null) {
                visualizationElements.Edge visEdge = new visualizationElements.Edge(source, destination, edge.getName(), Color.BLACK);
                edges.add(visEdge);
                System.out.println("Edge " + edge.getName() + " added" + " from " + edge.getSource().getName() + " to " + edge.getDestination().getName());
            } else {
                System.err.println("Error: Source or Destination vertex not found for edge " + edge.getName());
            }
        }

        return Objects.equals(graphDrawer.edgeTypeComboBox.getSelectedItem(), "Directed");
    }
}
