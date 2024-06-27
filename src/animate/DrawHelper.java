package animate;

import logging.LogElementList;
import visualizationElements.Vertex;

import java.awt.*;
import java.util.Vector;

public class DrawHelper {

    public Vector<visualizationElements.Vertex> redraw(LogElementList<logging.LogElement> loglist, Vector<Vertex> vertex) {
        Vector<visualizationElements.Vertex> tempVertex = new Vector<visualizationElements.Vertex>();
        if (loglist.isInitialized()) {
            for (int i = 0; i < loglist.size(); i++) {
                LogElement logElement = (LogElement) loglist.get(i);
                switch ((int) logElement.getValue()) {
                    case 0: // GRAY, unvisited
                        tempVertex.add(new Vertex(vertex.get(i).getXpos(), vertex.get(i).getYpos(), "Marking" + i, Color.GRAY));
                        System.out.println("Vertex " + i + " is unvisited");
                        break;
                    case 1: // RED, visited
                        tempVertex.add(new Vertex(vertex.get(i).getXpos(), vertex.get(i).getYpos(), "Marking" + i, Color.RED));
                        System.out.println("Vertex " + i + " is visited");
                        break;
                    case 2: // YELLOW, current
                        tempVertex.add(new Vertex(vertex.get(i).getXpos(), vertex.get(i).getYpos(), "Marking" + i, Color.YELLOW));
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

    public void setVertexes(Vector<Vertex> vertexes) {
        vertexes.add(new Vertex(20, 20, "1", Color.BLACK));
        vertexes.add(new Vertex(80, 20, "2", Color.BLACK));
        vertexes.add(new Vertex(20, 80, "3", Color.BLACK));
        vertexes.add(new Vertex(80, 80, "4", Color.BLACK));
    }
}
