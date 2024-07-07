package animate;

import logging.LogElementList;
import testApplication.TestLogElement;

import java.util.Random;

public class AlgorithmTopologicalSort extends logging.Algorithm{
    GraphDrawer graphDrawer;

    public AlgorithmTopologicalSort(visualization.ParameterArea parameterArea, GraphDrawer graphDrawer) {
        super(parameterArea, "AlgorithmTopologicalSort");
        this.graphDrawer = graphDrawer;
    }

    @Override
    public LogElementList run() {
        LogElementList<TestLogElement>logList = new LogElementList<TestLogElement>();
//        logList.add(new LogElement(0, "Step 0", 0));
//        logList.add(new LogElement(1, "Step 1", 1));
//        logList.add(new LogElement(2, "Step 2", 2));
//        logList.add(new LogElement(3, "Step 3", 3));

        for(int i=0; i<graphDrawer.getVertexCount(); i++){
            logList.add(new LogElement(i, graphDrawer.getVertexNames()[i] , randomNumber(0,3)));
            System.out.println(logList.get(i));
        }
        return logList;
    }
    // make a random number generator
    private static int randomNumber(int min, int max){
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
