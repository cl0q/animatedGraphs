package animate;

import logging.LogElementList;
import visualizationElements.*;

import java.awt.*;
import java.util.Vector;

public class DrawArea extends visualization.DrawArea {
    DrawHelper drawHelper;

    private static final long serialVersionUID = 1L;

    public DrawArea() {
        super();
    }

    public DrawArea(LogElementList<logging.LogElement> logList, String drawAreaName, DrawHelper drawHelper) {
        super(logList, drawAreaName);
        this.drawHelper = drawHelper;
    }

    public void draw(Graphics g) {
        drawGraph(g);
    }

    private void drawGraph(Graphics g) {
        Vector<visualizationElements.Vertex> vertexes = new Vector<>();
        Vector<visualizationElements.Edge> edges = new Vector<>();

        boolean isDirected = drawHelper.setGraph(vertexes, edges);
        System.out.println("Vertices count: " + vertexes.size());
        System.out.println("Edges count: " + edges.size());

        if (vertexes.isEmpty()) {
            System.err.println("No vertices to draw");
            return;
        }

        vertexes = drawHelper.redraw(logList);

        Graph graph = new Graph(vertexes, edges, isDirected, EdgeStyle.Direct);
        graph.draw(g);
    }
}
