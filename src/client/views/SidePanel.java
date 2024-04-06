package client.views;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
    public SidePanel(String ip, String username) {
        // Set the Layout of the panel
        setLayout(new BorderLayout());

        // Add the FPS Panel
        add(fps, BorderLayout.NORTH);

        // Add the Guide Panel
        add(new InstructionPanel(ip, username), BorderLayout.CENTER);
    }

    /**
     * The setFPS method is used to set the frames per second.
     * @param fps The frames per second.
     */
    public void setFPS(int fps) {
        this.fps.setLabel(String.valueOf(fps));
    }

    /**
     * Below are the Instructions Displayed
     */
    private class InstructionPanel extends Panel {
        public InstructionPanel(String localhost, String username) {
            super("Welcome to Particle Go!", new BorderLayout());

            String text = "<html><br>Connected at: <B>" + localhost + "</B><br><br>Hello, " + username + "! You are now connected to world of Particle Go!<br><br>Roam around using the <B>W A S D</B> keys or your arrow keys.</html>";

            add(new JLabel(text), BorderLayout.NORTH);
        }
    }

    /**
     * The FPS class is a JPanel that is used to display the frames per second.
     */
    public class FPS extends JPanel {
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