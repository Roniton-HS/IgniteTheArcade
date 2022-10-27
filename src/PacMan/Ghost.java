package PacMan;

import Input.ImageLoader;
import Main.Game;
import Worlds.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Ghost {

    //general
    Game game;
    private int x;
    private int y;
    private final int size = 38;
    public final int color;

    //phase
    private int phase; //0: scatter | 1: chase | 2: fear | 3: go to base | 4: leave base
    private boolean startInBase = true;
    private boolean leaveBase = false;
    public boolean fear = false;
    public boolean eaten = false;


    //movement
    private int targetX;
    private int targetY;
    private int direction = 1; // 1: up | 2: left | 3: down | 4: down
    private int directionTimer = 38;

    //graphical
    static int blinkStartTicks;
    private boolean blink;
    private int blinkTimer = 0;
    BufferedImage right, left, down, up;
    BufferedImage eyesUp = ImageLoader.loadImage("/sprites/ghost/eyesUp.png");
    BufferedImage eyesLeft = ImageLoader.loadImage("/sprites/ghost/eyesLeft.png");
    BufferedImage eyesDown = ImageLoader.loadImage("/sprites/ghost/eyesDown.png");
    BufferedImage eyesRight = ImageLoader.loadImage("/sprites/ghost/eyesRight.png");
    BufferedImage frightened = ImageLoader.loadImage("/sprites/ghost/scaredGhost.png");

    /*
    ====================================================================================================================
    init
    ====================================================================================================================
    */
    public Ghost(int x, int y, int color, Game game) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.color = color;
        setColor();
        phase = 0;
    }


    /*
    ====================================================================================================================
    game logic
    ====================================================================================================================
     */
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

    private void handlePhase() {
        if (startInBase) {
            phase = 3;
            switch (color) {
                case 1 -> {
                    if (PacMan.ticks >= 300) {
                        startInBase = false;
                        leaveBase = true;
                    }
                }
                case 2 -> {
                    if (PacMan.ticks >= 0) {
                        startInBase = false;
                        leaveBase = true;
                    }
                }
                case 3 -> {
                    if (PacMan.ticks >= 900) {
                        startInBase = false;
                        leaveBase = true;
                    }
                }
                case 4 -> {
                    if (PacMan.ticks >= 600) {
                        startInBase = false;
                        leaveBase = true;
                    }
                }
            }
        } else if (eaten) {
            phase = 3;
        } else if (fear) {

            if (PacMan.ticks - blinkStartTicks > 900) {
                fear = false;
                blink = false;
            } else if (PacMan.ticks - blinkStartTicks > 750) {
                blink = true;
            } else {
                phase = 2;
            }
        } else if (leaveBase) {
            phase = 4;
        } else {
            if (PacMan.ticks >= 5040) {
                phase = 1; //endless chase
            } else if (PacMan.ticks >= 4740) {
                phase = 0;
            } else if (PacMan.ticks >= 3540) {
                phase = 1;
            } else if (PacMan.ticks >= 3240) {
                phase = 0;
            } else if (PacMan.ticks >= 2040) {
                phase = 1;
            } else if (PacMan.ticks >= 1620) {
                phase = 0;
            } else if (PacMan.ticks >= 420) {
                phase = 1;
            } else {
                phase = 0;
            }
        }
    }

    public void startFear() {
        fear = true;
        blink = false;
        blinkStartTicks = PacMan.ticks;
    }

    /*
    ====================================================================================================================
    movement
    ====================================================================================================================
     */
    public void move() {
        updateDirection();
        //move
        switch (direction) {
            case 1 -> y -= 2;
            case 2 -> x -= 2;
            case 3 -> y += 2;
            case 4 -> x += 2;
        }
        teleport();
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
                    if (getVectorSize(x, y, targetX, targetY) < 60) {
                        eaten = false;
                        fear = false;
                    }
                }
                case 4 -> {
                    targetLeaveBase();
                    direction = directionByVector();
                    if (getVectorSize(x, y, targetX, targetY) < 60) {
                        leaveBase = false;
                    }
                }
            }
            directionTimer = 2;
        } else {
            directionTimer = directionTimer + 2;
        }
    }

    private void teleport(){
        if (x <= -1 * PacMan.getBlockSize()) {
            x += 21 * PacMan.getBlockSize();
        } else if (x >= 20 * PacMan.getBlockSize()) {
            x -= 21 * PacMan.getBlockSize();
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
                            targetX = 0;
                            targetY = 0;
                        } else {
                            //unten links
                            targetX = 0;
                            targetY = 25 * PacMan.getBlockSize();
                        }
                    } else {
                        if (PacMan.getPlayer().getY() < 25 * PacMan.getBlockSize() / 2) {
                            //oben rechts
                            targetX = 18 * PacMan.getBlockSize();
                            targetY = 0;
                        } else {
                            //unten rechts
                            targetX = 18 * PacMan.getBlockSize();
                            targetY = 25 * PacMan.getBlockSize();
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
                targetX = 0;
                targetY = 0;
            }
            case 2 -> {
                targetX = 18 * PacMan.getBlockSize();
                targetY = 0;
            }
            case 3 -> {
                targetX = 0;
                targetY = 25 * PacMan.getBlockSize();
            }
            case 4 -> {
                targetX = 18 * PacMan.getBlockSize();
                targetY = 25 * PacMan.getBlockSize();
            }
        }
    }

    private void targetBase() {
        targetX = 9 * PacMan.getBlockSize();
        targetY = 12 * PacMan.getBlockSize();
    }

    private void targetLeaveBase() {
        targetX = 9 * PacMan.getBlockSize();
        targetY = 8 * PacMan.getBlockSize();
    }

    private boolean checkFree(String direction) {
        ArrayList<Rectangle> bounds = PacMan.getWorldBounds();
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


    /*
    ====================================================================================================================
    render
    ====================================================================================================================
     */
    public void render(Graphics g) {
        renderImage(g);
        //renderVector(g);
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


    /*
    ====================================================================================================================
    getters / setters
    ====================================================================================================================
     */
    private void setColor() {
        switch (color) {
            //red
            case 2 -> {
                right = ImageLoader.loadImage("/sprites/ghost/sprite_03.png");
                left = ImageLoader.loadImage("/sprites/ghost/sprite_04.png");
                down = ImageLoader.loadImage("/sprites/ghost/sprite_05.png");
                up = ImageLoader.loadImage("/sprites/ghost/sprite_06.png");
            }
            //blue
            case 4 -> {
                right = ImageLoader.loadImage("/sprites/ghost/sprite_07.png");
                left = ImageLoader.loadImage("/sprites/ghost/sprite_08.png");
                down = ImageLoader.loadImage("/sprites/ghost/sprite_09.png");
                up = ImageLoader.loadImage("/sprites/ghost/sprite_10.png");
            }
            //orange
            case 3 -> {
                right = ImageLoader.loadImage("/sprites/ghost/sprite_11.png");
                left = ImageLoader.loadImage("/sprites/ghost/sprite_12.png");
                down = ImageLoader.loadImage("/sprites/ghost/sprite_13.png");
                up = ImageLoader.loadImage("/sprites/ghost/sprite_14.png");
            }
            //pink
            case 1 -> {
                right = ImageLoader.loadImage("/sprites/ghost/sprite_15.png");
                left = ImageLoader.loadImage("/sprites/ghost/sprite_16.png");
                down = ImageLoader.loadImage("/sprites/ghost/sprite_17.png");
                up = ImageLoader.loadImage("/sprites/ghost/sprite_18.png");
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x + size / 4, y + size / 4, size / 2, size / 2);
    }

    private double getVectorSize(int xGhost, int yGhost, int xPlayer, int yPlayer) {
        return Math.sqrt((xPlayer - xGhost) * (xPlayer - xGhost) + (yPlayer - yGhost) * (yPlayer - yGhost));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}