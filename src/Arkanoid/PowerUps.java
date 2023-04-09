package Arkanoid;


import Input.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class PowerUps {

    // Powerup klasse schreiben, in der das bild gespeichert wird, was es macht und so

    private boolean[] valid;
    private ArrayList<PowerUp> powerUps = new ArrayList<>();

    BufferedImage iconGetSmaller = ImageLoader.loadImage("/pacManRes/ghost/eyesUp.png");
    BufferedImage iconGetBigger = ImageLoader.loadImage("/pacManRes/ghost/eyesLeft.png");
    BufferedImage iconGetMoreBalls = ImageLoader.loadImage("/pacManRes/ghost/eyesDown.png");

    public boolean[] getValid() {
        return valid;
    }

    public void setValid(int index, boolean valid) {
        this.valid[index] = valid;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void createPowerUps(){
        powerUps.add(new PowerUp(iconGetSmaller,0));
        powerUps.add(new PowerUp(iconGetBigger,1));
        powerUps.add(new PowerUp(iconGetMoreBalls,2));

        createValid();
    }

    private void createValid() {
        valid = new boolean[powerUps.size()];
        Arrays.fill(valid, true);
    }

}
