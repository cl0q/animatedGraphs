package animate;

import graph.DirectedGraph;
import graph.marking.EdgeColorMarking;
import graph.marking.VertexColorMarking;
import logging.LogElementList;

/**
 * Implementiert einen Algorithmus zur topologischen Sortierung.
 * Diese Klasse erweitert die Algorithm-Klasse und überschreibt die run-Methode,
 * um die topologische Sortierung auf einem gerichteten Graphen auszuführen.
 */
public class AlgorithmTopologicalSort extends logging.Algorithm {

    final GraphDrawer graphDrawer;
    final DirectedGraph<VertexColorMarking, EdgeColorMarking> directedGraph;

    /**
     * Konstruktor für den Algorithmus zur topologischen Sortierung.
     *
     * @param parameterArea der Parameterbereich für die Visualisierung
     * @param graphDrawer   der GraphDrawer zur Visualisierung
     * @param directedGraph der gerichtete Graph
     */
    public AlgorithmTopologicalSort(visualization.ParameterArea parameterArea,
                                    GraphDrawer graphDrawer,
                                    DirectedGraph<VertexColorMarking, EdgeColorMarking> directedGraph) {
        super(parameterArea, "AlgorithmTopologicalSort");
        this.graphDrawer = graphDrawer;
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
