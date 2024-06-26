package graph.marking;

import java.awt.*;

// TODO: Check if implementation is correct/wanted like this
public abstract class Marking {

    private Color color;

    public final Color getColor() {
        return color;
    }

    public final void setColor(final Color color) {
        this.color = color;
    }

    public abstract String toString();
}
