package animate;

import graph.marking.VertexColorMarking;
import logging.Algorithm;
import logging.LogElement;
import logging.LogElementList;
import util.Pair;
import visualizationElements.*;

import java.awt.*;
import java.util.Vector;

public class DrawArea extends visualization.DrawArea {
    DrawHelper drawHelper;
    Algorithm algorithm;

    private static final long serialVersionUID = 1L;

    private final Vector<Vertex> vertexes = new Vector<>();
    private final Vector<Edge> edges = new Vector<>();
    private Pair<VertexLogElement<?>, Color> previousVertexColors = new Pair<>();

    private boolean isInitialized = false;
    private boolean isDirected;

    public DrawArea() {
        super();
    }

    public <T extends Algorithm> DrawArea(LogElementList<?> logList, String drawAreaName, DrawHelper drawHelper, T algorithm) {
        super(logList, drawAreaName);
        this.drawHelper = drawHelper;
        this.algorithm = algorithm;
    }

    @Override
    public void draw(Graphics g) {
        if(!isInitialized)
            initGraph(g);
        else
            redrawGraph(g);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (this.logList.isInitialized()) {
            this.draw(g);
        }
    }

    private void initGraph(Graphics g) {
        isInitialized = true;
        isDirected = drawHelper.setGraph(vertexes, edges);
        System.out.println("Vertices count: " + vertexes.size());
        System.out.println("Edges count: " + edges.size());

        if (vertexes.isEmpty()) {
            System.err.println("No vertices to draw");
            return;
        }

        Graph graph = new Graph(vertexes, edges, isDirected, EdgeStyle.Direct);
        graph.draw(g);
    }

    private void redrawGraph(Graphics g) {
        if (vertexes.isEmpty()) {
            System.err.println("No vertices to draw");
            return;
        }
        if(getSelectedAlgorithm().equals("AlgorithmDepthSearchRecursive"))
            redrawUndirectedGraph(g);
        else
            redrawDirectedGraph(g);
    }

    private void redrawUndirectedGraph(Graphics g) {
        VertexLogElement<?> vertexLogElement = (VertexLogElement<?>) this.logList.get();
        Vertex vertex = vertexLogElement.getVertex();

        tryResetVertex(vertexLogElement);

        updateVertex(vertex);

        previousVertexColors = new Pair<>(vertexLogElement, vertex.getColor());

        Graph graph = new Graph(vertexes, edges, isDirected, EdgeStyle.Direct);
        graph.draw(g);
    }

    private void redrawDirectedGraph(Graphics g) {
        Object obj = this.logList.get();
        if(obj instanceof VertexLogElement<?> vertexLogElement) {
            Vertex vertex = vertexLogElement.getVertex();

            tryResetVertex(vertexLogElement);

            updateVertex(vertex);

            previousVertexColors = new Pair<>(vertexLogElement, vertex.getColor());

            Graph graph = new Graph(vertexes, edges, isDirected, EdgeStyle.Direct);
            graph.draw(g);
        } else if(obj instanceof EdgeLogElement<?> edgeLogElement) {
            Edge edge = edgeLogElement.getEdge();

            updateEdge(edge);

            Graph graph = new Graph(vertexes, edges, isDirected, EdgeStyle.Direct);
            graph.draw(g);
        }
    }

    private void tryResetVertex(final VertexLogElement<?> logElement) {
        if(previousVertexColors.getFirst() == null) {
            return;
        }
        if(logList.indexOf(logElement) >= logList.indexOf(previousVertexColors.getFirst())) {
            return;
        }
        if (previousVertexColors.getSecond() != Color.BLUE) {
            return;
        }

        Vertex previousVertex = previousVertexColors.getFirst().getVertex();
        previousVertex.setColor(Color.BLACK);

        updateVertex(previousVertex);
    }

    private void updateVertex(Vertex vertex) {
        removeIfVertexExists(vertex);
        vertexes.add(vertex);
    }

    private void updateEdge(Edge edge) {
        removeIfEdgeExists(edge);
        edges.add(edge);
    }

    private void removeIfVertexExists(Vertex vertex) {
        vertexes.removeIf(v ->
                v.getXpos() == vertex.getXpos()
                        && v.getYpos() == vertex.getYpos()
                        && v.getMarking().equals(vertex.getMarking()));
    }

    private void removeIfEdgeExists(Edge edge) {
        edges.removeIf(v ->
                v.getSource() == edge.getSource()
                        && v.getDestination() == edge.getDestination()
                        && v.getMarking().equals(edge.getMarking()));
    }

    private String getSelectedAlgorithm() {
        return algorithm.getTitle();
    }
}
