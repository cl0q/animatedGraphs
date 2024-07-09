package animate;

import graph.Graph;
import graph.marking.EdgeMarking;
import graph.marking.VertexMarking;

public class GraphLogElement <T extends VertexMarking, U extends EdgeMarking> extends logging.LogElement {

    protected final long value;

    protected final Graph<T, U> graph;

    /**
     * Erzeugt ein leeres GraphLogElement.
     */
    public GraphLogElement() {
        super();
        value = 0;
        graph = null;
    }

    /**
     * Erzeugt ein GraphLogElement mit Schritt, Beschreibung, Wert und Graphen.
     *
     * @param step der Schritt an dem das LogElement gespeichert wird
     * @param description die Beschreibung des LogElements
     * @param value der Wert des LogElements
     * @param graph der Graph
     */
    public GraphLogElement(int step, String description, long value, Graph<T, U> graph) {
        super(step, description);
        this.value=value;
        this.graph = graph;
    }

    /**
     * @return den Wert des LogElements
     */
    public long getValue() {
        return value;
    }

    /**
     * @return den gerichteten Graphen
     */
    public Graph<T, U> getGraph(){
        return graph;
    }
}
