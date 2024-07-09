package graph;

import animate.VertexLogElement;
import graph.marking.*;
import logging.LogElementList;

import java.awt.*;
import java.util.Stack;
import java.util.Vector;

/**
 * Implementation eines ungerichteten Graphen.
 *
 * @param <T> implementierende Klasse der Knotenmarkierung
 * @param <U> implementierende Klasse der Kantenmarkierung
 */
public class UndirectedGraph<T extends VertexMarking, U extends EdgeMarking> extends Graph<T, U> {

    private String name;
    private int stepCounter = 0; // Zähler der Schritte für das LogElement

    public final LogElementList<VertexLogElement<T>> vertexLogElementList = new LogElementList<>();

    public UndirectedGraph() {
        super();
    }

    public UndirectedGraph(String s) {
        super(s);
        this.name = s;
    }

    @Override
    public boolean areAdjacent(MarkedVertex<T> n1, MarkedVertex<T> n2) {
        return getAllEdges()
                .stream()
                .anyMatch(e -> e.getSource().equals(n1) && e.getDestination().equals(n2)
                        || e.getSource().equals(n2) && e.getDestination().equals(n1));
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
     * Gibt den Grad eines Knotens zurück.
     *
     * @param n der Knoten
     * @return der Grad des Knotens
     */
    public int degree(MarkedVertex<T> n) {
        int degree = 0;
        for(MarkedEdge<U> e : getAllEdges()) {
            if(e.getSource().equals(n) || e.getDestination().equals(n)) {
                degree++;
            }
        }
        return degree;
    }

    /**
     * Gibt den Grad eines Knotens mit angegebenen Namen zurück.
     *
     * @param s Name des Knotens
     * @return der Grad des Knotens
     */
    public int degree(String s) {
        MarkedVertex<T> vertex = getVertex(s);
        if (vertex != null) {
            return degree(vertex);
        }
        return 0;
    }

    /**
     * Gibt die Nachbarn eines Knotens zurück.
     *
     * @param n der Knoten
     * @return eine Liste der Nachbarn
     */
    public Vector<MarkedVertex<T>> getNeighbours(MarkedVertex<T> n) {
        Vector<MarkedVertex<T>> neighbours = new Vector<>();
        for (MarkedEdge<U> edge : getAllEdges()) {
            if (edge.getSource().equals(n)) {
                neighbours.add((MarkedVertex<T>) edge.getDestination());
            } else if (edge.getDestination().equals(n)) {
                neighbours.add((MarkedVertex<T>) edge.getSource());
            }
        }
        return neighbours;
    }

    public String toString() {
        return super.toString();
    }

    /**
     * Führt eine Tiefensuche rekursiv durch.
     *
     * @param start der Startknoten
     * @return eine Liste der besuchten Knoten
     */
    public Vector<MarkedVertex<T>> depthSearchRecursive(MarkedVertex<T> start) {
        Vector<MarkedVertex<T>> visited = new Vector<>();
        Stack<MarkedVertex<T>> stack = new Stack<>();
        stack.push(start); // Startknoten auf den Stack legen
        markVertex(start, VertexMarking.STARTING_COLOR, "Start");
        depthSearchRecursive(stack, visited); // Rekursive Tiefensuche starten
        System.out.println("Visited" + visited);
        return visited;
    }

    /**
     * Hilfsmethode für die rekursive Tiefensuche.
     *
     * @param stack der Stack mit den zu besuchenden Knoten
     * @param visited die Liste der besuchten Knoten
     */
    private void depthSearchRecursive(Stack<MarkedVertex<T>> stack, Vector<MarkedVertex<T>> visited) {
        if (!stack.isEmpty()) { // Solange der Stack nicht leer ist
            MarkedVertex<T> vertex = stack.pop(); // Knoten vom Stack nehmen
            if (!visited.contains(vertex)) { // Wenn der Knoten noch nicht besucht wurde
                markVertex(vertex, VertexMarking.CURRENT_COLOR, "Visiting");

                visited.add(vertex);// Knoten als besucht markieren
                for (MarkedVertex<T> neighbor : getNeighbours(vertex)) { // Alle Nachbarn des Knotens durchlaufen
                    if (!visited.contains(neighbor)) { // Wenn der Nachbar noch nicht besucht wurde
                        stack.push(neighbor); // Nachbar auf den Stack legen
                        markVertex(neighbor, VertexMarking.NEIGHBOR_COLOR, "Neighbor");
                    }
                }
                markVertex(vertex, VertexMarking.FINISHED_COLOR, "Finished");
                depthSearchRecursive(stack, visited); // Rekursiver Aufruf für den nächsten Knoten
            }
        }
    }

    /**
     * Markiert einen Knoten mit einer Farbe und fügt ihn der Log-Liste mit seinem Zustand hinzu.
     *
     * @param vertex der zu markierende Knoten
     * @param color die Farbe, mit der der Knoten markiert werden soll
     * @param state der Zustand des Knotens
     */
    private void markVertex(MarkedVertex<T> vertex, Color color, String state) {
        vertex.getMarking().markVertex(vertex, color);
        vertexLogElementList.add(new VertexLogElement<>(getStepCounter(),
                "[ Vector ] " + state + ": " + vertex.getName(), 0, vertex.clone()));
        countStep();
    }

    /**
     * Liefert eine LogElementList. In dieser befinden sich die VertexLogElemente, welche die verschiedenen Schritte im Tiefensuche-Algorithmus
     * mit ihren dazugehörigen Knoten und deren Attributen dokumentiert haben.
     *
     * @return die LogElementList mit den VertexLogElementen
     */
    public LogElementList<VertexLogElement<T>> getVertexLogElementList() {
        return vertexLogElementList;
    }

    /**
     * @return die aktuelle Schrittzahl
     */
    public int getStepCounter() {
        return stepCounter;
    }

    /**
     * Setzt die Schrittzahl zurück.
     */
    private void resetStepCounter() {
        this.stepCounter = 0;
    }

    /**
     * Erhöht die Schrittzahl um 1.
     */
    private void countStep() {
        stepCounter++;
    }
}
