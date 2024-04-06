package models;

import views.Screen;

public class Particle {
    // The size of the Particle in the Screen
    public final static int DIAMETER = 5;

    // The position of the Particle in the Screen
    private int x;
    private int y;
    
    // The speed of the Particle
    private float speed;

    // The velocity of the Particle
    private float velX;
    private float velY;

    // The angle of the Particle
    private float angle;
    
    /**
     * The Particle constructor
     * @param spawnX - The x position where the Particle spawns
     * @param spawnY - The y position where the Particle spawns
     * @param speed - The speed of the Particle
     * @param angle - The angle of the Particle
     */
    public Particle(String spawnX, String spawnY, String speed, String angle) {
        int tempX = Integer.parseInt(spawnX);
        int tempY = 720 - Integer.parseInt(spawnY);

        this.x = tempX != 0 ? (tempX == Screen.WIDTH ? tempX - DIAMETER : tempX) : tempX + DIAMETER;
        this.y = tempY != 0 ? (tempY == Screen.HEIGHT ? tempY - DIAMETER : tempY) : tempY + DIAMETER;

        this.speed = Float.parseFloat(speed);
        this.angle = Float.parseFloat(angle) % 360;

        // Compute the velocity of the Particle
        this.velX = (float) (this.speed * Math.cos(Math.toRadians(this.angle)));
        this.velY = (float) (this.speed * Math.sin(Math.toRadians(this.angle)));
    }

    /**
     * The Particle constructor
     * @param spawnX - The x position where the Particle spawns
     * @param spawnY - The y position where the Particle spawns
     * @param speed - The speed of the Particle
     * @param angle - The angle of the Particle
     */
    public Particle(int spawnX, int spawnY, float speed, float angle) {
        this.x = spawnX != 0 ? (spawnX == Screen.WIDTH ? spawnX - DIAMETER : spawnX) : spawnX + DIAMETER;
        this.y = 720 - (spawnY != 0 ? (spawnY == Screen.HEIGHT ? spawnY - DIAMETER : spawnY) : spawnY + DIAMETER);
        
        this.speed = speed;
        this.angle = angle % 360;

        // Compute the velocity of the Particle
        this.velX = (float) (this.speed * Math.cos(Math.toRadians(this.angle)));
        this.velY = (float) (this.speed * Math.sin(Math.toRadians(this.angle)));
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVelocityX(float velX) {
        this.velX = velX;
    }

    public void setVelocityY(float velY) {
        this.velY = velY;
    }

    public void refreshXY(){
        this.x = Math.round(this.x + this.velX);
        this.y = Math.round(this.y - this.velY);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getVelocityX() {
        return this.velX;
    }

    public float getVelocityY() {
        return this.velY;
    }

    public float getAngle() {
        return this.angle;
    }

    public int getFutureX(float dt) {
        return (int) (this.x + this.velX * dt);
    }

    public int getFutureY(float dt) {
        return (int) (this.y + this.velY * dt);
    }
}
