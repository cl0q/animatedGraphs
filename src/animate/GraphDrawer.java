package animate;

import graph.DirectedGraph;
import graph.UndirectedGraph;
import graph.marking.MarkedEdge;
import graph.marking.MarkedVertex;
import graph.marking.EdgeColorMarking;
import graph.marking.VertexColorMarking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

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
    private int nextVertexIndex = 0;  // Keep track of the next vertex index
    private int edgeCount = 0;

    private JComboBox<String> vertexComboBox;
    private JButton searchButton;
    private JComboBox<String> algorithmComboBox;
    JComboBox<String> edgeTypeComboBox; // Changed visibility to package-private for access in DrawHelper

    public GraphDrawer() {
        setTitle("GraphDrawer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        DrawingPanel drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        JPanel rightPanel = createRightPanel();
        add(rightPanel, BorderLayout.EAST);

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("<html>Left Click: Add Vertex<br>Right Click: Connect Vertices<br>Middle Click: Delete Vertex/Edge</html>"));

        JLabel edgeTypeLabel = new JLabel("Edge Type:");
        controlPanel.add(edgeTypeLabel);

        edgeTypeComboBox = new JComboBox<>(new String[]{"Undirected", "Directed"});
        edgeTypeComboBox.addActionListener(_ -> updateEdgeTypes(Objects.equals(edgeTypeComboBox.getSelectedItem(), "Directed")));
        controlPanel.add(edgeTypeLabel);
        controlPanel.add(edgeTypeComboBox);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel algorithmLabel = new JLabel("Select Algorithm:");
        algorithmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(algorithmLabel);

        algorithmComboBox = new JComboBox<>(new String[]{"Depth First Search", "Topological Sort"});
        algorithmComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        algorithmComboBox.addActionListener(e -> updateAlgorithmSelection());
        rightPanel.add(algorithmComboBox);

        JLabel vertexLabel = new JLabel("Select Vertex:");
        vertexLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(vertexLabel);

        vertexComboBox = new JComboBox<>();
        vertexComboBox.setMaximumSize(new Dimension(120, 25));
        vertexComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateVertexComboBox();1
        rightPanel.add(vertexComboBox);

        searchButton = new JButton("Search");
        searchButton.setMaximumSize(new Dimension(200, 25));
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.addActionListener(e -> searchAlgorithm());
        rightPanel.add(searchButton);

        return rightPanel;
    }

    private void updateVertexComboBox() {
        vertexComboBox.removeAllItems();
        for (MarkedVertex<VertexColorMarking> vertex : markedVertices) {
            vertexComboBox.addItem(vertex.getName());
        }
    }

    private void searchAlgorithm() {
        String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
        System.out.println("SelectedItem: " + vertexComboBox.getSelectedItem());;
        selectedVertex = getSelectedMarkedVertex();

        if (selectedVertex == null) {
            JOptionPane.showMessageDialog(this, "Please select a starting vertex.");
            return;
        }
        System.out.println("///     SEARCH      ///");
        System.out.println("Searching " + selectedVertex.getName() + " using " + selectedAlgorithm);
        System.out.println();
        printCurrentState();

        if (Objects.equals(selectedAlgorithm, "Depth First Search")) {
            addDrawnGraphToUndirectedGraph();
            AlgorithmDepthSearchRecursive algorithm = new AlgorithmDepthSearchRecursive(parameterArea, this, undirectedGraph);
            VisualizationFramerwork.init(algorithm, parameterArea, this);
        } else if (Objects.equals(selectedAlgorithm, "Topological Sort")) {
            addDrawnGraphToDirectedGraph();
            AlgorithmTopologicalSort algorithm = new AlgorithmTopologicalSort(parameterArea, this, directedGraph);
            VisualizationFramerwork.init(algorithm, parameterArea, this);
        }
    }

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

    private class DrawingPanel extends JPanel {
        public DrawingPanel() {
                    System.out.println("///    Draw Listener       ");
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        MarkedVertex<VertexColorMarking> vertex = new MarkedVertex<>("V" + nextVertexIndex++, e.getX(), e.getY(), vertexColorMarking);
                        vertexColorMarking.setColor(vertex, Color.BLACK);
                        markedVertices.add(vertex);
                        selectedVertex = null; // Reset selectedVertex if new vertex is added
                        System.out.println("Added vertex: " + vertex.getName() + " at (" + vertex.getX() + ", " + vertex.getY() + ")");
                        updateVertexComboBox();
                        repaint();
                    } else if (SwingUtilities.isRightMouseButton(e)) {
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
                    } else if (SwingUtilities.isMiddleMouseButton(e)) {
                        deleteElement(e.getX(), e.getY());
                        selectedVertex = null; // Reset selectedVertex if deletion occurs
                        updateVertexComboBox();
                        repaint();
                    }
                }
            });
            System.out.println();
        }

        private MarkedVertex<VertexColorMarking> findVertex(int x, int y) {
            for (MarkedVertex<VertexColorMarking> v : markedVertices) {
                if (v.contains(x, y)) {
                    return v;
                }
            }
            return null;
        }

        private MarkedEdge<EdgeColorMarking> findEdge(int x, int y) {
            for (MarkedEdge<EdgeColorMarking> edge : markedEdges) {
                if (edge.contains(x, y)) {
                    return edge;
                }
            }
            return null;
        }

        private void deleteElement(int x, int y) {
            MarkedVertex<VertexColorMarking> v = findVertex(x, y);
            if (v != null) {
                markedEdges.removeIf(edge -> edge.getSource() == v || edge.getDestination() == v);
                markedVertices.remove(v);
                System.out.println("Removed vertex: " + v.getName());
                renameVertices(); // Ensure consistent vertex naming
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

    private void updateEdgeTypes(boolean directed) {
        for (MarkedEdge<EdgeColorMarking> edge : markedEdges) {
            edge.setDirected(directed);
        }
        repaint();
    }

    private void renameVertices() {
        nextVertexIndex = 0; // Reset vertex index
        for (MarkedVertex<VertexColorMarking> markedVertex : markedVertices) {
            markedVertex.setName("V" + nextVertexIndex++);
        }
        updateVertexComboBox();
        repaint();
    }

    public ArrayList<MarkedVertex<VertexColorMarking>> getMarkedVertices() {
        return markedVertices;
    }

    public ArrayList<MarkedEdge<EdgeColorMarking>> getMarkedEdges() {
        return markedEdges;
    }

    public int getVertexCount() {
        return markedVertices.size();
    }

    public String[] getVertexNames() {
        String[] names = new String[markedVertices.size()];
        for (int i = 0; i < markedVertices.size(); i++) {
            names[i] = markedVertices.get(i).getName();
        }
        return names;
    }

    public MarkedVertex<VertexColorMarking> getSelectedVertex() {
        return selectedVertex;
    }

    public MarkedVertex<VertexColorMarking> getSelectedMarkedVertex(){
        for (MarkedVertex<VertexColorMarking> vertex : markedVertices) {
            if (vertex.getName().equals(vertexComboBox.getSelectedItem())) {
                return vertex;
            }
        }
        return null;
    }

    private void addDrawnGraphToUndirectedGraph() {
        for (MarkedVertex<VertexColorMarking> vertex : markedVertices) {
            undirectedGraph.addVertex(vertex);
        }

        for (MarkedEdge<EdgeColorMarking> edge : markedEdges) {
            undirectedGraph.addEdge(edge);
        }
    }

    private void addDrawnGraphToDirectedGraph(){
        for (MarkedVertex<VertexColorMarking> vertex : markedVertices) {
            directedGraph.addVertex(vertex);
        }

        for (MarkedEdge<EdgeColorMarking> edge : markedEdges) {
            directedGraph.addEdge(edge);
        }
    }

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

    public void init() {
        parameterArea = new ParameterArea();
        undirectedGraph = new UndirectedGraph<VertexColorMarking, EdgeColorMarking> ();
        directedGraph = new DirectedGraph<VertexColorMarking, EdgeColorMarking>();

        setVisible(true);
    }
}
