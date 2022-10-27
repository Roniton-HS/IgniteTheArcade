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
        if (game.getKeyHandler().p) {
            PacMan pacMan = new PacMan(game);
            Worlds.setWorld(pacMan);
        }
        if (game.getKeyHandler().o) {
            SnakeWorld snakeWorld = new SnakeWorld(game);
            Worlds.setWorld(snakeWorld);
        }
        if (game.getKeyHandler().s) {
            MineSweeper mineSweeper = new MineSweeper(game, 50, 20);
            Worlds.setWorld(mineSweeper);
        }
    }

    @Override
    public void render(Graphics g) {


    }
}
