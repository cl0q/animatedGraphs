package animate;

import graph.DirectedGraph;
import graph.marking.EdgeColorMarking;
import graph.marking.MarkedVertex;
import graph.marking.VertexColorMarking;
import logging.LogElementList;

public class AlgorithmTopologicalSort extends logging.Algorithm {

    final GraphDrawer graphDrawer;
    final DirectedGraph<VertexColorMarking, EdgeColorMarking> directedGraph;
    MarkedVertex<VertexColorMarking> selectedVertexFromComboBox;

    public AlgorithmTopologicalSort(visualization.ParameterArea parameterArea,
                                    GraphDrawer graphDrawer,
                                    DirectedGraph<VertexColorMarking, EdgeColorMarking> directedGraph) {
        super(parameterArea, "AlgorithmTopologicalSort");
        this.graphDrawer = graphDrawer;
        this.directedGraph = directedGraph;
    }

    @Override
    public LogElementList<?> run() {
        LogElementList<?> logList;

        // TODO: Check if necessary since topSort() is a void method
        selectedVertexFromComboBox = graphDrawer.getSelectedVertex(); // Wählt den Knoten aus, von dem aus der Algorithmus starten soll

        directedGraph.topSort();

        logList = directedGraph.getLogElementList();
        logList = directedGraph.getDirectedGraphLogElementList();

        //directedGraph.getLogElementList().add(new VertexLogElement<>(9999, "[" + title + " done!] : " + directedGraph.workingOrderArrayToString(), 0, ((VertexLogElement<VertexColorMarking>)logList.getLast()).getMarkedVertex().clone()));

        return logList;
    }
}
