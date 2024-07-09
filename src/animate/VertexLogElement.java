package animate;

import graph.marking.MarkedVertex;
import graph.marking.VertexMarking;
import visualizationElements.Vertex;

/**
 * LogElement, das einen markierten Knoten enthält.
 *
 * @param <T> die Implementierung der Markierung, die mit dem Knoten verbunden ist
 */
public class VertexLogElement<T extends VertexMarking> extends logging.LogElement {

    protected final long value;

    protected final MarkedVertex<T> vertex;

    /**
     * Erzeugt ein neues leeres VertexLogElement.
     */
    public VertexLogElement() {
        super();
        value = 0;
        vertex = new MarkedVertex<>();
    }

    /**
     * Erzeugt ein neues VertexLogElement mit Schritt, Beschreibung, Wert und markiertem Knoten.
     *
     * @param step der Schritt an dem das LogElement gespeichert wird
     * @param description die Beschreibung des LogElements
     * @param value der Wert des LogElements
     * @param vertex der markierte Knoten
     */
    public VertexLogElement(int step, String description, long value, MarkedVertex<T> vertex) {
        super(step, description);
        this.value=value;
        this.vertex = vertex;
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
    public MarkedVertex<T> getMarkedVertex(){
        return vertex;
    }

    /**
     * Gibt einen Knoten des visualizationFrameworks zurück.
     * Es wird ein neuer Knoten des visualizationFrameworks erzeugt, indem alle Attribute des markierten Knotens übernommen werden.
     *
     * @return der markierte Knoten als konvertierter visualizationElements.Vertex
     */
    public Vertex getVertex() {
        return new Vertex(vertex.getX(), vertex.getY(), vertex.getMarking().getColor(vertex));
    }
}
