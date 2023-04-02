package Pong;

import java.awt.*;

import static Main.Constants.ALMOST_WHITE;

public class Player extends Rectangle {
    private final Rectangle player;
    private final Rectangle collisionPlayer;
    private final int WIDTH = 10;
    private final int HEIGHT = 80;
    private final int SPEED = 5;
    private int y;
    private final boolean left;
    private int score = 0;
    private boolean won = false;

    /**
     * creates the players for pong
     *
     * @param windowSize to calculate the y value of the player
     * @param side       true - left player, false - right player
     */
    public Player(int windowSize, boolean side) {
        if (side) {
            player = new Rectangle(30, windowSize / 2 - HEIGHT / 2, WIDTH, HEIGHT);
            left = true;
        } else {
            player = new Rectangle(windowSize - 30 - WIDTH, windowSize / 2 - HEIGHT / 2, WIDTH, HEIGHT);
            left = false;
        }
        collisionPlayer = new Rectangle(player.x, player.y - SPEED, WIDTH, HEIGHT + 2 * SPEED);
        y = player.y;
    }

    public Rectangle getCollisionPlayer() {
        return collisionPlayer;
    }

    @SuppressWarnings("unused")
    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getSPEED() {
        return SPEED;
    }

    public int getIntY() {
        return y;
    }

    public void setIntY(int y) {
        this.y = y;
        player.y = y;
    }

    public boolean isLeft() {
        return left;
    }

    public void addPoint() {
        this.score++;
    }

    public boolean isWon() {
        return won;
    }

    public boolean checkScore() {
        if (score >= 99) {
            won = true;
        }
        return won;
    }

    public void moveCollision() {
        collisionPlayer.y = player.y - SPEED;
    }

    public Rectangle getBounds() {
        return player.getBounds();
    }

    public void calculatePlayerBounce(Ball ball) {
        int relativeCollision = -(player.y - ball.getIntY() + player.height / 2);
        double normRelativeCollision;
        if (left) {
            normRelativeCollision = relativeCollision / (player.height / 2.0);
        } else {
            normRelativeCollision = -(relativeCollision / (player.height / 2.0));
        }

        double angle = normRelativeCollision * ball.getMAX_ANGLE();

        if (left) {
            ball.setIntX(player.x + player.width);
            ball.setSpeedX(Math.cos(angle) * ball.getSPEED());
            ball.setSpeedY(-Math.sin(angle) * ball.getSPEED());
        } else {
            ball.setIntX(player.x - ball.getDIAMETER());
            ball.setSpeedX(Math.cos(angle) * -ball.getSPEED());
            ball.setSpeedY(-Math.sin(angle) * -ball.getSPEED());
        }
    }

    public void render(Graphics g) {
        g.setColor(ALMOST_WHITE);
        g.fillRect(player.x, player.y, player.width, player.height);
    }

    public void renderBorder(Graphics g) {
        g.setColor(Color.blue);
        g.drawRect(player.x, player.y, player.width, player.height);
        g.setColor(Color.cyan);
        g.drawRect(collisionPlayer.x, collisionPlayer.y, collisionPlayer.width, collisionPlayer.height);
    }

    public String createScore() {
        if (score <= 9) {
            return "0" + score;
        } else {
            return "" + score;
        }
    }

    public String createWon() {
        if (left) {
            return "P1 WON";
        } else {
            return "P2 WON";
        }
    }
}
