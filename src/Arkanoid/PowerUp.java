package Arkanoid;


import java.awt.*;

public class PowerUp {

    // Powerup klasse schreiben, in der das bild gespeichert wird, was es macht und so

    private boolean[] powerUps = new boolean[3];

    public PowerUp() {
        for (int i = 0; i < powerUps.length; i++){
            powerUps[i] = true;
        }
    }

    public boolean[] getPowerUps() {
        return powerUps;
    }

    public void setPowerUps(int index, boolean value) {
        this.powerUps[index] = value;
    }

    public int getSpeed(){
        return 5;
    }

    public void spawnPowerUp(int powerUp, Ball ball){
        switch (powerUp){
            case 0 -> spawnGetSmaller(ball);
            case 1 -> spawngetBigger(ball);
            case 2 -> spawnMoreBalls(ball);
            default -> {}
        }
    }

    private void spawnGetSmaller(Ball ball) {
        Rectangle powerUp = new Rectangle(ball.getIntX(), ball.getIntY(), 10,10);

    }
}
