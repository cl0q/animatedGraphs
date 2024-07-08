package graph;

import graph.marking.EdgeMarking;
import graph.marking.MarkedEdge;
import graph.marking.MarkedVertex;
import graph.marking.VertexMarking;

import java.util.Vector;

public abstract class Graph<T extends VertexMarking, U extends EdgeMarking>  {

    private String name;
    private final Vector<MarkedVertex<T>> vertexes;
    private final Vector<MarkedEdge<U>> edges;

    public Graph() {
        this.vertexes = new Vector<>();
        this.edges = new Vector<>();
    }

    public Graph(String s) {
        this();
        this.name = s;
    }

    /**
     * Fügt eine Kante zum Graphen hinzu.
     *
     * @param e die hinzuzufügende Kante
     */
    public void addEdge(MarkedEdge<U> e) {
        this.edges.add(e);
    }

    /**
     * Fügt einen Knoten zum Graphen hinzu.
     *
     * @param n der hinzuzufügende Knoten
     */
    public void addVertex(MarkedVertex<T> n) {
        this.vertexes.add(n);
    }

    /**
     * Überprüft, ob zwei Knoten benachbart sind.
     *
     * @param n1 der erste Knoten
     * @param n2 der zweite Knoten
     * @return true, wenn die Knoten benachbart sind, andernfalls false
     */
    public abstract boolean areAdjacent(MarkedVertex<T> n1, MarkedVertex<T> n2);

    /**
     * Überprüft, ob zwei Knoten mit den angegebenen Namen benachbart sind.
     *
     * @param s1 der Name des ersten Knotens
     * @param s2 der Name des zweiten Knotens
     * @return true, wenn die Knoten benachbart sind, andernfalls false
     */
    public abstract boolean areAdjacent(String s1, String s2);

    /**
     * @return der Grad des Graphen
     */
    public int degree() {
        return vertexes.size();
    }

    /**
     * @return ein Vektor aller Kanten im Graphen
     */
    public Vector<MarkedEdge<U>> getAllEdges() {
        return edges;
    }

    /**
     * @return ein Vektor aller Knoten im Graphen
     */
    public Vector<MarkedVertex<T>> getAllVertexes() {
        return vertexes;
    }

    /**
     * @return der Name des Graphen
     */
    public String getName() {
        return name;
    }

    /**
     * Überprüft, ob der Graph die angegebene Kante enthält.
     *
     * @param e die zu überprüfende Kante
     * @return true, wenn der Graph die Kante enthält, andernfalls false
     */
    public boolean hasEdge(MarkedEdge<U> e) {
        return edges.contains(e);
    }

    /**
     * Überprüft, ob der Graph eine Kante mit dem angegebenen Namen enthält.
     *
     * @param s der Name der zu überprüfenden Kante
     * @return true, wenn der Graph die Kante enthält, andernfalls false
     */
    public boolean hasEdge(String s) {
        return edges.stream().anyMatch(e -> e.getName().equals(s));
    }

    /**
     * Überprüft, ob der Graph eine Kante zwischen den angegebenen Knoten enthält.
     *
     * @param n1 der erste Knoten
     * @param n2 der zweite Knoten
     * @return true, wenn der Graph die Kante enthält, andernfalls false
     */
    public boolean hasEdge(MarkedVertex<T> n1, MarkedVertex<T> n2) {
        return edges.stream().anyMatch(e -> e.getSource().equals(n1) && e.getDestination().equals(n2));
    }

    /**
     * Überprüft, ob der Graph eine Kante zwischen Knoten mit den angegebenen Namen enthält.
     *
     * @param s1 der Name des ersten Knotens
     * @param s2 der Name des zweiten Knotens
     * @return true, wenn der Graph die Kante enthält, andernfalls false
     */
    public boolean hasEdge(String s1, String s2) {
        MarkedVertex<T> n1 = getVertex(s1);
        MarkedVertex<T> n2 = getVertex(s2);
        if (n1 != null && n2 != null) {
            return hasEdge(n1, n2);
        }
        return false;
    }

    /**
     * Gibt den Knoten mit dem angegebenen Namen zurück.
     *
     * @param name der Name des Knotens
     * @return der Knoten mit dem angegebenen Namen oder null, wenn nicht gefunden
     */
    public MarkedVertex<T> getVertex(String name) {
        return vertexes.stream().filter(v -> v.getName().equals(name)).findFirst().orElse(null);
    }

    /**
     * Überprüft, ob der Graph eine Schleife am angegebenen Knoten enthält.
     *
     * @param n der zu überprüfende Knoten
     * @return true, wenn der Graph eine Schleife am Knoten enthält, andernfalls false
     */
    public boolean hasLoop(MarkedVertex<T> n) {
        return hasEdge(n, n);
    }

    /**
     * Überprüft, ob der Graph eine Schleife am Knoten mit dem angegebenen Namen enthält.
     *
     * @param s der Name des zu überprüfenden Knoten
     * @return true, wenn der Graph eine Schleife am Knoten enthält, andernfalls false
     */
    public boolean hasLoop(String s) {
        MarkedVertex<T> n = getVertex(s);
        if (n != null) {
            return hasLoop(n);
        }
        return false;
    }

    /**
     * Überprüft, ob der Graph den angegebenen Knoten enthält.
     *
     * @param n der zu überprüfende Knoten
     * @return true, wenn der Graph den Knoten enthält, andernfalls false
     */
    public boolean hasVertex(MarkedVertex<T> n) {
        return vertexes.contains(n);
    }

    /**
     * Überprüft, ob der Graph einen Knoten mit dem angegebenen Namen enthält.
     *
     * @param s der Name des zu überprüfenden Knoten
     * @return true, wenn der Graph den Knoten enthält, andernfalls false
     */
    public boolean hasVertex(String s) {
        return vertexes.stream().anyMatch(v -> v.getName().equals(s));
    }

    /**
     * @return die Anzahl der Kanten im Graphen
     */
    public int numberOfEdges() {
        return edges.size();
    }

    /**
     * @return die Anzahl der Knoten im Graphen
     */
    public int numberOfVertexes() {
        return vertexes.size();
    }

    /**
     * Entfernt die Kante aus dem Graphen.
     * @param e die zu entfernende Kante
     * @return true, wenn die Kante entfernt wurde, andernfalls false
     */
    public boolean removeEdge(MarkedEdge<U> e) {
        return edges.remove(e);
    }

    /**
     * Entfernt die Kante mit dem angegebenen Namen aus dem Graphen.
     * @param s der Name der zu entfernenden Kante
     * @return true, wenn die Kante entfernt wurde, andernfalls false
     */
    public boolean removeEdge(String s) {
        return edges.removeIf(e -> e.getName().equals(s));
    }

    /**
     * Entfernt den Knoten aus dem Graphen.
     * @param n der zu entfernende Knoten
     * @return true, wenn der Knoten entfernt wurde, andernfalls false
     */
    public boolean removeVertex(MarkedVertex<T> n) {
        return vertexes.remove(n);
    }

    /**
     * Entfernt den Knoten mit dem angegebenen Namen aus dem Graphen.
     * @param s der Name des zu entfernenden Knotens
     * @return true, wenn der Knoten entfernt wurde, andernfalls false
     */
    public boolean removeVertex(String s) {
        return vertexes.removeIf(v -> v.getName().equals(s));
    }

    /**
     * Setzt den Namen des Graphen.
     *
     * @param s der Name des Graphen
     */
    public void setName(String s) {
        this.name = s;
    }

    public String toString() {
        return "Graph{" +
                "name='" + name + '\'' +
                ", vertexes=" + vertexes +
                ", edges=" + edges +
                '}';
    }
}
