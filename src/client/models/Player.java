package client.models;

import java.awt.Image;

import client.views.Sprite;

public class Player {
    public static final int STOP = -1;
    public static final int FORWARD = 0;
    public static final int BACKWARD = 1;
    public static final int LEFTWARD = 2;
    public static final int RIGHTWARD = 3;

    // Spawn Points of the Client
    private int x, y;

    // Username of the Client
    private String username;

    // It's own Sprite Object
    private Sprite sprite;

    /**
     * Create a Player with specific spawn points
     * @param x - the X coordinate
     * @param y - the Y coordinate
     */
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.username = "Traveller";
        this.sprite = new Sprite();
    }

    public Player(String x, String y) {
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
        this.username = "Traveller";
        this.sprite = new Sprite();
    }

    public Player(String username) {
        this.x = 500;
        this.y = 500;
        this.username = username;
        this.sprite = new Sprite();
    }

    /**
     * Create a Player with specific spawn points and username
     * @param x - the X coordinate
     * @param y - the Y coordinate
     * @param username - the username of the Player
     */
    public Player(int x, int y, String username) {
        this.x = x;
        this.y = y;
        this.username = username;
        this.sprite = new Sprite();
    }

    public Player(String x, String y, String username) {
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
        this.username = username;
        this.sprite = new Sprite();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getUsername() {
        return this.username;
    }

    public Image getImage() {
        return this.sprite.getImage();
    }

    public String getLocation() {
        return this.username + Protocol.SEPARATOR + this.x + Protocol.SEPARATOR + this.y + Protocol.EOF;
    }

    /**
     * Set the (X, Y) coordinate of the Player.
     * @param x - the X coordinate
     * @param y - the Y coordinate
     */
    public void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setCoordinate(String x, String y) {
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
    }

    /**
     * Move the Player in the specified direction.
     * @param direction
     */
    public void move(int direction) {
        switch (direction) {
            case FORWARD:
                this.y++;
                this.sprite.setImage(Sprite.FORWARD);
                break;
            case BACKWARD:
                this.y--;
                sprite.setImage(Sprite.BACKWARD);
                break;
            case LEFTWARD:
                this.x--;
                this.sprite.setImage(Sprite.LEFTWARD);
                break;
            case RIGHTWARD:
                this.sprite.setImage(Sprite.RIGHTWARD);
                this.x++;
                break;
            case STOP:
            default:
                this.sprite.pauseImage();
        }
    }
}


