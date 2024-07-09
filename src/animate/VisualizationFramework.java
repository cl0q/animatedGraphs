package animate;

import animate.drawing.DrawHelper;
import animate.drawing.GraphDrawer;
import animate.drawing.TextArea;
import logging.LogElement;
import logging.LogElementList;
import visualization.*;

import javax.swing.*;

public class VisualizationFramework {

    public VisualizationFramework() {
        super();
    }

    public static <T extends logging.Algorithm> void init(T algorithm, animate.drawing.ParameterArea parameterArea, GraphDrawer graphDrawer) {
        LogElementList<LogElement> logList = new LogElementList<>();
        DrawHelper drawHelper = new DrawHelper(graphDrawer);
        animate.drawing.DrawArea drawArea = new animate.drawing.DrawArea(logList, "visualization", drawHelper, algorithm);
        animate.drawing.TextArea textArea = new animate.drawing.TextArea(logList);
        animate.drawing.LegendArea legendArea = new animate.drawing.LegendArea();
        HybridWindow<animate.drawing.DrawArea, TextArea, animate.drawing.ParameterArea, T, LogElement, animate.drawing.LegendArea> applet = new HybridWindow<>(drawArea, textArea, parameterArea, algorithm, logList, legendArea);

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
