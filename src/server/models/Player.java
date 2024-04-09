package models;

import java.net.Socket;

public class Player {
    // Direction Constants
    public static final int FORWARD = 0;
    public static final int BACKWARD = 1;
    public static final int LEFTWARD = 2;
    public static final int RIGHTWARD = 3;

    // Coordinate
    private int x, y;

    // Direction
    private int direction;

    // Unique Identifier
    private String username;

    // Client Socket
    private Socket socket;

    public Player(String username, String x, String y, Socket socket) {
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
        this.username = username;
        this.socket = socket;
        this.direction = FORWARD;
    }

    public Player(String username, int x, int y, Socket socket) {
        this.x = x;
        this.y = y;
        this.username = username;
        this.socket = socket;
        this.direction = FORWARD;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getLocation() {
        return this.username + Protocol.SEPARATOR + this.x + Protocol.SEPARATOR + this.y + Protocol.EOF;
    }

    public int getDirection() {
        return this.direction;
    }

    public String getUsername() {
        return this.username;
    }

    public Socket getSocket() {
        return this.socket;
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

    public void move(int direction) {
        this.direction = direction;
        switch (direction) {
            case FORWARD:
                this.y++;
                break;
            case BACKWARD:
                this.y--;
                break;
            case LEFTWARD:
                this.x--;
                break;
            case RIGHTWARD:
                this.x++;
                break;
        }
    }
}
