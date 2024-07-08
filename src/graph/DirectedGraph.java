package graph;

import animate.VertexLogElement;
import graph.marking.*;
import logging.LogElementList;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Implementation eines gerichteten Graphen.
 *
 * @param <T> implementierende Klasse der Knotenmarkierung
 * @param <U> implementierende Klasse der Kantenmarkierung
 */
public class DirectedGraph<T extends VertexMarking, U extends EdgeMarking> extends Graph<T, U> {

    private int stepCounter = 0;
    public final LogElementList<VertexLogElement> vertexLogElementList = new LogElementList<>();

    /**
     * Erzeugt einen neuen gerichteten Graphen.
     */
    public DirectedGraph() {
        super();
    }

    /**
     * Erzeugt einen neuen gerichteten Graphen mit einem Namen.
     * @param s der Name des Graphen
     */
    public DirectedGraph(final String s) {
        super(s);
    }

    @Override
    public boolean areAdjacent(MarkedVertex<T> n1, MarkedVertex<T> n2) {
        return getAllEdges()
                .stream()
                .anyMatch(e -> e.getSource().equals(n1) && e.getDestination().equals(n2));
    }

    @Override
    public boolean areAdjacent(String s1, String s2) {
        MarkedVertex<T> n1 = getVertex(s1);
        MarkedVertex<T> n2 = getVertex(s2);
        if(n1 != null && n2 != null) {
            return areAdjacent(n1, n2);
        }
        return false;
    }

    /**
     * Überprüft, ob zwei Knoten stark benachbart sind.
     *
     * @param n1 der erste Knoten
     * @param n2 der zweite Knoten
     * @return true, wenn die Knoten stark benachbart sind, andernfalls false
     */
    public boolean areStrongAdjacent(MarkedVertex<T> n1, MarkedVertex<T> n2) {
        return areAdjacent(n1, n2) && areAdjacent(n2, n1);
    }

    /**
     * Überprüft, ob zwei Knoten mit den angegebenen Namen stark benachbart sind.
     *
     * @param s1 Name des ersten Knotens
     * @param s2 Name des zweiten Knotens
     * @return true, wenn die Knoten stark benachbart sind, andernfalls false
     */
    public boolean areStrongAdjacent(String s1, String s2) {
        return areAdjacent(s1, s2) && areAdjacent(s2, s1);
    }

    /**
     * Gibt die Vorgänger eines Knotens zurück.
     *
     * @param n der Knoten
     * @return eine Liste der Vorgänger
     */
    public Vector<MarkedVertex<T>> getPredecessors(final MarkedVertex<T> n) {
        Vector<MarkedVertex<T>> predecessors = new Vector<>();
        for (MarkedEdge<U> edge : getAllEdges()) {
            if (edge.getDestination().equals(n)) {
                predecessors.add((MarkedVertex<T>) edge.getSource());
            }
        }
        return predecessors;
    }

    /**
     * Gibt die Nachfolger eines Knotens zurück.
     *
     * @param n der Knoten
     * @return eine Liste der Nachfolger
     */
    public Vector<MarkedVertex<T>> getSuccessors(final MarkedVertex<T> n) {
        Vector<MarkedVertex<T>> successors = new Vector<>();
        for (MarkedEdge<U> edge : getAllEdges()) {
            if (edge.getSource().equals(n)) {
                successors.add((MarkedVertex<T>) edge.getDestination());
            }
        }
        return successors;
    }

    /**
     * Gibt die ausgehenden Kanten eines Knotens zurück.
     *
     * @param vertex der Knoten
     * @return eine Liste der ausgehenden Kanten
     */
    public List<MarkedEdge<U>> getOutgoingEdges(MarkedVertex<T> vertex) {
        List<MarkedEdge<U>> outgoingEdges = new ArrayList<>();
        for (MarkedEdge<U> edge : getAllEdges()) {
            if (edge.getSource().equals(vertex)) {
                outgoingEdges.add(edge);
            }
        }
        return outgoingEdges;
    }

    /**
     * Gibt den Eingangsgrad eines Knotens zurück.
     *
     * @param n der Knoten
     * @return der Eingangsgrad
     */
    public int inDegree(MarkedVertex<T> n) {
        return getPredecessors(n).size();
    }

    /**
     * Gibt den Ausgangsgrad eines Knotens zurück.
     *
     * @param n der Knoten
     * @return der Ausgangsgrad
     */
    public int outDegree(MarkedVertex<T> n) {
        return getSuccessors(n).size();
    }

    /**
     * Gibt den Eingangsgrad eines Knotens mit angegebenen Namen zurück.
     *
     * @param s Namen des Knotens
     * @return der Eingangsgrad
     */
    public int inDegree(String s) {
        MarkedVertex<T> vertex = getVertex(s);
        if(vertex != null) {
            return inDegree(vertex);
        }
        return 0;
    }

    /**
     * Gibt den Ausgangsgrad eines Knotens mit angegebenen Namen zurück.
     *
     * @param s Namen des Knotens
     * @return der Ausgangsgrad
     */
    public int outDegree(String s) {
        MarkedVertex<T> vertex = getVertex(s);
        if (vertex != null) {
            return outDegree(vertex);
        }
        return 0;
    }

    /**
     * Führt eine topologische Sortierung des Graphen durch.
     *
     * @return eine Liste der Knoten in topologischer Reihenfolge
     */
    public List<MarkedVertex<T>> topSort() {
        List<MarkedVertex<T>> sortedList = new ArrayList<>();
        Set<MarkedVertex<T>> visited = new HashSet<>();
        Set<MarkedVertex<T>> stack = new HashSet<>();

        for (MarkedVertex<T> vertex : getAllVertexes()) {
            if (!visited.contains(vertex)) { // Wenn der Knoten noch nicht besucht wurde
                if (topologicalSortAlgorithm(vertex, visited, stack, sortedList)) { // Topologische Sortierung starten
                    return null; // Leere Liste zurückgeben, wenn ein Zyklus gefunden wurde
                }
            }
        }

        Collections.reverse(sortedList); // Liste umkehren, um die richtige Reihenfolge zu erhalten
        return sortedList;
    }

    /**
     * Rekursive Hilfsmethode für die topologische Sortierung.
     *
     * @param vertex der aktuelle Knoten
     * @param visited die Menge der besuchten Knoten
     * @param stack der aktuelle Stack
     * @param sortedList die sortierte Liste
     * @return true, wenn ein Zyklus gefunden wurde, andernfalls false
     */
    private boolean topologicalSortAlgorithm(MarkedVertex<T> vertex, Set<MarkedVertex<T>> visited, Set<MarkedVertex<T>> stack, List<MarkedVertex<T>> sortedList) {
        visited.add(vertex); // Knoten als besucht markieren
        stack.add(vertex); // Knoten auf den Stack legen
        markVertex(vertex, VertexMarking.CURRENT_COLOR, "Visiting");

        for (MarkedEdge<U> edge : getOutgoingEdges(vertex)) { // Alle ausgehenden Kanten des Knotens durchlaufen
            MarkedVertex<T> neighbor = (MarkedVertex<T>) edge.getDestination();
            markVertex(neighbor, VertexMarking.NEIGHBOR_COLOR, "Neighbor");
            if (stack.contains(neighbor)) { // Wenn der Nachbar bereits auf dem Stack ist, wurde ein Zyklus gefunden
                // TODO: Add cycle marking for edges
                System.out.println("Cycle detected!");
                return true;
            }
            if (!visited.contains(neighbor)) { // Wenn der Nachbar noch nicht besucht wurde
                if (topologicalSortAlgorithm(neighbor, visited, stack, sortedList)) { // Rekursiver Aufruf für den Nachbarn
                    return true;
                }
            }
        }

        stack.remove(vertex); // Knoten vom Stack entfernen
        sortedList.add(vertex); // Knoten zur sortierten Liste hinzufügen
        markVertex(vertex, VertexMarking.FINISHED_COLOR, "Finished");
        countStep();
        return false;
    }

    /**
     * Überprüft, ob der Graph einen Zyklus enthält.
     *
     * @return true, wenn der Graph einen Zyklus enthält, andernfalls false
     */
    public boolean hasCycle() {
        return topSort() == null;
    }

    /**
     * Markiert einen Knoten mit einer Farbe und fügt ihn der Log-Liste mit seinem Zustand hinzu.
     *
     * @param vertex der zu markierende Knoten
     * @param color die Farbe, mit der der Knoten markiert werden soll
     * @param desc der Zustand des Knotens
     */
    private void markVertex(MarkedVertex<T> vertex, Color color, String desc) {
        vertex.getMarking().markVertex(vertex, color);
        vertexLogElementList.add(new VertexLogElement(getStepCounter(),
                "[ Vector ] " + desc + ": " + vertex.getName(), 0, vertex.clone()));
        countStep();
    }

    /**
     * Liefert eine LogElementList. In dieser befinden sich die VertexLogElemente, welche die verschiedenen Schritte im TopologicalSort-Algorithmus
     * mit ihren dazugehörigen Knoten und deren Attributen dokumentiert haben.
     *
     * @return die LogElementList mit den VertexLogElementen
     */
    public LogElementList<VertexLogElement> getVertexLogElementList() {
        return vertexLogElementList;
    }

    /**
     * @return die aktuelle Schrittzahl
     */
    private int getStepCounter() {
        return stepCounter;
    }

    /**
     * Setzt die Schrittzahl zurück.
     */
    private void resetStepCounter() {
        this.stepCounter = 1;
    }

    /**
     * Erhöht die Schrittzahl um 1.
     */
    private void countStep() {
        stepCounter++;
    }
}
