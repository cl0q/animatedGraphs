package graph.marking;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

// TODO: Check if implementation is correct/wanted like this
public abstract class Marking {

    public static final Color DEFAULT_COLOR = Color.BLACK;

    private final Map<Object, Color> objectColorMap = new HashMap<>();

    public final Color getColor(final Object obj) {
        return objectColorMap.get(obj);
    }

    public final void setColor(final Object obj, final Color color) {
        objectColorMap.put(obj, color);
    }

    public abstract String toString();
}
