package models;

import java.net.Socket;

public class Player {
    // X Coordinate
    private int x;

    // Y Coordinate
    private int y;

    // Unique Identifier
    private String username;

    // Client Socket
    private Socket socket;

    public Player(int x, int y, String username, Socket socket) {
        this.x = x;
        this.y = y;
        this.username = username;
        this.socket = socket;
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
}
