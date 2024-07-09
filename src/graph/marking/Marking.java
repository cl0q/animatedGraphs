package graph.marking;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementierung der Speicherlogik für die Farben-Markierung.
 */
public abstract class Marking {

    public static final Color DEFAULT_COLOR = Color.BLACK;
    public static final Color STARTING_COLOR = Color.RED;
    public static final Color CURRENT_COLOR = Color.YELLOW;
    public static final Color NEIGHBOR_COLOR = Color.BLUE;
    public static final Color CYCLE_COLOR = Color.MAGENTA;
    public static final Color EDGE_VISISTED_COLOR = Color.ORANGE;
    public static final Color FINISHED_COLOR = Color.GREEN;

    // Mapping der Knoten/Kanten (Vertex/Edge) mit ihrer dazugehörigen Farbe
    private final Map<Object, Color> objectColorMap = new HashMap<>();

    /**
     * Gibt die Farbe der Markierung zurück.
     *
     * @param obj das Objekt, dessen Farbe zurückgegeben werden soll
     * @return die Farbe der Markierung falls Object vorhanden, andernfalls DEFAULT_COLOR
     */
    public final Color getColor(final Object obj) {
        return objectColorMap.getOrDefault(obj, DEFAULT_COLOR);
    }
    /**
     * Setzt die Farbe der Markierung.
     *
     * @param obj das Objekt, dessen Farbe gesetzt werden soll
     * @param color die Farbe, die gesetzt werden soll
     */
    public final void setColor(final Object obj, final Color color) {
        objectColorMap.put(obj, color);
    }
    public abstract String toString();
}
