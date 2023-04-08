package Arkanoid;

import java.awt.*;

public class Player extends Rectangle {
    private final Rectangle player;
    private final Rectangle collisionPlayer;
    private final int WIDTH = 100;
    private final int HEIGHT = 10;
    private final int SPEED = 5;
    private int x, y;

    public Player(int windowWidth, int windowHeight){
        player = new Rectangle((windowWidth / 2) - (WIDTH / 2), windowHeight - 70 - HEIGHT, WIDTH, HEIGHT);
        collisionPlayer = new Rectangle(player.x - SPEED, player.y, WIDTH + (2 * SPEED), HEIGHT);
        x = player.x;
        y = player.y;
    }

    public Rectangle getCollisionPlayer() {
        return collisionPlayer;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    @SuppressWarnings("unused")
    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getSpeed() {
        return 5;
    }

    public int getIntX() {
        return x;
    }

    public void setIntX(int x) {
        this.x = x;
        player.x = x;
    }

    public int getIntY() {
        return y;
    }

    @SuppressWarnings("unused")
    public void setIntY(int y) {
        this.y = y;
        player.y = y;
    }

    public void moveCollision() {
        collisionPlayer.x = player.x - SPEED;
    }

    public Rectangle getBounds() {
        return player.getBounds();
    }

    public void calculatePlayerBounce(Ball ball) {
        int relativeCollision = -(player.x - (ball.getIntX()+ball.getDIAMETER()/2) + player.width / 2);
        double normRelativeCollision = relativeCollision / (player.width / 2.0);

        double angle = normRelativeCollision * ball.getMaxAngle();

        ball.setIntY(player.y - ball.getDIAMETER());
        ball.setSpeedX(-Math.sin(angle) * ball.getSpeed());
        ball.setSpeedY(Math.cos(angle) * ball.getSpeed());
    }

    public void render(Graphics g) {
        g.setColor(new Color(148, 0, 118));
        g.fillRect(player.x, player.y, player.width, player.height);

        g.setColor(new Color(233, 0, 185));
        g.fillRect(player.x + 3, player.y + 3, player.width - 6, player.height - 6);
    }

    public void renderBorder(Graphics g) {
        g.setColor(Color.blue);
        g.drawRect(player.x, player.y, player.width, player.height);
        g.setColor(Color.cyan);
        g.drawRect(collisionPlayer.x, collisionPlayer.y, collisionPlayer.width, collisionPlayer.height);
    }
}
