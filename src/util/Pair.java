package util;

/**
 * Klasse, die ein immutable Paar von zwei Objekten darstellt.
 *
 * @param <X> der Typ des ersten Objekts
 * @param <Y> der Typ des zweiten Objekts
 */
public final class Pair<X, Y> {

    private final X x; // First Element
    private final Y y; // Second Element

    /**
     * Erzeugt ein neues leeres Paar.
     */
    public Pair() {
        this.x = null;
        this.y = null;
    }

    /**
     * Erzeugt ein neues Paar mit den angegebenen Objekten.
     *
     * @param x das erste Objekt
     * @param y das zweite Objekt
     */
    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return das erste Objekt
     */
    public X getFirst() {
        return this.x;
    }

    /**
     * @return das zweite Objekt
     */
    public Y getSecond() {
        return this.y;
    }
}