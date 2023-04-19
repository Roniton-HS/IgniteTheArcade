package Arkanoid;

import java.awt.*;

import static Main.Constants.ALMOST_WHITE;
import static java.lang.Math.PI;

public class Ball extends Rectangle {
    private final Rectangle ball;
    private final int DIAMETER = 10;
    private double speedX = 0;
    private double speedY = 5;
    private int x;
    private int y;
    private boolean fire = false;

    public Ball(Player player) {
        this.ball = new Rectangle((player.getIntX() + (player.getIntWidth() / 2)) - (DIAMETER / 2), player.getIntY() - DIAMETER, DIAMETER, DIAMETER);
        x = ball.x;
        y = ball.y;
    }

    public Ball(Ball oldBall) {
        this.ball = new Rectangle(oldBall.getIntX(), oldBall.getIntY(), DIAMETER, DIAMETER);
        x = oldBall.x;
        y = oldBall.y;
    }

    public int getDIAMETER() {
        return DIAMETER;
    }

    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public double getMaxAngle() {
        return (75 * PI) / 180;
    }

    public int getIntX() {
        return x;
    }

    public void setIntX(int x) {
        this.x = x;
        ball.x = x;
    }

    public int getIntY() {
        return y;
    }

    public void setIntY(int y) {
        this.y = y;
        ball.y = y;
    }

    public boolean isFire() {
        return fire;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }

    public int getSpeed() {
        return 5;
    }

    public Rectangle getBounds() {
        return ball.getBounds();
    }

    public void render(Graphics g) {
        if (fire) {
            g.setColor(new Color(255, 195, 14));
        } else {
            g.setColor(ALMOST_WHITE);
        }
        g.fillOval(ball.x, ball.y, ball.width, ball.height);
    }

    public void renderBorder(Graphics g) {
        g.setColor(Color.green);
        g.drawRect(ball.x, ball.y, ball.width, ball.height);
    }
}
