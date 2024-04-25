package FlappyBird;

import Input.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
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
    private BufferedImage pipe_top_t = ImageLoader.loadImage("/flappyBirdRes/pipe_top_t.png");
    private BufferedImage pipe_top_b = ImageLoader.loadImage("/flappyBirdRes/pipe_top_b.png");
    private BufferedImage pipe_body = ImageLoader.loadImage("/flappyBirdRes/pipe_body.png");


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

        g.drawImage(pipe_top_t,pipeT.x-2,lengthT-pipe_top_t.getHeight(),pipe_top_t.getWidth()*2,pipe_top_t.getHeight()*2,null);
        g.drawImage(pipe_body,pipeT.x,pipeT.y,pipe_body.getWidth()*2,lengthT-pipe_top_t.getHeight(),null);

        g.drawImage(pipe_top_b,pipeB.x-2,pipeB.y,pipe_top_b.getWidth()*2,pipe_top_b.getHeight()*2,null);
        g.drawImage(pipe_body,pipeB.x,pipeB.y+pipe_top_b.getHeight()*2,pipe_body.getWidth()*2,lengthB-pipe_top_b.getHeight()*2,null);

        //g.setColor(DARK_GREEN);
        //g.fillRect(pipeT.x, pipeT.y, pipeT.width, pipeT.height);
        //g.fillRect(pipeB.x, pipeB.y, pipeB.width, pipeB.height);
    }
}