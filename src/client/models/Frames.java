package client.models;

import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.imageio.ImageIO;

import client.views.Sprite;

/**
 * A special LinkedList that holds the frames of the game.
 */
public class Frames extends LinkedList<client.models.Frames.Frame> {
    /**
     * Add a frame to the list of frames.
     * @param frame - the frame to add
     * @param type - the type of frame
     * @return - whether the frame was added
     */
    public boolean addParticleFrame(String rawFrame) {
        String[] particles = rawFrame.split(";");
        Frame frame = new Frame(FrameType.PARTICLE);

        // FORMAT: X,Y
        for (int i = 0; i < particles.length; i++) {
            String[] parts = particles[i].split(",");
            frame.getElements().add(new Element(parts[0], parts[1]));
        }

        return this.add(frame);
    }

    /**
     * Add a frame to the list of frames.
     * @param frame - the frame to add
     * @param type - the type of frame
     * @return - whether the frame was added
     */
    public boolean addPlayerFrame(String rawFrame) {
        String[] players = rawFrame.split(Protocol.EOF);
        Frame frame = new Frame(FrameType.PLAYER);

        // FORMAT: USERNAME,X,Y,DIRECTION
        for (int i = 0; i < players.length; i++) {
            String[] parts = players[i].split(Protocol.SEPARATOR);
            frame.getElements().add(new Element(parts[0], parts[1], parts[2], parts[3], this.size()));
        }

        return this.add(frame);
    }

    /**
     * Get the next frame from the list of frames.
     * @return - the next frame
     */
    public Frame getNextFrame() {
        return this.poll();
    }

    /**
     * Peek the next frame from the list of frames.
     * @return the next frame
     */
    public Frame peekNextFrame() {
        return this.peek();
    }

    public class Frame {
        private boolean isPlayer;
        private boolean isParticle;
        private ArrayList<Element> elements;

        public Frame(int type) {
            this.elements = new ArrayList<Element>();

            switch (type) {
                case FrameType.PLAYER:
                    this.isPlayer = true;
                    this.isParticle = false;
                    
                case FrameType.PARTICLE:
                    this.isPlayer = false;
                    this.isParticle = true;
                    
            }
        }

        public boolean isPlayer() {
            return this.isPlayer;
        }

        public boolean isParticle() {
            return this.isParticle;
        }

        public ArrayList<Element> getElements() {
            return this.elements;
        }
    }

    public class Element {
        private int x, y, direction, index;
        private String name;

        /**
         * Create a new Element. This specific constructor is used for particles.
         * @param x - the x coordinate
         * @param y - the y coordinate
         */
        public Element(String x, String y) {
            this.x = Integer.parseInt(x);
            this.y = Integer.parseInt(y);
            this.direction = -1;
            this.index = -1;
            this.name = null;
        }

        /**
         * Create a new Element. This specific constructor is used for players.
         * @param username - the name of the player
         * @param x - the x coordinate
         * @param y - the y coordinate
         * @param direction - the direction the player is facing
         * @param index - the index of the player
         */
        public Element(String name, String x, String y, String direction, int index) {
            this.name = name;
            this.x = Integer.parseInt(x);
            this.y = Integer.parseInt(y);
            this.direction = Integer.parseInt(direction);
            this.index = index;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getDirection() {
            return this.direction;
        }

        public int getIndex() {
            return this.index;
        }

        public String getName() {
            return this.name;
        }

        public Image getImage() {
            try {
                switch (direction) {
                    case Sprite.LEFTWARD:
                        return (Image) ImageIO.read(getClass().getResource(Sprite.LEFT_PATHS[index]));
                        
                    case Sprite.BACKWARD:
                        return (Image) ImageIO.read(getClass().getResource(Sprite.BACK_PATHS[index]));
                        
                    case Sprite.RIGHTWARD:
                        return (Image) ImageIO.read(getClass().getResource(Sprite.RIGHT_PATHS[index]));
                        
                    case Sprite.FORWARD:
                    default:
                        return (Image) ImageIO.read(getClass().getResource(Sprite.FRONT_PATHS[index]));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class FrameType {
        public static final int PLAYER = 0;
        public static final int PARTICLE = 1;
    }
}
