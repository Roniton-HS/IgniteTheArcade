package FlappyBird;

import Main.Constants;

import java.awt.*;

public class Player extends Rectangle {
    private final Rectangle bird;
    private final int BIRD_SIZE = 20;
    private float speed;
    private int x;
    private int y;

    public Player() {
        bird = new Rectangle(10, 10, BIRD_SIZE, BIRD_SIZE);
        x = bird.x;
        y = bird.y;
        speed = 0;
    }

    public float getSpeed() {
        return speed;
    }

    public Player setSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public int getIntX() {
        return x;
    }

    public void setIntX(int x) {
        this.x = x;
        bird.x = x;
    }

    public int getIntY() {
        return y;
    }

    public void setIntY(int y) {
        this.y = y;
        bird.y = y;
    }

    public Rectangle getBounds() {
        return bird.getBounds();
    }

    public void render(Graphics g) {
        g.setColor(Constants.ALMOST_WHITE);
        g.fillRect(bird.x, bird.y, BIRD_SIZE, BIRD_SIZE);
    }
}
