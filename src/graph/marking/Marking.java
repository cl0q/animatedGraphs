package graph.marking;

import java.awt.*;

/**
 * Abstrakte Klasse, welche die Farben der Markierungen speichert.
 */
public abstract class Marking {

    /**
     * Gibt die Farbe der Markierung zurück.
     *
     * @param obj das Objekt, dessen Farbe zurückgegeben werden soll
     * @return die Farbe der Markierung
     */
    public abstract Color getColor(final Object obj);

    /**
     * Setzt die Farbe der Markierung.
     * @param obj das Objekt, dessen Farbe gesetzt werden soll
     * @param color die Farbe, die gesetzt werden soll
     */
    public abstract void setColor(final Object obj, final Color color);

    public abstract String toString();
}
