package Arkanoid;

import java.awt.*;

public class Brick extends Rectangle {
    private int hp;
    private int score;

    public Brick(int x, int y, int width, int height, int hp) {
        super(x, y, width, height);
        this.hp = hp;

        switch (hp){
            case 1 -> score = 100;
            case 2 -> score = 200;
            case 3 -> score = 400;
            case 4 -> score = 800;
            case 5 -> score = 1000;
            default -> score = 0;
        }
    }

    public int getHp() {
        return hp;
    }

    public Brick setHp(int hp) {
        this.hp = hp;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Brick setScore(int score) {
        this.score = score;
        return this;
    }

    public void render(Graphics g) {
        switch (hp) {
            case 1 -> g.setColor(Color.red);
            case 2 -> g.setColor(Color.yellow);
            case 3 -> g.setColor(Color.green);
            case 4 -> g.setColor(Color.blue);
            case 5 -> g.setColor(Color.orange);
            default -> g.setColor(Color.black);
        }

        g.fillRect(x, y, width, height);
    }
}
