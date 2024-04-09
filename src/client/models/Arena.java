package client.models;

public class Arena {
    // Arena Size
    private int width, height;

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
    public boolean isEdge() {
        return player == null ? null : (player.getX() == 0 || player.getX() == width - 1 || player.getY() == 0 || player.getY() == height - 1);
    }
}

