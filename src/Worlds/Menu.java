package Worlds;

import Arkanoid.Arkanoid;
import Chess.Chess;
import Input.ImageLoader;
import Input.MouseHandler;
import Main.Constants;
import Main.Game;
import Main.GameCamera;
import Minesweeper.Minesweeper;
import PacMan.PacMan;
import Pong.Pong;
import Snake.Snake;
import Snake2.Snake2;
import Tetris.Tetris;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Main.Constants.recursiveBold;

@SuppressWarnings({"PointlessArithmeticExpression", "UnaryPlus"})
public class Menu extends Worlds {

    private GameCamera gameCamera = new GameCamera();
    private boolean moving = false;

    //player
    private BufferedImage head = ImageLoader.loadImage("/menuRes/head0.png");
    final int PLAYER_START_X = 474;
    final int PLAYER_START_Y = 410;
    final int PLAYER_SIZE = 4;
    Rectangle player = new Rectangle(PLAYER_START_X, PLAYER_START_Y, head.getWidth() * PLAYER_SIZE, head.getHeight() * PLAYER_SIZE);

    //portals
    private BufferedImage portal = ImageLoader.loadImage("/menuRes/portal0.png");
    private final int LEVEL_OFFSET = 350;
    private final int LEVEL_Y = 340;
    private final int LEVEL_SIZE = 4;
    private final int PORTAL_WIDTH = LEVEL_SIZE * portal.getWidth();
    private final int PORTAL_HEIGHT = LEVEL_SIZE * portal.getHeight();


    ArrayList<Rectangle> levels = new ArrayList<>();
    Rectangle pong =      new Rectangle(-3 * LEVEL_OFFSET, LEVEL_Y, PORTAL_WIDTH, PORTAL_HEIGHT);
    Rectangle tetris =      new Rectangle(-2 * LEVEL_OFFSET, LEVEL_Y, PORTAL_WIDTH, PORTAL_HEIGHT);
    Rectangle chess =       new Rectangle(-1 * LEVEL_OFFSET, LEVEL_Y, PORTAL_WIDTH, PORTAL_HEIGHT);
    Rectangle pacMan =      new Rectangle(+0 * LEVEL_OFFSET, LEVEL_Y, PORTAL_WIDTH, PORTAL_HEIGHT);
    Rectangle minesweeper = new Rectangle(+1 * LEVEL_OFFSET, LEVEL_Y, PORTAL_WIDTH, PORTAL_HEIGHT);
    Rectangle snake =       new Rectangle(+2 * LEVEL_OFFSET, LEVEL_Y, PORTAL_WIDTH, PORTAL_HEIGHT);
    Rectangle arkanoid =    new Rectangle(+3 * LEVEL_OFFSET, LEVEL_Y, PORTAL_WIDTH, PORTAL_HEIGHT);

    /**
     * Constructor
     */
    public Menu(Game game) {
        super(game);
        game.getDisplay().resize(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT - 200);
        levels.add(pacMan);
        levels.add(minesweeper);
        levels.add(snake);
        levels.add(arkanoid);
        levels.add(chess);
        levels.add(tetris);
        levels.add(pong);
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
            player.x -= PLAYER_SPEED;
            gameCamera.move(-PLAYER_SPEED, 0);
            moving = true;
        }
        if (game.getKeyHandler().d) {
            player.x += PLAYER_SPEED;
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

        if (game.getKeyHandler().b) {
            Snake2 snake = new Snake2(game);
            Worlds.setWorld(snake);
        }

        MouseHandler.reset();
    }

    /**
     * loads up a game if the player intersects
     */
    private void checkGame() {
        final int MINESWEEPER_BLOCK_SIZE = 50;
        final int MINESWEEPER_MAP_SIZE = 17;
        if (player.getBounds().intersects(pacMan.getBounds())) {
            PacMan pacMan = new PacMan(game);
            Worlds.setWorld(pacMan);
        } else if (player.getBounds().intersects(minesweeper.getBounds())) {
            Minesweeper mineSweeper = new Minesweeper(game, MINESWEEPER_BLOCK_SIZE, MINESWEEPER_MAP_SIZE);
            Worlds.setWorld(mineSweeper);
        } else if (player.getBounds().intersects(snake.getBounds())) {
            Snake snakeWorld = new Snake(game);
            Worlds.setWorld(snakeWorld);
        } else if (player.getBounds().intersects(arkanoid.getBounds())) {
            Arkanoid arkanoid = new Arkanoid(game);
            Worlds.setWorld(arkanoid);
        } else if (player.getBounds().intersects(chess.getBounds())) {
            Chess chess = new Chess(game);
            Worlds.setWorld(chess);
        } else if (player.getBounds().intersects(tetris.getBounds())) {
            Tetris tetris = new Tetris(game, true);
            Worlds.setWorld(tetris);
        }else if (player.getBounds().intersects(pong.getBounds())) {
            Pong pong = new Pong(game);
            Worlds.setWorld(pong);
        }
    }

    /*
    ====================================================================================================================
    RENDERING
    ====================================================================================================================
     */
    private int portalAnimation = 0;
    private int portalAnimationDelay = 0;
    private int flameAnimation;
    private int flameAnimationDelay;
    private int legAnimation;

    //images
    private final BufferedImage arcadeMachine = ImageLoader.loadImage("/menuRes/arcadeMachine.png");
    private final BufferedImage legsIdle = ImageLoader.loadImage("/menuRes/legs.png");
    private final BufferedImage legsLeft = ImageLoader.loadImage("/menuRes/legLeft.png");
    private final BufferedImage legRight = ImageLoader.loadImage("/menuRes/legRight.png");
    private BufferedImage legs = legsIdle;

    private final BufferedImage portal0 = portal;
    private final BufferedImage portal1 = ImageLoader.loadImage("/menuRes/portal1.png");
    private final BufferedImage portal2 = ImageLoader.loadImage("/menuRes/portal2.png");
    private final BufferedImage portal3 = ImageLoader.loadImage("/menuRes/portal3.png");
    private final BufferedImage portal4 = ImageLoader.loadImage("/menuRes/portal4.png");

    //head sprites
    private final BufferedImage head0 = head;
    private final BufferedImage head1 = ImageLoader.loadImage("/menuRes/head1.png");
    private final BufferedImage head2 = ImageLoader.loadImage("/menuRes/head2.png");
    private final BufferedImage head3 = ImageLoader.loadImage("/menuRes/head3.png");

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
        for (Rectangle level : levels) {
            g.drawImage(arcadeMachine, level.x + ARCADE_OFFSET_X - off, 30 + ARCADE_OFFSET_Y, arcadeMachine.getWidth() * 4, arcadeMachine.getHeight() * 4, null);
            g.drawImage(portal, level.x - off, level.y, level.width, level.height, null);
        }

        //render level names
        final float LEVEL_TEXT_SIZE = 30.F;
        final int LEVEL_TEXT_HEIGHT = -278;
        final int PONG_TEXT_OFFSET = 110;
        final int TETRIS_TEXT_OFFSET = 100;
        final int CHESS_TEXT_OFFSET = 108;
        final int PACMAN_TEXT_OFFSET = 95;
        final int MINESWEEPER_TEXT_OFFSET = 53;
        final int SNAKE_TEXT_OFFSET = 105;
        final int ARKANOID_TEXT_OFFSET = 80;
        g.setColor(Color.WHITE);
        g.setFont(recursiveBold.deriveFont(recursiveBold.getSize() * LEVEL_TEXT_SIZE));
        g.drawString("Pong", pong.x + PONG_TEXT_OFFSET + ARCADE_OFFSET_X - off, pong.y + LEVEL_TEXT_HEIGHT);
        g.drawString("Tetris", tetris.x + TETRIS_TEXT_OFFSET + ARCADE_OFFSET_X - off, pacMan.y + LEVEL_TEXT_HEIGHT);
        g.drawString("Chess", chess.x + CHESS_TEXT_OFFSET + ARCADE_OFFSET_X - off, pacMan.y + LEVEL_TEXT_HEIGHT);
        g.drawString("PacMan", pacMan.x + PACMAN_TEXT_OFFSET + ARCADE_OFFSET_X - off, pacMan.y + LEVEL_TEXT_HEIGHT);
        g.drawString("Minesweeper", minesweeper.x + MINESWEEPER_TEXT_OFFSET + ARCADE_OFFSET_X - off, minesweeper.y + LEVEL_TEXT_HEIGHT);
        g.drawString("Snake", snake.x + SNAKE_TEXT_OFFSET + ARCADE_OFFSET_X - off, snake.y + LEVEL_TEXT_HEIGHT);
        g.drawString("Arkanoid", arkanoid.x + ARKANOID_TEXT_OFFSET + ARCADE_OFFSET_X - off, arkanoid.y + LEVEL_TEXT_HEIGHT);

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
        g.setFont(recursiveBold.deriveFont(recursiveBold.getSize() * ENTER_TEXT_SIZE));
        for (Rectangle r : levels) {
            if (player.getBounds().intersects(r.getBounds())) {
                g.drawString("[E] to Enter", player.x + ENTER_TEXT_OFFSET_X - off, player.y + ENTER_TEXT_OFFSET_Y);
            }
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
