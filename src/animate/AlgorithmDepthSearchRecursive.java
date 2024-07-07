package animate;

import logging.LogElementList;
import testApplication.TestLogElement;

import java.util.Random;

public class AlgorithmDepthSearchRecursive extends logging.Algorithm {
    GraphDrawer graphDrawer;

    public AlgorithmDepthSearchRecursive(visualization.ParameterArea parameterArea, GraphDrawer graphDrawer) {
        super(parameterArea, "AlgorithmDepthSearchRecursive");
        this.graphDrawer = graphDrawer;
    }

    @Override
    public LogElementList run() {
        LogElementList<TestLogElement> logList = new LogElementList<>();

        for (int i = 0; i < graphDrawer.getVertexCount(); i++) {
            logList.add(new LogElement(i, graphDrawer.getVertexNames()[i], randomNumber(0, 3)));
            System.out.println(logList.get(i));
        }
        return logList;
    }

    private static int randomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
