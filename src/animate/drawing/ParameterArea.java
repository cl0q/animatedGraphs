package animate.drawing;

import javax.swing.*;
import java.io.Serial;

public class ParameterArea extends visualization.ParameterArea {

    @Serial
    private static final long serialVersionUID = 1L;

    protected JTextField maxValue;

    public ParameterArea() {
        super();
        setBorder(BorderFactory.createTitledBorder("ParameterArea"));

    }
}