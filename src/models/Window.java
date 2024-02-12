package models;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import views.ControlPanel;
import views.SimPanel;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;

public class Window extends JFrame {
    // Window Title
    private final static String TITLE = "Particle Simulator";

    // Resources
    private final Resources resources;

    // Executor
    private final ExecutorService executor;

    public Window(ExecutorService executor, Resources resources) {
        // Set the title of the window
        super(TITLE);
        
        // Set the resources
        this.resources = resources;

        // Set the executor
        this.executor = executor;

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set the size of the window
        setSize(1480, 720);

        // Set the window to be not resizable
        setResizable(false);
        
        // Set the layout of the window
        setLayout(new FlowLayout());
        
        // Set the window to be visible
        setVisible(true);

        // Create the Sim Panel
        SimPanel simPanel = new SimPanel(executor, resources);
        
        // Create the Control Panel
        ControlPanel controlPanel = new ControlPanel(executor, resources, simPanel);

        // Create the a Split Pane for the Sim Panel and Control Panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(0);
        splitPane.setDividerLocation(1280);
        splitPane.add(simPanel);
        splitPane.add(controlPanel);

        // Add the split pane to the window
        setContentPane(splitPane);

        // Close everything when X is clicked
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                executor.shutdownNow();
            }
        });
    }
}
