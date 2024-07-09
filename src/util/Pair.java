package util;

public final class Pair<X, Y> {

    private final X x;
    private final Y y;

    public Pair() {
        this.x = null;
        this.y = null;
    }

    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getFirst() {
        return this.x;
    }

    public Y getSecond() {
        return this.y;
    }
}