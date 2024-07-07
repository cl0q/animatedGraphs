package animate;

import graph.Edge;
import graph.Vertex;
import graph.marking.EdgeColorMarking;
import graph.marking.MarkedEdge;
import graph.marking.MarkedVertex;
import graph.marking.VertexColorMarking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GraphDrawer extends JFrame {
    private ParameterArea parameterArea;
    private final VertexColorMarking vertexColorMarking = new VertexColorMarking();
    private final EdgeColorMarking edgeColorMarking = new EdgeColorMarking();
    private final ArrayList<MarkedVertex<VertexColorMarking>> markedVertices = new ArrayList<>();
    private final ArrayList<MarkedEdge<EdgeColorMarking>> markedEdges = new ArrayList<>();
    private Vertex selectedVertex = null;
    private int vertexCount = 0;
    private int edgeCount = 0;

    private JComboBox<String> vertexComboBox;
    private JButton searchButton;
    private JComboBox<String> algorithmComboBox;
    JComboBox<String> edgeTypeComboBox; // Changed visibility to package-private for access in DrawHelper

    public GraphDrawer() {
        setTitle("Graph Drawing Application");
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
        edgeTypeComboBox.addActionListener(e -> updateEdgeTypes(edgeTypeComboBox.getSelectedItem().equals("Directed")));
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
        rightPanel.add(algorithmComboBox);

        JLabel vertexLabel = new JLabel("Select Vertex:");
        vertexLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(vertexLabel);

        vertexComboBox = new JComboBox<>();
        vertexComboBox.setMaximumSize(new Dimension(120, 25));
        vertexComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateVertexComboBox();
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
        for (Vertex vertex : markedVertices) {
            vertexComboBox.addItem(vertex.getName());
        }
    }

    private void searchAlgorithm() {
        String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
        String selectedVertex = (String) vertexComboBox.getSelectedItem();

        if (selectedVertex == null) {
            JOptionPane.showMessageDialog(this, "Please select a starting vertex.");
            return;
        }

        System.out.println("Searching " + selectedVertex + " using " + selectedAlgorithm);
        printCurrentState();

        if (selectedAlgorithm.equals("Depth First Search")) {
            AlgorithmDepthSearchRecursive algorithm = new AlgorithmDepthSearchRecursive(parameterArea, this);
            VisualizationFramerwork.init(algorithm, parameterArea, this);
        } else if (selectedAlgorithm.equals("Topological Sort")) {
            AlgorithmTopologicalSort algorithm = new AlgorithmTopologicalSort(parameterArea, this);
            VisualizationFramerwork.init(algorithm, parameterArea, this);
        }
    }

    private void printCurrentState() {
        System.out.println("Vertices:");
        for (Vertex v : markedVertices) {
            System.out.println(v.getName() + " (" + v.getX() + ", " + v.getY() + ")");
        }

        System.out.println("Edges:");
        for (Edge e : markedEdges) {
            System.out.println(e.getName() + " from " + e.getSource().getName() + " to " + e.getDestination().getName() + " (Directed: " + e.isDirected() + ")");
        }
    }

    private class DrawingPanel extends JPanel {
        public DrawingPanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        MarkedVertex<VertexColorMarking> vertex = new MarkedVertex<>("V" + vertexCount++, e.getX(), e.getY(), vertexColorMarking);
                        vertexColorMarking.setColor(vertex, Color.BLACK);
                        markedVertices.add(vertex);
                        System.out.println("Added vertex: " + vertex.getName() + " at (" + vertex.getX() + ", " + vertex.getY() + ")");
                        updateVertexComboBox();
                        repaint();
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        Vertex v = findVertex(e.getX(), e.getY());
                        if (v != null) {
                            if (selectedVertex == null) {
                                selectedVertex = v;
                            } else {
                                boolean isDirected = edgeTypeComboBox.getSelectedItem().equals("Directed");
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
                        updateVertexComboBox();
                        repaint();
                    }
                }
            });
        }

        private Vertex findVertex(int x, int y) {
            for (Vertex v : markedVertices) {
                if (v.contains(x, y)) {
                    return v;
                }
            }
            return null;
        }

        private Edge findEdge(int x, int y) {
            for (Edge edge : markedEdges) {
                if (edge.contains(x, y)) {
                    return edge;
                }
            }
            return null;
        }

        private void deleteElement(int x, int y) {
            Vertex v = findVertex(x, y);
            if (v != null) {
                markedEdges.removeIf(edge -> edge.getSource() == v || edge.getDestination() == v);
                markedVertices.remove(v);
                System.out.println("Removed vertex: " + v.getName());
                return;
            }
            Edge edge = findEdge(x, y);
            if (edge != null) {
                markedEdges.remove(edge);
                System.out.println("Removed edge: " + edge.getName());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Edge edge : markedEdges) {
                edge.drawHere(g);
            }
            for (MarkedVertex vertex : markedVertices) {
                vertex.drawHere(g);
            }
        }
    }

    private void updateEdgeTypes(boolean directed) {
        for (Edge edge : markedEdges) {
            edge.setDirected(directed);
        }
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

    public void init() {
        parameterArea = new ParameterArea();
        setVisible(true);
    }
}
