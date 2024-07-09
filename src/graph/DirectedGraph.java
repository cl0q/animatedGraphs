package graph;

import animate.GraphLogElement;
import graph.marking.*;
import logging.LogElementList;
import util.Pair;

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
    private int vertexCounter;
    private int markedVertexCounter = 0;
    private final LogElementList<GraphLogElement<T, U>> graphLogElementList = new LogElementList<>();
    private List<String> workingOrderArray = new ArrayList<>();
    private List<Pair<MarkedVertex<T>, Integer>> valueCache = new ArrayList<>();

    /**
     * Erzeugt einen gerichteten Graphen.
     */
    public DirectedGraph() {
        super();
    }

    /**
     * Erzeugt einen gerichteten Graphen mit einem Namen.
     *
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
        if (n1 != null && n2 != null) {
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
        if (vertex != null) {
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
        Stack<MarkedVertex<T>> stack = new Stack<>();

        // Initialisierung der Kantennummern
        vertexCounter = getAllVertexes().size();

        logGraph("Base Graph");

        for (MarkedVertex<T> vertex : getAllVertexes()) {
            if (getAllVertexes().indexOf(vertex) == 0) {
                markVertex(vertex, Marking.STARTING_COLOR);
                logGraph("[ " + vertex.getName() + " ] : Start");
            }
            if (!visited.contains(vertex)) { // Wenn der Knoten noch nicht besucht wurde
                if (topologicalSortAlgorithm(vertex, visited, stack, sortedList)) { // Topologische Sortierung starten
                    return null; // Leere Liste zurückgeben, wenn ein Zyklus gefunden wurde
                }
            }
        }
        Collections.reverse(sortedList);// Liste umkehren, um die richtige Reihenfolge zu erhalten
        printWorkingOrderArray();
        return sortedList;
    }

    /**
     * Rekursive Hilfsmethode für die topologische Sortierung.
     *
     * @param vertex     der aktuelle Knoten
     * @param visited    die Menge der besuchten Knoten
     * @param stack      der aktuelle Stack
     * @param sortedList die sortierte Liste
     * @return true, wenn ein Zyklus gefunden wurde, andernfalls false
     */
    private boolean topologicalSortAlgorithm(MarkedVertex<T> vertex, Set<MarkedVertex<T>> visited, Stack<MarkedVertex<T>> stack, List<MarkedVertex<T>> sortedList) {
        visited.add(vertex); // Knoten als besucht markieren
        stack.push(vertex); // Knoten auf den Stack legen
        markVertex(vertex, Marking.CURRENT_COLOR);
        logGraph("[ " + vertex.getName() + " ] : Traverse");


        for (MarkedEdge<U> edge : getOutgoingEdges(vertex)) { // Alle ausgehenden Kanten des Knotens durchlaufen
            MarkedVertex<T> neighbor = (MarkedVertex<T>) edge.getDestination();
            markEdge(edge, Marking.EDGE_VISISTED_COLOR);
            logGraph("[ " + edge.getName() + " ] : Traverse");
            if (stack.contains(neighbor)) { // Wenn der Nachbar bereits auf dem Stack ist, wurde ein Zyklus gefunden
                for (MarkedVertex<T> v : stack) {
                    markVertex(v, Marking.CYCLE_COLOR);
                    getOutgoingEdges(v)
                            .forEach(e -> markEdge(e, Marking.CYCLE_COLOR));
                }
                logGraph("Cycle detected!");
                System.out.println("Cycle detected!");
                return true;
            }
            if (!visited.contains(neighbor)) { // Wenn der Nachbar noch nicht besucht wurde
                markVertex(neighbor, Marking.NEIGHBOR_COLOR);
                logGraph("[ " + vertex.getName() + " ] : Neighbor " + neighbor.getName());
                if (topologicalSortAlgorithm(neighbor, visited, stack, sortedList)) { // Rekursiver Aufruf für den Nachbarn
                    return true;
                }
            }
        }

        stack.remove(vertex); // Knoten vom Stack entfernen
        sortedList.add(vertex); // Knoten zur sortierten Liste hinzufügen
        markVertex(vertex, Marking.FINISHED_COLOR, vertexCounter--);
        logGraph("[ " + vertex.getName() + " ] : Finished");
        workingOrderArray.add(vertex.getName());
        countStep();
        markedVertexCounter++;
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
     * Markiert einen Knoten mit einer Farbe.
     *
     * @param vertex der zu markierende Knoten
     * @param color  die Farbe, mit der der Knoten markiert werden soll
     */
    private void markVertex(MarkedVertex<T> vertex, Color color) {
        vertex.getMarking().markVertex(vertex, color);
    }

    /**
     * Speichert den Zustand des jetzigen Graphen als GraphLogElement in der LogElementListe.
     *
     * @param state der neue Zustand des Graphen
     */
    private void logGraph(String state) {
        graphLogElementList.add(new GraphLogElement<>(getStepCounter(),
                state, 0, this.clone()));
        countStep();
    }

    /**
     * Markiert einen Knoten mit einer Farbe.
     *
     * @param vertex      der zu markierende Knoten
     * @param color       die Farbe, mit der der Knoten markiert werden soll
     * @param value       der Wert des Knotens
     */
    private void markVertex(MarkedVertex<T> vertex, Color color, int value) {
        vertex.getMarking().markVertex(vertex, color);
        valueCache.add(new Pair<>(vertex, value));
    }

    /**
     * Markiert eine Kante mit einer Farbe.
     *
     * @param edge  die zu markierende Kante
     * @param color die Farbe, mit der die Kante markiert werden soll
     */
    private void markEdge(MarkedEdge<U> edge, Color color) {
        edge.getMarking().markEdge(edge, color);
    }

    /**
     * Liefert eine LogElementList. In dieser befinden sich die DirectedGraphLogElemente, welche die verschiedenen Schritte im TopologicalSort-Algorithmus
     * mit ihren dazugehörigen Knoten/Kanten und deren Attributen dokumentieren.
     *
     * @return eine LogElementList mit den DirectedGraphLogElemente
     */
    public LogElementList<GraphLogElement<T, U>> getGraphLogElementList() {
        return graphLogElementList;
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

    private void printWorkingOrderArray() {
        System.out.println("///     Working Order TopologicalSort     ///");
        if (workingOrderArray.size() == markedVertexCounter)
            for (String s : workingOrderArray) {
                System.out.print(s + ", ");
            }
        System.out.println();
    }

    public ArrayList<String> getWorkingOrderArray() {
        return (ArrayList<String>) workingOrderArray;
    }

    public String workingOrderArrayToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < workingOrderArray.size(); i++) {
            String s = workingOrderArray.get(i);
            int value = valueCache.stream()
                    .filter(p -> p.getFirst().getName().equals(s))
                    .findFirst().orElseThrow().getSecond();

            sb.append(s).append(" : ").append(value);
            if (i < workingOrderArray.size() - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

    private void setValueCache(List<Pair<MarkedVertex<T>, Integer>> valueCache) {
        this.valueCache = valueCache;
    }

    public DirectedGraph<T, U> clone() {
        super.clone();
        DirectedGraph<T, U> clonedGraph = (DirectedGraph<T, U>) super.clone();;

        clonedGraph.setValueCache(this.valueCache);
        return clonedGraph;
    }
}
