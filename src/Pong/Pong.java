package Pong;

import Main.Constants;
import Main.Game;
import Worlds.Worlds;

import java.awt.*;

import static Main.Constants.ALMOST_BLACK;
import static Main.Constants.ALMOST_WHITE;

public class Pong extends Worlds {
    final private int WINDOW_SIZE = 510;

    private Rectangle playerLeft;
    private Rectangle playerRight;
    private Rectangle collisionPlayerLeft;
    private Rectangle collisionPlayerRight;
    private final int PLAYER_SPEED = 5;
    private Ball ball;
    private final int BALL_DIAMETER = 10;


    public Pong(Game game) {
        super(game);
        game.getDisplay().resize(WINDOW_SIZE + 16, WINDOW_SIZE + 39);
        createGame();
    }

    private void createGame() {
        int PLAYER_WIDTH = 10;
        int PLAYER_HEIGHT = 50;
        playerLeft = new Rectangle(30, WINDOW_SIZE / 2 - PLAYER_HEIGHT / 2, PLAYER_WIDTH, PLAYER_HEIGHT);
        collisionPlayerLeft = new Rectangle(playerLeft.x - PLAYER_SPEED, playerLeft.y, playerLeft.width + (2 * PLAYER_SPEED), playerLeft.height);

        playerRight = new Rectangle(WINDOW_SIZE - 30 - PLAYER_WIDTH, WINDOW_SIZE / 2 - PLAYER_HEIGHT / 2, PLAYER_WIDTH, PLAYER_HEIGHT);
        collisionPlayerRight = new Rectangle(playerRight.x - PLAYER_SPEED, playerRight.y, playerRight.width + (2 * PLAYER_SPEED), playerRight.height);

        ball = new Ball(WINDOW_SIZE / 2 - BALL_DIAMETER / 2, WINDOW_SIZE / 2 - BALL_DIAMETER / 2, BALL_DIAMETER);
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
        g.setColor(ALMOST_WHITE);
        g.fillRect(playerLeft.x, playerLeft.y, playerLeft.width, playerLeft.height);
        g.fillRect(playerRight.x, playerRight.y, playerRight.width, playerRight.height);
    }
}
