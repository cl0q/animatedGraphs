package animate;

import graph.UndirectedGraph;
import graph.marking.EdgeColorMarking;
import graph.marking.MarkedVertex;
import graph.marking.VertexColorMarking;
import logging.LogElementList;

public class AlgorithmDepthSearchRecursive extends logging.Algorithm {

    final GraphDrawer graphDrawer;
    final UndirectedGraph<VertexColorMarking, EdgeColorMarking>  undirectedGraph;
    MarkedVertex<VertexColorMarking> selectedVertexFromComboBox;

    public AlgorithmDepthSearchRecursive(visualization.ParameterArea parameterArea,
                                         GraphDrawer graphDrawer,
                                         UndirectedGraph<VertexColorMarking, EdgeColorMarking> undirectedGraph) {
        super(parameterArea, "AlgorithmDepthSearchRecursive");
        this.graphDrawer = graphDrawer;
        this.undirectedGraph = undirectedGraph;
    }

    @Override
    public LogElementList<VertexLogElement<VertexColorMarking>> run() {
        LogElementList<VertexLogElement<VertexColorMarking>> logList;

        selectedVertexFromComboBox = graphDrawer.getSelectedVertex();

        undirectedGraph.depthSearchRecursive(selectedVertexFromComboBox);

        logList = undirectedGraph.getVertexLogElementList();

        return logList;
    }
}
