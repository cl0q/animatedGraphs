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

public class GraphDrawer extends JFrame {
    private final ArrayList<Vertex> vertices = new ArrayList<>();
    private final ArrayList<Edge> edges = new ArrayList<>();
    private Vertex selectedVertex = null;
    private int vertexCount = 0;
    private int edgeCount = 0;

    public GraphDrawer() {
        setTitle("Graph Drawing Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        DrawingPanel drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        JButton exportButton = new JButton("Export");
        exportButton.addActionListener(e -> exportGraph());

        JPanel controlPanel = new JPanel();
        controlPanel.add(exportButton);

        JLabel instructions = new JLabel("<html>Left Click: Add Vertex<br>Right Click: Connect Vertices<br>Middle Click: Delete Vertex/Edge</html>");
        controlPanel.add(instructions);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private class DrawingPanel extends JPanel {
        public DrawingPanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        vertices.add(new Vertex("V" + vertexCount++, e.getX(), e.getY()));
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
