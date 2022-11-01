package Snake;

import Input.ImageLoader;
import Main.Game;
import Main.TextPrinter;
import Snake.Snake;
import Snake.Apple;
import Worlds.Worlds;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SnakeWorld extends Worlds {

    boolean startScreen = false;

    private BufferedImage snakeBody = ImageLoader.loadImage("/icon.png");

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
            if (snake.start >= 3) {
                eatApple();
            }
        } else {
            startScreen = true;
        }
    }

    @Override
    public void render(Graphics g) {

        renderFrame(g);


        g.setColor(Color.gray);
        g.fillRect(4 * snake.blockSize, 4 * snake.blockSize, 23 * snake.blockSize, 23 * snake.blockSize);

        g.setColor(Color.black);
        g.drawRect(4 * snake.blockSize, 4 * snake.blockSize, 23 * snake.blockSize, 23 * snake.blockSize);

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

        for (int i = 2; i < 13; i++) {
            g.drawRect(((2 * i) + 1) * snake.blockSize, 4 * snake.blockSize, snake.blockSize, 23 * snake.blockSize);
        }
        for (int i = 2; i < 13; i++) {
            g.drawRect(4 * snake.blockSize, ((2 * i) + 1) * snake.blockSize, 23 * snake.blockSize, snake.blockSize);
        }
    }

    public void renderAppleCounter(Graphics g) {
        g.setFont(new Font("Monospaced", Font.BOLD, 60));
        g.setColor(Color.red);
        g.drawString("Score: " + snake.appleCounter, 4 * snake.blockSize, 3 * snake.blockSize);

    }

    public void renderFrame(Graphics g) {
        for (int i = 2; i < 13; i++) {
            g.drawImage(snakeBody, 0, 2 * i * snake.blockSize, 128,128,null);

        }
    }

    public void eatApple() {
        if (snake.getBounds().intersects(apple.getBounds())) {
            snake.appleCollected = true;
            snake.appleCounter++;
            updateApple();
        }
    }

    public void updateApple() {
        Random randomX = new Random();
        apple.xApple = (randomX.nextInt(20) + 4) * snake.blockSize;
        Random randomY = new Random();
        apple.yApple = (randomY.nextInt(20) + 4) * snake.blockSize;
        for (int i = 0; i < snake.snake.size(); i++) {
            Rectangle rectangle = (Rectangle) snake.snake.get(i);
            if (rectangle.getBounds().intersects(apple.getBounds())) {
                Random randomX2 = new Random();
                apple.xApple = (randomX2.nextInt(20) + 4) * snake.blockSize;
                Random randomY2 = new Random();
                apple.yApple = (randomY2.nextInt(20) + 4) * snake.blockSize;
            }
        }
    }

}
