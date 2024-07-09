package animate.drawing;

import javax.swing.*;
import java.awt.*;

public class LegendArea extends visualization.LegendArea {

    public LegendArea() {
        super();
        setPreferredSize(new Dimension(150, 200)); // Adjust the height to accommodate the additional entry
    }

    @Override
    public void initialize(Graphics g) {
        setBorder(BorderFactory.createTitledBorder("LegendArea"));

        int y = 30;
        g.setColor(Color.BLACK);
        g.fillOval(10, y, 20, 20);
        g.setColor(Color.BLACK);
        g.drawOval(10, y, 20, 20);
        g.drawString("Standartknoten", 40, y + 15);

        g.setColor(Color.RED);
        g.fillOval(10, 2*y, 20, 20);
        g.setColor(Color.RED);
        g.drawOval(10, 2*y, 20, 20);
        g.drawString("Startknoten", 40, (y*2) + 15);

        g.setColor(Color.ORANGE);
        g.fillOval(10, 3*y, 20, 20);
        g.setColor(Color.ORANGE);
        g.drawOval(10, 3*y, 20, 20);
        g.drawString("Aktueller Knoten", 40, (y*3) + 15);

        g.setColor(Color.BLUE);
        g.fillOval(10, 4*y, 20, 20);
        g.setColor(Color.BLUE);
        g.drawOval(10, 4*y, 20, 20);
        g.drawString("Benachbarter Knoten", 40, (y*4) + 15);

        g.setColor(Color.MAGENTA);
        g.fillOval(10, 5*y, 20, 20);
        g.setColor(Color.MAGENTA);
        g.drawOval(10, 5*y, 20, 20);
        g.drawString("Zyklenfarbe", 40, (y*5) + 15);

        g.setColor(Color.GREEN);
        g.fillOval(10, 6*y, 20, 20); // Move the green node here
        g.setColor(Color.GREEN);
        g.drawOval(10, 6*y, 20, 20);
        g.drawString("Fertiger Knoten", 40, (y*6) + 15);

        g.setColor(Color.BLACK);
        // Draw the black edge
        int startX = 10;
        int startY = 7 * y + 10;
        int endX = 30;
        int endY = 7 * y + 30;
        g.drawLine(startX, startY, endX, endY);
        // Draw arrowhead
        int arrowSize = 6;
        int dx = endX - startX;
        int dy = endY - startY;
        double angle = Math.atan2(dy, dx);
        int arrowX1 = endX - (int) (arrowSize * Math.cos(angle - Math.PI / 6));
        int arrowY1 = endY - (int) (arrowSize * Math.sin(angle - Math.PI / 6));
        int arrowX2 = endX - (int) (arrowSize * Math.cos(angle + Math.PI / 6));
        int arrowY2 = endY - (int) (arrowSize * Math.sin(angle + Math.PI / 6));
        g.drawLine(endX, endY, arrowX1, arrowY1);
        g.drawLine(endX, endY, arrowX2, arrowY2);
        g.drawString("Kante", 40, (y*7) + 15);

        g.setColor(Color.getHSBColor(96f, 88f, 51f));
        // Draw the directed edge with arrowhead
        startX = 10;
        startY = 8 * y + 10;
        endX = 30;
        endY = 8 * y + 30;
        g.drawLine(startX, startY, endX, endY);
        dx = endX - startX;
        dy = endY - startY;
        angle = Math.atan2(dy, dx);
        arrowX1 = endX - (int) (arrowSize * Math.cos(angle - Math.PI / 6));
        arrowY1 = endY - (int) (arrowSize * Math.sin(angle - Math.PI / 6));
        arrowX2 = endX - (int) (arrowSize * Math.cos(angle + Math.PI / 6));
        arrowY2 = endY - (int) (arrowSize * Math.sin(angle + Math.PI / 6));
        g.drawLine(endX, endY, arrowX1, arrowY1);
        g.drawLine(endX, endY, arrowX2, arrowY2);
        g.setColor(Color.BLACK);
        g.drawString("Besuchte Kante", 40, (y*8) + 15);
    }
}
