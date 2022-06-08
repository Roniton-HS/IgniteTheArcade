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
    private int x;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int y;
    private final int size = 38;
    public final int color;

    private boolean startInBase = true;
    private boolean leaveBase = false;
    public boolean fear = false;
    private boolean blink;
    private int blinkTimer = 0;
    public boolean eaten = false;
    private int phase; //0: scatter | 1: chase | 2: fear | 3: go to base | 4: leave base

    /*
     * movement
     */
    private int targetX;
    private int targetY;
    private int direction = 1; // 1: up | 2: left | 3: down | 4: down
    private int directionTimer = 38;
    public static long startTime;
    static long time;

    ArrayList<Rectangle> bounds = PacMan.getGhostWorldBounds();

    BufferedImage right, left, down, up;
    BufferedImage eyesUp = ImageLoader.loadImage("/eyesUp.png");
    BufferedImage eyesLeft = ImageLoader.loadImage("/eyesLeft.png");
    BufferedImage eyesDown = ImageLoader.loadImage("/eyesDown.png");
    BufferedImage eyesRight = ImageLoader.loadImage("/eyesRight.png");
    BufferedImage frightened = ImageLoader.loadImage("/scaredGhost.png");


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
        if (blink) {
            blinkTimer++;
            if (blinkTimer >= 30) {
                blinkTimer = 0;
            }
        }
    }

    public void render(Graphics g) {
        renderImage(g);
        renderVector(g);
    }

    private void handlePhase() {
        if (startInBase) {
            phase = 3;
            switch (color) {
                case 1 -> {
                    if (System.currentTimeMillis() - startTime > 5000) {
                        startInBase = false;
                        leaveBase = true;
                    }
                }
                case 2 -> {
                    if (System.currentTimeMillis() - startTime > 10000) {
                        startInBase = false;
                        leaveBase = true;
                    }
                }
                case 3 -> {
                    if (System.currentTimeMillis() - startTime > 15000) {
                        startInBase = false;
                        leaveBase = true;
                    }
                }
                case 4 -> {
                    if (System.currentTimeMillis() - startTime > 20000) {
                        startInBase = false;
                        leaveBase = true;
                    }
                }
            }
        } else if (eaten) {
            phase = 3;
        } else if (fear) {

            if (System.currentTimeMillis() - time > 15000) {
                fear = false;
                blink = false;
            } else if (System.currentTimeMillis() - time > 12500) {
                blink = true;
            } else {
                phase = 2;
            }
        } else if (leaveBase) {
            phase = 4;
        } else {
            long systemTime = System.currentTimeMillis() - startTime;
            if (systemTime >= 84000) {
                phase = 1; //endless chase
            } else if (systemTime >= 79000) {
                phase = 0;
            } else if (systemTime >= 59000) {
                phase = 1;
            } else if (systemTime >= 54000) {
                phase = 0;
            } else if (systemTime >= 34000) {
                phase = 1;
            } else if (systemTime >= 27000) {
                phase = 0;
            } else if (systemTime > 7000) {
                phase = 1;
            }
        }
    }

    public void renderImage(Graphics g) {
        if (eaten) {
            switch (direction) {
                case 1 -> g.drawImage(eyesUp, x, y, 38, 38, null);
                case 2 -> g.drawImage(eyesLeft, x, y, 38, 38, null);
                case 3 -> g.drawImage(eyesDown, x, y, 38, 38, null);
                case 4 -> g.drawImage(eyesRight, x, y, 38, 38, null);
            }
        } else if (fear) {
            if (blink) {
                if (blinkTimer < 15) {
                    g.drawImage(frightened, x, y, 38, 38, null);
                } else {
                    switch (direction) {
                        case 1 -> g.drawImage(up, x, y, 38, 38, null);
                        case 2 -> g.drawImage(left, x, y, 38, 38, null);
                        case 3 -> g.drawImage(down, x, y, 38, 38, null);
                        case 4 -> g.drawImage(right, x, y, 38, 38, null);
                    }
                }
            } else {
                g.drawImage(frightened, x, y, 38, 38, null);
            }

        } else {
            switch (direction) {
                case 1 -> g.drawImage(up, x, y, 38, 38, null);
                case 2 -> g.drawImage(left, x, y, 38, 38, null);
                case 3 -> g.drawImage(down, x, y, 38, 38, null);
                case 4 -> g.drawImage(right, x, y, 38, 38, null);
            }
        }
    }

    private void renderVector(Graphics g) {
        g.drawLine(this.x + size / 2, this.y + size / 2, targetX + size / 2, targetY + size / 2);
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
            switch (phase) {
                case 0 -> {
                    targetScatter();
                    direction = directionByVector();
                }
                case 1 -> {
                    targetPlayer();
                    direction = directionByVector();
                }
                case 2 -> direction = directionRandom();
                case 3 -> {
                    targetBase();
                    direction = directionByVector();
                    if (getVectorSize(x, y, targetX, targetY) < 40) {
                        eaten = false;
                        fear = false;
                        if (!startInBase) {
                            leaveBase = true;
                        }
                    }
                }
                case 4 -> {
                    targetLeaveBase();
                    direction = directionByVector();
                    if (getVectorSize(x, y, targetX, targetY) < 20) {
                        System.out.println(color);
                        leaveBase = false;
                    }
                }
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
                if (checkFree("up")) {
                    canUp = true;
                    if (getVectorSize(this.x, this.y - PacMan.getBlockSize() / 2, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 1;
                    }
                }
                if (checkFree("left")) {
                    canLeft = true;
                    if (getVectorSize(this.x - PacMan.getBlockSize() / 2, this.y, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 2;
                    }
                }
                if (checkFree("right")) {
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
                if (checkFree("up")) {
                    canUp = true;
                    if (getVectorSize(this.x, this.y - PacMan.getBlockSize() / 2, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 1;
                    }
                }
                if (checkFree("left")) {
                    canLeft = true;
                    if (getVectorSize(this.x - PacMan.getBlockSize() / 2, this.y, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 2;
                    }
                }
                if (checkFree("down")) {
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
                if (checkFree("right")) {
                    canRight = true;
                    if (getVectorSize(this.x + size + PacMan.getBlockSize() / 2, this.y, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 4;
                    }
                }
                if (checkFree("left")) {
                    canLeft = true;
                    if (getVectorSize(this.x - PacMan.getBlockSize() / 2, this.y, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 2;
                    }
                }
                if (checkFree("down")) {
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
                if (checkFree("up")) {
                    canUp = true;
                    if (getVectorSize(this.x, this.y - PacMan.getBlockSize() / 2, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 1;
                    }
                }
                if (checkFree("right")) {
                    canRight = true;
                    if (getVectorSize(this.x + size + PacMan.getBlockSize() / 2, this.y, targetX, targetY) < getVectorSize(this.x, this.y, targetX, targetY)) {
                        return 4;
                    }
                }
                if (checkFree("down")) {
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

        if (checkFree("up")) {
            canUp = true;
        }
        if (checkFree("down")) {
            canDown = true;
        }
        if (checkFree("left")) {
            canLeft = true;
        }
        if (checkFree("right")) {
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
                if (checkFree("right") && Math.random() > 0.5) {
                    return 4;
                }
                if (checkFree("left") && Math.random() > 0.5) {
                    return 2;
                }
                if (checkFree("down")) {
                    return 3;
                }
                if (checkFree("right")) {
                    return 4;
                }
                if (checkFree("left")) {
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
                    Ghost ghost = PacMan.ghosts.get(1);
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
        targetY = 13 * PacMan.getBlockSize();
    }

    private void targetLeaveBase() {
        targetX = 13 * PacMan.getBlockSize();
        targetY = 9 * PacMan.getBlockSize();
    }

    private boolean checkFree(String direction) {
        for (Rectangle bound : bounds) {
            if (direction.equals("up") && new Rectangle(this.x, this.y - PacMan.getBlockSize() / 2, size, 1).intersects(bound)) {
                return false;
            }
            if (direction.equals("down") && new Rectangle(this.x, this.y + size + PacMan.getBlockSize() / 2, size, 1).intersects(bound)) {
                return false;
            }
            if (direction.equals("right") && new Rectangle(this.x + size + PacMan.getBlockSize() / 2, this.y, 1, size).intersects(bound)) {
                return false;
            }
            if (direction.equals("left") && new Rectangle(this.x - PacMan.getBlockSize() / 2, this.y, 1, size).intersects(bound)) {
                return false;
            }
        }
        return true;
    }

    public void startFear() {
        fear = true;
        blink = false;
        time = System.currentTimeMillis();
    }

    public Rectangle getBounds() {
        return new Rectangle(x + size / 4, y + size / 4, size / 2, size / 2);
    }

    private double getVectorSize(int xGhost, int yGhost, int xPlayer, int yPlayer) {
        return Math.sqrt((xPlayer - xGhost) * (xPlayer - xGhost) + (yPlayer - yGhost) * (yPlayer - yGhost));
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
