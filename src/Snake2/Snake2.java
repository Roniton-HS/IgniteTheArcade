package Snake2;

import Main.Constants;
import Main.Game;
import Worlds.Worlds;

import java.awt.*;
import java.util.Random;

import static Main.Constants.emulogic;

public class Snake2 extends Worlds {
    public static final int BLOCK_SIZE = 32;
    public boolean gameStart = false;

    private boolean startScreen = false;

    public final Player snake = new Player(14 * BLOCK_SIZE, 14 * BLOCK_SIZE, game, true, this);
    public final Player snake2 = new Player(8 * BLOCK_SIZE, 14 * BLOCK_SIZE, game, false, this);
    private Apple apple = new Apple(8 * BLOCK_SIZE, 10 * BLOCK_SIZE);

    /**
     * Constructor
     */
    public Snake2(Game game) {
        super(game, "Snake2");
    }

    @Override
    public void init() {
        game.getDisplay().resize(753, 904);
    }

    @Override
    public void tick() {
        if (gameStart) {
            snake.tick();
            snake2.tick();
            if (snake.start >= 3) {
                checkApple(snake);
                checkApple(snake2);
            }
        } else {
            startScreen = true;
        }
    }

    @Override
    public void render(Graphics g) {
        //background
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, 1000, 1000);
        g.setColor(Constants.GREEN);
        final int BORDER_SIZE = 5;
        g.fillRect(2 * BLOCK_SIZE - BORDER_SIZE, 4 * BLOCK_SIZE - BORDER_SIZE, 19 * BLOCK_SIZE + 2 * BORDER_SIZE, 21 * BLOCK_SIZE + 2 * BORDER_SIZE);
        g.setColor(Color.WHITE);
        g.fillRect(2 * BLOCK_SIZE, 4 * BLOCK_SIZE, 19 * BLOCK_SIZE, 21 * BLOCK_SIZE);

        snake2.render(g);
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
        g.setFont(emulogic.deriveFont(emulogic.getSize() * 60.0F));
        g.setColor(Constants.GREEN);
        g.drawString("Score: " + snake.appleCounter, BLOCK_SIZE, 3 * BLOCK_SIZE);
    }

    /**
     * checks if the snake can eat an apple
     */
    public void checkApple(Player p) {
        if (p.getBounds().intersects(apple.getBounds())) {
            p.appleCollected = true;
            p.appleCounter++;
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
