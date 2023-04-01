package Pong;

import Main.Constants;

import java.awt.*;

import static Main.Constants.ALMOST_WHITE;

public class Ball {
    private final Rectangle ball;
    private final int DIAMETER = 10;
    private double speedX = 0;
    private double speedY = 5;

    public Ball(int windowSize) {
        ball = new Rectangle(windowSize / 2 - DIAMETER / 2, windowSize / 2 - DIAMETER / 2, DIAMETER, DIAMETER);
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


    public void render(Graphics g) {
        g.setColor(ALMOST_WHITE);
        g.fillOval(ball.x, ball.y, ball.width, ball.height);
    }
}

