package Chess;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Figures {
    BufferedImage image;
    final int WIDTH = 90;
    final int HEIGHT = 90;

    boolean selected = false;
    boolean black;
     int x;
     int y;

    ArrayList <Coordinates> coordinates = new ArrayList<>();

    public Figures(int x, int y, boolean black) {
        this.x = x;
        this.y = y;
        this.black = black;
    }

    public void tick(){

    }
    public void render(Graphics g){
        g.drawImage(image, (x)* Chess.FIELD_SIZE, (y)* Chess.FIELD_SIZE, WIDTH, HEIGHT, null);

        if(selected) {
            g.setColor(Color.RED);
            drawThickRect(g, x* Chess.FIELD_SIZE, y* Chess.FIELD_SIZE, Chess.FIELD_SIZE, Chess.FIELD_SIZE, 5);
            renderWhereTo(g);
        }


    }

    public static void drawThickRect(Graphics g, int x, int y, int width, int height, int thickness) {
        for (int i = 0; i < thickness; i++) {
            g.drawRect(x + i, y + i, width - 2 * i, height - 2 * i);
        }
    }

    public void renderWhereTo(Graphics g){
        g.setColor(new Color(255, 0, 0, 100));
        for (Coordinates c:coordinates) {
            g.fillRect(c.getX()*Chess.FIELD_SIZE, c.getY()*Chess.FIELD_SIZE, Chess.FIELD_SIZE, Chess.FIELD_SIZE);
            drawThickRect(g, c.getX()*Chess.FIELD_SIZE, c.getY()*Chess.FIELD_SIZE, Chess.FIELD_SIZE, Chess.FIELD_SIZE, 3);
        }
    }



}
