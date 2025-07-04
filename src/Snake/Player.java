
package Snake;

import Main.Constants;
import Main.Game;
import Worlds.Worlds;

import java.awt.*;
import java.util.ArrayList;

public class Player {
    private int x, y;
    private int tick;
    private int directions;
    public int appleCounter;
    public int start;
    public ArrayList<Rectangle> tiles = new ArrayList<>();
    public boolean appleCollected = false;
    private final Game game;
    private final Snake snakeWorld;

    public Player(int x, int y, Game game, Snake snakeWorld) {
        this.x = x;
        this.y = y;
        this.game = game;
        this.snakeWorld = snakeWorld;
        tiles.add(new Rectangle(x, y, Snake.BLOCK_SIZE, Snake.BLOCK_SIZE));
    }

    public void tick() {
        move();
    }

    public void render(Graphics g) {
        for (int i = 0; i < tiles.size(); i++) {
            Rectangle r = tiles.get(i);
            if (i == tiles.size() - 1) {
                g.setColor(new Color(0, 203, 0));
            } else {
                g.setColor(Constants.GREEN);
            }
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
            // Store current position
            int newX = x;
            int newY = y;

            switch (directions) {
                case 0 -> newY = y - Snake.BLOCK_SIZE;
                case 1 -> newX = x - Snake.BLOCK_SIZE;
                case 2 -> newY = y + Snake.BLOCK_SIZE;
                case 3 -> newX = x + Snake.BLOCK_SIZE;
            }

            // Check if new position would be within bounds
            int mapX = (newX - Snake.mapToScreenX(0)) / Snake.BLOCK_SIZE;
            int mapY = (newY - Snake.mapToScreenY(0)) / Snake.BLOCK_SIZE;

            if (mapX >= 0 && mapX < 19 && mapY >= 0 && mapY < 21) {
                x = newX;
                y = newY;
                tiles.add(new Rectangle(x, y, Snake.BLOCK_SIZE, Snake.BLOCK_SIZE));
                tick = 0;
                waitForStart();
            } else {
                snakeWorld.gameStart = false;
                restart();
            }
        } else {
            tick++;
        }
    }

    public void waitForStart() {
        if (start >= 3) {
            if (!appleCollected) {
                tiles.removeFirst();
            } else {
                appleCollected = false;
            }
            die();
        } else {
            start++;
        }
    }

    public void die() {
        Rectangle head = tiles.getLast();
        // Check collision with self
        for (int i = 0; i < tiles.size(); i++) {
            Rectangle rectangle = tiles.get(i);
            if (head.getBounds().intersects(rectangle.getBounds()) && i != tiles.size() - 1) {
                snakeWorld.gameStart = false;
                restart();
            }
        }
    }

    public void restart() {
        Snake snakeWorld = new Snake(game);
        Worlds.setWorld(snakeWorld);
    }

    public Rectangle getBounds() {
        return tiles.getLast();
    }
}
