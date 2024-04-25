package FlappyBird;

import java.awt.*;
import java.util.Random;

import static Main.Constants.*;

@SuppressWarnings("unused")
public class Pipe extends Rectangle {
    private Rectangle pipeT, pipeB;
    private Rectangle scoreBorder;
    private final int WIDTH = 50;
    private final int MIN_LENGTH = 100;
    private int lengthT, lengthB;
    private final int SPEED = 5;
    private int x;
    private final int PREV_X;
    private int gap;
    private final int MIN_GAP = 150;
    private final int MAX_GAP = 250;
    private final Random RANDOM = new Random();
    private boolean valid = false;

    public Pipe(int prevX) {
        this.PREV_X = prevX;
        getValues();
        createPipes();
    }

    public void getValues() {
        while (!valid) {
            lengthT = RANDOM.nextInt(350) + MIN_LENGTH;
            gap = RANDOM.nextInt(100) + MIN_GAP;
            lengthB = 700 - lengthT - gap;

            if (lengthB >= MIN_LENGTH) {
                valid = true;
            }
        }
    }

    public void createPipes() {
        this.x = PREV_X + WIDTH + 300;
        pipeT = new Rectangle(x, 0, WIDTH, lengthT);
        pipeB = new Rectangle(x, lengthT + gap, WIDTH, lengthB);
        scoreBorder = new Rectangle(pipeT.x + WIDTH / 2, lengthT, 1, gap);
    }

    public Rectangle getPipeT() {
        return pipeT;
    }

    public void setPipeT(Rectangle pipeT) {
        this.pipeT = pipeT;
    }

    public Rectangle getPipeB() {
        return pipeB;
    }

    public void setPipeB(Rectangle pipeB) {
        this.pipeB = pipeB;
    }

    public Rectangle getScoreBorder() {
        return scoreBorder;
    }

    public void setScoreBorder(Rectangle scoreBorder) {
        this.scoreBorder = scoreBorder;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getMIN_LENGTH() {
        return MIN_LENGTH;
    }

    public int getLengthT() {
        return lengthT;
    }

    public void setLengthT(int lengthT) {
        this.lengthT = lengthT;
    }

    public int getLengthB() {
        return lengthB;
    }

    public void setLengthB(int lengthB) {
        this.lengthB = lengthB;
    }

    public int getSPEED() {
        return SPEED;
    }

    public int getIntX() {
        return x;
    }

    public void setIntX(int x) {
        this.x = x;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public int getMIN_GAP() {
        return MIN_GAP;
    }

    public int getMAX_GAP() {
        return MAX_GAP;
    }

    public Random getRANDOM() {
        return RANDOM;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void updatePipe(int newX) {
        this.x = newX;
        pipeT.x = newX;
        pipeB.x = newX;
        scoreBorder.x = newX + WIDTH / 2;
    }

    public void delScoreBorder() {
        scoreBorder.y = 1000;
    }

    public void render(Graphics g) {
        g.setColor(DARK_GREEN);
        g.fillRect(pipeT.x, pipeT.y, pipeT.width, pipeT.height);
        g.fillRect(pipeB.x, pipeB.y, pipeB.width, pipeB.height);
        g.setColor(ALMOST_BLACK);
        g.fillRect(scoreBorder.x, scoreBorder.y, scoreBorder.width, scoreBorder.height);
    }
}