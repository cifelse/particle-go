package models;

import java.util.concurrent.CopyOnWriteArrayList;

public class Resources {
    private final CopyOnWriteArrayList<Particle> particles;

    public Resources() {
        particles = new CopyOnWriteArrayList<Particle>();
    }

    public CopyOnWriteArrayList<Particle> getParticles() {
        return this.particles;
    }

    public void addParticle(Particle particle) {
        this.particles.add(particle);
    }

    public void clearParticles() {
        this.particles.clear();
        System.gc();
    }
}
