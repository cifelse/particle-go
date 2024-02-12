package models;

public class Wall {
    private final int x1;
    private final int x2;
    private final int y1;
    private final int y2;
    
    private final float angle;

    /**
     * Constructor for Wall
     * @param x1 - x coordinate of the first point
     * @param x2 - x coordinate of the second point
     * @param y1 - y coordinate of the first point
     * @param y2 - y coordinate of the second point
     */
    public Wall(int x1, int x2, int y1, int y2){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;

        this.angle = (float) Math.atan2((y2 - y1), (x2 - x1));
    }

    public boolean bounce(int x, int y){
        float d1 = (float) (Math.sqrt(Math.pow(x1 - x, 2) + (Math.pow(y1 - y, 2))));      // A to C

        float d2 = (float) (Math.sqrt(Math.pow(x2 - x, 2) + (Math.pow(y2 - y, 2))));      // B to C

        float d3 = (float) (Math.sqrt(Math.pow(x1 - x2, 2) + (Math.pow(y1 - y2, 2))));    // A to B

        return ((d1 + d2) >= d3 * 0.999 && (d1 + d2) <= d3 * 1.005);
    }

    public int getX1() {
        return this.x1;
    }

    public int getX2() {
        return this.x2;
    }

    public int getY1() {
        return this.y1;
    }

    public int getY2() {
        return this.y2;
    }

    public float getAngle() {
        return this.angle;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Wall)) return false;

        return (((Wall) obj).x1 == x1 && ((Wall) obj).y1 == y1 && ((Wall) obj).x2 == x2 && ((Wall) obj).y2 == y2);
    }
}
