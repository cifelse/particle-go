package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.Resources;
import models.Wall;

public class AddWallPanel extends Panel {

    private static final String PANEL_TITLE = "Add Walls";

    private InputField x1, x2, y1, y2;

    private Resources resources;

    public AddWallPanel(Resources resources) {
        // Call the Panel constructor
        super(PANEL_TITLE, new BorderLayout());

        // Set the Resources
        this.resources = resources;

        // Add the Input Panel
        add(new InputPanel(), BorderLayout.NORTH);

        // Add the Button Panel
        add(new ButtonPanel(), BorderLayout.SOUTH);
    }

    /**
     * Below are the InputField Configurations
     */
    private class InputPanel extends Panel {
        public InputPanel() {
            super(new GridLayout(2, 4, 0, 5));

            x1 = addInputBar("<html>X<sub>1</sub</html>", 5);

            y1 = addInputBar("<html>Y<sub>1</sub</html>", 5);

            x1 = addInputBar("<html>X<sub>2</sub</html>", 5);

            y2 = addInputBar("<html>Y<sub>2</sub</html>", 5);
        }
    }

    /**
    * Below are the Button Configurations
    */
    private class ButtonPanel extends Panel {
        public ButtonPanel() {
            super(new GridLayout(1, 2));

            addButton("Add Wall", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    resources.addWall(new Wall(x1.getText(), y1.getText(), x2.getText(), y2.getText()));
                }
            });

            addButton("Clear Walls", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    resources.clearWalls();
                }
            });
        }
    }
}
