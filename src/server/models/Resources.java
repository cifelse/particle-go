package models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Resources implements Serializable {
    private final CopyOnWriteArrayList<Particle> particles;

    private final Map<String, Player> players;

    public Resources() {
        this.particles = new CopyOnWriteArrayList<Particle>();
        this.players = new HashMap<String, Player>();
    }

    public CopyOnWriteArrayList<Particle> getParticles() {
        return this.particles;
    }

    public Map<String, Player> getPlayers() {
        return this.players;
    }

    public Player getPlayer(String username) {
        return this.players.get(username);
    }

    public void clearParticles() {
        this.particles.clear();
        System.gc();
    }

    public void addParticle(Particle particle) {
        this.particles.add(particle);
    }

    public void addPlayer(Player player) {
        this.players.put(player.getUsername(), player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player.getUsername());
    }

    public void removePlayer(String username) {
        this.players.remove(username);
    }
}
