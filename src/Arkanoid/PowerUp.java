package Arkanoid;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp extends Rectangle {
    private BufferedImage icon;
    private int id;

    public PowerUp(int x, int y, BufferedImage icon, int id) {
        super(x, y, 20, 20);
        this.icon = icon;

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

    public void render(Graphics g) {
        switch (id) {
            case 0 -> g.setColor(Color.blue);
            case 1 -> g.setColor(Color.red);
            case 2 -> g.setColor(Color.yellow);
            default -> g.setColor(Color.black);
        }
        g.fillRect(x, y, width, height);
    }

    public void renderBorder(Graphics g) {
    }
}
