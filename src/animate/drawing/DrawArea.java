package animate.drawing;

import graph.marking.edge.EdgeColorMarking;
import graph.marking.vertex.VertexColorMarking;
import logging.Algorithm;
import logging.LogElementList;
import visualizationElements.*;

import java.awt.*;
import java.io.Serial;
import java.util.Vector;

public class DrawArea extends visualization.DrawArea {

    DrawHelper drawHelper;
    Algorithm algorithm;

    @Serial
    private static final long serialVersionUID = 1L;

    private final Vector<Vertex> vertexes = new Vector<>();
    private final Vector<Edge> edges = new Vector<>();

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

    /**
     * Initialisiert den Graphen.
     *
     * @param g Graphics-Objekt zur Zeichnung
     */
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

    /**
     * Zeichnet den Graphen neu.
     *
     * @param g Graphics-Objekt zur Zeichnung
     */
    private void redrawGraph(Graphics g) {
        if (vertexes.isEmpty()) {
            System.err.println("No vertices to draw");
            return;
        }
        redraw(g,
                ((GraphLogElement<VertexColorMarking, EdgeColorMarking>) this.logList.get())
                .getGraph());
    }

    /**
     * Zeichnet den Graphen neu.
     *
     * @param g Graphics-Objekt zur Zeichnung
     * @param graph der gezeichnet werden soll
     */
    private void redraw(Graphics g, graph.structure.graph.Graph<?, ?> graph) {
        resetGraph();

        vertexes.addAll(graph.getAllVertexes()
                .stream()
                .map(v ->
                        new Vertex(v.getX(),
                                v.getY(),
                                v.getName(),
                                v.getMarking().getColor(v)))
                .toList());
        edges.addAll(graph.getAllEdges()
                .stream()
                .map(e ->
                        new Edge(convertVertex(e.getSource()),
                                convertVertex(e.getDestination()),
                                e.getName(),
                                e.getMarking().getColor(e)))
                .toList());
        Graph drawGraph = new Graph(vertexes, edges, isDirected, EdgeStyle.Direct);
        drawGraph.draw(g);
    }

    /**
     * Konvertiert einen graph.structure.Vertex in einen visualizationElements.Vertex.
     *
     * @param vertex der zu konvertierende Vertex
     * @return der konvertierte Vertex
     */
    private visualizationElements.Vertex convertVertex(graph.structure.Vertex vertex) {
        return new visualizationElements.Vertex(vertex.getX(), vertex.getY(), vertex.getName());
    }

    /**
     * Setzt den Graphen zurück durch Löschen der Listen.
     */
    private void resetGraph() {
        vertexes.clear();
        edges.clear();
    }

    /**
     * @return der Name des derzeitig ausgewählten Algorithmus
     */
    private String getSelectedAlgorithm() {
        return algorithm.getTitle();
    }
}
