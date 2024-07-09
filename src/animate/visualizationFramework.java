package animate;

import logging.LogElement;
import logging.LogElementList;
import visualization.*;

import javax.swing.*;

/**
 * Die Klasse visualizationFramework initialisiert und startet die Visualisierung für einen gegebenen Algorithmus.
 */
public class visualizationFramework {

    /**
     * Konstruktor für visualizationFramework.
     */
    public visualizationFramework() {
        super();
    }

    /**
     * Initialisiert und startet die Visualisierung des angegebenen Algorithmus.
     *
     * @param algorithm der Algorithmus, der visualisiert werden soll
     * @param parameterArea der Bereich zur Eingabe von Parametern
     * @param graphDrawer das Hauptfenster für die Graph-Zeichnung
     * @param <T> der Typ des Algorithmus, der ausgeführt wird
     */
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

        // Initialisieren und Starten des Applets
        applet.init();
        applet.start();

        frame.setSize(1000, 800);
        frame.setVisible(true);
    }
}
