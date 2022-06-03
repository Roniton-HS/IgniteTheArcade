package PacMan;

import Input.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Fruit {
    BufferedImage image;
    private final int x, y;
    private final int size = 32;

    public Fruit(int x, int y, int type) {
        this.x = x;
        this.y = y;
        switch (type) {
            case 1 -> image = ImageLoader.loadImage("/sprite_00.png");
            case 2 -> image = ImageLoader.loadImage("/sprite_01.png");
            case 3 -> image = ImageLoader.loadImage("/sprite_02.png");

        }
    }

    public void render(Graphics g) {
        g.drawImage(image, x, y, size, size, null);
        System.out.println("test");
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}
