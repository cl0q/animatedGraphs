package animate;

import logging.Algorithm;
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
        if(getSelectedAlgorithm().equals("AlgorithmDepthSearchRecursive"))
            redrawUndirectedGraph(g);
        else
            redrawDirectedGraph(g);
    }

    /**
     * Zeichnet den ungerichteten Graphen neu.
     *
     * @param g Graphics-Objekt zur Zeichnung
     */
    private void redrawUndirectedGraph(Graphics g) {
        VertexLogElement<?> vertexLogElement = (VertexLogElement<?>) this.logList.get();
        Vertex vertex = vertexLogElement.getVertex();

        tryResetVertex(vertexLogElement);

        updateVertex(vertex);

        previousVertexColors = new Pair<>(vertexLogElement, vertex.getColor());

        Graph graph = new Graph(vertexes, edges, isDirected, EdgeStyle.Direct);
        graph.draw(g);
    }

    /**
     * Zeichnet den gerichteten Graphen neu.
     *
     * @param g Graphics-Objekt zur Zeichnung
     */
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

            // TODO: Add draw of number (and check if correct)
            updateEdge(edge);

            Graph graph = new Graph(vertexes, edges, isDirected, EdgeStyle.Direct);
            graph.draw(g);
        }
    }

    /**
     * Überprüft, ob der Knoten zurückgesetzt werden muss in seinen
     * Ursprungszustand bei einem Rückwärts durchlauf der LogElementList.
     *
     * @param logElement das aktuelle LogElement
     */
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

    /**
     * Aktualisiert den Knoten in der Liste der Knoten.
     * Entfernt den Knoten, wenn er bereits existiert und fügt ihn dann mit aktualisierter Farbe wieder hinzu.
     *
     * @param vertex der zu aktualisierende Knoten
     */
    private void updateVertex(Vertex vertex) {
        removeIfVertexExists(vertex);
        vertexes.add(vertex);
    }

    /**
     * Aktualisiert die Kante in der Liste der Kanten.
     * Entfernt die Kante, wenn sie bereits existiert und fügt sie dann mit aktualisierter Farbe wieder hinzu.
     *
     * @param edge die zu aktualisierende Kante
     */
    private void updateEdge(Edge edge) {
        removeIfEdgeExists(edge);
        edges.add(edge);
    }

    /**
     * Entfernt den Knoten aus der Liste der Knoten, wenn er bereits mit denselben Attributen, exkludiert der Farbe,
     * in der Liste existiert.
     *
     * @param vertex der zu überprüfende Knoten
     */
    private void removeIfVertexExists(Vertex vertex) {
        vertexes.removeIf(v ->
                v.getXpos() == vertex.getXpos()
                        && v.getYpos() == vertex.getYpos()
                        && v.getMarking().equals(vertex.getMarking()));
    }

    /**
     * Entfernt die Kante aus der Liste der Kanten, wenn sie bereits mit denselben Attributen, exkludiert der Farbe,
     * in der Liste existiert.
     *
     * @param edge die zu überprüfende Kante
     */
    private void removeIfEdgeExists(Edge edge) {
        edges.removeIf(v ->
                v.getSource() == edge.getSource()
                        && v.getDestination() == edge.getDestination()
                        && v.getMarking().equals(edge.getMarking()));
    }

    /**
     * @return der Name des derzeitig ausgewählten Algorithmus
     */
    private String getSelectedAlgorithm() {
        return algorithm.getTitle();
    }
}
