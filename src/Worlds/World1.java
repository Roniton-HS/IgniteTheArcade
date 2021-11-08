package Worlds;

import Main.Game;

import java.awt.*;

public class World1 extends Worlds {
    /**
     * Constructor
     */
    public World1(Game game) {
        super(game);
    }

    @Override
    public void tick() {
        if(game.getKeyHandler().p){
            PacMan pacMan = new PacMan(game);
            Worlds.setWorld(pacMan);
        }
        if (game.getKeyHandler().o) {
            SnakeWorld snakeWorld = new SnakeWorld(game);
            Worlds.setWorld(snakeWorld);
        }
    }

    @Override
    public void render(Graphics g) {


    }
}
