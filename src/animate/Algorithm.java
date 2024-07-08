package animate;

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
    public LogElementList run() {
        LogElementList<TestLogElement>logList = new LogElementList<TestLogElement>();
        // TODO: outcommented
//        logList.add(new VertexLogElement(0, "Step 0", 0));
//        logList.add(new VertexLogElement(1, "Step 1", 1));
//        logList.add(new VertexLogElement(2, "Step 2", 2));
//        logList.add(new VertexLogElement(3, "Step 3", 3));

        return logList;
    }
}
