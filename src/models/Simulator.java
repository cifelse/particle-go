package models;

import java.util.concurrent.ExecutorService;

import views.ControlPanel;
import views.SimPanel;
import views.Window;

public class Simulator {
    public Simulator(ExecutorService executor, Resources resources) {
        // Create the Control Panel
        ControlPanel controlPanel = new ControlPanel(executor, resources);

        // Create the Sim Panel
        SimPanel simPanel = new SimPanel(executor, resources, controlPanel);

        // Place the Panels in a Window
        new Window(executor).setPanels(simPanel, controlPanel);
    }
}
