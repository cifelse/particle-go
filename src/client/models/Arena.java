package client.models;

public class Arena {
    // For Edge detection
    public static final int SAFE = -1;
    public static final int TOP = 0;
    public static final int RIGHT = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;

    // Arena Size
    private int width, height;
    // The main player
    private Player player;

    /**
     * Create a new Arena with the specified width and height.
     * @param width - the width of the Arena
     * @param height - the height of the Arena
     */
    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.player = null;
    }

    public Arena(int width, int height, Player player) {
        this.width = width;
        this.height = height;
        this.player = player;
    }

    public Arena(String width, String height, Player player) {
        this.width = Integer.parseInt(width);
        this.height = Integer.parseInt(height);
        this.player = player;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Check if the Player is at the edge of the Arena.
     * @return true if the Player is at the edge, false otherwise
     */
    public boolean isEdge(int futureMove) {
        switch (futureMove) {
            case Player.FORWARD:
                return this.getPlayer().getY() + 1 >= this.height;
            case Player.BACKWARD:
                return this.getPlayer().getY() - 1 < 0;
            case Player.LEFTWARD:
                return this.getPlayer().getX() - 1 < 0;
            case Player.RIGHTWARD:
            default:
                return this.getPlayer().getX() + 1 >= this.width;
        }
    }
}

