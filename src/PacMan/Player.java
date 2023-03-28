package PacMan;

import Input.ImageLoader;
import Main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Main.Constants.emulogic;

public class Player {
    //general
    Game game;
    private int x;
    private int y;
    int width = 38;
    int height = 38;

    //movement
    public int direction, nextDirection;

    //graphical
    private final BufferedImage image0 = ImageLoader.loadImage("/pacMan/player/PacMan3.png");
    public final BufferedImage image1 = ImageLoader.loadImage("/pacMan/player/PacMan1.png");
    private final BufferedImage image2 = ImageLoader.loadImage("/pacMan/player/PacMan2.png");
    private BufferedImage usedImage0, usedImage1, usedImage2;
    private int animationCount = 0;
    private int animationDelay = 0;
    private int pointsToShow;
    private boolean showPoints;
    private long displayTimer;

    /*
    ====================================================================================================================
    Init Methods
    ====================================================================================================================
     */
    public Player(int x, int y, Game game) {
        this.game = game;
        this.x = x;
        this.y = y;
    }


    /*
    ====================================================================================================================
    game logic
    ====================================================================================================================
    */
    public void tick() {
        input();
        move();
        tickAnimation();
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
        ArrayList<Rectangle> bounds = PacMan.getWorldBounds();
        for (int i = 0; i < bounds.size(); i++) {
            Rectangle border = bounds.get(i);
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


    /*
    ====================================================================================================================
    movement
    ====================================================================================================================
    */
    public void move() {
        ArrayList<Rectangle> bounds = PacMan.getWorldBounds();
        for (Rectangle bound : bounds) {
            if (getNextFrontBound().intersects(bound)) {
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
        return new Rectangle(x + width / 4, y + height / 4, width / 2, height / 2);
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


    /*
    ====================================================================================================================
    render
    ====================================================================================================================
    */
    public void render(Graphics g) {
        animation(g);
        renderScore(g);

    }

    private void animation(Graphics g) {
        if (direction == 0) {
            g.drawImage(image0, x + 3, y + 3, 32, 32, null);
        } else {
            switch (animationCount) {
                case 0 -> g.drawImage(usedImage0, x + 3, y + 3, 32, 32, null);
                case 1, 3 -> g.drawImage(usedImage1, x + 3, y + 3, 32, 32, null);
                case 2 -> g.drawImage(usedImage2, x + 3, y + 3, 32, 32, null);
            }
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

    public void displayScore(int score) {
        pointsToShow = score;
        showPoints = true;
        displayTimer = System.currentTimeMillis();
    }

    public void renderScore(Graphics g) {
        if (showPoints) {
            g.setColor(Color.WHITE);
            g.setFont(emulogic.deriveFont(emulogic.getSize() * 10.0F));
            g.drawString("" + pointsToShow, x + 32, y);
            if (System.currentTimeMillis() - displayTimer > 500) {
                showPoints = false;
            }
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


    /*
    ====================================================================================================================
    getter / setter
    ====================================================================================================================
    */
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setCords(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
