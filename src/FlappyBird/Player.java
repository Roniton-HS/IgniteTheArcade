package FlappyBird;


import java.awt.*;

import static Main.Constants.*;

public class Player extends Rectangle {
    private final Rectangle BIRD;
    private final int BIRD_SIZE = 20;
    private final int ACCELERATION = 2;
    private int speed;
    private final int X = 100;
    private int y;

    public Player(int windowHeight) {
        BIRD = new Rectangle(X, (windowHeight - 100) / 2, BIRD_SIZE, BIRD_SIZE);
        y = BIRD.y;
        speed = 1;
    }

    public int getBIRD_SIZE() {
        return BIRD_SIZE;
    }

    public int getACCELERATION() {
        return ACCELERATION;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getIntY() {
        return y;
    }

    public void setIntY(int y) {
        this.y = y;
        BIRD.y = y;
    }

    public Rectangle getBounds() {
        return BIRD.getBounds();
    }

    public void render(Graphics g) {
        g.setColor(DARK_YELLOW);
        g.fillRect(BIRD.x, BIRD.y, BIRD_SIZE, BIRD_SIZE);
    }
}
