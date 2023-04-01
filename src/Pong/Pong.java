package Pong;

import Main.Constants;
import Main.Game;
import Worlds.Worlds;

import java.awt.*;

import static Main.Constants.*;

public class Pong extends Worlds {
    final private int WINDOW_SIZE = 510;

    private Player playerLeft;
    private Player playerRight;
    private Rectangle collisionPlayerLeft;
    private Rectangle collisionPlayerRight;
    private final int PLAYER_SPEED = 5;
    private Ball ball;
    private final int BALL_DIAMETER = 10;


    public Pong(Game game) {
        super(game);
        game.getDisplay().resize(WINDOW_SIZE + WIN10_WIDTH_DIFF, WINDOW_SIZE + WIN10_HEIGHT_DIFF);
        createGame();
    }

    private void createGame() {
        playerLeft = new Player(WINDOW_SIZE,false);
        collisionPlayerLeft = playerLeft.getCollisionPlayer();

        playerRight = new Player(WINDOW_SIZE,true);
        collisionPlayerRight = playerRight.getCollisionPlayer();

        ball = new Ball(WINDOW_SIZE);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        renderBackground(g);
        ball.render(g);
        renderPlayer(g);
    }

    private void renderBackground(Graphics g) {
        g.setColor(ALMOST_BLACK);
        g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);
        g.setColor(ALMOST_WHITE);

        final int WIDTH = 4;
        final int HEIGHT = 10;
        for (int i = 0; i < 21; i++) {
            g.fillRect(WINDOW_SIZE / 2 - WIDTH / 2, (int) (2.5 * HEIGHT * i), WIDTH, HEIGHT);
        }
    }

    private void renderPlayer(Graphics g) {
        playerLeft.render(g);
        playerRight.render(g);
    }
}
