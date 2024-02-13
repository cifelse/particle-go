package models;

public class Particle {
    // The size of the Particle in the Screen
    public final static int DIAMETER = 5;

    private int x;
    private int y;
    
    private float speed;
    private float velX;
    private float velY;
    private float angle;
    
    public Particle(int spawnX, int spawnY, int speed, int angle) {
        this.x = spawnX;
        this.y = spawnY;
        this.speed = speed;
        this.angle = angle % 360;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void refreshXY(){
        this.x = Math.round(this.x + this.velX);
        this.y = Math.round(this.y - this.velY);
    }

    public void setVelocityX(float velX) {
        this.velX = velX;
    }

    public void setVelocityY(float velY) {
        this.velY = velY;
    }


    public int getFutureX(float dt){
        return (int) (this.x + this.velX * dt);
    }

    public int getFutureY(float dt){
        return (int) (this.y + this.velY * dt);
    }
}
