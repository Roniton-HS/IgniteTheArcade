package Arkanoid;

import java.awt.*;
import java.util.ArrayList;

public class Brick extends Rectangle {
    private int hp;
    private int score;
    private ArrayList<Rectangle> borders = new ArrayList<>();

    public Brick(int x, int y, int width, int height, int hp) {
        super(x, y, width, height);
        this.hp = hp;

        switch (hp) {
            case 1 -> score = 100;
            case 2 -> score = 200;
            case 3 -> score = 400;
            case 4 -> score = 800;
            case 5 -> score = 1000;
            default -> score = 0;
        }

        createBorders();
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

    public ArrayList<Rectangle> getBorders() {
        return borders;
    }

    public Brick setBorders(ArrayList<Rectangle> borders) {
        this.borders = borders;
        return this;
    }

    private void createBorders() {
        borders.add(new Rectangle(x, y, width, 1)); //border top
        borders.add(new Rectangle(x, y + height, width, 1)); //border bottom
        borders.add(new Rectangle(x, y, 1, height)); //border left
        borders.add(new Rectangle(x + width, y, 1, height)); //border right
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

        switch (hp) {
            case 1 -> g.setColor(new Color(75, 8, 8));
            case 2 -> g.setColor(new Color(187, 149, 13));
            case 3 -> g.setColor(new Color(32, 91, 15));
            case 4 -> g.setColor(new Color(14, 24, 96));
            case 5 -> g.setColor(new Color(105, 53, 11));
            default -> g.setColor(Color.black);
        }

        for (int i = 0; i < 3; i++) {
            g.drawRect(x + i, y + i, width - 2 * i, height - 2 * i);
        }
    }
}
