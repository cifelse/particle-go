package models;

import java.util.concurrent.CopyOnWriteArrayList;

public class Resources {
    private final CopyOnWriteArrayList<Particle> particles;

    private final CopyOnWriteArrayList<Player> players;

    public Resources() {
        particles = new CopyOnWriteArrayList<Particle>();
        players = new CopyOnWriteArrayList<Player>();
    }

    public CopyOnWriteArrayList<Particle> getParticles() {
        return this.particles;
    }

    public CopyOnWriteArrayList<Player> getPlayers() {
        return this.players;
    }

    public void clearParticles() {
        this.particles.clear();
        System.gc();
    }

    public void addParticle(Particle particle) {
        this.particles.add(particle);
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }
}
