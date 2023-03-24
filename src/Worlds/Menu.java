package Worlds;

import Input.ImageLoader;
import Input.MouseHandler;
import Main.Game;
import Minesweeper.Minesweeper;
import PacMan.PacMan;
import Snake.SnakeWorld;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Menu extends Worlds {
    Font font;
    int portalAnimation = 0;
    int portalAnimationDelay = 0;
    Rectangle player = new Rectangle(0, 0, 52, 60);

    BufferedImage portal0 = ImageLoader.loadImage("/menu/portal0.png");
    BufferedImage portal1 = ImageLoader.loadImage("/menu/portal1.png");
    BufferedImage portal2 = ImageLoader.loadImage("/menu/portal2.png");
    BufferedImage portal3 = ImageLoader.loadImage("/menu/portal3.png");
    BufferedImage portal4 = ImageLoader.loadImage("/menu/portal4.png");
    BufferedImage portal = portal0;
    BufferedImage head0 = ImageLoader.loadImage("/menu/head0.png");
    BufferedImage head1 = ImageLoader.loadImage("/menu/head1.png");
    BufferedImage head2 = ImageLoader.loadImage("/menu/head2.png");
    BufferedImage head3 = ImageLoader.loadImage("/menu/head3.png");
    BufferedImage legs = ImageLoader.loadImage("/menu/legs.png");
    BufferedImage legsLeft = ImageLoader.loadImage("/menu/legLeft.png");
    BufferedImage legRight = ImageLoader.loadImage("/menu/legRight.png");
    Rectangle pacMan = new Rectangle(50, 200, 64, 64);
    Rectangle minesweeper = new Rectangle(450, 200, 64, 64);
    Rectangle snake = new Rectangle(850, 200, 64, 64);
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

        portalAnimation();
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

    private void portalAnimation() {
        switch (portalAnimation) {
            default -> portal = portal0;
            case 1 -> portal = portal1;
            case 2 -> portal = portal2;
            case 3 -> portal = portal3;
            case 4 -> portal = portal4;
        }
        if (portalAnimation > 3) {
            portalAnimation = 0;
        } else {
            if (portalAnimationDelay > 3) {
                portalAnimation++;
                portalAnimationDelay = 0;
            } else {
                portalAnimationDelay++;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawImage(portal, (int) pacMan.getX(), (int) pacMan.getY(), (int) pacMan.getWidth(), (int) pacMan.getHeight(), null);
        g.drawImage(portal, (int) minesweeper.getX(), (int) minesweeper.getY(), (int) minesweeper.getWidth(), (int) minesweeper.getHeight(), null);
        g.drawImage(portal, (int) snake.getX(), (int) snake.getY(), (int) snake.getWidth(), (int) snake.getHeight(), null);

        renderPlayer(g);

        g.setColor(Color.BLACK);
        g.setFont(font.deriveFont(font.getSize() * 40.0F));
        g.drawString("PacMan", (int) pacMan.getX() - 20, (int) pacMan.getY() - 10);
        g.drawString("Minesweeper", (int) minesweeper.getX() - 75, (int) minesweeper.getY() - 10);
        g.drawString("Snake", (int) snake.getX() - 10, (int) snake.getY() - 10);

        g.setFont(font.deriveFont(font.getSize() * 20.0F));
        for (Rectangle r : levels) {
            if (player.getBounds().intersects(r.getBounds())) {
                g.drawString("[E] to Enter", (int) player.getX() - 20, (int) player.getY() - 5);
            }
        }
    }

    public void renderPlayer(Graphics g) {
        g.drawImage(head0, (int) player.getX(), (int) player.getY(), (int) player.getWidth(), (int) player.getHeight(), null);
        g.drawImage(legs, (int) player.getX()+4, (int) player.getY() + 56, 44, 16, null);
    }
}
