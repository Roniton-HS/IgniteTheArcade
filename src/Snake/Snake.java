package Snake;

import Main.Constants;
import Main.Game;
import Worlds.Worlds;

import java.awt.*;
import java.util.Random;

import static Main.Constants.emulogic;

public class Snake extends Worlds {
    public static final int BLOCK_SIZE = 32;
    private static final float MAP_OFFSET_X = 6.0f;
    private static final float MAP_OFFSET_Y = 4.0f;
    private static final int MAP_WIDTH = 19;
    private static final int MAP_HEIGHT = 21;
    private static final int BORDER_SIZE = 8;
    private static final int SCORE_HEIGHT = 115;

    public boolean gameStart = false;
    private boolean startScreen = false;

    private final Player snake = new Player(14 * 32, 14 * 32, game, this);
    private final Apple apple = new Apple(8 * 32, 10 * 32);

    /**
     * Constructor
     */
    public Snake(Game game) {
        super(game, "Snake");
    }

    @Override
    public void init() {

    }

    @Override
    public void tick() {
        if (gameStart) {
            snake.tick();
            if (snake.start >= 3) {
                checkApple();
            }
        } else {
            startScreen = true;
        }
    }

    @Override
    public void render(Graphics g) {
        // Background
        g.setColor(Constants.BACKGROUND_FILTER);
        g.fillRect(0, 0, 1028, 1028);

        // Game border
        g.setColor(Constants.BUTTON_HOVER);
        g.fillRect(mapToScreenX(0) - BORDER_SIZE,
                mapToScreenY(0) - BORDER_SIZE,
                MAP_WIDTH * BLOCK_SIZE + 2 * BORDER_SIZE,
                MAP_HEIGHT * BLOCK_SIZE + 2 * BORDER_SIZE);

        // Game background
        g.setColor(Color.WHITE);
        g.fillRect(mapToScreenX(0),
                mapToScreenY(0),
                MAP_WIDTH * BLOCK_SIZE,
                MAP_HEIGHT * BLOCK_SIZE);

        snake.render(g);
        apple.render(g);
        renderGrid(g);
        renderAppleCounter(g);

        if (startScreen) {
            renderStartScreen(g);
        }
    }

    public void renderStartScreen(Graphics g) {
        g.setColor(new Color(208, 208, 208, 205));
        g.fillRect(0, mapToScreenY(MAP_HEIGHT/2) - 50, 831, 100);
        g.setFont(emulogic.deriveFont(emulogic.getSize() * 30.0F));
        g.setColor(Color.black);
        g.drawString("Press [Space] to start", 30, mapToScreenY(MAP_HEIGHT/2));
        if (game.getKeyHandler().space) {
            gameStart = true;
            startScreen = false;
        }
    }

    public void renderGrid(Graphics g) {
        g.setColor(Color.BLACK);
        // Vertical lines
        for (int i = 0; i < MAP_WIDTH/2 + 1; i++) {  // Changed from i = 1 to i = 0
            g.drawRect(mapToScreenX(i * 2),
                    mapToScreenY(0),
                    BLOCK_SIZE,
                    MAP_HEIGHT * BLOCK_SIZE);
        }
        // Horizontal lines
        for (int i = 0; i < MAP_HEIGHT/2 + 1; i++) {
            g.drawRect(mapToScreenX(0),
                    mapToScreenY(i * 2),
                    MAP_WIDTH * BLOCK_SIZE,
                    BLOCK_SIZE);
        }
        // Border
        g.drawRect(mapToScreenX(0),
                mapToScreenY(0),
                MAP_WIDTH * BLOCK_SIZE,
                MAP_HEIGHT * BLOCK_SIZE);
    }


    public void renderAppleCounter(Graphics g) {
        g.setColor(Constants.BUTTON_HOVER);
        g.fillRect(mapToScreenX(0) - BORDER_SIZE,
                10,
                MAP_WIDTH * BLOCK_SIZE + 2 * BORDER_SIZE,
                SCORE_HEIGHT);
        g.setFont(emulogic.deriveFont(emulogic.getSize() * 60.0F));
        g.setColor(Color.white);
        g.drawString("Score: " + snake.appleCounter,
                mapToScreenX(2),
                mapToScreenY(-1));
    }

    /**
     * checks if the snake can eat an apple
     */
    public void checkApple() {
        if (snake.getBounds().intersects(apple.getBounds())) {
            snake.appleCollected = true;
            snake.appleCounter++;
            updateApple();
        }
    }

    /**
     * spawn a new apple
     */
    public void updateApple() {
        Random r = new Random();
        do {
            int mapX = r.nextInt(MAP_WIDTH - 4) + 2;
            int mapY = r.nextInt(MAP_HEIGHT - 4) + 2;
            apple.x = mapToScreenX(mapX);
            apple.y = mapToScreenY(mapY);
        } while (inSnake(apple));
    }


    /**
     * check if the new apple is inside the snake
     */
    private boolean inSnake(Apple apple) {
        for (Rectangle r : snake.tiles) {
            if (r.getBounds().intersects(apple.getBounds())) {
                return true;
            }
        }
        return false;
    }

    public static int mapToScreenX(int mapX) {
        return (int)((mapX + MAP_OFFSET_X) * BLOCK_SIZE);
    }

    public static int mapToScreenY(int mapY) {
        return (int)((mapY + MAP_OFFSET_Y) * BLOCK_SIZE);
    }

    public static int screenToMapX(int screenX) {
        return (int)((screenX / BLOCK_SIZE) - MAP_OFFSET_X);
    }

    public static int screenToMapY(int screenY) {
        return (int)((screenY / BLOCK_SIZE) - MAP_OFFSET_Y);
    }

}
