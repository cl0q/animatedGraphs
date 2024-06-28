package animate;

import visualizationElements.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GraphDrawer extends  JFrame {
    private final ArrayList<Vertex> vertices = new ArrayList<>();
    private final ArrayList<Edge> edges = new ArrayList<>();
    private Vertex selectedVertex = null;
    private int vertexCount = 0;
    private int edgeCount = 0;

    private JComboBox<String> vertexComboBox;
    private JButton searchButton;
    private JRadioButton algorithm1;
    private JRadioButton algorithm2;

    public GraphDrawer() {
        setTitle("Graph Drawing Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        DrawingPanel drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        JPanel rightPanel = createRightPanel();
        add(rightPanel, BorderLayout.EAST);

        JButton exportButton = new JButton("Export");
        exportButton.addActionListener(e -> exportGraph());

        JPanel controlPanel = new JPanel();
        controlPanel.add(exportButton);

        JLabel instructions = new JLabel("<html>Left Click: Add Vertex<br>Right Click: Connect Vertices<br>Middle Click: Delete Vertex/Edge</html>");
        controlPanel.add(instructions);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel algorithmLabel = new JLabel("Select Algorithm:");
        algorithmLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(algorithmLabel);

        algorithm1 = new JRadioButton("Algorithm 1");
        algorithm2 = new JRadioButton("Algorithm 2");
        algorithm1.setAlignmentX(Component.CENTER_ALIGNMENT);
        algorithm2.setAlignmentX(Component.CENTER_ALIGNMENT);

        ButtonGroup algorithmGroup = new ButtonGroup();
        algorithmGroup.add(algorithm1);
        algorithmGroup.add(algorithm2);

        rightPanel.add(algorithm1);
        rightPanel.add(algorithm2);

        algorithm1.addActionListener(e -> updateSearchButton());
        algorithm2.addActionListener(e -> updateSearchButton());

        JLabel vertexLabel = new JLabel("Select Vertex:");
        vertexLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(vertexLabel);

        vertexComboBox = new JComboBox<>();
        vertexComboBox.setMaximumSize(new Dimension(120, 25)); // Set the preferred size of the dropdown menu
        vertexComboBox.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the dropdown menu
        updateVertexComboBox();
        rightPanel.add(vertexComboBox);

        vertexComboBox.addActionListener(e -> updateSearchButton());

        searchButton = new JButton();
        searchButton.setMaximumSize(new Dimension(200, 25));
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        updateSearchButton();
        searchButton.addActionListener(e -> searchAlgorithm());
        rightPanel.add(searchButton);

        return rightPanel;
    }

    private void updateVertexComboBox() {
        vertexComboBox.removeAllItems();
        for (Vertex vertex : vertices) {
            vertexComboBox.addItem(vertex.getName());
        }
        updateSearchButton();
    }

    private void updateSearchButton() {
        if (searchButton != null) {
            String selectedAlgorithm = algorithm1.isSelected() ? "Algorithm 1" : algorithm2.isSelected() ? "Algorithm 2" : "Algorithm";
            String selectedVertex = (String) vertexComboBox.getSelectedItem();
            searchButton.setText("Search " + (selectedVertex != null ? selectedVertex : "") + " using " + selectedAlgorithm);
        }
    }

    private void searchAlgorithm() {
        String selectedAlgorithm = algorithm1.isSelected() ? "Algorithm 1" : algorithm2.isSelected() ? "Algorithm 2" : "Algorithm";
        String selectedVertex = (String) vertexComboBox.getSelectedItem();
        System.out.println("Searching " + selectedVertex + " using " + selectedAlgorithm);

        JOptionPane.showMessageDialog(this, "Searching " + selectedVertex + " using " + selectedAlgorithm);
    }

    private class DrawingPanel extends JPanel {
        public DrawingPanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        vertices.add(new Vertex("V" + vertexCount++, e.getX(), e.getY()));
                        updateVertexComboBox();
                        repaint();
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        Vertex v = findVertex(e.getX(), e.getY());
                        if (v != null) {
                            if (selectedVertex == null) {
                                selectedVertex = v;
                            } else {
                                edges.add(new Edge("E" + edgeCount++, selectedVertex, v));
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
            for (Vertex v : vertices) {
                if (v.contains(x, y)) {
                    return v;
                }
            }
            return null;
        }

        private Edge findEdge(int x, int y) {
            for (Edge edge : edges) {
                if (edge.contains(x, y)) {
                    return edge;
                }
            }
            return null;
        }

        private void deleteElement(int x, int y) {
            Vertex v = findVertex(x, y);
            if (v != null) {
                edges.removeIf(edge -> edge.getSource() == v || edge.getDestination() == v);
                vertices.remove(v);
                return;
            }
            Edge edge = findEdge(x, y);
            if (edge != null) {
                edges.remove(edge);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Edge edge : edges) {
                edge.drawHere(g);
            }
            for (Vertex vertex : vertices) {
                vertex.drawHere(g);
            }
        }
    }

    private void exportGraph() {
        File directory = new File("./src/animate");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(directory, "graph.txt")))) {
            for (Vertex vertex : vertices) {
                writer.write(vertex.getName() + ";" + vertex.getX() + ";" + vertex.getY());
                writer.newLine();
            }
            for (Edge edge : edges) {
                writer.write(edge.getName() + ";" + edge.getSource().getName() + ";" + edge.getDestination().getName());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Graph exported successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error exporting graph: " + e.getMessage());
        }
    }

    public static class Vertex extends visualizationElements.Vertex {
        private final String name;
        private final int x, y;
        private static final int SIZE = 20;

        Vertex(String name, int x, int y) {
            super(x, y, name);
            this.name = name;
            this.x = x;
            this.y = y;
        }

        public String getName() {
            return name;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        void drawHere(Graphics g) {
            g.setColor(Color.BLACK);
            g.fillOval(x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
            g.setColor(Color.WHITE);
            g.drawString(name, x - SIZE / 2 + 4, y + 4);
        }

        boolean contains(int px, int py) {
            int radius = SIZE / 2;
            return Math.pow(px - x, 2) + Math.pow(py - y, 2) <= Math.pow(radius, 2);
        }
    }

    public static class Edge {
        private final String name;
        private final Vertex source;
        private final Vertex destination;

        Edge(String name, Vertex source, Vertex destination) {
            this.name = name;
            this.source = source;
            this.destination = destination;
        }

        public String getName() {
            return name;
        }

        public Vertex getSource() {
            return source;
        }

        public Vertex getDestination() {
            return destination;
        }

        void drawHere(Graphics g) {
            g.setColor(Color.BLACK);
            g.drawLine(source.getX(), source.getY(), destination.getX(), destination.getY());
            int mx = (source.getX() + destination.getX()) / 2;
            int my = (source.getY() + destination.getY()) / 2;
            g.setColor(Color.RED);
            g.drawString(name, mx, my);
        }

        boolean contains(int px, int py) {
            int x1 = source.getX();
            int y1 = source.getY();
            int x2 = destination.getX();
            int y2 = destination.getY();

            double distance = Math.abs((y2 - y1) * px - (x2 - x1) * py + x2 * y1 - y2 * x1) / Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
            return distance < 5;
        }
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public String[] getVertexNames(){
        String[] names = new String[vertexCount];
        for(int i = 0; i < vertexCount; i++){
            names[i] = "V" + i;
        }
        return names;
    }
}
