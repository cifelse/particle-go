import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;

import models.Resources;
import models.Simulator;

public class Main {
    // Max Threads
    public final static int MAX_THREADS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the Brain
            ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

            // Create the Heart
            Resources resources = new Resources();
            
            // Build the Body
            new Simulator(executor, resources);
        });
    }
}
