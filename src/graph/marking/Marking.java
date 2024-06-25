package graph.marking;

import java.util.List;

public abstract class Marking {

    public abstract void mark();

    public abstract void unmark();

    public abstract boolean isMarked();

    public abstract List<?> getMarked();

    public abstract String toString();
}
