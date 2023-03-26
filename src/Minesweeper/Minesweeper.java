package Minesweeper;

import Input.ImageLoader;
import Input.MouseHandler;
import Main.Constants;
import Main.Game;
import Worlds.Worlds;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class Minesweeper extends Worlds {
    int blockSize;
    int winCount;
    int mapSize;
    int[][] map;
    int[][] clicked;
    public static Font numFont;
    boolean gameLost = false;
    boolean gameWon = false;
    boolean firstClick = true;

    /**
     * Constructor
     */
    public Minesweeper(Game game, int blockSize, int mapSize) {
        super(game);
        game.getDisplay().resize(967, 990);
        this.blockSize = blockSize;
        this.mapSize = mapSize;
        map = new int[mapSize][mapSize];
        clicked = new int[mapSize][mapSize];
        winCount = mapSize * mapSize - mapSize * mapSize / 10;
        initBombs();
        initNum();
        loadFont();
    }

    @Override
    public void tick() {
        input();
        winCon();
    }

    /**
     * checks if the game is won
     */
    private void winCon() {
        if (winCount <= 0) {
            gameWon = true;
        }
    }

    /**
     * checks mouse and keyboard input
     */
    private void input() {
        int clickX = MouseHandler.getClickX() / blockSize;
        int clickY = MouseHandler.getClickY() / blockSize;

        if (!gameLost && !gameWon) {
            if (clickX < clicked.length && clickY < clicked.length) { //in bounds?

                switch (clicked[clickX][clickY]) {
                    case 0 -> {
                        if (map[clickX][clickY] == 9) { //clicked bomb
                            if (firstClick) {
                                reset();
                                return;
                            }
                            clicked[clickX][clickY] = 1;
                            bomb();
                        } else { //clicked free
                            if (map[clickX][clickY] != 0 && firstClick) {
                                reset();
                                return;
                            }
                            reveal(clickX, clickY);
                        }
                    }
                    case 1 -> {

                    }
                    case 2 -> {
                        //nothing
                    }
                }
                firstClick = false;
            }
            int lClickX = MouseHandler.getLClickX() / blockSize;
            int lClickY = MouseHandler.getLClickY() / blockSize;
            if (lClickX < clicked.length && lClickY < clicked.length && clicked[lClickX][lClickY] != 1) {
                if (clicked[lClickX][lClickY] == 2) {
                    clicked[lClickX][lClickY] = 0;
                } else {
                    clicked[lClickX][lClickY] = 2;
                }
            }

        } else {
            for (int i = 0; i < mapSize; i++) {
                for (int j = 0; j < mapSize; j++) {
                    if (map[j][i] == 9) {
                        clicked[j][i] = 1;
                    }
                }
            }
            if (game.getKeyHandler().enter) {
                reset();
            }

        }
        MouseHandler.reset();
    }

    private void bomb() {
        gameLost = true;
    }

    private void reset() {
        map = new int[mapSize][mapSize];
        clicked = new int[mapSize][mapSize];
        winCount = mapSize * mapSize - mapSize * mapSize / 10;
        initBombs();
        initNum();
        gameLost = false;
        gameWon = false;
        firstClick = true;
    }

    private void reveal(int x, int y) {
        if (map[x][y] != 0 && clicked[x][y] == 0) {
            clicked[x][y] = 1;
            winCount--;
            return;
        }
        if (map[x][y] == 0 && clicked[x][y] == 0) {
            clicked[x][y] = 1;
            winCount--;
            if (x < map.length - 1) { //rechts
                reveal(x + 1, y);
                if (y < map.length - 1) { //unten rechts
                    reveal(x + 1, y + 1);
                }
                if (y > 0) { //oben rechts
                    reveal(x + 1, y - 1);
                }
            }
            if (y < map.length - 1) { //unten
                reveal(x, y + 1);
            }
            if (x > 0) {
                reveal(x - 1, y);
                if (y < map.length - 1) { //unten links
                    reveal(x - 1, y + 1);
                }
                if (y > 0) { //oben links
                    reveal(x - 1, y - 1);
                }
            }
            if (y > 0) {
                reveal(x, y - 1);
            }
        }
    }


    private void initBombs() {
        Random r = new Random();
        int ranX, ranY;
        int bombs = mapSize * mapSize / 6;
        for (int i = 0; i < bombs; i++) {
            ranX = r.nextInt(map.length);
            ranY = r.nextInt(map.length);
            if (map[ranX][ranY] != 9) {
                map[ranX][ranY] = 9;
            } else {
                i--;
            }
        }
    }

    private void initNum() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[j][i] == 9) {
                    continue;
                }
                int bombsNear = 0;
                if (j + 1 < map.length && map[j + 1][i] == 9) {
                    bombsNear++;
                }
                if (j - 1 >= 0 && map[j - 1][i] == 9) {
                    bombsNear++;
                }
                if (i + 1 < map.length && map[j][i + 1] == 9) {
                    bombsNear++;
                }
                if (i - 1 >= 0 && map[j][i - 1] == 9) {
                    bombsNear++;
                }
                if (j + 1 < map.length && i + 1 < map.length && map[j + 1][i + 1] == 9) {
                    bombsNear++;
                }
                if (j - 1 >= 0 && i - 1 >= 0 && map[j - 1][i - 1] == 9) {
                    bombsNear++;
                }
                if (j + 1 < map.length && i - 1 >= 0 && map[j + 1][i - 1] == 9) {
                    bombsNear++;
                }
                if (j - 1 >= 0 && i + 1 < map.length && map[j - 1][i + 1] == 9) {
                    bombsNear++;
                }

                map[j][i] = bombsNear;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(224, 224, 224));
        g.fillRect(0, 0, 2000, 2000);
        renderMap(g);
        renderClicked(g);

        int xPos = 250;
        int yPos = 450;
        if (gameLost) {
            g.setColor(new Color(209, 0, 38));
            g.fillRect(xPos, yPos, 500, 100);
            g.setColor(new Color(0, 0, 0));
            g.setFont(numFont.deriveFont(numFont.getSize() * 60.0F));
            g.drawString("You Lost", xPos + 100, yPos + 60);
            g.setFont(numFont.deriveFont(numFont.getSize() * 20.0F));
            g.drawString("press [enter] to restart", xPos + 105, yPos + 80);
            g.drawRect(xPos, yPos, 500, 100);
        } else if (gameWon) {
            g.setColor(new Color(15, 186, 51));
            g.fillRect(xPos, yPos, 500, 100);
            g.setColor(new Color(0, 0, 0));
            g.setFont(numFont.deriveFont(numFont.getSize() * 60.0F));
            g.drawString("You Won", xPos + 125, yPos + 60);
            g.setFont(numFont.deriveFont(numFont.getSize() * 20.0F));
            g.drawString("press [enter] to restart", xPos + 105, yPos + 80);
            g.drawRect(xPos, yPos, 500, 100);
        }
    }

    private void renderClicked(Graphics g) {
        for (int i = 0; i < clicked.length; i++) {
            for (int j = 0; j < clicked.length; j++) {
                if (clicked[j][i] == 0) {
                    g.setColor(new Color(12, 86, 135));
                    g.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
                } else if (clicked[j][i] == 2) {
                    g.setColor(new Color(12, 86, 135));
                    g.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
                    g.drawImage(flag, j * blockSize, i * blockSize, blockSize, blockSize, null);
                }
                g.setColor(Color.BLACK);
                g.drawRect(j * blockSize, i * blockSize, blockSize, blockSize);
            }
        }
    }

    private final BufferedImage bomb = ImageLoader.loadImage("/minesweeper/bomb.png");
    private final BufferedImage flag = ImageLoader.loadImage("/minesweeper/flag.png");

    private void loadFont() {
        InputStream is = getClass().getResourceAsStream("/fonts/Recursive Bold.ttf");
        try {
            assert is != null;
            numFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void renderMap(Graphics g) {
        int xOff = 15;
        int yOff = 40;
        g.setColor(Color.BLACK);
        g.setFont(numFont.deriveFont(numFont.getSize() * 40.0F));
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                int entry = map[j][i];
                if (entry == 9) {
                    if (gameLost) {
                        g.setColor(new Color(209, 0, 38));
                        g.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
                    }
                    if (gameWon) {
                        g.setColor(new Color(15, 186, 51));
                        g.fillRect(j * blockSize, i * blockSize, blockSize, blockSize);
                    }
                    g.drawImage(bomb, j * blockSize + (int) (blockSize / 1.3 / 5.3),
                            i * blockSize + (int) (blockSize / 1.3 / 5.3),
                            (int) (blockSize / 1.3), (int) (blockSize / 1.3), null);
                } else if (entry != 0) {
                    g.setColor(Color.BLACK);
                    String value = String.valueOf(entry);
                    g.drawString(value, xOff + j * blockSize, yOff + i * blockSize);
                }
            }
        }
    }
}

