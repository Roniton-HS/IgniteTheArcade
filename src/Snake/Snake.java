package Snake;

import Main.Game;
import Worlds.Worlds;
import Worlds.World1;

import java.awt.*;
import java.util.ArrayList;

public class Snake {
    public static boolean gameStart = false;
    int x, y;
    int tick;
    int directions;
    int start;

    boolean appleCollected = false;
    Game game;

    public Snake(int x, int y, Game game) {
        this.x = x;
        this.y = y;
        this.game = game;
    }


    ArrayList snake = new ArrayList();

    public void tick() {
        move();
    }

    public void render(Graphics g) {
        for (int i = 0; i < snake.size(); i++) {
            Rectangle rectangle = (Rectangle) snake.get(i);
            g.fillRect(rectangle.getBounds().x, rectangle.getBounds().y, rectangle.getBounds().width, rectangle.getBounds().height);
            g.setColor(Color.green);
        }
    }

    public void move() {

        if (game.getKeyHandler().w) {
            directions = 0;
        }
        if (game.getKeyHandler().a) {
            directions = 1;
        }
        if (game.getKeyHandler().s) {
            directions = 2;
        }
        if (game.getKeyHandler().d) {
            directions = 3;
        }

        if (game.getKeyHandler().e) {
            appleCollected = true;
        }

        if (tick > 10) {
            switch (directions) {
                case 0 -> y = y - 32;
                case 1 -> x = x - 32;
                case 2 -> y = y + 32;
                case 3 -> x = x + 32;
            }
            snake.add(new Rectangle(this.x, this.y, 32, 32));
            tick = 0;
            waitForStart();
        } else {
            tick++;
        }
    }

    public void waitForStart() {
        if (start > 3) {
            if (!appleCollected) {
                snake.remove(0);
            } else {
                appleCollected = false;
            }
            die();
        }

        start++;
    }

    public void die() {
        Rectangle head = (Rectangle) snake.get(snake.size() - 1);

        for (int i = 0; i < snake.size(); i++) {
            Rectangle rectangle = (Rectangle) snake.get(i);
            if (head.getBounds().intersects(rectangle.getBounds()) && i != snake.size() - 1) {
                gameStart = false;
            }
        }
        Rectangle up = new Rectangle(32, 32, 863, 1);
        Rectangle down = new Rectangle(32, 959, 863, 1);
        Rectangle left = new Rectangle(32, 32, 1, 863);
        Rectangle right = new Rectangle(959, 32, 1, 863);
        if (head.getBounds().intersects(up.getBounds()) ||
                head.getBounds().intersects(down.getBounds()) ||
                head.getBounds().intersects(left.getBounds()) ||
                head.getBounds().intersects(right.getBounds())) {
            gameStart = false;
        }
    }

}
