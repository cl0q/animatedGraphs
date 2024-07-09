package animate.algorithm;

import animate.drawing.GraphLogElement;
import graph.marking.edge.EdgeColorMarking;
import graph.marking.vertex.VertexColorMarking;
import graph.structure.graph.DirectedGraph;
import logging.LogElementList;

/**
 * Implementiert einen Algorithmus zur topologischen Sortierung.
 * Diese Klasse erweitert die Algorithm-Klasse und überschreibt die run-Methode,
 * um die topologische Sortierung auf einem gerichteten Graphen auszuführen.
 */
public class AlgorithmTopologicalSort extends logging.Algorithm {

    final DirectedGraph<VertexColorMarking, EdgeColorMarking> directedGraph;

    /**
     * Konstruktor für den Algorithmus zur topologischen Sortierung.
     *
     * @param parameterArea der Parameterbereich für die Visualisierung
     * @param directedGraph der gerichtete Graph
     */
    public AlgorithmTopologicalSort(visualization.ParameterArea parameterArea,
                                    DirectedGraph<VertexColorMarking, EdgeColorMarking> directedGraph) {
        super(parameterArea, "AlgorithmTopologicalSort");
        this.directedGraph = directedGraph;
    }

    /**
     * Führt den Algorithmus zur topologischen Sortierung aus.
     *
     * @return LogElementList<?> die Liste der Protokollelemente, die während der Ausführung erzeugt wurden
     */
    @Override
    public LogElementList<?> run() {
        LogElementList<?> logList;

        directedGraph.topSort();
        logList = directedGraph.getGraphLogElementList();
        directedGraph.getGraphLogElementList()    // Protokollelement für den Abschluss des Algorithmus
                .add(new GraphLogElement<>(9999,
                        "[" + title + " abgeschlossen!] : " + directedGraph.workingOrderArrayToString(),
                        0,
                        ((GraphLogElement<VertexColorMarking, EdgeColorMarking>) logList
                                .getLast())
                                .getGraph()
                                .clone()));
        return logList;
    }
}

