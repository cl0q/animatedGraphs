package animate.drawing;

import animate.VisualizationFramework;
import animate.algorithm.AlgorithmDepthSearchRecursive;
import animate.algorithm.AlgorithmTopologicalSort;
import animate.drawing.ParameterArea;
import graph.marking.edge.EdgeColorMarking;
import graph.marking.edge.MarkedEdge;
import graph.marking.vertex.MarkedVertex;
import graph.marking.vertex.VertexColorMarking;
import graph.structure.graph.DirectedGraph;
import graph.structure.graph.UndirectedGraph;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Die Klasse GraphDrawer ermöglicht die Erstellung, Manipulation und Visualisierung von Graphen.
 */
public class GraphDrawer extends JFrame {

    private ParameterArea parameterArea;
    private UndirectedGraph<VertexColorMarking, EdgeColorMarking> undirectedGraph;
    private DirectedGraph<VertexColorMarking, EdgeColorMarking> directedGraph;
    private final VertexColorMarking vertexColorMarking = new VertexColorMarking();
    private final EdgeColorMarking edgeColorMarking = new EdgeColorMarking();
    private final ArrayList<MarkedVertex<VertexColorMarking>> markedVertices = new ArrayList<>();
    private final ArrayList<MarkedEdge<EdgeColorMarking>> markedEdges = new ArrayList<>();
    private MarkedVertex<VertexColorMarking> selectedVertex = null;
    private int vertexCount = 0;
    private int nextVertexIndex = 0;  // Behalte den nächsten Vertex-Index im Auge
    private int edgeCount = 0;

    private JComboBox<String> vertexComboBox;
    private JButton searchButton;
    private JComboBox<String> algorithmComboBox;
    protected final JComboBox<String> edgeTypeComboBox; // Sichtbarkeit auf package-private geändert für den Zugriff in DrawHelper

    /**
     * Konstruktor für GraphDrawer, initialisiert die Benutzeroberfläche.
     */
    public GraphDrawer() {
        setTitle("GraphDrawer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        DrawingPanel drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        vertexComboBox = new JComboBox<>();
        edgeTypeComboBox = new JComboBox<>(new String[]{"Undirected", "Directed"});
        algorithmComboBox = new JComboBox<>(new String[]{"Depth First Search", "Topological Sort"});
        algorithmComboBox.addActionListener(e -> updateAlgorithmSelection());

        JPanel rightPanel = createRightPanel();
        add(rightPanel, BorderLayout.EAST);

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("<html>Left Click: Add Vertex<br>Right Click: Connect Vertices<br>Middle Click: Delete Vertex/Edge</html>"));

        add(controlPanel, BorderLayout.SOUTH);
    }

    /**
     * Erstellt das rechte Panel der Benutzeroberfläche.
     *
     * @return das erstellte JPanel
     */
    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel edgeTypeLabel = new JLabel("Edge Type:");
        edgeTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(edgeTypeLabel);

        edgeTypeComboBox.setMaximumSize(new Dimension(200, 25));
        edgeTypeComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(edgeTypeComboBox);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel algorithmLabel = new JLabel("Select Algorithm:");
        algorithmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(algorithmLabel);

        algorithmComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, vertexComboBox.getPreferredSize().height));
        algorithmComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(algorithmComboBox);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel vertexLabel = new JLabel("Select Vertex:");
        vertexLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(vertexLabel);

        vertexComboBox.setMaximumSize(new Dimension(120, 25));
        vertexComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(vertexComboBox);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        searchButton = new JButton("Search");
        searchButton.setMaximumSize(new Dimension(200, 25));
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.addActionListener(e -> searchAlgorithm());
        rightPanel.add(searchButton);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton loadLastGraphButton = new JButton("Load Last Graph");
        loadLastGraphButton.setMaximumSize(new Dimension(200, 25));
        loadLastGraphButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadLastGraphButton.addActionListener(e -> loadLastGraph());
        rightPanel.add(loadLastGraphButton);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton loadGraphButton = new JButton("Load Graph");
        loadGraphButton.setMaximumSize(new Dimension(200, 25));
        loadGraphButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGraphButton.addActionListener(e -> loadGraphFromFile());
        rightPanel.add(loadGraphButton);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton exportButton = new JButton("Export Graph");
        exportButton.setMaximumSize(new Dimension(200, 25));
        exportButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exportButton.addActionListener(e -> exportGraph());
        rightPanel.add(exportButton);

        return rightPanel;
    }

    /**
     * Aktualisiert die ComboBox mit den Knoten.
     */
    private void updateVertexComboBox() {
        vertexComboBox.removeAllItems();
        for (MarkedVertex<VertexColorMarking> vertex : markedVertices) {
            vertexComboBox.addItem(vertex.getName());
        }
    }

    /**
     * Führt den ausgewählten Algorithmus aus.
     */
    private void searchAlgorithm() {
        String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
        System.out.println("SelectedItem: " + vertexComboBox.getSelectedItem());
        selectedVertex = getSelectedMarkedVertex();

        if (selectedVertex == null && !selectedAlgorithm.equals("Topological Sort")) {
            JOptionPane.showMessageDialog(this, "Please select a starting vertex.");
            return;
        }

        // Überprüft, ob topologische Sortierung ausgewählt wurde und der Graph ungerichtet ist
        if (Objects.equals(selectedAlgorithm, "Topological Sort") && Objects.equals(edgeTypeComboBox.getSelectedItem(), "Undirected")) {
            JOptionPane.showMessageDialog(this, "Topologische Sortierung wird für ungerichtete Graphen nicht unterstützt.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        System.out.println("///     SEARCH      ///");
        System.out.println("Searching " + (selectedVertex != null ? selectedVertex.getName() : "N/A") + " using " + selectedAlgorithm);
        System.out.println();
        printCurrentState();

        // Startet den ausgewählten Algorithmus
        if (Objects.equals(selectedAlgorithm, "Depth First Search")) {
            addDrawnGraphToUndirectedGraph();
            AlgorithmDepthSearchRecursive algorithm = new AlgorithmDepthSearchRecursive(parameterArea, this, undirectedGraph);
            VisualizationFramework.init(algorithm, parameterArea, this);
        } else if (Objects.equals(selectedAlgorithm, "Topological Sort")) {
            addDrawnGraphToDirectedGraph();
            AlgorithmTopologicalSort algorithm = new AlgorithmTopologicalSort(parameterArea, directedGraph);
            VisualizationFramework.init(algorithm, parameterArea, this);
        }

        exportGraphToFile("./src/animate/resources/graph.txt");
    }

    /**
     * Gibt den aktuellen Zustand des gezeichneten Graphen aus.
     */
    private void printCurrentState() {
        System.out.println("///     Drawn Graph     ///");
        System.out.println("Vertices:");
        for (MarkedVertex<VertexColorMarking> v : markedVertices) {
            System.out.println(v.getName() + " (" + v.getX() + ", " + v.getY() + ")");
        }

        System.out.println("Edges:");
        for (MarkedEdge<EdgeColorMarking> e : markedEdges) {
            System.out.println(e.getName() + " from " + e.getSource().getName() + " to " + e.getDestination().getName() + " (Directed: " + e.isDirected() + ")");
        }
        System.out.println();
    }

    /**
     * Inner Class für das Zeichenpanel, welches die Mausereignisse behandelt.
     */
    private class DrawingPanel extends JPanel {
        public DrawingPanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    // Hinzufügen eines Knotens bei Linksklick

                    if (SwingUtilities.isLeftMouseButton(e)) {
                        MarkedVertex<VertexColorMarking> vertex = new MarkedVertex<>("V" + nextVertexIndex++, e.getX(), e.getY(), vertexColorMarking);
                        vertexColorMarking.setColor(vertex, Color.BLACK);
                        markedVertices.add(vertex);
                        selectedVertex = null; // Zurücksetzen des ausgewählten Knotens, wenn ein neuer Knoten hinzugefügt wird
                        System.out.println("Added vertex: " + vertex.getName() + " at (" + vertex.getX() + ", " + vertex.getY() + ")");
                        updateVertexComboBox();
                        repaint();
                    }
                    // Verbinden von Knoten bei Rechtsklick
                    else if (SwingUtilities.isRightMouseButton(e)) {
                        MarkedVertex<VertexColorMarking> v = findVertex(e.getX(), e.getY());
                        if (v != null) {
                            if (selectedVertex == null) {
                                selectedVertex = v;
                            } else {
                                boolean isDirected = Objects.equals(edgeTypeComboBox.getSelectedItem(), "Directed");
                                MarkedEdge<EdgeColorMarking> edge = new MarkedEdge<>("E" + edgeCount++, selectedVertex, v, isDirected, edgeColorMarking);
                                edgeColorMarking.setColor(edge, Color.BLACK);
                                markedEdges.add(edge);
                                System.out.println("Added edge: " + edge.getName() + " from " + edge.getSource().getName() + " to " + edge.getDestination().getName() + " (Directed: " + edge.isDirected() + ")");
                                selectedVertex = null;
                                repaint();
                            }
                        }
                    }
                    // Löschen von Knoten oder Kanten bei Mittelklick
                    else if (SwingUtilities.isMiddleMouseButton(e)) {
                        deleteElement(e.getX(), e.getY());
                        selectedVertex = null; // Zurücksetzen des ausgewählten Knotens bei Löschung
                        updateVertexComboBox();
                        repaint();
                    }
                }
            });
            System.out.println();
        }

        /**
         * Findet einen Knoten an den gegebenen Koordinaten.
         *
         * @param x die X-Koordinate
         * @param y die Y-Koordinate
         * @return der gefundene Knoten oder null, falls keiner gefunden wird
         */
        private MarkedVertex<VertexColorMarking> findVertex(int x, int y) {
            for (MarkedVertex<VertexColorMarking> v : markedVertices) {
                if (v.contains(x, y)) {
                    return v;
                }
            }
            return null;
        }

        /**
         * Findet eine Kante an den gegebenen Koordinaten.
         *
         * @param x die X-Koordinate
         * @param y die Y-Koordinate
         * @return die gefundene Kante oder null, falls keine gefunden wird
         */
        private MarkedEdge<EdgeColorMarking> findEdge(int x, int y) {
            for (MarkedEdge<EdgeColorMarking> edge : markedEdges) {
                if (edge.contains(x, y)) {
                    return edge;
                }
            }
            return null;
        }

        /**
         * Löscht ein Element (Knoten oder Kante) an den gegebenen Koordinaten.
         *
         * @param x die X-Koordinate
         * @param y die Y-Koordinate
         */
        private void deleteElement(int x, int y) {
            MarkedVertex<VertexColorMarking> v = findVertex(x, y);
            if (v != null) {
                markedEdges.removeIf(edge -> edge.getSource() == v || edge.getDestination() == v);
                markedVertices.remove(v);
                System.out.println("Removed vertex: " + v.getName());
                renameVertices(); // Sicherstellen, dass die Knotennamen konsistent sind
                return;
            }
            MarkedEdge<EdgeColorMarking> edge = findEdge(x, y);
            if (edge != null) {
                markedEdges.remove(edge);
                System.out.println("Removed edge: " + edge.getName());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (MarkedEdge<EdgeColorMarking> edge : markedEdges) {
                edge.drawHere(g);
            }
            for (MarkedVertex<VertexColorMarking> vertex : markedVertices) {
                vertex.drawHere(g);
            }
        }
    }

    /**
     * Aktualisiert den Typ der Kanten (gerichtet oder ungerichtet).
     *
     * @param directed true, wenn die Kanten gerichtet sein sollen, sonst false
     */
    private void updateEdgeTypes(boolean directed) {
        for (MarkedEdge<EdgeColorMarking> edge : markedEdges) {
            edge.setDirected(directed);
        }
        repaint();
    }

    /**
     * Bennent die Knoten um, um konsistente Knotennamen sicherzustellen.
     */
    private void renameVertices() {
        nextVertexIndex = 0; // Zurücksetzen des Vertex-Index
        for (MarkedVertex<VertexColorMarking> markedVertex : markedVertices) {
            markedVertex.setName("V" + nextVertexIndex++);
        }
        updateVertexComboBox();
        repaint();
    }

    /**
     * @return die Liste der markierten Knoten
     */
    public ArrayList<MarkedVertex<VertexColorMarking>> getMarkedVertices() {
        return markedVertices;
    }

    /**
     * @return die Liste der markierten Kanten
     */
    public ArrayList<MarkedEdge<EdgeColorMarking>> getMarkedEdges() {
        return markedEdges;
    }

    /**
     * @return die Anzahl der Knoten im Graphen
     */
    public int getVertexCount() {
        return markedVertices.size();
    }

    /**
     * @return die Namen der Knoten im Graphen
     */
    public String[] getVertexNames() {
        String[] names = new String[markedVertices.size()];
        for (int i = 0; i < markedVertices.size(); i++) {
            names[i] = markedVertices.get(i).getName();
        }
        return names;
    }

    /**
     * @return der aktuell ausgewählte Knoten
     */
    public MarkedVertex<VertexColorMarking> getSelectedVertex() {
        return selectedVertex;
    }

    /**
     * @return der aktuell markierte Knoten, der in der ComboBox ausgewählt ist
     */
    public MarkedVertex<VertexColorMarking> getSelectedMarkedVertex() {
        for (MarkedVertex<VertexColorMarking> vertex : markedVertices) {
            if (vertex.getName().equals(vertexComboBox.getSelectedItem())) {
                return vertex;
            }
        }
        return null;
    }

    /**
     * Fügt den gezeichneten Graphen dem ungerichteten Graphen hinzu.
     */
    private void addDrawnGraphToUndirectedGraph() {
        for (MarkedVertex<VertexColorMarking> vertex : markedVertices) {
            undirectedGraph.addVertex(vertex);
        }

        for (MarkedEdge<EdgeColorMarking> edge : markedEdges) {
            undirectedGraph.addEdge(edge);
        }
    }

    /**
     * Fügt den gezeichneten Graphen dem gerichteten Graphen hinzu.
     */
    private void addDrawnGraphToDirectedGraph() {
        for (MarkedVertex<VertexColorMarking> vertex : markedVertices) {
            directedGraph.addVertex(vertex);
        }

        for (MarkedEdge<EdgeColorMarking> edge : markedEdges) {
            directedGraph.addEdge(edge);
        }
    }

    /**
     * Aktualisiert die Auswahl des Algorithmus in der ComboBox.
     */
    private void updateAlgorithmSelection() {
        String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
        if ("Topological Sort".equals(selectedAlgorithm)) {
            vertexComboBox.setEnabled(false);
            vertexComboBox.setBackground(Color.GRAY);
        } else {
            vertexComboBox.setEnabled(true);
            vertexComboBox.setBackground(Color.WHITE);
        }
    }

    /**
     * Initialisiert die Benutzeroberfläche und den Graphen.
     */
    public void init() {
        parameterArea = new ParameterArea();
        undirectedGraph = new UndirectedGraph<VertexColorMarking, EdgeColorMarking>();
        directedGraph = new DirectedGraph<VertexColorMarking, EdgeColorMarking>();

        setVisible(true);
    }

    /**
     * Exportiert den aktuellen Graphen in eine Datei.
     */
    private void exportGraph() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            exportGraphToFile(selectedFile.getAbsolutePath());
        }
    }

    /**
     * Exportiert den aktuellen Graphen in die angegebene Datei.
     *
     * @param filePath der Pfad zur Datei
     */
    private void exportGraphToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("graph");
            writer.newLine();
            for (MarkedVertex<VertexColorMarking> vertex : markedVertices) {
                writer.write(vertex.getName() + ";" + vertex.getX() + ";" + vertex.getY());
                writer.newLine();
            }
            for (MarkedEdge<EdgeColorMarking> edge : markedEdges) {
                writer.write(edge.getName() + ";" + edge.getSource().getName() + ";" + edge.getDestination().getName() + ";" + (edge.isDirected() ? "Directed" : "Undirected"));
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lädt den zuletzt gespeicherten Graphen.
     */
    private void loadLastGraph() {
        String filePath = "./src/animate/resources/graph.txt";
        loadGraphFromFile(filePath);
    }

    /**
     * Öffnet einen Datei-Dialog, um einen Graphen aus einer Datei zu laden.
     */
    private void loadGraphFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            loadGraphFromFile(selectedFile.getAbsolutePath());
        }
    }

    /**
     * Lädt einen Graphen aus der angegebenen Datei.
     *
     * @param filePath der Pfad zur Datei
     */
    private void loadGraphFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            if (line == null || !line.trim().equals("graph")) {
                JOptionPane.showMessageDialog(this, "Ungültige Datei: Die Datei muss mit 'graph' beginnen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            markedVertices.clear();
            markedEdges.clear();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("V")) {
                    String[] parts = line.split(";");
                    MarkedVertex<VertexColorMarking> vertex = new MarkedVertex<>(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), vertexColorMarking);
                    vertexColorMarking.setColor(vertex, Color.BLACK);
                    markedVertices.add(vertex);
                } else if (line.startsWith("E")) {
                    String[] parts = line.split(";");
                    MarkedVertex<VertexColorMarking> source = getVertexByName(parts[1]);
                    MarkedVertex<VertexColorMarking> destination = getVertexByName(parts[2]);
                    boolean isDirected = "Directed".equals(parts[3]);
                    MarkedEdge<EdgeColorMarking> edge = new MarkedEdge<>(parts[0], source, destination, isDirected, edgeColorMarking);
                    edgeColorMarking.setColor(edge, Color.BLACK);
                    markedEdges.add(edge);
                }
            }
            updateVertexComboBox();
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sucht einen Knoten anhand seines Namens.
     *
     * @param name der Name des Knotens
     * @return der gefundene Knoten oder null, falls keiner gefunden wird
     */
    private MarkedVertex<VertexColorMarking> getVertexByName(String name) {
        for (MarkedVertex<VertexColorMarking> vertex : markedVertices) {
            if (vertex.getName().equals(name)) {
                return vertex;
            }
        }
        return null;
    }
}

