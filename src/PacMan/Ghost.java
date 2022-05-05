package PacMan;

import Input.ImageLoader;
import Main.Game;
import Worlds.PacMan;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Ghost {
    /*
     * general
     */
    Game game;
    private int x, y;
    private final int size = 38;
    private final int color;

    public static boolean fear = false;
    public boolean eaten = false;

    public void setPhase(int phase) {
        this.phase = phase;
    }

    private int phase = 0; //0: scatter | 1: chase | 2: fear | 3: go to base

    /*
     * movement
     */
    private int targetX;
    private int targetY;
    private int direction = 1; // 1: up | 2: left | 3: down | 4: down
    private int directionTimer = 38;
    static long startTime;
    static long time;

    ArrayList bounds = PacMan.getGhostWorldBounds();

    BufferedImage right, left, down, up;


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Ghost(int x, int y, int color, Game game) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.color = color;
        setColor();
        phase = 0;
        startTime = System.currentTimeMillis();
    }

    public void tick() {
        handlePhase();
        move();
    }

    private void handlePhase() {
        if (eaten) {
            phase = 3;
        } else if (fear) {
            if (System.currentTimeMillis() - time > 15000) {
                fear = false;
            } else {
                phase = 2;
            }
        } else {
            //System.out.println(time);
            if (time >= 84000) {
                phase = 1; //endless chase
            } else if (time >= 79000) {
                phase = 0;
            } else if (time >= 59000) {
                phase = 1;
            } else if (time >= 54000) {
                phase = 0;
            } else if (time >= 34000) {
                phase = 1;
            } else if (time >= 27000) {
                phase = 0;
            } else if (time > 7000) {
                phase = 1;
            }
        }
    }

    public static void startFear() {
        fear = true;
        time = System.currentTimeMillis();
    }


    public void render(Graphics g) {

        switch (direction) {
            case 1 -> g.drawImage(up, x, y, 38, 38, null);
            case 2 -> g.drawImage(left, x, y, 38, 38, null);
            case 3 -> g.drawImage(down, x, y, 38, 38, null);
            case 4 -> g.drawImage(right, x, y, 38, 38, null);
        }
        renderVector(g);
    }

    public void move() {
        updateDirection();
        //move
        switch (direction) {
            case 1 -> y -= 2;
            case 2 -> x -= 2;
            case 3 -> y += 2;
            case 4 -> x += 2;
        }
    }

    public void updateDirection() {
        //make Ghost move an entire block
        if (directionTimer >= 38) {
            if (phase == 0) {
                targetScatter();
                direction = directionByVector();
            } else if (phase == 1) {
                targetPlayer();
                direction = directionByVector();
            } else if (phase == 2) {
                direction = directionRandom();
            } else if (phase == 3) {
                System.out.println("yes");
                targetBase();
                direction = directionByVector();
            }
            directionTimer = 2;
        } else {
            directionTimer = directionTimer + 2;
        }
    }

    private int directionByVector() {
        boolean canUp = false;
        boolean canDown = false;
        boolean canRight = false;
        boolean canLeft = false;
        switch (direction) {
            case 1 -> {
                if (!checkBorder("up")) {
                    canUp = true;
                    if (getVectorSize(this.x, this.y - PacMan.getBlockSize() / 2, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 1;
                    }
                }
                if (!checkBorder("left")) {
                    canLeft = true;
                    if (getVectorSize(this.x - PacMan.getBlockSize() / 2, this.y, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 2;
                    }
                }
                if (!checkBorder("right")) {
                    canRight = true;
                    if (getVectorSize(this.x + size + PacMan.getBlockSize() / 2, this.y, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 4;
                    }
                }
                if (canUp)
                    return 1;
                if (canRight)
                    return 4;
                if (canLeft)
                    return 2;
                return 3;
            }
            case 2 -> {
                if (!checkBorder("up")) {
                    canUp = true;
                    if (getVectorSize(this.x, this.y - PacMan.getBlockSize() / 2, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 1;
                    }
                }
                if (!checkBorder("left")) {
                    canLeft = true;
                    if (getVectorSize(this.x - PacMan.getBlockSize() / 2, this.y, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 2;
                    }
                }
                if (!checkBorder("down")) {
                    canDown = true;
                    if (getVectorSize(this.x, this.y + size + PacMan.getBlockSize() / 2, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 3;
                    }
                }
                if (canLeft)
                    return 2;
                if (canUp)
                    return 1;
                if (canDown)
                    return 3;
                return 4;
            }
            case 3 -> {
                if (!checkBorder("right")) {
                    canRight = true;
                    if (getVectorSize(this.x + size + PacMan.getBlockSize() / 2, this.y, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 4;
                    }
                }
                if (!checkBorder("left")) {
                    canLeft = true;
                    if (getVectorSize(this.x - PacMan.getBlockSize() / 2, this.y, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 2;
                    }
                }
                if (!checkBorder("down")) {
                    canDown = true;
                    if (getVectorSize(this.x, this.y + size + PacMan.getBlockSize() / 2, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 3;
                    }
                }
                if (canDown)
                    return 3;
                if (canRight)
                    return 4;
                if (canLeft)
                    return 2;
                return 1;
            }
            case 4 -> {
                if (!checkBorder("up")) {
                    canUp = true;
                    if (getVectorSize(this.x, this.y - PacMan.getBlockSize() / 2, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 1;
                    }
                }
                if (!checkBorder("right")) {
                    canRight = true;
                    if (getVectorSize(this.x + size + PacMan.getBlockSize() / 2, this.y, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 4;
                    }
                }
                if (!checkBorder("down")) {
                    canDown = true;
                    if (getVectorSize(this.x, this.y + size + PacMan.getBlockSize() / 2, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 3;
                    }
                }
                if (canRight)
                    return 4;
                if (canUp)
                    return 1;
                if (canDown)
                    return 3;
                return 2;
            }
        }
        return 0;
    }

    private int directionRandom() {
        boolean canUp = false;
        boolean canDown = false;
        boolean canRight = false;
        boolean canLeft = false;

        if (!checkBorder("up")) {
            canUp = true;
        }
        if (!checkBorder("down")) {
            canDown = true;
        }
        if (!checkBorder("left")) {
            canLeft = true;
        }
        if (!checkBorder("right")) {
            canRight = true;
        }
        switch (direction) {
            case 1 -> {
                if (canRight && Math.random() > 0.5) {
                    return 4;
                }
                if (canLeft && Math.random() > 0.5) {
                    return 2;
                }
                if (canUp) {
                    return 1;
                }
                if (canRight) {
                    return 4;
                }
                if (canLeft) {
                    return 2;
                }
            }
            case 2 -> {
                if (canUp && Math.random() > 0.5) {
                    return 1;
                }
                if (canDown && Math.random() > 0.5) {
                    return 3;
                }
                if (canLeft) {
                    return 2;
                }
                if (canUp) {
                    return 1;
                }
                if (canDown) {
                    return 3;
                }
            }
            case 3 -> {
                if (!checkBorder("right") && Math.random() > 0.5) {
                    return 4;
                }
                if (!checkBorder("left") && Math.random() > 0.5) {
                    return 2;
                }
                if (!checkBorder("down")) {
                    return 3;
                }
                if (!checkBorder("right")) {
                    return 4;
                }
                if (!checkBorder("left")) {
                    return 2;
                }
                return 1;
            }
            case 4 -> {
                if (canUp && Math.random() > 0.5) {
                    return 1;
                }
                if (canDown && Math.random() > 0.5) {
                    return 3;
                }
                if (canRight) {
                    return 4;
                }
                if (canUp) {
                    return 1;
                }
                if (canDown) {
                    return 3;
                }
            }
        }
        return 0;
    }

    private void targetPlayer() {
        if (phase == 1) {
            //update target cords
            switch (color) {
                case 1 -> { //pink
                    switch (PacMan.getPlayer().direction) {
                        case 1 -> {
                            targetX = PacMan.getPlayer().getX();
                            targetY = PacMan.getPlayer().getY() - 3 * PacMan.getBlockSize();
                        }
                        case 2 -> {
                            targetX = PacMan.getPlayer().getX() - 3 * PacMan.getBlockSize();
                            targetY = PacMan.getPlayer().getY();
                        }
                        case 3 -> {
                            targetX = PacMan.getPlayer().getX();
                            targetY = PacMan.getPlayer().getY() + 3 * PacMan.getBlockSize();
                        }
                        case 4 -> {
                            targetX = PacMan.getPlayer().getX() + 3 * PacMan.getBlockSize();
                            targetY = PacMan.getPlayer().getY();
                        }
                    }

                }
                case 2 -> { //rot
                    targetX = PacMan.getPlayer().getX();
                    targetY = PacMan.getPlayer().getY();
                }
                case 3 -> { //orange
                    if (PacMan.getPlayer().getX() < 19 * PacMan.getBlockSize() / 2) {
                        if (PacMan.getPlayer().getY() < 25 * PacMan.getBlockSize() / 2) {
                            //oben links
                            targetX = 4 * PacMan.getBlockSize();
                            targetY = 0;
                        } else {
                            //unten links
                            targetX = 4 * PacMan.getBlockSize();
                            targetY = 20 * PacMan.getBlockSize();
                        }
                    } else {
                        if (PacMan.getPlayer().getY() < 25 * PacMan.getBlockSize() / 2) {
                            //oben rechts
                            targetX = 22 * PacMan.getBlockSize();
                            targetY = 0;
                        } else {
                            //unten rechts
                            targetX = 22 * PacMan.getBlockSize();
                            targetY = 20 * PacMan.getBlockSize();
                        }

                    }
                }
                case 4 -> { //blue
                    Ghost ghost = (Ghost) PacMan.ghosts.get(1);
                    targetX = PacMan.getPlayer().getX() + PacMan.getPlayer().getX() - ghost.getX();
                    targetY = PacMan.getPlayer().getY() + PacMan.getPlayer().getY() - ghost.getY();
                }

            }


        }
    }

    private void targetScatter() {
        switch (color) {
            case 1 -> {
                targetX = 4 * PacMan.getBlockSize();
                targetY = 0;
            }
            case 2 -> {
                targetX = 22 * PacMan.getBlockSize();
                targetY = 0;
            }
            case 3 -> {
                targetX = 4 * PacMan.getBlockSize();
                targetY = 20 * PacMan.getBlockSize();
            }
            case 4 -> {
                targetX = 22 * PacMan.getBlockSize();
                targetY = 20 * PacMan.getBlockSize();
            }
        }
    }

    private void targetBase() {
        targetX = 13 * PacMan.getBlockSize();
        targetY = 10 * PacMan.getBlockSize();
    }

    private boolean checkBorder(String direction) {
        for (Object bound : bounds) {
            Rectangle border = (Rectangle) bound;
            if (direction.equals("up") && new Rectangle(this.x, this.y - PacMan.getBlockSize() / 2, size, 1).intersects(border)) {
                return true;
            }
            if (direction.equals("down") && new Rectangle(this.x, this.y + size + PacMan.getBlockSize() / 2, size, 1).intersects(border)) {
                return true;
            }
            if (direction.equals("right") && new Rectangle(this.x + size + PacMan.getBlockSize() / 2, this.y, 1, size).intersects(border)) {
                return true;
            }
            if (direction.equals("left") && new Rectangle(this.x - PacMan.getBlockSize() / 2, this.y, 1, size).intersects(border)) {
                return true;
            }
        }
        return false;
    }

    private double getVectorSize(int xGhost, int yGhost, int xPlayer, int yPlayer) {
        return Math.sqrt((xPlayer - xGhost) * (xPlayer - xGhost) + (yPlayer - yGhost) * (yPlayer - yGhost));
    }

    private void renderVector(Graphics g) {
        g.drawLine(this.x + size / 2, this.y + size / 2, targetX + size / 2, targetY + size / 2);
    }

    public Rectangle getBounds() {
        return new Rectangle(x + size / 4, y + size / 4, size / 2, size / 2);
    }

    private void setColor() {
        switch (color) {
            //red
            case 2 -> {
                right = ImageLoader.loadImage("/sprite_03.png");
                left = ImageLoader.loadImage("/sprite_04.png");
                down = ImageLoader.loadImage("/sprite_05.png");
                up = ImageLoader.loadImage("/sprite_06.png");
            }
            //blue
            case 4 -> {
                right = ImageLoader.loadImage("/sprite_07.png");
                left = ImageLoader.loadImage("/sprite_08.png");
                down = ImageLoader.loadImage("/sprite_09.png");
                up = ImageLoader.loadImage("/sprite_10.png");
            }
            //orange
            case 3 -> {
                right = ImageLoader.loadImage("/sprite_11.png");
                left = ImageLoader.loadImage("/sprite_12.png");
                down = ImageLoader.loadImage("/sprite_13.png");
                up = ImageLoader.loadImage("/sprite_14.png");
            }
            //pink
            case 1 -> {
                right = ImageLoader.loadImage("/sprite_15.png");
                left = ImageLoader.loadImage("/sprite_16.png");
                down = ImageLoader.loadImage("/sprite_17.png");
                up = ImageLoader.loadImage("/sprite_18.png");
            }
        }
    }
}
