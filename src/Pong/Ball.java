package Pong;

import java.awt.*;

import static Main.Constants.ALMOST_WHITE;
import static java.lang.Math.PI;

public class Ball extends Rectangle {
    private final Rectangle ball;
    private final int DIAMETER = 10;
    private double speedX = 0;
    private double speedY = 0;
    private int x;
    private int y;

    public Ball(int windowSize) {
        ball = new Rectangle(windowSize / 2 - DIAMETER / 2, windowSize / 2 - DIAMETER / 2, DIAMETER, DIAMETER);
        x = ball.x;
        y = ball.y;
    }

    public int getDIAMETER() {
        return DIAMETER;
    }

    public int getSpeed() {
        return 5;
    }

    public double getMaxAngle() {
        return (75 * PI) / 180;
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

    public Rectangle getBounds() {
        return ball.getBounds();
    }

    public void render(Graphics g) {
        g.setColor(ALMOST_WHITE);
        g.fillOval(ball.x, ball.y, ball.width, ball.height);
    }

    public void renderBorder(Graphics g) {
        g.setColor(Color.green);
        g.drawRect(ball.x, ball.y, ball.width, ball.height);
    }
}

