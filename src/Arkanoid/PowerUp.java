package Arkanoid;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp extends Rectangle {
    private BufferedImage icon;
    private int id;

    public PowerUp(int x, int y, BufferedImage icon, int id) {
        super(x, y, 20, 20);
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

    public void getEffect(Player player, Ball ball) {
        final int amount = 10;
        switch (id) {
            case 0 -> actionGetSmaller(player, amount);
            case 1 -> actionGetBigger(player, amount);
            default -> {
            }
        }
    }

    private void actionGetSmaller(Player player, int amount) {
        player.changeWidth(player.getIntWidth() - amount, amount);
    }

    private void actionGetBigger(Player player, int amount) {
        player.changeWidth(player.getIntWidth() + amount, amount);
    }

    public void render(Graphics g) {
        g.drawImage(icon, x, y, width, height, null);
    }

    public void renderBorder(Graphics g) {
        g.setColor(Color.cyan);
        g.drawRect(x, y, width, height);
    }
}
