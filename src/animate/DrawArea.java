package animate;

import logging.LogElementList;
import visualizationElements.*;

import java.awt.*;
import java.util.Vector;

public class DrawArea extends visualization.DrawArea{
    DrawHelper drawHelper;

    private static final long serialVersionUID = 1L;

    public DrawArea() {
        super();
    }

    public DrawArea(LogElementList<logging.LogElement> logList, String drawAreaName, DrawHelper drawHelper){
        super(logList, drawAreaName);
        this.drawHelper = drawHelper;
    }

    public void draw(Graphics g){
        drawGraph(g);
    }

    private void drawGraph(Graphics g) {

        // create/add vertexes
        Vector<Vertex> vertexes = new Vector<Vertex>();
        Vector<Edge> edges = new Vector<Edge>();

        boolean isDirected = drawHelper.setGraph(vertexes, edges);
        vertexes = drawHelper.redraw(logList, vertexes);

        // create graph
        Graph graph = new Graph(vertexes, edges, isDirected, EdgeStyle.Direct);

        //drawHelper.redraw(logList, vertexes);
        graph.draw(g);
    }
}
