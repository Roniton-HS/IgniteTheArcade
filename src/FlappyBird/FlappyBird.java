package FlappyBird;

import Main.Constants;
import Main.Game;
import Worlds.Worlds;

import java.awt.*;

public class FlappyBird extends Worlds {
    final private int WINDOW_WIDTH = 1000;
    final private int WINDOW_HEIGHT = 750;
    private Player player;

    public FlappyBird(Game game) {
        super(game, "Flappy Bird");
    }

    @Override
    public void init() {
        game.getDisplay().resize(WINDOW_WIDTH, WINDOW_HEIGHT);
        player = new Player();
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Constants.ALMOST_BLACK);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        player.render(g);
    }
}
