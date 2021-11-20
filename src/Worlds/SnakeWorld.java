package Worlds;

import Main.Game;
import Main.TextPrinter;
import Snake.Snake;
import Snake.Apple;

import java.awt.*;
import java.util.Random;

public class SnakeWorld extends Worlds {

    boolean startScreen = false;

    Snake snake = new Snake(14 * 32, 14 * 32, game);
    Apple apple = new Apple(8 * 32, 10 * 32);

    /**
     * Constructor
     *
     * @param game
     */
    public SnakeWorld(Game game) {
        super(game);


    }

    @Override


    public void tick() {
        if (Snake.gameStart) {
            snake.tick();
            if (snake.start >= 3){
                eatApple();
            }
        } else {
            startScreen = true;
        }
    }

    @Override
    public void render(Graphics g) {


        g.setColor(Color.DARK_GRAY);
        g.fillRect(64, 64, 27 * snake.blockSize, 27 * snake.blockSize);

        g.setColor(Color.white);
        g.drawRect(64, 64, 27 * snake.blockSize, 27 * snake.blockSize);

        snake.render(g);

        renderGrid(g);

        if (startScreen) {
            renderStartScreen(g);
        } else {
            apple.render(g);
        }

    }

    public void renderStartScreen(Graphics g) {
        g.setColor(Color.gray);

        g.fillRect(80, 300, 831, 100);
        g.setFont(new Font("Monospaced", Font.BOLD, 50));
        g.setColor(Color.black);
        g.drawString("Press Space to start", 140, 350);
        if (game.getKeyHandler().space) {
            TextPrinter.clearText();
            g.clearRect(80, 300, 831, 100);
            Snake.gameStart = true;
            startScreen = false;
        }
    }

    public void renderGrid(Graphics g) {
        g.setColor(Color.BLACK);

        for(int i = 1; i < 14; i++) {
            g.drawRect(((2*i)+1)*snake.blockSize,2*snake.blockSize,snake.blockSize,27*snake.blockSize);
        }
        for(int i = 1; i < 14; i++) {
            g.drawRect(2*snake.blockSize,((2*i)+1)*snake.blockSize,27*snake.blockSize,snake.blockSize);
        }
    }

    public void eatApple() {
        if (snake.getBounds().intersects(apple.getBounds())) {
            snake.appleCollected = true;
            updateApple();
        }
    }

    public void updateApple() {
        Random randomX = new Random();
        apple.xApple = (randomX.nextInt(24) + 2) * snake.blockSize;
        Random randomY = new Random();
        apple.yApple = (randomY.nextInt(24) + 2) * snake.blockSize;
    }

}
