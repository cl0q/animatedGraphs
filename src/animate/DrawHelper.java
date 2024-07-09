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

    /**
     * Konstruktor für DrawHelper.
     *
     * @param graphDrawer der Zeichner für den Graphen
     */
    public DrawHelper(GraphDrawer graphDrawer) {
        this.graphDrawer = graphDrawer;
    }

    /**
     * Setzt die Knoten und Kanten des Graphen.
     *
     * @param vertexes die Liste der Knoten (Vertices)
     * @param edges die Liste der Kanten (Edges)
     * @return true, wenn der Graph gerichtet ist, sonst false
     */
    public boolean setGraph(Vector<visualizationElements.Vertex> vertexes, Vector<visualizationElements.Edge> edges) {
        Map<String, visualizationElements.Vertex> vertexMap = new HashMap<>();

        // Hinzufügen der markierten Knoten
        for (Vertex vertex : graphDrawer.getMarkedVertices()) {
            visualizationElements.Vertex visVertex = new visualizationElements.Vertex(vertex.getX(), vertex.getY(), vertex.getName(), Color.BLACK);
            vertexes.add(visVertex);
            vertexMap.put(vertex.getName(), visVertex);
            System.out.println("Vertex " + vertex.getName() + " hinzugefügt bei " + vertex.getX() + " " + vertex.getY());
        }

        // Hinzufügen der markierten Kanten
        for (Edge edge : graphDrawer.getMarkedEdges()) {
            visualizationElements.Vertex source = vertexMap.get(edge.getSource().getName());
            visualizationElements.Vertex destination = vertexMap.get(edge.getDestination().getName());

            // Überprüfen, ob die Quell- und Zielknoten vorhanden sind
            if (source != null && destination != null) {
                visualizationElements.Edge visEdge = new visualizationElements.Edge(source, destination, edge.getName(), Color.BLACK);
                edges.add(visEdge);
                System.out.println("Edge " + edge.getName() + " hinzugefügt von " + edge.getSource().getName() + " zu " + edge.getDestination().getName());
            } else {
                System.err.println("Fehler: Quell- oder Zielknoten nicht gefunden für Kante " + edge.getName());
            }
        }

        // Überprüfen, ob der Graph gerichtet ist
        return Objects.equals(graphDrawer.edgeTypeComboBox.getSelectedItem(), "Directed");
    }
}
