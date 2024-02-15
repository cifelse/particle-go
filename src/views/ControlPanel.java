package views;

import java.awt.BorderLayout;

import java.util.concurrent.Executor;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.Resources;

/**
 * The ControlPanel class is a JPanel that is used to summon particles and walls.
 */
public class ControlPanel extends JPanel {

    // FPS Counter
    private FPS fps = new FPS();

    /**
     * The ControlPanel constructor is used to create a new ControlPanel.
     * @param executor The executor to be used.
     * @param resources The resources to be used.
     */
    public ControlPanel(Executor executor, Resources resources) {
        // Set the Layout of the panel
        setLayout(new BorderLayout());

        // Add the FPS Panel
        add(fps, BorderLayout.NORTH);

        // Add the Add Particle Panel
        add(new AddParticlePanel(resources), BorderLayout.CENTER);

        // Add the Add Wall Panel
        add(new AddWallPanel(resources), BorderLayout.SOUTH);
    }

    /**
     * The setFPS method is used to set the frames per second.
     * @param fps The frames per second.
     */
    public void setFPS(int fps) {
        this.fps.setLabel(String.valueOf(fps));
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
