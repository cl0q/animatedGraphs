package animate;

import graph.Edge;
import graph.Vertex;
import logging.LogElementList;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class DrawHelper {

    private final GraphDrawer graphDrawer;

    public DrawHelper(GraphDrawer graphDrawer) {
        this.graphDrawer = graphDrawer;
    }

    public Vector<visualizationElements.Vertex> redraw(LogElementList<logging.LogElement> loglist) {
        Vector<visualizationElements.Vertex> tempVertex = new Vector<>();

        if (!loglist.isInitialized()) {
            System.err.println("Log list not initialized");
            return tempVertex;
        }

        System.out.println("Log list size: " + loglist.size());
        for (int i = 0; i < loglist.size(); i++) {
            VertexLogElement logElement = (VertexLogElement) loglist.get(i);
            Vertex graphVertex = graphDrawer.getMarkedVertices().get(i);
            int posX = graphVertex.getX();
            int posY = graphVertex.getY();
            String marking = graphVertex.getName();
            Color color;

            switch ((int) logElement.getValue()) {
                case 0: // GRAY, unvisited
                    color = Color.GRAY;
                    System.out.println("Vertex " + i + " is unvisited");
                    break;
                case 1: // RED, visited
                    color = Color.RED;
                    System.out.println("Vertex " + i + " is visited");
                    break;
                case 2: // YELLOW, current
                    color = Color.YELLOW;
                    System.out.println("Vertex " + i + " is current");
                    break;
                case 3: // GREEN, finished
                    color = Color.GREEN;
                    System.out.println("Vertex " + i + " is finished");
                    break;
                default:
                    color = Color.BLACK;
                    System.err.println("Unknown state for vertex " + i);
                    break;
            }

            tempVertex.add(new visualizationElements.Vertex(posX, posY, marking, color));
        }

        return tempVertex;
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

        return graphDrawer.edgeTypeComboBox.getSelectedItem().equals("Directed");
    }
}
