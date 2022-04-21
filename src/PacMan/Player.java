package PacMan;

import Input.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Main.Game;
import Worlds.PacMan;

public class Player {

    Game game;
    private int x;
    private int y;

    int direction, nextDirection;
    int width = 38;
    int height = 38;
    private BufferedImage image0 = ImageLoader.loadImage("/PacMan0.png");
    private BufferedImage image1 = ImageLoader.loadImage("/PacMan1.png");
    private BufferedImage image2 = ImageLoader.loadImage("/PacMan2.png");
    private BufferedImage usedImage0, usedImage1, usedImage2;
    private int animationCount = 0;
    private int animationDelay = 0;

    public Player(int x, int y, Game game) {
        this.game = game;
        this.x = x;
        this.y = y;
    }

    public void tick() {
        input();
        move();
        tickAnimation();
    }

    public void render(Graphics g) {
        animation(g);
    }

    public void setCords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void animation(Graphics g) {
        switch (animationCount) {
            case 0 -> g.drawImage(usedImage0, x + 3, y + 3, 32, 32, null);
            case 1, 3 -> g.drawImage(usedImage1, x + 3, y + 3, 32, 32, null);
            case 2 -> g.drawImage(usedImage2, x + 3, y + 3, 32, 32, null);

        }
    }

    private void tickAnimation() {
        if (animationDelay >= 5 && direction != 0) {
            animationCount++;
            if (animationCount > 3) {
                animationCount = 0;
            }
            animationDelay = 0;
        } else {
            animationDelay++;
        }
    }

    public static BufferedImage rotateImage(BufferedImage imageToRotate, int grad) {
        int widthOfImage = imageToRotate.getWidth();
        int heightOfImage = imageToRotate.getHeight();
        int typeOfImage = imageToRotate.getType();

        BufferedImage newImageFromBuffer = new BufferedImage(widthOfImage, heightOfImage, typeOfImage);

        Graphics2D graphics2D = newImageFromBuffer.createGraphics();

        graphics2D.rotate(Math.toRadians(grad), widthOfImage / 2, heightOfImage / 2);
        graphics2D.drawImage(imageToRotate, null, 0, 0);

        return newImageFromBuffer;
    }

    private void input() {
        if (game.getKeyHandler().w || game.getKeyHandler().up) {
            nextDirection = 1;
        }
        if (game.getKeyHandler().a || game.getKeyHandler().left) {
            nextDirection = 2;
        }
        if (game.getKeyHandler().s || game.getKeyHandler().down) {
            nextDirection = 3;
        }
        if (game.getKeyHandler().d || game.getKeyHandler().right) {
            nextDirection = 4;
        }
        ArrayList bounds = PacMan.getWorldBounds();
        for (int i = 0; i < bounds.size(); i++) {
            Rectangle border = (Rectangle) bounds.get(i);
            if (getNextBound().intersects(border)) {
                break;
            }
            if (i == bounds.size() - 1) {
                direction = nextDirection;
                switch (direction) {
                    case 1 -> {
                        usedImage0 = rotateImage(image0, -90);
                        usedImage1 = rotateImage(image1, -90);
                        usedImage2 = rotateImage(image2, -90);
                    }
                    case 2 -> {
                        usedImage0 = rotateImage(image0, 180);
                        usedImage1 = rotateImage(image1, 180);
                        usedImage2 = rotateImage(image2, 180);
                    }
                    case 3 -> {
                        usedImage0 = rotateImage(image0, 90);
                        usedImage1 = rotateImage(image1, 90);
                        usedImage2 = rotateImage(image2, 90);
                    }
                    default -> {
                        usedImage0 = image0;
                        usedImage1 = image1;
                        usedImage2 = image2;
                    }
                }
            }
        }
    }

    public void move() {
        ArrayList bounds = PacMan.getWorldBounds();
        for (Object bound : bounds) {
            Rectangle border = (Rectangle) bound;
            if (getNextFrontBound().intersects(border)) {
                direction = 0;
            }
        }

        switch (direction) {
            case 1 -> y -= 2;
            case 2 -> x -= 2;
            case 3 -> y += 2;
            case 4 -> x += 2;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x + width / 4, y + height / 4, width/2, height/2);
    }

    public Rectangle getNextBound() {
        return switch (nextDirection) {
            case 1 -> new Rectangle(x, y - 2, width, height);
            case 2 -> new Rectangle(x - 2, y, width, height);
            case 3 -> new Rectangle(x, y + 2, width, height);
            case 4 -> new Rectangle(x + 2, y, width, height);
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
