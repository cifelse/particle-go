package models;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class Streamer implements Modem {
    // Port for Watching the Stream
    public static final int PORT = 8080;

    /**
     * Create a Streamer that broadcasts to all players the contents and state of the game
     * @param executorService - Main Executor Service to handle Threads
     * @param resources - 
     */
    public Streamer(ExecutorService executorService, Resources resources) {
        try {
            // Start Accepting new Logins
            executorService.submit(new LoginHandler(executorService, resources));

            // Start Broadcasting the Contents
            executorService.submit(new StreamHandler());

            // Start Listening to Client Movements
            executorService.submit(new ClientHandler());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the Stream. Broadcasts to all Sockets the image on their screen.
     */
    private class StreamHandler implements Runnable {
        public StreamHandler() {

        }

        @Override
        public void run() {
            while (true) try {

            }
            catch (Exception e) {

            }
        }
    }

    /**
     * Handles each Client interactions and movements
     */
    private class ClientHandler implements Runnable {
        public ClientHandler() {

        }

        @Override
        public void run() {
            while (true) try {

            }
            catch (Exception e) {

            }
        }
    }

    /**
     * Handles all the incoming new logins in the Socket
     */
    private class LoginHandler implements Runnable {
        private ServerSocket serverSocket;

        private ExecutorService executorService;

        private Resources resources;

        public LoginHandler(ExecutorService executorService, Resources resources) throws Exception {
            serverSocket = new ServerSocket(Streamer.PORT);

            this.executorService = executorService;

            this.resources = resources;
        }

        @Override
        public void run() {
            while (true) try {
                // Accept new Client
                Socket socket = serverSocket.accept();
                
                // Handle the Adding in another Thread
                executorService.submit(new Thread(() -> {
                    String username = receive(socket);

                    System.out.println(username + "has joined the lobby.");

                    resources.addPlayer(new Player(500, 500, username, socket));
                }));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
