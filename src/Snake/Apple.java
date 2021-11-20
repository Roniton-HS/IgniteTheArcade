package Snake;

import java.awt.*;

public class Apple {

    public int xApple, yApple;

    public Apple(int xApple, int yApple) {
        this.xApple = xApple;
        this.yApple = yApple;
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(xApple, yApple, 32,32);
    }

    public Rectangle getBounds() {
        return new Rectangle(xApple, yApple, 32,32);
    }


}
