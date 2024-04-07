package client.views;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import javax.imageio.ImageIO;

public class Sprite {
    // Size of the Sprite in the Screen
    public static final int WIDTH = 150;
    public static final int HEIGHT = 150;

    // Direction Constants
    public static final int FORWARD = 0;
    public static final int BACKWARD = 1;
    public static final int LEFTWARD = 2;
    public static final int RIGHTWARD = 3;

    // Image Paths
    private static final String[] FRONT_PATHS = {
            "images/f1.png",
            "images/f2.png",
            "images/f3.png",
            "images/f4.png",
            "images/f5.png",
            "images/f6.png",
            "images/f7.png",
            "images/f8.png",
            "images/f9.png"
    };

    private static final String[] BACK_PATHS = {
        "images/b1.png",
        "images/b2.png",
        "images/b3.png",
        "images/b4.png",
        "images/b5.png",
        "images/b6.png",
        "images/b7.png",
        "images/b8.png",
        "images/b9.png"
    };

    private static final String[] LEFT_PATHS = {
        "images/l1.png",
        "images/l2.png",
        "images/l3.png",
        "images/l4.png",
        "images/l5.png",
        "images/l6.png",
        "images/l7.png",
        "images/l8.png",
        "images/l9.png"
    };

    private static final String[] RIGHT_PATHS = {
        "images/r1.png",
        "images/r2.png",
        "images/r3.png",
        "images/r4.png",
        "images/r5.png",
        "images/r6.png",
        "images/r7.png",
        "images/r8.png",
        "images/r9.png"
    };

    public BufferedImage[] currentImages;

    private String[] currentPaths;
    private int currentImageIndex;
    private boolean stand;

    private Timer timer;

    public Sprite() {
        // Set the Sprite to Stand only
        this.stand = true;

        // Initialize the current image index
        currentImageIndex = 0;

        // Default initial image is Forward
        setImage(FORWARD);

        // Create Timer to change image every 1 second
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!stand) currentImageIndex = (currentImageIndex + 1) % currentPaths.length;
            }
        });
        timer.start();
    }

    /**
     * Initialize a Sprite with a custom Direction
     * @param direction - Sprire Direction
     */
    public Sprite(int direction) {
        // Set the Sprite to Stand only
        this.stand = true;

        // Initialize the current image index
        currentImageIndex = 0;

        // Default initial image is Forward
        setImage(direction);

        // Create Timer to change image every 1 second
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!stand) currentImageIndex = (currentImageIndex + 1) % currentPaths.length;
            }
        });
        timer.start();
    }

    /**
     * Get the Current Image of the Sprite
     * @return - The current image of the Sprite
     */
    public Image getImage() {
        return this.currentImages[currentImageIndex].getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
    }

    public Image pauseImage() {
        this.stand = true;
        return this.currentImages[0].getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
    }

    /**
     * Walk in a direction or Change the Image of the Sprite
     * @param direction
     */
    public void setImage(int direction) {
        this.stand = false;

        // Set the current paths
        switch (direction) {
            case FORWARD:
                currentPaths = FRONT_PATHS;
                break;
            case BACKWARD:
                currentPaths = BACK_PATHS;
                break;
            case LEFTWARD:
                currentPaths = LEFT_PATHS;
                break;
            case RIGHTWARD:
                currentPaths = RIGHT_PATHS;
                break;
            default:
                currentPaths = FRONT_PATHS;
        }
        
        // Load front images
        currentImages = new BufferedImage[currentPaths.length];

        try {
            for (int i = 0; i < currentPaths.length; i++) {
                currentImages[i] = ImageIO.read(getClass().getResource(currentPaths[i]));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}