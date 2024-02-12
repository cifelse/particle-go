import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;

import models.Resources;
import models.Window;

public class Main {
    // Max Threads
    private final static int MAX_THREADS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

        SwingUtilities.invokeLater(() -> {
            Resources resources = new Resources();
            
            // Open a new Particle Simulator Window
            new Window(executor, resources);
        });
    }
}
