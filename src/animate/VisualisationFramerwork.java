package animate;

import logging.LogElementList;
import testApplication.*;
import visualization.HybridWindow;

import javax.swing.*;

public class VisualisationFramerwork {

    public VisualisationFramerwork() {
        super();
    }

    public static void main(String[]args){
        LogElementList<TestLogElement> logList=new LogElementList<TestLogElement>();
        TestParameterArea parameterArea=new TestParameterArea();
        TestDrawArea drawArea=new TestDrawArea(logList,"visualization");
        TestTextArea textArea=new TestTextArea(logList);
        TestAlgorithm algorithm=new TestAlgorithm(parameterArea);
        TestLegendArea legendArea=new TestLegendArea();
        HybridWindow<TestDrawArea, TestTextArea, TestParameterArea, TestAlgorithm, TestLogElement, TestLegendArea> applet=new HybridWindow<TestDrawArea,TestTextArea,TestParameterArea,TestAlgorithm,TestLogElement,TestLegendArea>(drawArea,textArea,parameterArea,algorithm,logList,legendArea);


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
