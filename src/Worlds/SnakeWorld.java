package Worlds;

import Main.Game;
import Snake.Snake;

import java.awt.*;

public class SnakeWorld extends Worlds {

    Snake snake = new Snake(320, 320, game);

    /**
     * Constructor
     *
     * @param game
     */
    public SnakeWorld(Game game) {
        super(game);


    }

    @Override


    public void tick() {
            snake.tick();
    }

    @Override
    public void render(Graphics g) {
        snake.render(g);

        g.drawRect(64,64,863, 863);

    }
}
