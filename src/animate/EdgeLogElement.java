package animate;

import graph.Vertex;
import graph.marking.EdgeMarking;
import graph.marking.MarkedEdge;
import visualizationElements.Edge;

/**
 * LogElement, das eine markierten Kante enthält.
 * @param <U> die Implementierung der Markierung, die mit der Kante verbunden ist
 */
public class EdgeLogElement<U extends EdgeMarking> extends logging.LogElement{

    protected long value;

    protected final MarkedEdge<U> edge;

    /**
     * Erzeugt ein neues leeres EdgeLogElement.
     */
    public EdgeLogElement() {
        super();
        value = 0;
        edge = new MarkedEdge<>();
    }

    /**
     * Erzeugt ein neues leeres EdgeLogElement mit Schritt, Beschreibung, Wert und markierter Kante.
     *
     * @param step der Schritt an dem das LogElement gespeichert wird
     * @param description die Beschreibung des LogElements
     * @param value der Wert des LogElements
     * @param edge die markierte Kante
     */
    public EdgeLogElement(int step, String description, long value, MarkedEdge<U> edge){
        super(step, description);
        this.value = value;
        this.edge = edge;
    }

    /**
     * @return den Wert des LogElements
     */
    public long getValue(){
        return value;
    }

    /**
     * @return die markierte Kante
     */
    public MarkedEdge<U> getMarkedEdge(){
        return edge;
    }

    /**
     * Gibt eine Kante des visualizationFrameworks zurück.
     * Es wird eine neue Kante des visualizationFrameworks erzeugt, indem alle Attribute der markierten Kante übernommen werden
     * und die Knoten in visualizationElements.Vertex konvertiert werden.
     *
     * @return die markierte Kante als konvertierte visualizationElements.Edge
     */
    public Edge getEdge() {
        return new Edge(convertVertex(edge.getSource()), convertVertex(edge.getDestination()), edge.getName(), edge.getMarking().getColor(edge));
    }

    /**
     * Konvertiert einen graph.Vertex in einrn visualizationElements.Vertex.
     *
     * @param vertex der zu konvertierende Vertex
     * @return der konvertierte Vertex
     */
    private visualizationElements.Vertex convertVertex(Vertex vertex) {
        return new visualizationElements.Vertex(vertex.getX(), vertex.getY(), vertex.getName());
    }
}
