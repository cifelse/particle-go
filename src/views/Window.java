package views;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.concurrent.ExecutorService;

/**
 * The Window class is a JFrame that is used to display the simulation and control panels.
 */
public class Window extends JFrame {
    // Window Title
    public final static String TITLE = "Particle Simulator";

    // Window Size
    public final static int WIDTH = 1480;

    public final static int HEIGHT = 720;

    /**
     * The Window constructor is used to create a new Window.
     * @param executor - The executor to be used.
     */
    public Window(ExecutorService executor) {
        // Set the title of the window
        super(TITLE);

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set the size of the window
        setSize(Window.WIDTH, Window.HEIGHT);

        // Set the window to be not resizable
        setResizable(false);
        
        // Set the layout of the window
        setLayout(new FlowLayout());
        
        // Set the window to be visible
        setVisible(true);

        // Close everything when X is clicked
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                executor.shutdownNow();
                System.gc();
            }
        });
    }

    /**
     * The setPanels method is used to set the panels of the window.
     * @param simPanel - The simulation panel
     * @param controlPanel - The control panel
     */
    public void setPanels(SimPanel simPanel, ControlPanel controlPanel) {
        // Create the a Split Pane for the Sim Panel and Control Panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(1);
        splitPane.setDividerLocation(SimPanel.WIDTH);
        splitPane.add(simPanel);
        splitPane.add(controlPanel);

        // Add the split pane to the window
        setContentPane(splitPane);
    }
}
