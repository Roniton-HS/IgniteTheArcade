package Arkanoid;

import java.awt.*;

public class Ball extends Rectangle {
    private double speedX = 0;
    private double speedY = 5;

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
}
