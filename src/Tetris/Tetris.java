package Tetris;

import Main.Game;
import Worlds.Worlds;

import java.awt.*;

public class Tetris extends Worlds {
    private final int WIDTH = 10;
    private final int HEIGHT = 20;
    private int[][] map = new int[WIDTH][HEIGHT];
    private final int BLOCK_SIZE = 25;
    /*
    0:  empty

    1:  static o
    2:  static i
    3:  static s
    4:  static z
    5:  static l
    6:  static j
    7:  static t

    8:  moving o
    9:  moving i
    10: moving s
    11: moving z
    12: moving l
    13: moving j
    14: moving t
     */

    /**
     * Constructor
     */
    public Tetris(Game game) {
        super(game);
        game.getDisplay().resize(1000, 1000);
    }

    @Override
    public void tick() {
        if (game.getKeyHandler().s) {
            moveDown();
        }
        if (game.getKeyHandler().a) {
            moveLeft();
        }
        if (game.getKeyHandler().d) {
            moveRight();
        }
        if (game.getKeyHandler().e) {
            map[5][5] = 1;
            map[5][4] = 1;
            map[5][3] = 1;
        }
    }

    private void moveLeft(){

    }

    private void moveRight(){

    }

    private void moveDown() {
        if (checkSpace()) {
            for (int i = HEIGHT - 2; i > 0; i--) {
                for (int j = 0; j < WIDTH - 1; j++) {
                    if (map[j][i] <= 7 && map[j][i] !=0) {
                        map[j][i + 1] = map[j][i];
                        map[j][i] = 0;
                    }
                }
            }
        } else {
            makeStatic();
        }
    }

    private boolean checkSpace() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (map[j][i] <= 7 && map[j][i] != 0) {
                    if (i == HEIGHT - 1) {
                        return false;
                    }
                    if (map[j][i + 1] >= 8) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void makeStatic() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (map[j][i] <= 7 && map[j][i] != 0) {
                    map[j][i] += 7;
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        final int SPACING = 2 * BLOCK_SIZE;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (map[j][i] == 0) {
                    g.setColor(Color.BLACK);
                    g.drawRect(SPACING + j * BLOCK_SIZE, SPACING + i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                } else if (map[j][i] == 1) {
                    g.setColor(Color.RED);
                    g.fillRect(SPACING + j * BLOCK_SIZE, SPACING + i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    g.setColor(Color.BLACK);
                    g.drawRect(SPACING + j * BLOCK_SIZE, SPACING + i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                } else if (map[j][i] == 8) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(SPACING + j * BLOCK_SIZE, SPACING + i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    g.setColor(Color.BLACK);
                    g.drawRect(SPACING + j * BLOCK_SIZE, SPACING + i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
    }
}
