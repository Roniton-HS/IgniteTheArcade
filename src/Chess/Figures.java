package Chess;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Figures {
    BufferedImage image;
    final int WIDTH = 90;
    final int HEIGHT = 90;

    boolean selected = false;
    private int x;
    private int y;

    public Figures(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void tick(){

    }
    public void render(Graphics g){
        g.drawImage(image, (x)*ChessWorld.FIELD_SIZE, (y)*ChessWorld.FIELD_SIZE, WIDTH, HEIGHT, null);

        if(selected) {
            g.setColor(Color.RED);
            drawThickRect(g, x*ChessWorld.FIELD_SIZE, y*ChessWorld.FIELD_SIZE, ChessWorld.FIELD_SIZE, ChessWorld.FIELD_SIZE, 5);
        }
    }

    public static void drawThickRect(Graphics g, int x, int y, int width, int height, int thickness) {
        for (int i = 0; i < thickness; i++) {
            g.drawRect(x + i, y + i, width - 2 * i, height - 2 * i);
        }
    }

}
