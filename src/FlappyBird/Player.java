package FlappyBird;


import Input.ImageLoader;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static Main.Constants.*;

public class Player extends Rectangle {
    private final Rectangle BIRD;
    private final int BIRD_SIZE = 20;
    private final int ACCELERATION = 2;
    private int speed;
    private final int X = 100;
    private int y;
    private BufferedImage imgBird = ImageLoader.loadImage("/flappyBirdRes/bird.png");
    private BufferedImage rotImgBird;

    public Player(int windowHeight) {
        BIRD = new Rectangle(X, (windowHeight - 100) / 2, BIRD_SIZE, BIRD_SIZE);
        y = BIRD.y;
        speed = 0;
    }

    public int getBIRD_SIZE() {
        return BIRD_SIZE;
    }

    public int getACCELERATION() {
        return ACCELERATION;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getIntY() {
        return y;
    }

    public void setIntY(int y) {
        this.y = y;
        BIRD.y = y;
    }

    public Rectangle getBounds() {
        return BIRD.getBounds();
    }

    public void render(Graphics g) {
        //g.setColor(DARK_YELLOW);
        //g.fillRect(BIRD.x, BIRD.y, BIRD_SIZE, BIRD_SIZE);
        //g.drawImage(imgBird, BIRD.x - 11, BIRD.y - 6, imgBird.getWidth() * 2, imgBird.getHeight() * 2, null);
        rotateImg();

        g.drawImage(rotImgBird,BIRD.x,BIRD.y,rotImgBird.getWidth()*2,rotImgBird.getHeight()*2, null);
    }

    private void rotateImg(){
        int widthOfImage = imgBird.getWidth();
        int heightOfImage = imgBird.getHeight();
        int typeOfImage = imgBird.getType();

        rotImgBird = new BufferedImage(widthOfImage, heightOfImage, typeOfImage);
        Graphics2D graphics2D = rotImgBird.createGraphics();

        graphics2D.rotate(Math.toRadians(calcRotation()), widthOfImage / 2, heightOfImage / 2);
        graphics2D.drawImage(imgBird, null, 0, 0);
    }

    private double calcRotation(){
        return 0.075*Math.pow(speed,2)+3.758*speed;
    }
}
