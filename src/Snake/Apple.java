package Snake;

import java.awt.*;

public class Apple {
    public int x, y;

    public Apple(int xApple, int yApple) {
        this.x = xApple;
        this.y = yApple;
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, Snake.BLOCK_SIZE, Snake.BLOCK_SIZE);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, Snake.BLOCK_SIZE, Snake.BLOCK_SIZE);
    }
}