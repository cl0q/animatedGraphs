package animate;

import logging.LogElementList;
import visualizationElements.Edge;
import visualizationElements.Vertex;

import java.awt.*;
import java.util.Vector;

public class DrawHelper {
    private GraphDrawer graphDrawer;

    public DrawHelper(GraphDrawer graphDrawer) {
        this.graphDrawer = graphDrawer;
    }

    public Vector<visualizationElements.Vertex> redraw(LogElementList<logging.LogElement> loglist, Vector<Vertex> vertex) {
        Vector<visualizationElements.Vertex> tempVertex = new Vector<visualizationElements.Vertex>();
        if (loglist.isInitialized()) {
            System.out.println(loglist.size());
            for (int i = 0; i < loglist.size(); i++) {
                LogElement logElement = (LogElement) loglist.get(i);
                switch ((int) logElement.getValue()) {
                    case 0: // GRAY, unvisited
                        tempVertex.add(new Vertex(vertex.get(i).getXpos(), vertex.get(i).getYpos(), graphDrawer.getVertexNames()[i], Color.GRAY));
                        System.out.println("Vertex " + i + " is unvisited");
                        break;
                    case 1: // RED, visited
                        tempVertex.add(new Vertex(vertex.get(i).getXpos(), vertex.get(i).getYpos(), graphDrawer.getVertexNames()[i], Color.RED));
                        System.out.println("Vertex " + i + " is visited");
                        break;
                    case 2: // YELLOW, current
                        tempVertex.add(new Vertex(vertex.get(i).getXpos(), vertex.get(i).getYpos(), graphDrawer.getVertexNames()[i], Color.YELLOW));
                        System.out.println("Vertex " + i + " is current");
                        break;
                    case 3: // GREEN, finished
                        tempVertex.add(new Vertex(vertex.get(i).getXpos(), vertex.get(i).getYpos(), "Marking" + i, Color.GREEN));
                        System.out.println("Vertex " + i + " is finished");
                        break;
                }
            }
        }

        return tempVertex;
    }

    public void setGraph(Vector<Vertex> vertexes, Vector<Edge> edges) {
        for (GraphDrawer.Vertex vertex : graphDrawer.getVertices()) {
            vertexes.add(new Vertex(vertex.getX(), vertex.getY(), vertex.getName(), Color.BLACK));
            System.out.println("Vertex " + vertex.getName() + " added" + " at " + vertex.getX() + " " + vertex.getY());
        }

        for(GraphDrawer.Edge edge : graphDrawer.getEdges()){
            edges.add(new Edge(edge.getSource(), edge.getDestination(), edge.getName(), Color.BLACK));
            System.out.println("Vertex " + edge.getName() + " added" + " at " + edge.getSource() + " " + edge.getDestination());
        }
    }
}
