package Arkanoid;


import Input.ImageLoader;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class PowerUps {

    private boolean[] valid;
    private final ArrayList<PowerUp> powerUps = new ArrayList<>();

    // TODO create icons
    BufferedImage iconPowerSmaller = ImageLoader.loadImage("/arkanoidRes/powerSmaller.png");
    BufferedImage iconPowerBigger = ImageLoader.loadImage("/arkanoidRes/powerBigger.png");
    BufferedImage iconPowerMoreBalls = ImageLoader.loadImage("/arkanoidRes/powerMoreBalls.png");
    BufferedImage iconPowerFire = ImageLoader.loadImage("/arkanoidRes/powerFire.png");

    public boolean[] getValid() {
        return valid;
    }

    public void setValid(int index, boolean valid) {
        this.valid[index] = valid;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void createPowerUps() {
        powerUps.add(new PowerUp(iconPowerSmaller, 0));
        powerUps.add(new PowerUp(iconPowerBigger, 1));
        powerUps.add(new PowerUp(iconPowerMoreBalls, 2));
        powerUps.add(new PowerUp(iconPowerFire, 3));

        createValid();
    }

    private void createValid() {
        valid = new boolean[powerUps.size()];
        Arrays.fill(valid, true);
    }

}
