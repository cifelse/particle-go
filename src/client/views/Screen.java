package client.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The SimPanel class is the Main Panel that is used to display the simulation of the particles and walls.
 */
public class Screen extends JPanel implements ActionListener, KeyListener {
    // SimPanel Screen Size
    public final static int WIDTH = 720;
    public final static int HEIGHT = 720;
    public final static int FRAME_RATE = 15;
    
    // Frames and Timers
    private int frameCount;
    private Timer timer, fps;

    // The Sprite
    private Sprite sprite;

    public Screen(SidePanel sidepanel) {
        // Focus on the Screen always
        setFocusable(true);

        // Set the size of the panel
        setBackground(Color.WHITE);

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
                sidepanel.setFPS(frameCount * 2);
                frameCount =  0;
            }
        });
        fps.start();

        // Initialize the Sprite
        this.sprite = new Sprite(Sprite.FORWARD);

        // Add Key Listener
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.drawImage(this.sprite.getImage(), (WIDTH / 2) - Sprite.WIDTH / 2, (HEIGHT / 2) - Sprite.HEIGHT / 2, this); 
            
            // synchronized(particles) {
            //     for (Particle p : particles) {
            //         g2d.drawOval(p.getX(), p.getY(), Particle.DIAMETER, Particle.DIAMETER);
            //     }
            // }
        }

        this.frameCount++;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Repaint the SimPanel
        repaint();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();
        int keyCode = e.getKeyCode();

        if (keyChar == 'w' || keyCode == KeyEvent.VK_UP ) {
            this.sprite.setImage(Sprite.FORWARD);
        } 
        else if (keyChar == 'a' || keyCode == KeyEvent.VK_LEFT) {
            this.sprite.setImage(Sprite.LEFTWARD);
        }
        else if (keyChar == 's' || keyCode == KeyEvent.VK_DOWN) {
            this.sprite.setImage(Sprite.BACKWARD);
        }
        else if (keyChar == 'd' || keyCode == KeyEvent.VK_RIGHT) {
            this.sprite.setImage(Sprite.RIGHTWARD);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.sprite.pauseImage();
    }
}

