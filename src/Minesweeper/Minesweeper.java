package Minesweeper;

import Input.ImageLoader;
import Input.MouseHandler;
import Main.Constants;
import Main.Game;
import Worlds.Worlds;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static Main.Constants.recursiveBold;

public class Minesweeper extends Worlds {
    private static final float MAP_OFFSET_X = 1.75f;
    private static final float MAP_OFFSET_Y = 1.75f;
    private static final int GAME_OVER_WIDTH = 500;
    private static final int GAME_OVER_HEIGHT = 100;
    private static final int LARGE_FONT_SIZE = 60;
    private static final int SMALL_FONT_SIZE = 20;

    int blockSize;
    int winCount;
    int mapSize;
    int[][] map;
    int[][] clicked;
    boolean gameLost = false;
    boolean gameWon = false;
    boolean firstClick = true;
    final int BOMBS;

    /**
     * Constructor
     */
    public Minesweeper(Game game, int blockSize, int mapSize) {
        super(game, "Minesweeper");
        this.blockSize = blockSize;
        this.mapSize = mapSize;
        BOMBS = mapSize * mapSize / 6;
    }

    @Override
    public void init() {
        map = new int[mapSize][mapSize];
        clicked = new int[mapSize][mapSize];
        winCount = mapSize * mapSize - BOMBS;
        initBombs();
        initNum();
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
        // right-click
        int clickX = screenToMapX(game.getMouseHandler().getClickX());
        int clickY = screenToMapY(game.getMouseHandler().getClickY());
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

            // left-click
            int lClickX = screenToMapX(game.getMouseHandler().getLClickX());
            int lClickY = screenToMapY(game.getMouseHandler().getLClickY());
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
        game.getMouseHandler().reset();
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
        for (int i = 0; i < BOMBS; i++) {
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
        final int BORDER_SIZE = 5;
        g.setColor(Constants.BACKGROUND_FILTER);
        g.fillRect(0, 0, 1028, 1028);
        g.setColor(Color.BLACK);
        g.fillRect(mapToScreenX(0) - BORDER_SIZE,
                mapToScreenY(0) - BORDER_SIZE,
                mapSize * blockSize + 2 * BORDER_SIZE,
                mapSize * blockSize + 2 * BORDER_SIZE);
        g.setColor(Color.WHITE);
        g.fillRect(mapToScreenX(0),
                mapToScreenY(0),
                mapSize * blockSize,
                mapSize * blockSize);

        renderMap(g);
        renderClicked(g);
        renderGameOverMessage(g);
    }

    private void renderGameOverMessage(Graphics g) {
        if (!gameLost && !gameWon) {
            return;
        }

        // Center the message box relative to the map
        int messageX = mapToScreenX(0) + (mapSize * blockSize - GAME_OVER_WIDTH) / 2;
        int messageY = mapToScreenY(0) + (mapSize * blockSize - GAME_OVER_HEIGHT) / 2;

        // Draw the background box
        g.setColor(gameLost ? new Color(209, 0, 38) : new Color(15, 186, 51));
        g.fillRect(messageX, messageY, GAME_OVER_WIDTH, GAME_OVER_HEIGHT);

        // Draw the border
        g.setColor(Color.BLACK);
        g.drawRect(messageX, messageY, GAME_OVER_WIDTH, GAME_OVER_HEIGHT);

        // Main message
        g.setFont(recursiveBold.deriveFont((float) LARGE_FONT_SIZE));
        String mainMessage = gameLost ? "You Lost" : "You Won";
        int mainMessageX = messageX + (GAME_OVER_WIDTH - g.getFontMetrics().stringWidth(mainMessage)) / 2;
        g.drawString(mainMessage, mainMessageX, messageY + 60);

        // Restart instruction
        g.setFont(recursiveBold.deriveFont((float) SMALL_FONT_SIZE));
        String restartMsg = "press [enter] to restart";
        int restartMsgX = messageX + (GAME_OVER_WIDTH - g.getFontMetrics().stringWidth(restartMsg)) / 2;
        g.drawString(restartMsg, restartMsgX, messageY + 80);
    }


    private void renderClicked(Graphics g) {
        for (int i = 0; i < clicked.length; i++) {
            for (int j = 0; j < clicked.length; j++) {
                if (clicked[j][i] == 0 || clicked[j][i] == 2) {
                    g.setColor(Constants.BUTTON_HOVER);
                    g.fillRect(mapToScreenX(j), mapToScreenY(i), blockSize, blockSize);

                    if (clicked[j][i] == 2) {
                        g.drawImage(flag, mapToScreenX(j), mapToScreenY(i),
                                blockSize, blockSize, null);
                    }
                }
                g.setColor(Color.BLACK);
                g.drawRect(mapToScreenX(j), mapToScreenY(i), blockSize, blockSize);
            }
        }
    }

    private final BufferedImage bomb = ImageLoader.loadImage("/minesweeperRes/bomb.png");
    private final BufferedImage flag = ImageLoader.loadImage("/minesweeperRes/flag.png");

    private void renderMap(Graphics g) {
        int xOff = 15;
        int yOff = 40;
        g.setColor(Color.BLACK);
        g.setFont(recursiveBold.deriveFont(recursiveBold.getSize() * 40.0F));
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                int entry = map[j][i];
                if (entry == 9) {
                    if (gameLost) {
                        g.setColor(new Color(209, 0, 38));
                        g.fillRect(mapToScreenX(j), mapToScreenY(i), blockSize, blockSize);
                    }
                    if (gameWon) {
                        g.setColor(new Color(15, 186, 51));
                        g.fillRect(mapToScreenX(j), mapToScreenY(i), blockSize, blockSize);
                    }
                    g.drawImage(bomb,
                            mapToScreenX(j) + (int)(blockSize / 1.3 / 5.3),
                            mapToScreenY(i) + (int)(blockSize / 1.3 / 5.3),
                            (int)(blockSize / 1.3),
                            (int)(blockSize / 1.3),
                            null);
                } else if (entry != 0) {
                    g.setColor(Color.BLACK);
                    String value = String.valueOf(entry);
                    g.drawString(value,
                            xOff + mapToScreenX(j),
                            yOff + mapToScreenY(i));
                }
            }
        }
    }

    private int screenToMapX(int screenX) {
        return (screenX - (int)(MAP_OFFSET_X * blockSize)) / blockSize;
    }

    private int screenToMapY(int screenY) {
        return (int) ((screenY - MAP_OFFSET_Y * blockSize) / blockSize);
    }

    private int mapToScreenX(int mapX) {
        return (int)(mapX * blockSize + MAP_OFFSET_X * blockSize);
    }

    private int mapToScreenY(int mapY) {
        return (int) (mapY * blockSize + MAP_OFFSET_Y * blockSize);
    }

}

