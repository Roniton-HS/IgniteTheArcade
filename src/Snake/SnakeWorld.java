package Snake;

import Main.Constants;
import Main.Game;
import Main.TextPrinter;
import Worlds.Worlds;

import java.awt.*;
import java.util.Random;

public class SnakeWorld extends Worlds {
    public static final int BLOCK_SIZE = 32;

    boolean startScreen = false;

    Snake snake = new Snake(14 * 32, 14 * 32, game);
    Apple apple = new Apple(8 * 32, 10 * 32);

    /**
     * Constructor
     */
    public SnakeWorld(Game game) {
        super(game);
        game.getDisplay().resize(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
    }

    @Override
    public void tick() {
        if (Snake.gameStart) {
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
        //background
        g.setColor(Color.gray);
        g.fillRect(4 * BLOCK_SIZE, 4 * BLOCK_SIZE, 23 * BLOCK_SIZE, 23 * BLOCK_SIZE);

        snake.render(g);
        apple.render(g);
        renderGrid(g);
        renderAppleCounter(g);

        if (startScreen) {
            renderStartScreen(g);
        }
    }

    public void renderStartScreen(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(80, 300, 831, 100);
        g.setFont(new Font("Monospaced", Font.BOLD, 50));
        g.setColor(Color.black);
        g.drawString("Press [Space] to start", 140, 350);
        if (game.getKeyHandler().space) {
            TextPrinter.clearText();
            g.clearRect(80, 300, 831, 100);
            Snake.gameStart = true;
            startScreen = false;
        }
    }

    /**
     * renders the map
     */
    public void renderGrid(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i = 2; i < 13; i++) {
            g.drawRect(((2 * i) + 1) * BLOCK_SIZE, 4 * BLOCK_SIZE, BLOCK_SIZE, 23 * BLOCK_SIZE);
        }
        for (int i = 2; i < 13; i++) {
            g.drawRect(4 * BLOCK_SIZE, ((2 * i) + 1) * BLOCK_SIZE, 23 * BLOCK_SIZE, BLOCK_SIZE);
        }
        g.setColor(Color.black);
        g.drawRect(4 * BLOCK_SIZE, 4 * BLOCK_SIZE, 23 * BLOCK_SIZE, 23 * BLOCK_SIZE);
    }

    /**
     * renders the score
     */
    public void renderAppleCounter(Graphics g) {
        g.setFont(new Font("Monospaced", Font.BOLD, 60));
        g.setColor(Color.red);
        g.drawString("Score: " + snake.appleCounter, 4 * BLOCK_SIZE, 3 * BLOCK_SIZE);
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
            apple.x = (r.nextInt(20) + 4) * BLOCK_SIZE;
            apple.y = (r.nextInt(20) + 4) * BLOCK_SIZE;
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
}
