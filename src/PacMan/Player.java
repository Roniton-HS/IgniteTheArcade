package PacMan;

import Main.Game;

import java.awt.*;
import java.util.ArrayList;

import Worlds.PacMan;

public class Player {

    Game game;
    int x, y;
    int direction;
    int width = 32;
    int height = 32;

    public Player(int x, int y, Game game) {
        this.game = game;
        this.x = x;
        this.y = y;
    }

    public void tick() {
        input();
        move();
        PacMan.getWorldBounds();
    }

    public void render(Graphics g) {
        g.fillRect(x, y, 32, 32);
    }

    private void input() {
        if (game.getKeyHandler().w) {
            direction = 1;
        }
        if (game.getKeyHandler().a) {
            direction = 2;
        }
        if (game.getKeyHandler().s) {
            direction = 3;
        }
        if (game.getKeyHandler().d) {
            direction = 4;
        }
    }

    public void move() {
        System.out.println(direction);
        ArrayList bounds = PacMan.getWorldBounds();
        for (int i = 0; i < bounds.size(); i++) {
            Rectangle border = (Rectangle) bounds.get(i);
            if (getNextBound().intersects(border)) {
                direction = 0;
            }
        }

        switch (direction) {
            case 1 -> y -= 1;
            case 2 -> x -= 1;
            case 3 -> y += 1;
            case 4 -> x += 1;
        }
    }

    public Rectangle getNextBound() {
        return switch (direction) {
            case 1 -> new Rectangle(x, y - 1, width, height);
            case 2 -> new Rectangle(x - 1, y, width, height);
            case 3 -> new Rectangle(x, y + 1, width, height);
            case 4 -> new Rectangle(x + 1, y, width, height);
            default -> new Rectangle(0, 0, 0, 0);
        };
    }
}
