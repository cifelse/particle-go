package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;

public class GuidePanel extends Panel {

    private static final String PANEL_TITLE = "Welcome to Particle Go!";

    public GuidePanel() {
        // Call the Panel constructor
        super(PANEL_TITLE, new BorderLayout());

        // Add the Input Panel
        add(new InstructionPanel(), BorderLayout.NORTH);
    }

    /**
     * Below are the InputField Configurations
     */
    private class InstructionPanel extends Panel {
        public InstructionPanel() {
            super(new GridLayout(2, 4, 0, 5));

            
        }
    }
}
