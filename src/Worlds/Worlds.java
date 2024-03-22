package Worlds;

import Main.Game;

import java.awt.*;

public abstract class Worlds {

    protected Game game;
    private static Worlds currentWorld = null;
    private final String name;

    /**
     * Constructor
     */
    public Worlds(Game game, String name) {
        this.game = game;
        this.name = name;
    }

    /**
     * this function is automatically called if the world is being loaded
     */
    public abstract void init();

    /**
     * sets a new world
     *
     * @param world -> new world
     */
    public static void setWorld(Worlds world) {
        currentWorld = world;
        world.init();
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

    public String getName() {
        return name;
    }
}
