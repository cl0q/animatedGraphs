package animate;

import graph.DirectedGraph;
import graph.marking.EdgeMarking;
import graph.marking.MarkedVertex;
import graph.marking.VertexMarking;
import visualizationElements.Vertex;

public class DirectedGraphLogElement<T extends VertexMarking, U extends EdgeMarking> extends logging.LogElement {

    protected final long value;

    protected final DirectedGraph<T, U> directedGraph;

    /**
     * Erzeugt ein neues leeres VertexLogElement.
     */
    public DirectedGraphLogElement() {
        super();
        value = 0;
        directedGraph = new DirectedGraph<>();
    }

    /**
     * Erzeugt ein neues VertexLogElement mit Schritt, Beschreibung, Wert und markiertem Knoten.
     *
     * @param step der Schritt an dem das LogElement gespeichert wird
     * @param description die Beschreibung des LogElements
     * @param value der Wert des LogElements
     * @param directedGraph der gerichtete Graph
     */
    public DirectedGraphLogElement(int step, String description, long value, DirectedGraph<T, U> directedGraph) {
        super(step, description);
        this.value=value;
        this.directedGraph = directedGraph;
    }

    /**
     * @return den Wert des LogElements
     */
    public long getValue() {
        return value;
    }

    /**
     * @return den markierten Knoten
     */
    public DirectedGraph<T, U> getDirectedGraph(){
        return directedGraph;
    }
}

