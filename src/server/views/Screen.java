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
    // Screen Size
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

            int rad = (int) Math.floor(Particle.DIAMETER / 2) ;

            synchronized (particles) {
                g2d.setColor(Color.WHITE);

                for (Particle p : particles) {
                    int x = p.getX() <= rad ? rad : (p.getX() >= Screen.WIDTH ? p.getX() - rad : p.getX());
                    int y = p.getY() <= rad ? rad : (p.getY() >= Screen.HEIGHT ? p.getY() - rad : p.getY());

                    g2d.drawOval(x, Screen.HEIGHT - y, Particle.DIAMETER, Particle.DIAMETER);
                }
            }

            synchronized (players) {
                g2d.setColor(Color.RED);

                for (Player player : players.values()) {
                    int x = player.getX() <= rad ? rad : (player.getX() >= Screen.WIDTH ? player.getX() - rad : player.getX());
                    int y = player.getY() <= rad ? rad : (player.getY() >= Screen.HEIGHT ? player.getY() - rad : player.getY());

                    g2d.drawOval(x, Screen.HEIGHT - y, Particle.DIAMETER, Particle.DIAMETER);
                }
            }
        }

        this.frameCount++;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        for (Particle particle : particles) {
            executor.submit(() -> {
                synchronized(particle) {
                    int dia = Particle.DIAMETER;

                    // // Check if particle hits walls of the SimPanel
                    if (particle.getX() - dia / 2 <= 0 || particle.getX() + dia / 2 >= (Screen.WIDTH - dia)) {
                        particle.setVelocityX(-particle.getVelocityX());
                    }
                    
                    if (particle.getY() - dia / 2 <= 0 || particle.getY() + dia / 2 >= (Screen.HEIGHT - (dia * 7))) {
                        particle.setVelocityY(-particle.getVelocityY());
                    }

                    // Update the position of the particle
                    particle.refreshXY();
                } 
            });
        }

        // Repaint the SimPanel
        repaint();
    }
}
