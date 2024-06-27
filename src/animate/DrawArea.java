package animate;

import logging.LogElementList;
import testApplication.TestLogElement;
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

        drawHelper.setVertexes(vertexes);
        vertexes = drawHelper.redraw(logList, vertexes);


        // create/add edges
        Vector<Edge> edges = new Vector<Edge>();

        edges.add(new Edge(vertexes.get(0), vertexes.get(1), "a", Color.BLACK));
        edges.add(new Edge(vertexes.get(1), vertexes.get(3), "b", Color.BLACK));
        edges.add(new Edge(vertexes.get(2), vertexes.get(3), "c", Color.BLACK));
        edges.add(new Edge(vertexes.get(0), vertexes.get(2), "d", Color.BLACK));


        // create graph
        Graph graph = new Graph(vertexes, edges, false, EdgeStyle.Direct);

        //drawHelper.redraw(logList, vertexes);
        graph.draw(g);
    }
}
