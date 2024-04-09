package client.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import javax.swing.JPanel;
import javax.swing.Timer;

import client.models.Modem;
import client.models.Player;
import client.models.Protocol;
import client.models.Frames;
import client.models.Frames.*;
import client.models.Arena;

/**
 * The Main Panel that is used to display the simulation of the particleFrames and playerFrames.
 */
public class Screen extends JPanel implements ActionListener, Modem {
    // Element Config
    public static final int DIAMETER = 200;
    public static final int FONT_SIZE = 20;
    public static final int FRAME_LIMIT = 30;
    public static final int WIDTH_RATIO = 22;
    public static final int HEIGHT_RATIO = 22;

    // Screen Size
    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int FRAME_RATE = 15;
    
    // Frames and Timers
    private int frameCount;
    private Timer timer, fps;

    // Side Panel
    private SidePanel sidePanel;

    // To Listen to the Server's Stream
    private StreamListener streamListener;

    // Create the Arena and Player
    private Arena arena;
    private Player player;

    // Frames to Render
    private Frames particleFrames;
    private Frames playerFrames;

    /**
     * Create a Screen with the ExecutorService, Socket, SidePanel and Player
     * @param executorService - the ExecutorService
     * @param socket - the Socket to communicate with the Server
     * @param sidepanel - the SidePanel to display the details
     * @param username - the Player's chosen name to display
     */
    public Screen(ExecutorService executorService, Socket socket, SidePanel sidepanel, String username) {
        // Focus on the Screen always
        setFocusable(true);

        // Set the size of the panel
        setBackground(Color.WHITE);

        // Set the preferred size of the panel
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        // Store SidePanel locally in this Class
        this.sidePanel = sidepanel;

        // Set the Timer for the Frame Count (DO NOT DELETE)
        this.timer = new Timer(FRAME_RATE, this);
        this.timer.start();

        // Start the FPS Counter (DO NOT DELETE)
        this.frameCount = 0;
        this.fps = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sidepanel.setFPS(frameCount * 2);
                frameCount =  0;
            }
        });
        this.fps.start();

        // Initialize the Player to Display
        this.player = new Player(username);

        // Frames to be Rendered
        this.particleFrames = new Frames();
        this.playerFrames = new Frames();

        // Get the Map Size and Set the Arena
        String[] mapSize = receive(socket).split(Protocol.SEPARATOR);
        this.arena = new Arena(mapSize[0], mapSize[1], this.player);

        // Add Custom KeyListener for tracking the Player Movements
        addKeyListener(new CustomKeyListener(socket));

        // Add StreamListener for new Instructions to be displayed in this Screen
        this.streamListener = new StreamListener(socket);
        executorService.submit(this.streamListener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (!streamListener.isConnected()) return;

        this.frameCount++;

        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;

            Player player = this.arena.getPlayer();

            // Draw the player.getUsername() above the sprite
            g2d.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
            g2d.drawString(player.getUsername(), (Screen.WIDTH - g2d.getFontMetrics().stringWidth(player.getUsername())) / 2, (Screen.HEIGHT / 2) - Sprite.HEIGHT / 2 - FONT_SIZE);
            
            // Draw the Image
            g2d.drawImage(player.getImage(), (Screen.WIDTH / 2) - Sprite.WIDTH / 2, (Screen.HEIGHT / 2) - Sprite.HEIGHT / 2, this); 
            
            // Paint the particleFrames
            synchronized (particleFrames) {
                g2d.setColor(Color.BLACK);

                // Check if there's frames to Paint
                Frame frame = particleFrames.poll();

                // If frame queue is empty, skip this function
                if (frame != null) {
                    int rad = (int) Math.floor(DIAMETER / 2);

                    // Paint the Particle Coordinates
                    for (Element p : frame.getElements()) {
                        int x = (Screen.WIDTH / 2) - rad + (p.getX() - player.getX()) * WIDTH_RATIO;
                        int y = (Screen.HEIGHT / 2) - rad - (p.getY() - player.getY()) * HEIGHT_RATIO;

                        // Only Render if within the Screen
                        if (x >= 0 && x <= Screen.WIDTH && y >= 0 && y <= Screen.HEIGHT)
                            g2d.drawOval(x, y, DIAMETER, DIAMETER);
                    }
                }
            }

            // Paint Other playerFrames if Available
            synchronized (playerFrames) {
                g2d.setColor(Color.BLACK);

                // Check if there's frames to Paint
                Frame frame = playerFrames.poll();

                // If frame queue is empty, skip this function
                if (frame != null) {
                    int rad = (int) Math.floor((DIAMETER - 100) / 2);
                    
                    // Paint the Player Coordinates
                    for (Element p : frame.getElements()) {
                        int x = (Screen.WIDTH / 2) - rad + (p.getX() - player.getX()) * WIDTH_RATIO;
                        int y = (Screen.HEIGHT / 2) - rad - (p.getY() - player.getY()) * HEIGHT_RATIO;

                        // Only Render if within the Screen
                        if (x >= 0 && x <= Screen.WIDTH && y >= 0 && y <= Screen.HEIGHT) {
                            // g2d.drawImage(p.getImage(), x, y, this);
                            g2d.drawString(p.getName(), (x - g2d.getFontMetrics().stringWidth(p.getName())), y + FONT_SIZE);
                            g2d.drawRect(x, y, DIAMETER - 100, DIAMETER - 100);
                        }
                    }
                }
            }

            // if (this.explorer.getCenter_x() - 16 < 0) {
            //     g2d.fillRect(0, 0, Math.abs(this.explorer.getCenter_x() - 16)*width_ratio, HEIGHT);
            // }

            // if (this.explorer.getCenter_x() + 16 > WIDTH) {
            //     int overflow = this.explorer.getCenter_x() + 16;
            //     int width = (overflow -WIDTH) * width_ratio;
            //     g2d.fillRect(WIDTH- (width), 0, width, HEIGHT);
                
            // }

            // if (this.explorer.getCenter_y() - 9 < 0){
            //     g2d.fillRect(0, 0, WIDTH, Math.abs(this.explorer.getCenter_y() - 9)*height_ratio);
            // }

            // if (this.explorer.getCenter_y() + 9 >  HEIGHT) {
            //     int overflow = this.explorer.getCenter_y() + 9;
            //     int height = (overflow - HEIGHT) * height_ratio;
            //     g2d.fillRect(0, HEIGHT - height, WIDTH, height);
            // }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Repaint the SimPanel
        repaint();
    }

    /**
     * All KeyListener Configurations below
     */
    private class CustomKeyListener extends KeyAdapter implements Modem {
        private Socket socket;

        public CustomKeyListener(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            char keyChar = e.getKeyChar();
            int keyCode = e.getKeyCode();

            /**
             * When moving, broadcast it first to the server then
             * move locally to avoid delay in movement.
             */

            if (keyChar == 'w' || keyCode == KeyEvent.VK_UP ) {
                broadcast(socket, Sprite.FORWARD);
                player.move(Player.FORWARD);
            } 
            else if (keyChar == 'a' || keyCode == KeyEvent.VK_LEFT) {
                broadcast(socket, Sprite.LEFTWARD);
                player.move(Player.LEFTWARD);
            }
            else if (keyChar == 's' || keyCode == KeyEvent.VK_DOWN) {
                broadcast(socket, Sprite.BACKWARD);
                player.move(Player.BACKWARD);
            }
            else if (keyChar == 'd' || keyCode == KeyEvent.VK_RIGHT) {
                broadcast(socket, Sprite.RIGHTWARD);
                player.move(Player.RIGHTWARD);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            player.move(Player.STOP);
        }
    }

    /**
     * Listen to every update of the Stream
     */
    private class StreamListener implements Runnable, Modem {
        private Socket socket;
        private boolean isConnected;

        public StreamListener(Socket socket) {
            this.socket = socket;
            this.isConnected = true;

            // Tell the Server where you are
            broadcast(socket, player.getLocation());
        }

        public boolean isConnected() {
            return this.isConnected;
        }

        @Override
        public void run() {
            try {
                while (isConnected) {
                    // Receive Stream Details
                    String raw = receive(socket);

                    // Abort if Null
                    if (raw == null) {
                        isConnected = false;
                        continue;
                    }

                    int type = Integer.parseInt(raw.split(Protocol.MESSAGE)[0]);
                    String coord = raw.split(Protocol.MESSAGE)[1];

                    if (type == FrameType.PARTICLE) synchronized (particleFrames) {
                        this.isConnected = !coord.isEmpty() ? (particleFrames.addParticleFrame(coord)) : false;

                        if (particleFrames.size() > FRAME_LIMIT) particleFrames.poll();
                    }

                    if (type == FrameType.PLAYER) synchronized (playerFrames) {
                        this.isConnected = !coord.isEmpty() ? (playerFrames.addPlayerFrame(coord)) : false;

                        if (playerFrames.size() > FRAME_LIMIT) playerFrames.poll();
                    }
                }

                // Once Disconnected, Alert the SidePanel
                sidePanel.setStatus(false);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
