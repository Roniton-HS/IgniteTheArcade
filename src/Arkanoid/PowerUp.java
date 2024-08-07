package Arkanoid;

import jdk.jfr.consumer.RecordedClass;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class PowerUp extends Rectangle {
    private final BufferedImage icon;
    private final int id;

    public PowerUp(int x, int y, BufferedImage icon, int id) {
        super(x, y, 32, 32);
        this.icon = icon;
        this.id = id;
    }

    public PowerUp(BufferedImage icon, int id) {
        this.icon = icon;
        this.id = id;
    }

    public BufferedImage getIcon() {
        return icon;
    }

    public int getId() {
        return id;
    }

    public int getSpeed() {
        return 3;
    }

    public void getEffect(Player player, ArrayList<Ball> balls, ArrayList<Rectangle> borders) {
        final int AMOUNT = 10;
        switch (id) {
            case 0 -> actionGetSmaller(player, AMOUNT);
            case 1 -> actionGetBigger(player, AMOUNT, borders);
            case 2 -> actionGetMoreBalls(balls);
            case 3 -> actionFire(balls);
            default -> {
            }
        }
    }

    private void actionGetSmaller(Player player, int amount) {
        if (player.getIntWidth() > 50) {
            player.changeWidth(player.getIntWidth() - amount, amount);
        }
    }

    private void actionGetBigger(Player player, int amount, ArrayList<Rectangle> borders) {
        if (player.getIntWidth() < 150) {
            if (player.getCollisionPlayer().getBounds().intersects(borders.get(0).getBounds())) {
                player.setIntX(player.getIntX() + amount / 2);
            } else if (player.getCollisionPlayer().getBounds().intersects(borders.get(1).getBounds())) {
                player.setIntX((player.getIntX() - amount / 2));
            }

            player.changeWidth(player.getIntWidth() + amount, amount);
        }
    }

    private void actionGetMoreBalls(ArrayList<Ball> balls) {
        Random random = new Random();
        ArrayList<Ball> validBalls = new ArrayList<>();

        for (Ball ball:balls){
            if (!ball.getDestroy()){
                validBalls.add(ball);
            }
        }

        Ball ball = validBalls.get(random.nextInt(validBalls.size()));
        Ball newBall = new Ball(ball);
        double angle = (random.nextInt(46) * Math.PI) / 180;
        double speedX = Math.cos(angle) * -ball.getSpeed();
        double speedY = -Math.sin(angle) * -ball.getSpeed();

        if (random.nextBoolean()) {
            newBall.setSpeedX(speedX);
        } else {
            newBall.setSpeedX(-speedX);
        }
        if (ball.getSpeedY() > 0) {
            newBall.setSpeedY(speedY);
        } else {
            newBall.setSpeedY(-speedY);
        }
        balls.add(newBall);
    }

    private void actionFire(ArrayList<Ball> balls) {
        for (Ball ball : balls) {
            ball.setFire(true);
        }
    }

    public void render(Graphics g) {
        g.drawImage(icon, x, y, width, height, null);
    }

    public void renderBorder(Graphics g) {
        g.setColor(Color.cyan);
        g.drawRect(x, y, width, height);
    }
}
