package Snake;

import Main.Constants;
import Main.Game;
import Worlds.Worlds;

import java.awt.*;
import java.util.Random;

import static Main.Constants.emulogic;

public class Snake extends Worlds {
    public static final int BLOCK_SIZE = 32;
    public boolean gameStart = false;

    private boolean startScreen = false;

    private final Player snake = new Player(14 * 32, 14 * 32, game, this);
    private Apple apple = new Apple(8 * 32, 10 * 32);

    /**
     * Constructor
     */
    public Snake(Game game) {
        super(game, "Snake");
    }

    @Override
    public void init() {
        game.getDisplay().resize(753, 904);
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
        //background
        g.setColor(Constants.BACKGROUND);
        g.fillRect(0, 0, 1000, 1000);
        g.setColor(Constants.BUTTON);
        final int BORDER_SIZE = 8;
        g.fillRect(2*BLOCK_SIZE - BORDER_SIZE, 4 * BLOCK_SIZE - BORDER_SIZE, 19 * BLOCK_SIZE + 2*BORDER_SIZE, 21 * BLOCK_SIZE + 2*BORDER_SIZE);
        g.setColor(Color.WHITE);
        g.fillRect(2*BLOCK_SIZE, 4 * BLOCK_SIZE, 19 * BLOCK_SIZE, 21 * BLOCK_SIZE);

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
        g.fillRect(0, 400, 831, 100);
        g.setFont(emulogic.deriveFont(emulogic.getSize() * 30.0F));
        g.setColor(Color.black);
        g.drawString("Press [Space] to start", 30, 460);
        if (game.getKeyHandler().space) {
            gameStart = true;
            startScreen = false;
        }
    }

    /**
     * renders the map
     */
    public void renderGrid(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i = 1; i < 11; i++) {
            g.drawRect(((2 * i)) * BLOCK_SIZE, 4 * BLOCK_SIZE, BLOCK_SIZE, 21 * BLOCK_SIZE);
        }
        for (int i = 2; i < 13; i++) {
            g.drawRect(2 * BLOCK_SIZE, ((2 * i)) * BLOCK_SIZE, 19 * BLOCK_SIZE, BLOCK_SIZE);
        }
        g.drawRect(2 * BLOCK_SIZE, 4 * BLOCK_SIZE, 19 * BLOCK_SIZE, 21 * BLOCK_SIZE);
    }

    /**
     * renders the score
     */
    public void renderAppleCounter(Graphics g) {
        g.setColor(Constants.BUTTON);
        g.fillRect(2*BLOCK_SIZE - 8,10,19 * BLOCK_SIZE + 2*8,115);
        g.setFont(emulogic.deriveFont(emulogic.getSize() * 60.0F));
        g.setColor(Color.white);
        g.drawString("Score: " + snake.appleCounter, 100, 3 * BLOCK_SIZE);
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
            apple.x = (r.nextInt(19 - 2) + 2) * BLOCK_SIZE;
            apple.y = (r.nextInt(21 - 4) + 4) * BLOCK_SIZE;
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
