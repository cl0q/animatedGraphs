package animate.algorithm;

import animate.drawing.GraphDrawer;
import animate.drawing.GraphLogElement;
import graph.structure.graph.UndirectedGraph;
import graph.marking.edge.EdgeColorMarking;
import graph.marking.vertex.MarkedVertex;
import graph.marking.vertex.VertexColorMarking;
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
    public LogElementList<?> run() {
        LogElementList<?> logList;

        selectedVertexFromComboBox = graphDrawer.getSelectedVertex();

        undirectedGraph.depthSearchRecursive(selectedVertexFromComboBox);

        logList = undirectedGraph.getGraphLogElement();

        undirectedGraph.getGraphLogElement()
                .add(new GraphLogElement<>(9999,
                "[" + title + " done!] : " + undirectedGraph.workingOrderArrayToString(),
                0,
                ((GraphLogElement<VertexColorMarking, EdgeColorMarking>)logList
                        .getLast())
                        .getGraph()
                        .clone()));
        return logList;
    }
}
