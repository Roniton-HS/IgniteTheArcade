package Pong;

import java.awt.*;

import static Main.Constants.ALMOST_WHITE;

public class Player {
    private final Rectangle player;
    private final Rectangle collisionPlayer;
    private final int WIDTH = 10;
    private final int HEIGHT = 50;
    private final int SPEED = 5;

    /**
     * creates the player for pong
     *
     * @param windowSize to calculate the y value of the player
     * @param side       false - left player, true - right player
     */
    public Player(int windowSize, boolean side) {
        if (!side) {
            player = new Rectangle(30, windowSize / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        } else {
            player = new Rectangle(windowSize - 30 - WIDTH, windowSize / 2 - HEIGHT / 2, WIDTH, HEIGHT);
        }
        collisionPlayer = new Rectangle(player.x, windowSize / 2 - player.x - SPEED, WIDTH, HEIGHT + 2 * SPEED);
    }

    public Rectangle getCollisionPlayer() {
        return collisionPlayer;
    }

    public int getSPEED() {
        return SPEED;
    }

    public void render(Graphics g) {
        g.setColor(ALMOST_WHITE);
        g.fillRect(player.x, player.y, player.width, player.height);
    }
}
