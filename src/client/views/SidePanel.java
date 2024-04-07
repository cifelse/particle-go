package client.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * The ControlPanel class is a JPanel that is used to summon particles and walls.
 */
public class SidePanel extends JPanel {
    // FPS Counter
    private FPS fps;

    private StatusPanel statusPanel;

    /**
     * The ControlPanel constructor is used to create a new ControlPanel.
     * @param executor The executor to be used.
     * @param resources The resources to be used.
     */
    public SidePanel(String ip, String username) {
        this.fps = new FPS();

        // Adjust size according to what's left of the Window from the Screen
        setPreferredSize(new Dimension(Window.WIDTH - Screen.WIDTH, Screen.HEIGHT));
        setMinimumSize(new Dimension(Window.WIDTH - Screen.WIDTH, Screen.HEIGHT));

        // Set the Layout of the panel
        setLayout(new BorderLayout());

        // Add the FPS Panel
        add(fps, BorderLayout.NORTH);

        // Add the Guide Panel
        this.statusPanel = new StatusPanel(ip, username);
        add(statusPanel, BorderLayout.CENTER);
    }

    /**
     * The setFPS method is used to set the frames per second.
     * @param fps The frames per second.
     */
    public void setFPS(int fps) {
        this.fps.setLabel(String.valueOf(fps));
    }

    public void setStatus(boolean status) {
        if (!status) {
            remove(statusPanel);
            add(new StatusPanel(), BorderLayout.CENTER);
        }
    }

    /**
     * Below are the Instructions Displayed
     */
    private class StatusPanel extends Panel {
        public StatusPanel(String localhost, String username) {
            super("Welcome to Particle Go!", new BorderLayout());

            String text = "<html><br>Connected at: <B>" + localhost + "</B><br><br>Hello, <B>" + username + "</B>! You are now connected to world of Particle Go!<br><br>Roam around using the <B>W A S D</B> keys or your arrow keys.</html>";

            add(new JLabel(text), BorderLayout.NORTH);
        }

        public StatusPanel() {
            super("Welcome to Particle Go", new BorderLayout());

            String text = "<html><br>Connected at: <B>DISCONNECTED</B><br><br><B>Error:</B> It looks like either the Server died or you have lost connection. Please restart this application again to try again.</html>";

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