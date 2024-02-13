package views;

import java.awt.GridLayout;
import java.util.concurrent.Executor;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.Resources;

/**
 * The ControlPanel class is a JPanel that is used to summon particles and walls.
 */
public class ControlPanel extends JPanel {

    private AddParticlePanel addParticlePanel;

    private AddWallPanel addWallPanel;

    /**
     * The ControlPanel constructor is used to create a new ControlPanel.
     * @param executor The executor to be used.
     * @param resources The resources to be used.
     * @param simPanel The simPanel to be used.
     */
    public ControlPanel(Executor executor, Resources resources, SimPanel simPanel) {
        add(new FPS(simPanel));
    }

    class FPS extends JPanel {
        private JLabel counter;

        public FPS(SimPanel simPanel) {
            setLayout(new GridLayout(1, 2));
            setBorder(new EmptyBorder(10, 0, 10, 0));
            
            // Add the FPS Label
            add(new JLabel("FPS: ", JLabel.RIGHT));

            // Add the FPS Counter
            counter = new JLabel("0", JLabel.LEFT);
            // counter.setText(simPanel.getFPS());
            add(counter);
        }

        public JLabel getCounterLabel() {
            return this.counter;
        }
    }
}
