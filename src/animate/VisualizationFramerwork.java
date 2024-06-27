package animate;

import logging.LogElement;
import logging.LogElementList;
import testApplication.*;
import visualization.*;

import javax.swing.*;

public class VisualizationFramerwork {

    public VisualizationFramerwork() {
        super();
    }

    public static void init(){
        LogElementList<LogElement> logList= new LogElementList<>();
        ParameterArea parameterArea=new ParameterArea();
        DrawHelper drawHelper=new DrawHelper();
        DrawArea drawArea=new DrawArea(logList,"visualization", drawHelper);
        TextArea textArea=new TextArea(logList);
        Algorithm algorithm=new Algorithm(parameterArea);
        LegendArea legendArea=new LegendArea();
        HybridWindow<DrawArea, TextArea, ParameterArea, Algorithm, LogElement, LegendArea> applet=new HybridWindow<DrawArea,TextArea,ParameterArea,Algorithm,LogElement,LegendArea>(drawArea,textArea,parameterArea,algorithm,logList,legendArea);


        JFrame frame=new JFrame("Visualise");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(applet);
        frame.pack();
        applet.init();
        applet.start();
        frame.setSize(800,600);
        frame.setVisible(true);
    }
}
