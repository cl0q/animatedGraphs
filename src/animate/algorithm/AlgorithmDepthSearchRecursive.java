package animate.algorithm;

import animate.drawing.GraphDrawer;
import animate.drawing.GraphLogElement;
import graph.marking.edge.EdgeColorMarking;
import graph.marking.vertex.MarkedVertex;
import graph.marking.vertex.VertexColorMarking;
import graph.structure.graph.UndirectedGraph;
import logging.LogElementList;

/**
 * Implementiert einen rekursiven Tiefensuche-Algorithmus.
 * Diese Klasse erweitert die Algorithm-Klasse und überschreibt die run-Methode,
 * um die Tiefensuche auf einem ungerichteten Graphen auszuführen.
 */
public class AlgorithmDepthSearchRecursive extends logging.Algorithm {

    final GraphDrawer graphDrawer;
    final UndirectedGraph<VertexColorMarking, EdgeColorMarking> undirectedGraph;
    MarkedVertex<VertexColorMarking> selectedVertexFromComboBox;

    /**
     * Konstruktor für den rekursiven Tiefensuche-Algorithmus.
     *
     * @param parameterArea      der Parameterbereich für die Visualisierung
     * @param graphDrawer        der GraphDrawer zur Visualisierung
     * @param undirectedGraph    der ungerichtete Graph
     */
    public AlgorithmDepthSearchRecursive(visualization.ParameterArea parameterArea,
                                         GraphDrawer graphDrawer,
                                         UndirectedGraph<VertexColorMarking, EdgeColorMarking> undirectedGraph) {
        super(parameterArea, "AlgorithmDepthSearchRecursive");
        this.graphDrawer = graphDrawer;
        this.undirectedGraph = undirectedGraph;
    }

    /**
     * Führt den Tiefensuche-Algorithmus aus.
     *
     * @return LogElementList<?> die Liste der Protokollelemente, die während der Ausführung erzeugt wurden
     */
    @Override
    public LogElementList<?> run() {
        LogElementList<?> logList;

        selectedVertexFromComboBox = graphDrawer.getSelectedVertex();
        undirectedGraph.depthSearchRecursive(selectedVertexFromComboBox);
        logList = undirectedGraph.getGraphLogElement();
        undirectedGraph.getGraphLogElement()    // Protokollelement für den Abschluss des Algorithmus
                .add(new GraphLogElement<>(9999,
                        "[" + title + " abgeschlossen!] : " + undirectedGraph.workingOrderArrayToString(),
                        0,
                        ((GraphLogElement<VertexColorMarking, EdgeColorMarking>)logList
                                .getLast())
                                .getGraph()
                                .clone()));
        return logList;
    }
}

