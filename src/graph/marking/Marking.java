package graph.marking;

import java.awt.*;

// TODO: Check if implementation is correct/wanted like this
public abstract class Marking {

    public abstract Color getColor(final Object obj);

    public abstract void setColor(final Object obj, final Color color);

    public abstract String toString();
}
