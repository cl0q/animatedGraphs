package graph.marking;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstrakte Klasse, welche die Farben der Markierungen speichert.
 */
public abstract class Marking {

    public static final Color DEFAULT_COLOR = Color.BLACK;

    // Mapping der Knoten/Kanten (Vertex/Edge) mit ihrer dazugehörigen Farbe
    private final Map<Object, Color> objectColorMap = new HashMap<>();

    /**
     * Gibt die Farbe der Markierung zurück.
     *
     * @param obj das Objekt, dessen Farbe zurückgegeben werden soll
     * @return die Farbe der Markierung falls Object vorhanden, sonst DEFAULT_COLOR
     */
    public final Color getColor(final Object obj) {
        return objectColorMap.getOrDefault(obj, DEFAULT_COLOR);
    }
    /**
     * Setzt die Farbe der Markierung.
     * @param obj das Objekt, dessen Farbe gesetzt werden soll
     * @param color die Farbe, die gesetzt werden soll
     */
    public final void setColor(final Object obj, final Color color) {
        objectColorMap.put(obj, color);
    }
    public abstract String toString();
}
