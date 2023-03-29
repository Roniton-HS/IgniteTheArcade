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
            case 1 -> g.setColor(new Color(255, 0, 56));
            case 2 -> g.setColor(new Color(255, 248, 7));
            case 3 -> g.setColor(new Color(17, 255, 17));
            case 4 -> g.setColor(new Color(17, 17, 255));
            case 5 -> g.setColor(new Color(255, 97, 0));
            default -> g.setColor(Color.black);
        }
        g.fillRect(x, y, width, height);

        switch (hp) {
            case 1 -> g.setColor(new Color(136, 0, 30));
            case 2 -> g.setColor(new Color(177, 172, 0));
            case 3 -> g.setColor(new Color(0, 153, 0));
            case 4 -> g.setColor(new Color(0, 0, 136));
            case 5 -> g.setColor(new Color(136, 52, 0));
            default -> g.setColor(Color.black);
        }

        for (int i = 0; i < 3; i++) {
            g.drawRect(x + i, y + i, width - 2 * i, height - 2 * i);
        }
    }
}
