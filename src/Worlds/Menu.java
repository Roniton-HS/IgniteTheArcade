package Worlds;

import Arkanoid.Arkanoid;
import Chess.ChessWorld;
import Input.ImageLoader;
import Input.MouseHandler;
import Main.Constants;
import Main.Game;
import Main.GameCamera;
import Minesweeper.Minesweeper;
import PacMan.PacMan;
import Snake.SnakeWorld;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Menu extends Worlds {

    private Font font;
    private GameCamera gameCamera = new GameCamera();

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

    //design sprites
    private final BufferedImage arcadeMachine = ImageLoader.loadImage("/menu/arcadeMachine.png");

    //player
    final int PLAYER_START_X = 474;
    final int PLAYER_START_Y = 410;
    final int PLAYER_SIZE = 4;
    Rectangle player = new Rectangle(PLAYER_START_X, PLAYER_START_Y, head.getWidth() * PLAYER_SIZE, head.getHeight() * PLAYER_SIZE);

    //levels
    private final int PACMAN_X = 0;
    private final int MINESWEEPER_X = 350;
    private final int SNAKE_X = 700;
    private final int LEVEL_Y = 340;
    private final int LEVEL_SIZE = 4;
    Rectangle pacMan = new Rectangle(PACMAN_X, LEVEL_Y, portal.getWidth() * LEVEL_SIZE, portal.getHeight() * LEVEL_SIZE);
    Rectangle minesweeper = new Rectangle(MINESWEEPER_X, LEVEL_Y, portal.getWidth() * LEVEL_SIZE, portal.getHeight() * LEVEL_SIZE);
    Rectangle snake = new Rectangle(SNAKE_X, LEVEL_Y, portal.getWidth() * LEVEL_SIZE, portal.getHeight() * LEVEL_SIZE);
    ArrayList<Rectangle> levels = new ArrayList<>();

    //animations
    private int portalAnimation = 0;
    private int portalAnimationDelay = 0;
    private int flameAnimation;
    private int flameAnimationDelay;
    private boolean moving = false;
    private int legAnimation;

    /**
     * Constructor
     */
    public Menu(Game game) {
        super(game);
        game.getDisplay().resize(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT - 200);
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
        //movement
        final int PLAYER_SPEED = 7;
        if (game.getKeyHandler().a) {
            player.x = (int) player.getX() - PLAYER_SPEED;
            gameCamera.move(-PLAYER_SPEED, 0);
            moving = true;
        }
        if (game.getKeyHandler().d) {
            player.x = (int) player.getX() + PLAYER_SPEED;
            gameCamera.move(PLAYER_SPEED, 0);
            moving = true;
        }
        if (!game.getKeyHandler().d && !game.getKeyHandler().a || game.getKeyHandler().a && game.getKeyHandler().d) {
            moving = false;
        }

        //actions
        if (game.getKeyHandler().e) {
            checkGame();
        }

        if (game.getKeyHandler().c) {
            ChessWorld chessWorld = new ChessWorld(game);
            Worlds.setWorld(chessWorld);
        }

        if (game.getKeyHandler().b) {
            Arkanoid arkanoid = new Arkanoid(game);
            Worlds.setWorld(arkanoid);
        }

        MouseHandler.reset();
    }

    /**
     * loads up a game if the player intersects
     */
    private void checkGame() {
        final int MINESWEEPER_BLOCK_SIZE = 50;
        final int MINESWEEPER_MAP_SIZE = 19;
        if (player.getBounds().intersects(pacMan.getBounds())) {
            PacMan pacMan = new PacMan(game);
            Worlds.setWorld(pacMan);
        } else if (player.getBounds().intersects(minesweeper.getBounds())) {
            Minesweeper mineSweeper = new Minesweeper(game, MINESWEEPER_BLOCK_SIZE, MINESWEEPER_MAP_SIZE);
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
        final int ANIMATION_SLOW = 3;
        final int SPRITES = 4;
        switch (portalAnimation) {
            default -> portal = portal0;
            case 1 -> portal = portal1;
            case 2 -> portal = portal2;
            case 3 -> portal = portal3;
            case 4 -> portal = portal4;
        }
        if (portalAnimation > SPRITES) {
            portalAnimation = 0;
        } else {
            if (portalAnimationDelay > ANIMATION_SLOW) {
                portalAnimation++;
                portalAnimationDelay = 0;
            } else {
                portalAnimationDelay++;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        final int off = gameCamera.getXOffset();
        //background
        g.setColor(new Color(27, 171, 181));
        g.fillRect(0, 0, 1000, 1000);
        g.setColor(Color.gray);
        g.fillRect(0, 464, 1000, 30);
        g.setColor(new Color(74, 74, 74));
        g.fillRect(0, 464 + 30, 1000, 500);

        //render arcade
        final int ARCADE_OFFSET_X = -88;
        final int ARCADE_OFFSET_Y = -2;
        g.drawImage(arcadeMachine, minesweeper.x + ARCADE_OFFSET_X - off, 30 + ARCADE_OFFSET_Y, arcadeMachine.getWidth() * 4, arcadeMachine.getHeight() * 4, null);
        g.drawImage(arcadeMachine, pacMan.x + ARCADE_OFFSET_X - off, 30 + ARCADE_OFFSET_Y, arcadeMachine.getWidth() * 4, arcadeMachine.getHeight() * 4, null);
        g.drawImage(arcadeMachine, snake.x + ARCADE_OFFSET_X - off, 30 + ARCADE_OFFSET_Y, arcadeMachine.getWidth() * 4, arcadeMachine.getHeight() * 4, null);

        //render levels
        g.drawImage(portal, pacMan.x - off, pacMan.y, pacMan.width, pacMan.height, null);
        g.drawImage(portal, minesweeper.x - off, minesweeper.y, minesweeper.width, minesweeper.height, null);
        g.drawImage(portal, snake.x - off, snake.y, snake.width, snake.height, null);

        //render level names
        final float LEVEL_TEXT_SIZE = 30.F;
        final int LEVEL_TEXT_HEIGHT = -275;
        final int PACMAN_TEXT_OFFSET = 95;
        final int MINESWEEPER_TEXT_OFFSET = 53;
        final int SNAKE_TEXT_OFFSET = 105;
        g.setColor(Color.WHITE);
        g.setFont(font.deriveFont(font.getSize() * LEVEL_TEXT_SIZE));
        g.drawString("PacMan", pacMan.x + PACMAN_TEXT_OFFSET + ARCADE_OFFSET_X - off, pacMan.y + LEVEL_TEXT_HEIGHT);
        g.drawString("Minesweeper", minesweeper.x + MINESWEEPER_TEXT_OFFSET + ARCADE_OFFSET_X - off, minesweeper.y + LEVEL_TEXT_HEIGHT);
        g.drawString("Snake", snake.x + SNAKE_TEXT_OFFSET + ARCADE_OFFSET_X - off, snake.y + LEVEL_TEXT_HEIGHT);

        //render player
        final int LEG_OFFSET_X = 4;
        final int LEG_OFFSET_Y = 56;
        g.drawImage(head, player.x - off, player.y, player.width, player.height, null);
        g.drawImage(legs, player.x + LEG_OFFSET_X - off, player.y + LEG_OFFSET_Y, legs.getWidth() * PLAYER_SIZE, legs.getHeight() * PLAYER_SIZE, null);

        //render enter text
        final float ENTER_TEXT_SIZE = 20.F;
        final int ENTER_TEXT_OFFSET_X = -20;
        final int ENTER_TEXT_OFFSET_Y = -5;
        g.setColor(Color.WHITE);
        g.setFont(font.deriveFont(font.getSize() * ENTER_TEXT_SIZE));
        for (Rectangle r : levels) {
            if (player.getBounds().intersects(r.getBounds())) {
                g.drawString("[E] to Enter", player.x + ENTER_TEXT_OFFSET_X - off, player.y + ENTER_TEXT_OFFSET_Y);
            }
        }
    }

    /**
     * ticks player animation
     */
    public void playerAnimation() {
        final int HEAD_ANIMATION_SLOW = 5;
        final int LEG_ANIMATION_SLOW = 9;
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
            if (flameAnimationDelay > HEAD_ANIMATION_SLOW) {
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
            if (legAnimation > LEG_ANIMATION_SLOW) {
                legAnimation = 0;
            } else {
                legAnimation++;
            }
        } else {
            legs = legsIdle;
        }
    }
}
