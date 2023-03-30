package Arkanoid;

import java.awt.*;

public class Ball extends Rectangle {
    private double speedX = 0;
    private double speedY = 5;
    private double angle;

    public Ball(int x, int y, int size) {
        super(x, y, size, size);
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

    public double getAngle() {
        return angle;
    }

    public Ball setAngle(double angle) {
        this.angle = angle;
        return this;
    }

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillOval(x, y, width, height);
    }
}
