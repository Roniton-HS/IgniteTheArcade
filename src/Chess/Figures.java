package Chess;

import Input.ImageLoader;
import Input.MouseHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Figures {
    BufferedImage image = ImageLoader.loadImage("/chessRes/Black/KingBlack.png");
    final int WIDTH = image.getWidth() * 4;
    final int HEIGHT = image.getHeight() * 4;

    Figures[][] figuresSave;
    boolean selected = false;
    boolean black;
    int x;
    int y;

    ArrayList<Coordinates> coordinates = new ArrayList<>();

    public Figures(int x, int y, boolean black, Figures[][] figuresSave) {
        this.x = x;
        this.y = y;
        this.black = black;
        this.figuresSave = figuresSave;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(image, ((x) * Chess.FIELD_SIZE) + 10, (y) * Chess.FIELD_SIZE, WIDTH, HEIGHT, null);

        if (selected) {
            g.setColor(new Color(0,255,0,50));
            g.fillRect(x*Chess.FIELD_SIZE, y*Chess.FIELD_SIZE, Chess.FIELD_SIZE, Chess.FIELD_SIZE);
            //drawThickRect(g, x * Chess.FIELD_SIZE, y * Chess.FIELD_SIZE, Chess.FIELD_SIZE, Chess.FIELD_SIZE, 5);
            renderWhereTo(g);
        }


    }

    public static void drawThickRect(Graphics g, int x, int y, int width, int height, int thickness) {
        for (int i = 0; i < thickness; i++) {
            g.drawRect(x + i, y + i, width - 2 * i, height - 2 * i);
        }
    }

    public void renderWhereTo(Graphics g) {
        g.setColor(new Color(255, 0, 0, 100));
        for (Coordinates c : coordinates) {
            g.fillRect(c.getX() * Chess.FIELD_SIZE, c.getY() * Chess.FIELD_SIZE, Chess.FIELD_SIZE, Chess.FIELD_SIZE);
            drawThickRect(g, c.getX() * Chess.FIELD_SIZE, c.getY() * Chess.FIELD_SIZE, Chess.FIELD_SIZE, Chess.FIELD_SIZE, 3);
        }
    }

    public void moveFigure() {
        if (selected) {
            //get Mouse X and Y Positions
            int clickX = (MouseHandler.getClickX() / Chess.FIELD_SIZE);
            int clickY = (MouseHandler.getClickY() / Chess.FIELD_SIZE);
            if (clickX >= 1 && clickX <= 8 && clickY >= 1 && clickY <= 8) {

                for (Coordinates c : coordinates) {

                    if (c.getX() == clickX && c.getY() == clickY) {
                        figuresSave[c.getX()][c.getY()] = figuresSave[x][y];
                        figuresSave[x][y] = null;
                        x = c.getX();
                        y = c.getY();
                    }
                }
            }
        }


    }


}
