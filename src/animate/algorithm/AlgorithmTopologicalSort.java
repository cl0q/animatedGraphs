package animate.algorithm;

import animate.drawing.GraphDrawer;
import animate.drawing.GraphLogElement;
import graph.structure.graph.DirectedGraph;
import graph.marking.edge.EdgeColorMarking;
import graph.marking.vertex.VertexColorMarking;
import logging.LogElementList;

public class AlgorithmTopologicalSort extends logging.Algorithm {

    final GraphDrawer graphDrawer;
    final DirectedGraph<VertexColorMarking, EdgeColorMarking> directedGraph;

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

        directedGraph.topSort();

        logList = directedGraph.getGraphLogElementList();

        directedGraph.getGraphLogElementList().add(new GraphLogElement<>(9999,
                "[" + title + " done!] : " + directedGraph.workingOrderArrayToString(),
                0,
                ((GraphLogElement<VertexColorMarking, EdgeColorMarking>)logList
                        .getLast())
                        .getGraph()
                        .clone()));
        return logList;
    }
}
