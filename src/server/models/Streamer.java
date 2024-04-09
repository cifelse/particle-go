package models;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import views.Screen;

public class Streamer implements Modem {
    // Port for Watching the Stream
    public static final int PORT = 8080;

    private ExecutorService executorService;

    private Resources resources;

    private Console console;

    /**
     * Create a Streamer that broadcasts to all players the contents and state of the game
     * @param executorService - Main Executor Service to handle Threads
     * @param resources - All Resources
     */
    public Streamer(ExecutorService executorService, Resources resources) {
        try {
            this.resources = resources;

            this.executorService = executorService;

            this.console = new Console("Server");

            console.log("Up and running! Listening for new Clients.");

            // Start Accepting new Logins
            executorService.submit(new LoginHandler());

            // Start Broadcasting the Contents
            executorService.submit(new StreamHandler());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the Stream. Broadcasts to all Sockets the image on their screen.
     */
    private class StreamHandler implements Runnable, Modem {
        @Override
        public void run() {
            while (true) try {
                synchronized (resources) {
                    for (Player player : resources.getPlayers().values()) {
                        Socket socket = player.getSocket();
                        boolean hasError = false;

                        if (!socket.isClosed()) {
                            // SEND ALL OF THE PARTICLES
                            String payload = Protocol.PARTICLE;

                            for (Particle particle : resources.getParticles()) {
                                payload += particle.getX() + Protocol.SEPARATOR + particle.getY() + Protocol.EOF;
                            }

                            if (payload != Protocol.PARTICLE) {
                                hasError = !(broadcast(socket, payload));
                            }

                            // SEND ALL OF THE PLAYERS
                            payload = Protocol.PLAYER;
                            
                            Map<String, Player> otherPlayers = resources.getOtherPlayers(player.getUsername());

                            for (Player pl : otherPlayers.values()) {
                                payload += pl.getLocation();
                            }

                            if (payload != Protocol.PLAYER) hasError = !(broadcast(socket, payload));
                        }

                        if (hasError) {
                            resources.removePlayer(player);
                            console.log((player.getUsername() + " (" + socket.getInetAddress() + ") disconnected."));
                        }
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles all the incoming new logins in the Socket
     */
    private class LoginHandler implements Runnable {
        private ServerSocket serverSocket;

        public LoginHandler() throws Exception {
            serverSocket = new ServerSocket(Streamer.PORT);
        }

        @Override
        public void run() {
            while (true) try {
                // Accept new Client
                Socket socket = serverSocket.accept();
                
                // Handle the Adding in another Thread
                executorService.submit(new Thread(() -> {
                    // Send the Map Specs
                    broadcast(socket, Screen.WIDTH + Protocol.SEPARATOR + Screen.HEIGHT);

                    String raw = receive(socket);

                    if (raw.isEmpty()) return;

                    // Each login you will receive: USERNAME,X,Y
                    String[] config = raw.split(Protocol.EOF)[0].split(Protocol.SEPARATOR);

                    console.log((config[0] + " (" + socket.getInetAddress() + ") has joined the lobby at " + config[1] + ", " + config[2] + "."));

                    synchronized (resources) {
                        resources.addPlayer(new Player(config[0], config[1], config[2], socket));
                    }

                    // Add a new Client Handler
                    executorService.submit(new ClientHandler(config[0], socket));
                }));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles each Client interactions and movements
     */
    private class ClientHandler implements Runnable, Modem {
        private String username;
        private Socket clientSocket;

        public ClientHandler(String username, Socket clientSocket) {
            this.username = username;
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            while (true) try {
                int request = receiveInt(clientSocket);

                if (request == Protocol.DISCONNECT) {
                    clientSocket.close();
                    resources.removePlayer(username);
                    break;
                }

                // Move the Player
                synchronized (resources) {
                    resources.getPlayer(username).move(request);
                }
            }
            catch (Exception e) {
                try {
                    if (!this.clientSocket.isClosed())
                        this.clientSocket.close();
                }
                catch (Exception err) {
                    err.printStackTrace();
                }
            }
        }
    }
}
