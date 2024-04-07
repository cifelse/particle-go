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
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import javax.swing.JPanel;
import javax.swing.Timer;

import client.models.Modem;

/**
 * The Main Panel that is used to display the simulation of the particles and players.
 */
public class Screen extends JPanel implements ActionListener {
    // Element Config
    public final int DIAMETER = 500;
    public final int FONT_SIZE = 20;

    // Screen Size
    public final static int WIDTH = 720;
    public final static int HEIGHT = 720;
    public final static int FRAME_RATE = 15;
    
    // Frames and Timers
    private int frameCount;
    private Timer timer, fps;

    // Side Panel
    SidePanel sidePanel;

    // Stream
    private StreamListener streamListener;
    
    // The Sprite and Username
    private Sprite sprite;
    private String username;

    // Frames 
    private Queue<String> particles;

    // private Queue<String> players;

    /**
     * Default Screen Constructor
     * @param socket - Client Socket to listen
     * @param sidepanel - The SidePanel for the Ping and FPS
     */
    public Screen(ExecutorService executorService, Socket socket, SidePanel sidepanel, String username) {
        // Focus on the Screen always
        setFocusable(true);

        // Set the size of the panel
        setBackground(Color.WHITE);

        // Set the preferred size of the panel
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        // Store SidePanel locally
        this.sidePanel = sidepanel;

        // Set the Initial Frame Count
        frameCount = 0;

        // Set the Timer
        timer = new Timer(FRAME_RATE, this);
        timer.start();

        // Set the FPS Counter
        fps = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sidepanel.setFPS(frameCount * 2);
                frameCount =  0;
            }
        });
        fps.start();

        // Initialize the Sprite
        this.sprite = new Sprite(Sprite.FORWARD);
        this.username = username;

        this.particles = new LinkedList<String>();
        // this.players = new LinkedList<String>();

        // Add Custom KeyListener
        addKeyListener(new CustomKeyListener(socket));

        // Add StreamListener
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

            // Draw the username above the sprite
            g2d.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
            g2d.drawString(username, (Screen.WIDTH - g2d.getFontMetrics().stringWidth(username)) / 2, (Screen.HEIGHT / 2) - Sprite.HEIGHT / 2 - FONT_SIZE);
            
            // Draw the Image
            g2d.drawImage(this.sprite.getImage(), (Screen.WIDTH / 2) - Sprite.WIDTH / 2, (Screen.HEIGHT / 2) - Sprite.HEIGHT / 2, this); 
            
            // Paint the Particles
            synchronized (particles) {
                g2d.setColor(Color.WHITE);

                // Check if there's frames to Paint
                String frame = particles.poll();

                // If frame queue is empty, abort function
                if (frame == null) return;

                // Get the Particle Coordinates
                String[] particles = frame.split(";");

                int rad = (int) Math.floor(DIAMETER / 2);
                
                for (String p : particles) {
                    String[] coord = p.split(",");

                    int x = Integer.parseInt(coord[0]);
                    int y = Integer.parseInt(coord[1]);

                    x = x <= rad ? rad : (x >= Screen.WIDTH ? x - rad : x);
                    y = y <= rad ? rad : (y >= Screen.HEIGHT ? y - rad : y);
        
                    g2d.drawOval(x, Screen.HEIGHT - y, DIAMETER, DIAMETER);
                    g2d.drawOval(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]), DIAMETER, DIAMETER);
                }
            }
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

            if (keyChar == 'w' || keyCode == KeyEvent.VK_UP ) {
                sprite.setImage(Sprite.FORWARD);
                broadcast(socket, Sprite.FORWARD);
            } 
            else if (keyChar == 'a' || keyCode == KeyEvent.VK_LEFT) {
                sprite.setImage(Sprite.LEFTWARD);
                broadcast(socket, Sprite.LEFTWARD);
            }
            else if (keyChar == 's' || keyCode == KeyEvent.VK_DOWN) {
                sprite.setImage(Sprite.BACKWARD);
                broadcast(socket, Sprite.BACKWARD);
            }
            else if (keyChar == 'd' || keyCode == KeyEvent.VK_RIGHT) {
                sprite.setImage(Sprite.RIGHTWARD);
                broadcast(socket, Sprite.RIGHTWARD);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            sprite.pauseImage();
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
        }

        public boolean isConnected() {
            return this.isConnected;
        }

        @Override
        public void run() {
            try {
                while (isConnected) {
                    // Receive Stream Details
                    String coord = receive(socket);

                    // Abort if Null
                    if (coord == null) {
                        isConnected = false;
                        continue;
                    }

                    synchronized (particles) {
                        // Decide what to do from the Data
                        this.isConnected = !coord.isEmpty() ? (particles.add(coord)) : false;
                    }
                }
                sidePanel.setStatus(false);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

