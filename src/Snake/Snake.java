package Snake;

import Main.Game;
import Worlds.Worlds;
import Worlds.SnakeWorld;

import java.awt.*;
import java.util.ArrayList;

public class Snake {
    public static boolean gameStart = false;
    int x, y;
    int tick;
    int directions;
    public int appleCounter;
    public int start;
    public static int blockSize = 32;


    public boolean appleCollected = false;
    Game game;

    public Snake(int x, int y, Game game) {
        this.x = x;
        this.y = y;
        this.game = game;
    }

    public ArrayList snake = new ArrayList();

    public void tick() {
        move();
    }

    public void render(Graphics g) {
        g.setColor(Color.green);

        for (int i = 0; i < snake.size(); i++) {
            Rectangle rectangle = (Rectangle) snake.get(i);
            g.fillRect(rectangle.getBounds().x, rectangle.getBounds().y, rectangle.getBounds().width, rectangle.getBounds().height);

        }
    }

    public void move() {

        if ((game.getKeyHandler().w || game.getKeyHandler().up) && directions != 2) {
            directions = 0;
        }
        if ((game.getKeyHandler().a || game.getKeyHandler().left) && directions != 3) {
            directions = 1;
        }
        if ((game.getKeyHandler().s || game.getKeyHandler().down) && directions != 0) {
            directions = 2;
        }
        if ((game.getKeyHandler().d || game.getKeyHandler().right) && directions != 1) {
            directions = 3;
        }

        if (tick > 10) {
            switch (directions) {
                case 0 -> y = y - blockSize;
                case 1 -> x = x - blockSize;
                case 2 -> y = y + blockSize;
                case 3 -> x = x + blockSize;
            }
            snake.add(new Rectangle(this.x, this.y, blockSize, blockSize));
            tick = 0;
            waitForStart();
        } else {
            tick++;
        }
    }

    public void waitForStart() {
        if (start >= 3) {
            if (!appleCollected) {
                snake.remove(0);
            } else {
                appleCollected = false;
            }
            die();
        } else {
            start++;
        }
    }

    public void die() {
        Rectangle head = (Rectangle) snake.get(snake.size() - 1);

        for (int i = 0; i < snake.size(); i++) {
            Rectangle rectangle = (Rectangle) snake.get(i);
            if (head.getBounds().intersects(rectangle.getBounds()) && i != snake.size() - 1) {
                gameStart = false;
                restart();
            }
        }
        Rectangle up = new Rectangle(3 * blockSize, 3 * blockSize, 23 * blockSize, 1);
        Rectangle down = new Rectangle(3 * blockSize, 27 * blockSize, 23 * blockSize, 1);
        Rectangle left = new Rectangle(3 * blockSize, 3 * blockSize, 1, 23 * blockSize);
        Rectangle right = new Rectangle(27 * blockSize, 3 * blockSize, 1, 23 * blockSize);
        if (head.getBounds().intersects(up.getBounds()) ||
                head.getBounds().intersects(down.getBounds()) ||
                head.getBounds().intersects(left.getBounds()) ||
                head.getBounds().intersects(right.getBounds())) {
            gameStart = false;
            restart();
        }
    }

    public void restart() {
        SnakeWorld snakeWorld = new SnakeWorld(game);
        Worlds.setWorld(snakeWorld);
    }

    public Rectangle getBounds() {
        Rectangle head = (Rectangle) snake.get(snake.size() - 1);
        return head;
    }

}
