package Worlds;

import Input.MouseHandler;
import Main.Game;
import Minesweeper.Minesweeper;
import PacMan.PacMan;
import Snake.SnakeWorld;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Menu extends Worlds {
    Font font;
    Rectangle player = new Rectangle(0, 0, 50, 50);
    Rectangle pacMan = new Rectangle(50, 200, 100, 100);
    Rectangle minesweeper = new Rectangle(450, 200, 100, 100);
    Rectangle snake = new Rectangle(850, 200, 100, 100);
    ArrayList<Rectangle> levels = new ArrayList<>();

    /**
     * Constructor
     */
    public Menu(Game game) {
        super(game);
        game.getDisplay().resize(1000, 1000);
        loadFont();
        levels.add(pacMan);
        levels.add(minesweeper);
        levels.add(snake);
    }

    @Override
    public void tick() {
        MouseHandler.reset();

        if (game.getKeyHandler().w) {
            player.y = (int) player.getY() - 2;
        }
        if (game.getKeyHandler().a) {
            player.x = (int) player.getX() - 2;
        }
        if (game.getKeyHandler().s) {
            player.y = (int) player.getY() + 2;
        }
        if (game.getKeyHandler().d) {
            player.x = (int) player.getX() + 2;
        }

        if (game.getKeyHandler().e) {
            checkGame();
        }
    }

    private void checkGame() {
        if (player.getBounds().intersects(pacMan.getBounds())) {
            PacMan pacMan = new PacMan(game);
            Worlds.setWorld(pacMan);
        } else if (player.getBounds().intersects(minesweeper.getBounds())) {
            Minesweeper mineSweeper = new Minesweeper(game, 50, 19);
            Worlds.setWorld(mineSweeper);
        } else if (player.getBounds().intersects(snake.getBounds())) {
            SnakeWorld snakeWorld = new SnakeWorld(game);
            Worlds.setWorld(snakeWorld);
        }
    }

    private void loadFont() {
        InputStream is = getClass().getResourceAsStream("/fonts/Recursive Bold.ttf");
        try {
            assert is != null;
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(font.deriveFont(font.getSize() * 20.0F));
        for (Rectangle r : levels) {
            if(player.getBounds().intersects(r.getBounds())){
                g.drawString("[E] to Enter", (int) player.getX()-20, (int) player.getY()-5);
            }
        }

        g.drawRect((int) pacMan.getX(), (int) pacMan.getY(), (int) pacMan.getWidth(), (int) pacMan.getHeight());
        g.drawRect((int) minesweeper.getX(), (int) minesweeper.getY(), (int) minesweeper.getWidth(), (int) minesweeper.getHeight());
        g.drawRect((int) snake.getX(), (int) snake.getY(), (int) snake.getWidth(), (int) snake.getHeight());

        g.setColor(Color.RED);
        g.fillRect((int) player.getX(), (int) player.getY(), (int) player.getWidth(), (int) player.getHeight());

        g.setColor(Color.BLACK);
        g.setFont(font.deriveFont(font.getSize() * 40.0F));
        g.drawString("PacMan", (int) pacMan.getX() - 20, (int) pacMan.getY() - 10);
        g.drawString("Minesweeper", (int) minesweeper.getX() - 75, (int) minesweeper.getY() - 10);
        g.drawString("Snake", (int) snake.getX() - 10, (int) snake.getY() - 10);
    }
}
