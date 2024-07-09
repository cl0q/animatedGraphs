package animate;

import logging.LogElement;
import logging.LogElementList;
import visualization.*;

import javax.swing.*;

public class visualizationFramework {

    public visualizationFramework() {
        super();
    }

    public static <T extends logging.Algorithm> void init(T algorithm, ParameterArea parameterArea, GraphDrawer graphDrawer) {
        LogElementList<LogElement> logList = new LogElementList<>();
        DrawHelper drawHelper = new DrawHelper(graphDrawer);
        DrawArea drawArea = new DrawArea(logList, "visualization", drawHelper, algorithm);
        TextArea textArea = new TextArea(logList);
        LegendArea legendArea = new LegendArea();
        HybridWindow<DrawArea, TextArea, ParameterArea, T, LogElement, LegendArea> applet = new HybridWindow<>(drawArea, textArea, parameterArea, algorithm, logList, legendArea);

        JFrame frame = new JFrame("Visualize " + algorithm.getTitle());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(applet);
        frame.pack();
        applet.init();
        applet.start();
        frame.setSize(1000, 800);
        frame.setVisible(true);
    }
}
