package client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.SwingUtilities;

import client.views.Window;

public class Client {
    // Max Threads
    public final static int MAX_THREADS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the Brain
            ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
            
            // Build the Body
            new Window(executor);
        });
    }
}