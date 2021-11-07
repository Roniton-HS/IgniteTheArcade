package Worlds;

import Main.Game;

import java.awt.*;

public abstract class Worlds {

    protected Game game;
    private static Worlds currentWorld = null;

    /**
     * Constructor
     */
    public Worlds(Game game) {
        this.game = game;
    }

    /**
     * sets a new world
     *
     * @param world -> new world
     */
    public static void setWorld(Worlds world) {
        currentWorld = world;
    }

    /**
     * @return current world
     */
    public static Worlds getWorld() {
        return currentWorld;
    }

    /**
     * updates the world
     */
    public abstract void tick();



    /**
     * renders the world
     *
     * @param g Graphics g
     */
    public abstract void render(Graphics g);

}
