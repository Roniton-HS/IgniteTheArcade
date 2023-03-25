package Snake;

import Main.Game;
import Worlds.Worlds;

import java.awt.*;
import java.util.ArrayList;

public class Snake {
    public static boolean gameStart = false;
    public int x, y;
    int tick;
    int directions;
    public int appleCounter;
    public int start;
    public ArrayList<Rectangle> tiles = new ArrayList();
    public boolean appleCollected = false;
    Game game;

    /**
     * Constructor
     */
    public Snake(int x, int y, Game game) {
        this.x = x;
        this.y = y;
        this.game = game;
    }

    public void tick() {
        move();
    }

    public void render(Graphics g) {
        g.setColor(Color.green);
        for (Rectangle r : tiles) {
            g.fillRect(r.getBounds().x, r.getBounds().y, r.getBounds().width, r.getBounds().height);
        }
    }

    public void move() {
        final int TICK_TIMER = 10;
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

        if (tick > TICK_TIMER) {
            switch (directions) {
                case 0 -> y = y - SnakeWorld.BLOCK_SIZE;
                case 1 -> x = x - SnakeWorld.BLOCK_SIZE;
                case 2 -> y = y + SnakeWorld.BLOCK_SIZE;
                case 3 -> x = x + SnakeWorld.BLOCK_SIZE;
            }
            tiles.add(new Rectangle(this.x, this.y, SnakeWorld.BLOCK_SIZE, SnakeWorld.BLOCK_SIZE));
            tick = 0;
            waitForStart();
        } else {
            tick++;
        }
    }

    public void waitForStart() {
        if (start >= 3) {
            if (!appleCollected) {
                tiles.remove(0);
            } else {
                appleCollected = false;
            }
            die();
        } else {
            start++;
        }
    }

    public void die() {
        Rectangle head = tiles.get(tiles.size() - 1);
        for (int i = 0; i < tiles.size(); i++) {
            Rectangle rectangle = tiles.get(i);
            if (head.getBounds().intersects(rectangle.getBounds()) && i != tiles.size() - 1) {
                gameStart = false;
                restart();
            }
        }
        Rectangle up = new Rectangle(3 * SnakeWorld.BLOCK_SIZE, 3 * SnakeWorld.BLOCK_SIZE, 23 * SnakeWorld.BLOCK_SIZE, 1);
        Rectangle down = new Rectangle(3 * SnakeWorld.BLOCK_SIZE, 27 * SnakeWorld.BLOCK_SIZE, 23 * SnakeWorld.BLOCK_SIZE, 1);
        Rectangle left = new Rectangle(3 * SnakeWorld.BLOCK_SIZE, 3 * SnakeWorld.BLOCK_SIZE, 1, 23 * SnakeWorld.BLOCK_SIZE);
        Rectangle right = new Rectangle(27 * SnakeWorld.BLOCK_SIZE, 3 * SnakeWorld.BLOCK_SIZE, 1, 23 * SnakeWorld.BLOCK_SIZE);
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
        return tiles.get(tiles.size() - 1);
    }

}
