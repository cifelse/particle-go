package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.concurrent.ExecutorService;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.Resources;

/**
 * The ControlPanel class is a JPanel that is used to summon particles and walls.
 */
public class SidePanel extends JPanel {

    // FPS Counter
    private FPS fps = new FPS();

    /**
     * The ControlPanel constructor is used to create a new ControlPanel.
     * @param executor The executor to be used.
     * @param resources The resources to be used.
     */
    public SidePanel(ExecutorService executor, Resources resources) {
        // Set the Layout of the panel
        setLayout(new BorderLayout());

        // Adjust size according to what's left of the Window from the Screen
        setPreferredSize(new Dimension(Window.WIDTH - Screen.WIDTH, Screen.HEIGHT));
        setMinimumSize(new Dimension(Window.WIDTH - Screen.WIDTH, Screen.HEIGHT));
        
        // Add the FPS Panel
        add(fps, BorderLayout.NORTH);

        // Add the Guide Panel
        add(new GuidePanel(), BorderLayout.CENTER);

        // Add the Add Particle Panel
        add(new AddParticlePanel(executor, resources), BorderLayout.SOUTH);
    }

    /**
     * The setFPS method is used to set the frames per second.
     * @param fps The frames per second.
     */
    public void setFPS(int fps) {
        this.fps.setLabel(String.valueOf(fps));
    }

    /**
     * The Guides shown at the Side Panel
     */
    public class GuidePanel extends Panel {
        // Border Name
        private static final String PANEL_TITLE = "Welcome to Particle Go!";
    
        public GuidePanel() {
            // Call the Panel constructor
            super(PANEL_TITLE, new BorderLayout());
    
            // Add the Input Panel
            add(new InstructionPanel(), BorderLayout.NORTH);
        }
    
        /**
         * Below are the Instructions Displayed
         */
        private class InstructionPanel extends Panel {
            public InstructionPanel() {
                super(new BorderLayout());
    
                add(new JLabel("<html>This is the Master Control Center. In here, you can see where the explorers are. Likewise, you can add particles to send to your explorers.<br/><br/></html>"), BorderLayout.NORTH);
    
                add(new JLabel("<html>To <B>add a particle</B>, interact with the controls down below.</html>"), BorderLayout.SOUTH);
            }
        }
    }

    /**
     * The FPS class is a JPanel that is used to display the frames per second.
     */
    class FPS extends JPanel {
        private JLabel counter;

        public FPS() {
            // Set the Border
            setBorder(new EmptyBorder(10, 0, 10, 0));
            
            // Add the FPS Label
            add(new JLabel("FPS: ", JLabel.RIGHT));

            // Add the FPS Counter
            counter = new JLabel("0", JLabel.LEFT);
            add(counter);
        }

        public void setLabel(String label) {
            this.counter.setText(label);
        }
    }
}
