package animate;

import graph.DirectedGraph;
import graph.marking.EdgeColorMarking;
import graph.marking.MarkedVertex;
import graph.marking.VertexColorMarking;
import logging.LogElementList;
import visualization.ParameterArea;

public class AlgorithmTopologicalSort extends logging.Algorithm {
    GraphDrawer graphDrawer;
    DirectedGraph<VertexColorMarking, EdgeColorMarking> directedGraph;
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

        selectedVertexFromComboBox = graphDrawer.getSelectedVertex();

        directedGraph.topSort();

        logList = directedGraph.getLogElementList();

        return logList;
    }
}
