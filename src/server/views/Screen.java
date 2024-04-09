package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

import javax.swing.JPanel;
import javax.swing.Timer;

import models.Particle;
import models.Player;
import models.Resources;

/**
 * The Main Panel to display the simulation of the particles and players.
 */
public class Screen extends JPanel implements ActionListener  {
    // Screen Config
    public static final int RIGHT_MARGIN = 7;
    public static final int BOTTOM_MARGIN = 37;

    public final static int WIDTH = 1280;
    public final static int HEIGHT = 720;
    public final static int FRAME_RATE = 15;
    
    // Main Executor
    private final ExecutorService executor;
    
    // Particles
    private CopyOnWriteArrayList<Particle> particles;

    // Players
    private Map<String, Player> players;

    // Frames and Timers
    private int frameCount;
    private Timer timer, fps;

    public Screen(ExecutorService executor, Resources resources, SidePanel controlPanel) {
        // Set the Particles and Players
        this.particles = resources.getParticles();
        this.players = resources.getPlayers();

        // Set the Executor
        this.executor = executor;

        // Set the size of the panel
        setBackground(Color.BLACK);

        // Set the preferred size of the panel
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        // Set the Initial Frame Count
        frameCount = 0;

        // Set the Timer
        timer = new Timer(FRAME_RATE, this);
        timer.start();

        // Set the FPS Counter
        fps = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlPanel.setFPS(frameCount * 2);
                frameCount =  0;
            }
        });
        fps.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;

            int rad = (int) Math.floor(Particle.DIAMETER / 2);

            synchronized (particles) {
                g2d.setColor(Color.WHITE);

                for (Particle p : particles) {
                    // Adjust the position of the particle according to the radius and margins
                    int x = p.getX();
                    int y = p.getY();
                    
                    // 0 < x <= WIDTH - RIGHT_MARGIN
                    x = x < 0 ? 0 : x < (Screen.WIDTH - RIGHT_MARGIN - rad) ? x : (Screen.WIDTH - RIGHT_MARGIN - rad); 

                    // BOTTOM_MARGIN < y <= HEIGHT
                    y = y < BOTTOM_MARGIN + rad ? BOTTOM_MARGIN + rad : y < Screen.HEIGHT ? y : Screen.HEIGHT;

                    // To Make (0,0) at the bottom left corner
                    y = Screen.HEIGHT - y;

                    g2d.drawOval(x, y, Particle.DIAMETER, Particle.DIAMETER);
                }
            }

            synchronized (players) {
                g2d.setColor(Color.RED);

                for (Player p : players.values()) {
                    // Adjust the position of the particle according to the radius and margins
                    int x = p.getX();
                    int y = p.getY();
                    
                    // 0 < x <= WIDTH - RIGHT_MARGIN
                    x = x < 0 ? 0 : x < (Screen.WIDTH - RIGHT_MARGIN) ? x : (Screen.WIDTH - RIGHT_MARGIN); 

                    // BOTTOM_MARGIN < y <= HEIGHT
                    y = y < BOTTOM_MARGIN ? BOTTOM_MARGIN : y < Screen.HEIGHT ? y : Screen.HEIGHT;

                    // To Make (0,0) at the bottom left corner
                    y = Screen.HEIGHT - y;

                    g2d.drawOval(x, y, Particle.DIAMETER, Particle.DIAMETER);
                }
            }
        }

        this.frameCount++;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        synchronized (particles) {
            int rad = (int) Math.floor(Particle.DIAMETER / 2);

            for (Particle particle : particles) {
                executor.submit(() -> {
                    // Adjust the position of the particle according to the radius and margins
                    int x = particle.getX();
                    int y = particle.getY();
                    
                    // 0 < x <= WIDTH - RIGHT_MARGIN
                    x = x < 0 ? 0 : x < (Screen.WIDTH - RIGHT_MARGIN) ? x : (Screen.WIDTH - RIGHT_MARGIN); 

                    // BOTTOM_MARGIN < y <= HEIGHT
                    y = y < BOTTOM_MARGIN ? BOTTOM_MARGIN : y < Screen.HEIGHT ? y : Screen.HEIGHT;

                    // // Check if particle hits walls of the SimPanel
                    if (x == 0 || x + rad >= (Screen.WIDTH - RIGHT_MARGIN)) {
                        particle.setVelocityX(-particle.getVelocityX());
                    }
                    
                    if (y == BOTTOM_MARGIN || y + rad >= Screen.HEIGHT) {
                        particle.setVelocityY(-particle.getVelocityY());
                    }

                    // Update the position of the particle
                    particle.refreshXY();
                
                });
            }
        }

        // Repaint the SimPanel
        repaint();
    }
}
