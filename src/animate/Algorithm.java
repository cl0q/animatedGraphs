package animate;

import graph.marking.MarkedVertex;
import logging.LogElementList;
import testApplication.TestLogElement;

public class Algorithm extends logging.Algorithm{
    public Algorithm() {
        super();
    }

    public Algorithm(visualization.ParameterArea parameterArea) {
        super(parameterArea, "Algorithm");
    }

    @Override
    public LogElementList<TestLogElement> run() {
        LogElementList<TestLogElement> logList = new LogElementList<>();
        logList.add(new VertexLogElement(0, "Step 0", 0, new MarkedVertex()));
        logList.add(new VertexLogElement(1, "Step 1", 1, new MarkedVertex()));
        logList.add(new VertexLogElement(2, "Step 2", 2, new MarkedVertex()));
        logList.add(new VertexLogElement(3, "Step 3", 3, new MarkedVertex()));

        return logList;
    }
}
