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

    //player
    Rectangle player = new Rectangle(500 - 26, 400, 52, 60);

    //portal sprites
    private final BufferedImage portal0 = ImageLoader.loadImage("/menu/portal0.png");
    private final BufferedImage portal1 = ImageLoader.loadImage("/menu/portal1.png");
    private final BufferedImage portal2 = ImageLoader.loadImage("/menu/portal2.png");
    private final BufferedImage portal3 = ImageLoader.loadImage("/menu/portal3.png");
    private final BufferedImage portal4 = ImageLoader.loadImage("/menu/portal4.png");
    private BufferedImage portal = portal0;

    //head sprites
    private final BufferedImage head0 = ImageLoader.loadImage("/menu/head0.png");
    private final BufferedImage head1 = ImageLoader.loadImage("/menu/head1.png");
    private final BufferedImage head2 = ImageLoader.loadImage("/menu/head2.png");
    private final BufferedImage head3 = ImageLoader.loadImage("/menu/head3.png");
    private BufferedImage head = head0;

    //leg sprites
    private final BufferedImage legsIdle = ImageLoader.loadImage("/menu/legs.png");
    private final BufferedImage legsLeft = ImageLoader.loadImage("/menu/legLeft.png");
    private final BufferedImage legRight = ImageLoader.loadImage("/menu/legRight.png");
    private BufferedImage legs = legsIdle;

    //levels
    Rectangle pacMan = new Rectangle(50, 400, 64, 64);
    Rectangle minesweeper = new Rectangle(450, 400, 64, 64);
    Rectangle snake = new Rectangle(850, 400, 64, 64);
    ArrayList<Rectangle> levels = new ArrayList<>();

    //animations
    int portalAnimation = 0;
    int portalAnimationDelay = 0;
    private int flameAnimation;
    private int flameAnimationDelay;
    private boolean moving = false;
    private int legAnimation;

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
        input();
        playerAnimation();
        portalAnimation();
    }

    /**
     * handles keyboard and mouse input
     */
    public void input() {
        MouseHandler.reset();
        if (game.getKeyHandler().a) {
            player.x = (int) player.getX() - 7;
            moving = true;
        }
        if (game.getKeyHandler().d) {
            player.x = (int) player.getX() + 7;
            moving = true;
        }
        if (!game.getKeyHandler().d && !game.getKeyHandler().a || game.getKeyHandler().a && game.getKeyHandler().d) {
            moving = false;
        }

        if (game.getKeyHandler().e) {
            checkGame();
        }
    }

    /**
     * loads up a game if the player intersects
     */
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

    /**
     * load a font
     */
    private void loadFont() {
        InputStream is = getClass().getResourceAsStream("/fonts/Recursive Bold.ttf");
        try {
            assert is != null;
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ticks portal animations
     */
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
        //render levels
        g.drawImage(portal, (int) pacMan.getX(), (int) pacMan.getY(), (int) pacMan.getWidth(), (int) pacMan.getHeight(), null);
        g.drawImage(portal, (int) minesweeper.getX(), (int) minesweeper.getY(), (int) minesweeper.getWidth(), (int) minesweeper.getHeight(), null);
        g.drawImage(portal, (int) snake.getX(), (int) snake.getY(), (int) snake.getWidth(), (int) snake.getHeight(), null);

        //render level names
        g.setColor(Color.BLACK);
        g.setFont(font.deriveFont(font.getSize() * 40.0F));
        g.drawString("PacMan", (int) pacMan.getX() - 20, (int) pacMan.getY() - 30);
        g.drawString("Minesweeper", (int) minesweeper.getX() - 75, (int) minesweeper.getY() - 30);
        g.drawString("Snake", (int) snake.getX() - 10, (int) snake.getY() - 30);

        //render player
        g.drawImage(head, (int) player.getX(), (int) player.getY(), (int) player.getWidth(), (int) player.getHeight(), null);
        g.drawImage(legs, (int) player.getX() + 4, (int) player.getY() + 56, 44, 16, null);

        //render enter text
        g.setColor(Color.BLACK);
        g.setFont(font.deriveFont(font.getSize() * 20.0F));
        for (Rectangle r : levels) {
            if (player.getBounds().intersects(r.getBounds())) {
                g.drawString("[E] to Enter", (int) player.getX() - 20, (int) player.getY() - 5);
            }
        }
    }

    /**
     * ticks player animation
     */
    public void playerAnimation() {
        //head animations
        switch (flameAnimation) {
            default -> head = head0;
            case 1 -> head = head1;
            case 2 -> head = head2;
            case 3 -> head = head3;
        }
        if (flameAnimation > 3) {
            flameAnimation = 0;
        } else {
            if (flameAnimationDelay > 5) {
                flameAnimation++;
                flameAnimationDelay = 0;
            } else {
                flameAnimationDelay++;
            }
        }

        //leg animations
        if (moving) {
            switch (legAnimation) {
                case 0, 1, 2, 3, 4 -> legs = legRight;
                case 5, 6, 7, 8, 9 -> legs = legsLeft;
            }
            if (legAnimation > 9) {
                legAnimation = 0;
            } else {
                legAnimation++;
            }
        } else {
            legs = legsIdle;
        }
    }
}
