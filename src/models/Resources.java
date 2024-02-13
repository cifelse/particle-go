package models;

import java.util.concurrent.CopyOnWriteArrayList;

public class Resources {
    private final CopyOnWriteArrayList<Particle> particles;
    private final CopyOnWriteArrayList<Wall> walls;

    public Resources() {
        particles = new CopyOnWriteArrayList<Particle>();
        walls = new CopyOnWriteArrayList<Wall>();
    }

    public CopyOnWriteArrayList<Particle> getParticles() {
        return this.particles;
    }

    public CopyOnWriteArrayList<Wall> getWalls() {
        return this.walls;
    }

    public void addParticle(Particle particle){
        this.particles.add(particle);
    }

    public void addWall(Wall wall){
        if (!walls.contains(wall)) {
            this.walls.add(wall);
        }
    }

    public void clearParticles(){
        this.particles.clear();
        System.gc();
    }

    public void clearWalls(){
        this.walls.clear();
        System.gc();
    }
}
