package Arkanoid;

import java.awt.*;

public class Ball extends Rectangle {
    private double speedX = 0;
    private double speedY = 5;
    private double angle;

    public Ball(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public double getSpeedX() {
        return speedX;
    }

    public Ball setSpeedX(double speedX) {
        this.speedX = speedX;
        return this;
    }

    public double getSpeedY() {
        return speedY;
    }

    public Ball setSpeedY(double speedY) {
        this.speedY = speedY;
        return this;
    }

    public double getAngle() {
        return angle;
    }

    public Ball setAngle(double angle) {
        this.angle = angle;
        return this;
    }
}
