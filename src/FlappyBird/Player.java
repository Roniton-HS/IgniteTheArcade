package FlappyBird;

import Main.Constants;

import java.awt.*;

import static Main.Constants.*;

public class Player extends Rectangle {
    private final Rectangle bird;
    private final int BIRD_SIZE = 20;
    private int acceleration;
    private int speed;
    private int x;
    private int y;

    public Player(int windowHeight) {
        bird = new Rectangle(100, (windowHeight-100) / 2, BIRD_SIZE, BIRD_SIZE);
        x = bird.x;
        y = bird.y;
        acceleration = 2;
        speed = 1;
    }

    public int getBIRD_SIZE() {
        return BIRD_SIZE;
    }
    public int getAcceleration() {
        return acceleration;
    }

    public Player setAcceleration(int acceleration) {
        this.acceleration = acceleration;
        return this;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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
        g.setColor(DARK_YELLOW);
        g.fillRect(bird.x, bird.y, BIRD_SIZE, BIRD_SIZE);
    }
}
