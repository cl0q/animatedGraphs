package graph.structure.graph;

import animate.drawing.GraphLogElement;
import graph.marking.*;
import graph.marking.edge.EdgeMarking;
import graph.marking.edge.MarkedEdge;
import graph.marking.vertex.MarkedVertex;
import graph.marking.vertex.VertexMarking;
import logging.LogElementList;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Implementation eines ungerichteten Graphen.
 *
 * @param <T> implementierende Klasse der Knotenmarkierung
 * @param <U> implementierende Klasse der Kantenmarkierung
 */
public class UndirectedGraph<T extends VertexMarking, U extends EdgeMarking> extends Graph<T, U> {

    private String name;
    private int stepCounter = 0; // Zähler der Schritte für das LogElement
    private int markedVertexCounter = 0;
    private final List<String> workingOrderArray = new ArrayList<>();

    public final LogElementList<GraphLogElement<T, U>> graphLogElementList = new LogElementList<>();

    /**
     * Erzeugt einen ungerichteten Graphen.
     */
    public UndirectedGraph() {
        super();
    }

    /**
     * Erzeugt einen ungerichteten Graphen mit einem Namen.
     *
     * @param s der Name des Graphen
     */
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
        return vertex != null ? degree(vertex) : 0;
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

    /**
     * Führt eine Tiefensuche mithilfe von Stapeln durch.
     *
     * @param start der Startknoten
     * @return eine Liste der besuchten Knoten
     */
    public Vector<MarkedVertex<T>> depthSearchRecursive(MarkedVertex<T> start) {
        Vector<MarkedVertex<T>> visited = new Vector<>();
        Stack<MarkedVertex<T>> stack = new Stack<>();
        logGraph("Base Graph");
        visited.add(start); // Startknoten als besucht markieren
        stack.push(start); // Startknoten auf den Stack legen
        markVertex(start, Marking.STARTING_COLOR);
        logGraph("[ " + start.getName() +" ] : Start");

        while(!stack.empty()) {
            MarkedVertex<T> vertex = stack.pop(); // Knoten vom Stack nehmen
            markVertex(vertex, Marking.FINISHED_COLOR);
            logGraph("[ " + vertex.getName() + " ] : Finished");
            markedVertexCounter++;
            workingOrderArray.add(vertex.getName());
            for (MarkedVertex<T> neighbor : getNeighbours(vertex)) {
                if(!visited.contains(neighbor)) {
                    /*
                        * Markierung der Kante zwischen Knoten und Nachbarknoten
                        * sowie des Nachbarknotens als besucht
                     */
                    markEdge(getEdgeBetweenNeighbors(vertex, neighbor),
                            Marking.EDGE_VISISTED_COLOR);
                    markVertex(neighbor,
                            Marking.NEIGHBOR_COLOR);
                    logGraph("[ " + vertex.getName() + " ] :  Neighbor " + neighbor.getName());

                    visited.add(neighbor); // Nachbar als besucht markieren
                    stack.push(neighbor); // Nachbar auf den Stack legen

                    // Markierung des Nachbarknotens als fertig bearbeitet
                    markVertex(neighbor,
                            Marking.FINISHED_COLOR);
                    logGraph("[ " + vertex.getName() + " ] : Finished neighbor "
                            + neighbor.getName());
                }
            }
        }
        printWorkingOrderArray();
        return visited;
    }

    /**
     * Führt eine rekursive Tiefensuche.
     *
     * @param vertex der als Nächstes zu besuchende Knoten
     * @param visited die Liste der besuchten Knoten
     */
    private void depthSearchRecursiveAlgo(MarkedVertex<T> vertex, Vector<MarkedVertex<T>> visited) {
        if(!visited.contains(vertex)) {
            visited.add(vertex);
            markVertex(vertex, Marking.CURRENT_COLOR);
            logGraph("[ " + vertex.getName() + " ] : Visiting");
            markedVertexCounter++;
            workingOrderArray.add(vertex.getName());
            for (MarkedVertex<T> neighbor : getNeighbours(vertex)) { // Alle Nachbarn des Knotens durchlaufen
                markVertex(neighbor, Marking.NEIGHBOR_COLOR);
                logGraph("[ " + vertex.getName() + " ] :  Neighbor " + neighbor.getName());
                depthSearchRecursiveAlgo(neighbor, visited);
            }
        }
    }

    /**
     * Liefert die Kante zwischen zwei benachbarten Knoten
     * oder erzeugt eine neue leere Kante, falls keine Kante zwischen den Knoten existiert.
     *
     * @param vertex der erste Knoten
     * @param neighbor ein Nachbar des ersten Knotens
     * @return Kante zwischen den beiden Knoten, falls eine existiert. Andernfalls eine leere Kante
     */
    private MarkedEdge<U> getEdgeBetweenNeighbors(MarkedVertex<T> vertex, MarkedVertex<T> neighbor) {
        return (MarkedEdge<U>) vertex.getEdges()
                .stream()
                .filter(e ->
                        e.getSource().equals(vertex) && e.getDestination().equals(neighbor)
                                || e.getSource().equals(neighbor) && e.getDestination().equals(vertex))
                .findFirst()
                .orElse(new MarkedEdge<U>());
    }

    /**
     * Markiert einen Knoten mit einer Farbe.
     *
     * @param vertex der zu markierende Knoten
     * @param color die Farbe, mit der der Knoten markiert werden soll
     */
    private void markVertex(MarkedVertex<T> vertex, Color color) {
        vertex.getMarking().markVertex(vertex, color);
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
     * Liefert eine LogElementList. In dieser befinden sich die VertexLogElemente, welche die verschiedenen Schritte im Tiefensuche-Algorithmus
     * mit ihren dazugehörigen Knoten und deren Attributen dokumentiert haben.
     *
     * @return die LogElementList mit den VertexLogElementen
     */
    public LogElementList<GraphLogElement<T, U>> getGraphLogElement() {
        return graphLogElementList;
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

    private void printWorkingOrderArray() {
        System.out.println("///     Working Order DepthFirstSearch     ///");
        if(workingOrderArray.size() == markedVertexCounter)
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
        for(int i = 0; i < workingOrderArray.size(); i++) {
            sb.append(workingOrderArray.get(i));
            if (i < workingOrderArray.size() - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }
}
