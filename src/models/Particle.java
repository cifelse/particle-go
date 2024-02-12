package models;

public class Particle {
    private int spawnX;
    private int spawnY;
    
    private float speed;
    private float velX;
    private float velY;
    private float angle;
    
    public Particle(int spawnX, int spawnY, int speed, int angle) {
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.speed = speed;
        this.angle = angle % 360;
    }
}
