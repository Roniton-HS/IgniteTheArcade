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
    BufferedImage right = ImageLoader.loadImage("/sprite_03.png");
    BufferedImage left = ImageLoader.loadImage("/sprite_04.png");
    BufferedImage down = ImageLoader.loadImage("/sprite_05.png");
    BufferedImage up = ImageLoader.loadImage("/sprite_06.png");
    private int direction = 2;
    private int width = 38;
    private int height = 38;

    public Ghost(int x, int y, int color, Game game) {
        this.game = game;
        this.x = x;
        this.y = y;

        switch (color) {
            //red
            case 1:
                right = ImageLoader.loadImage("/sprite_03.png");
                left = ImageLoader.loadImage("/sprite_04.png");
                down = ImageLoader.loadImage("/sprite_05.png");
                up = ImageLoader.loadImage("/sprite_06.png");
                break;
            //blue
            case 2:
                right = ImageLoader.loadImage("/sprite_07.png");
                left = ImageLoader.loadImage("/sprite_08.png");
                down = ImageLoader.loadImage("/sprite_09.png");
                up = ImageLoader.loadImage("/sprite_10.png");
                break;
            //orange
            case 3:
                right = ImageLoader.loadImage("/sprite_11.png");
                left = ImageLoader.loadImage("/sprite_12.png");
                down = ImageLoader.loadImage("/sprite_13.png");
                up = ImageLoader.loadImage("/sprite_14.png");
                break;
            //pink
            case 4:
                right = ImageLoader.loadImage("/sprite_15.png");
                left = ImageLoader.loadImage("/sprite_16.png");
                down = ImageLoader.loadImage("/sprite_17.png");
                up = ImageLoader.loadImage("/sprite_18.png");
                break;
        }
    }

    public void tick() {
        move();
    }

    public void render(Graphics g) {

        switch (direction) {
            case 1 -> g.drawImage(up, x, y, 38, 38, null);
            case 2 -> g.drawImage(left, x, y, 38, 38, null);
            case 3 -> g.drawImage(down, x, y, 38, 38, null);
            case 4 -> g.drawImage(right, x, y, 38, 38, null);
        }
    }

    public void move() {
        ArrayList bounds = PacMan.getWorldBounds();
        for (int i = 0; i < bounds.size(); i++) {
            Rectangle border = (Rectangle) bounds.get(i);
            //Switch on contact
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

        //Switch random
        if (Math.random() > 0.3) {
            double ranDir = Math.random(); //left or right?
            for (int j = 0; j < bounds.size(); j++) {
                Rectangle border = (Rectangle) bounds.get(j);
                if (getNextBound(ranDir).intersects(border)) {
                    break;
                }
                if (j == bounds.size() - 1) {
                    switch (direction) {
                        case 1, 3:
                            if (ranDir > 0.5) {
                                direction = 2;
                            } else {
                                direction = 4;
                            }
                            break;
                        case 2, 4:
                            if (ranDir > 0.5) {
                                direction = 1;
                            } else {
                                direction = 3;
                            }
                            break;
                    }
                }
            }
        }
        //move
        switch (direction) {
            case 1 -> y -= 1;
            case 2 -> x -= 1;
            case 3 -> y += 1;
            case 4 -> x += 1;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x + width / 4, y + height / 4, width/2, height/2);
    }

    public Rectangle getNextBound(double ranDir) {
        return switch (direction) {
            case 1, 3 -> ranDir > 0.5 ? new Rectangle(x - 2, y, width, height) : new Rectangle(x + 2, y, width, height);
            case 2, 4 -> ranDir > 0.5 ? new Rectangle(x, y - 2, width, height) : new Rectangle(x, y + 2, width, height);
            default -> new Rectangle(0, 0, 0, 0);
        };
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
