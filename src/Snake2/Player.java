package Snake2;

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
    public ArrayList<Rectangle> tiles = new ArrayList();
    public boolean appleCollected = false;
    private final Game game;
    private final Snake2 snakeWorld;
    private boolean firstPlayer;

    /**
     * Constructor
     */
    public Player(int x, int y, Game game, boolean firstPlayer, Snake2 snakeWorld) {
        this.x = x;
        this.y = y;
        this.game = game;
        this.firstPlayer = firstPlayer;
        this.snakeWorld = snakeWorld;
    }

    public void tick() {
        move();
    }

    public void render(Graphics g) {
        for (int i = 0; i < tiles.size(); i++) {
            Rectangle r = tiles.get(i);
            if (i == tiles.size() - 1) {
                if(firstPlayer){
                    g.setColor(new Color(0, 203, 0));
                }else {
                    g.setColor(Color.BLUE);
                }

            } else {
                if (firstPlayer) {
                    g.setColor(Constants.GREEN);
                }else {
                    g.setColor(Color.cyan);
                }

            }
            g.fillRect(r.getBounds().x, r.getBounds().y, r.getBounds().width, r.getBounds().height);
        }
    }

    public void move() {
        final int TICK_TIMER = 10;
        if(firstPlayer){
            if ((game.getKeyHandler().w) && directions != 2) {
                directions = 0;
            }
            if ((game.getKeyHandler().a) && directions != 3) {
                directions = 1;
            }
            if ((game.getKeyHandler().s) && directions != 0) {
                directions = 2;
            }
            if ((game.getKeyHandler().d) && directions != 1) {
                directions = 3;
            }
        }else {
            if ((game.getKeyHandler().up) && directions != 2) {
                directions = 0;
            }
            if ((game.getKeyHandler().left) && directions != 3) {
                directions = 1;
            }
            if ((game.getKeyHandler().down) && directions != 0) {
                directions = 2;
            }
            if ((game.getKeyHandler().right) && directions != 1) {
                directions = 3;
            }
        }


        if (tick > TICK_TIMER) {
            switch (directions) {
                case 0 -> y = y - Snake2.BLOCK_SIZE;
                case 1 -> x = x - Snake2.BLOCK_SIZE;
                case 2 -> y = y + Snake2.BLOCK_SIZE;
                case 3 -> x = x + Snake2.BLOCK_SIZE;
            }
            tiles.add(new Rectangle(this.x, this.y, Snake2.BLOCK_SIZE, Snake2.BLOCK_SIZE));
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
        Player other;
        if(firstPlayer){
            other = snakeWorld.snake2;
        }else {
            other = snakeWorld.snake;
        }

        //check self
        for (int i = 0; i < tiles.size(); i++) {
            Rectangle rectangle = tiles.get(i);
            if (head.getBounds().intersects(rectangle.getBounds()) && i != tiles.size() - 1) {
                snakeWorld.gameStart = false;
                if(other == snakeWorld.snake){
                    System.out.println("Arrows won");
                }else {
                    System.out.println("WASD won");
                }
                restart();
            }
        }

        //check other
        for (int i = 0; i < other.tiles.size(); i++) {
            Rectangle rectangle = other.tiles.get(i);
            if (head.getBounds().intersects(rectangle.getBounds()) && i != other.tiles.size() - 1) {
                snakeWorld.gameStart = false;
                if(other == snakeWorld.snake){
                    System.out.println("WASD won");
                }else {
                    System.out.println("Arrows won");
                }
                restart();
            }
        }

        //world bound check
        Rectangle up = new Rectangle(2 * Snake2.BLOCK_SIZE, 3 * Snake2.BLOCK_SIZE, 23 * Snake2.BLOCK_SIZE, 1);
        Rectangle down = new Rectangle(2 * Snake2.BLOCK_SIZE, 25 * Snake2.BLOCK_SIZE, 23 * Snake2.BLOCK_SIZE, 1);
        Rectangle left = new Rectangle(Snake2.BLOCK_SIZE, 3 * Snake2.BLOCK_SIZE, 1, 23 * Snake2.BLOCK_SIZE);
        Rectangle right = new Rectangle(21 * Snake2.BLOCK_SIZE, 3 * Snake2.BLOCK_SIZE, 1, 23 * Snake2.BLOCK_SIZE);
        if (head.getBounds().intersects(up.getBounds()) ||
                head.getBounds().intersects(down.getBounds()) ||
                head.getBounds().intersects(left.getBounds()) ||
                head.getBounds().intersects(right.getBounds())) {
            snakeWorld.gameStart = false;
            if(other == snakeWorld.snake){
                System.out.println("Arrows won");
            }else {
                System.out.println("WASD won");
            }
            restart();
        }
    }

    public void restart() {
        Snake2 snakeWorld = new Snake2(game);
        Worlds.setWorld(snakeWorld);
    }

    public Rectangle getBounds() {
        return tiles.get(tiles.size() - 1);
    }

}
