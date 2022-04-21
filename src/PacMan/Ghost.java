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
    private int phase = 0; //0: scatter | 1: chase

    /*
     * movement
     */
    private int targetX;
    private int targetY;
    private int direction = 1; // 1: up | 2: left | 3: down | 4: down
    private int directionTimer = 38;
    long startTime;
    long time;

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
        setPhase(0);
        startTime = System.currentTimeMillis();
    }

    public void tick() {
        time = System.currentTimeMillis() - startTime;
        System.out.println(time);
        if(time >= 84000){
            setPhase(1); //endless chase
        } else if (time >= 79000) {
            setPhase(0);
        } else if (time >= 59000) {
            setPhase(1);
        } else if (time >= 54000) {
            setPhase(0);
        } else if (time >= 34000) {
            setPhase(1);
        } else if (time >= 27000) {
            setPhase(0);
        } else if (time > 7000) {
            setPhase(1);
        }
        move();
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
        updateTarget();

        //make Ghost move an entire block
        if (directionTimer >= 38) {
            direction = changeDirection();
            directionTimer = 2;
        } else {
            directionTimer = directionTimer + 2;
        }

        //move
        switch (direction) {
            case 1 -> y -= 2;
            case 2 -> x -= 2;
            case 3 -> y += 2;
            case 4 -> x += 2;
        }
    }

    private void updateTarget() {
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

    private int changeDirection() {
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

    private void updatePhase() {

    }

    private void setPhase(int x) {
        if (x == 0) {
            phase = x;
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
        } else if (x == 1) {
            phase = x;
        }
    }

    private boolean checkBorder(String direction) {
        ArrayList bounds = PacMan.getWorldBounds();
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
