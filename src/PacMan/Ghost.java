package PacMan;

import Input.ImageLoader;
import Main.Game;
import Worlds.PacMan;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Ghost {
    Game game;
    private int x, y;
    BufferedImage red0 = ImageLoader.loadImage("/sprite_03.png");
    private int direction = 2;
    private int width = 38;
    private int height = 38;

    public Ghost(int x, int y, Game game) {
        this.game = game;
        this.x = x;
        this.y = y;
    }

    public void tick() {
        move();
        System.out.println(x + " " + y);
    }

    public void render(Graphics g) {
        g.drawImage(red0, x, y, 38, 38, null);
    }

    public void move() {
        ArrayList bounds = PacMan.getWorldBounds();
        for (int i = 0; i < bounds.size(); i++) {
            Rectangle border = (Rectangle) bounds.get(i);
            if (getNextFrontBound().intersects(border)) {
                if (Math.random() > 0.5) {
                    switch (direction) {
                        case 1 -> direction = 2;
                        case 2 -> direction = 3;
                        case 3 -> direction = 4;
                        case 4 -> direction = 1;
                    }
                } else {
                    switch (direction) {
                        case 1 -> direction = 4;
                        case 2 -> direction = 1;
                        case 3 -> direction = 2;
                        case 4 -> direction = 3;
                    }
                }
            }

        }
        switch (direction) {
            case 1 -> y -= 2;
            case 2 -> x -= 2;
            case 3 -> y += 2;
            case 4 -> x += 2;
        }
    }

    public Rectangle getNextFrontBound() {
        return switch (direction) {
            case 1 -> new Rectangle(x, y - 2, width, height - 30);
            case 2 -> new Rectangle(x - 2, y, width - 30, height);
            case 3 -> new Rectangle(x, y + height - 10, width, 12);
            case 4 -> new Rectangle(x + width - 10, y, 12, height);
            default -> new Rectangle(0, 0, 0, 0);
        };
    }
}
