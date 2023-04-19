package Arkanoid;

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

    public void getEffect(Player player, ArrayList<Ball> balls) {
        final int amount = 10;
        switch (id) {
            case 0 -> actionGetSmaller(player, amount);
            case 1 -> actionGetBigger(player, amount);
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

    private void actionGetBigger(Player player, int amount) {
        if (player.getIntWidth() < 150) {
            player.changeWidth(player.getIntWidth() + amount, amount);
        }
    }

    private void actionGetMoreBalls(ArrayList<Ball> balls) {
        Random random = new Random();
        Ball ball = balls.get(random.nextInt(balls.size()));
        Ball newBall = new Ball(ball);
        double angle = (random.nextInt(46) * Math.PI) / 180;
        double speedX = Math.cos(angle) * -ball.getSpeed();
        double speedY = -Math.sin(angle) * -ball.getSpeed();

        if (random.nextBoolean()) {
            newBall.setSpeedX(speedX);
        } else {
            newBall.setSpeedX(-speedX);
        }
        if (ball.getSpeedY()>0){
            newBall.setSpeedY(speedY);
        } else {
            newBall.setSpeedY(-speedY);
        }
        balls.add(newBall);
    }

    private void actionFire(ArrayList<Ball> balls) {
        for (Ball ball: balls) {
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
