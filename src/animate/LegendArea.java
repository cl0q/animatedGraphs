package animate;

import javax.swing.*;
import java.awt.*;

public class LegendArea extends visualization.LegendArea {

    public LegendArea() {
        super();
        setPreferredSize(new Dimension(150, 170));
    }

    @Override
    public void initialize(Graphics graphics) {
        setBorder(BorderFactory.createTitledBorder("LegendArea"));
    }
}
