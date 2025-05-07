package MainMenu;

import Arkanoid.Arkanoid;
import FlappyBird.FlappyBird;
import Input.ImageLoader;
import Main.Constants;
import Main.Game;
import Minesweeper.Minesweeper;
import PacMan.PacMan;
import Pong.Pong;
import Snake.Snake;
import Snake2.Snake2;
import Tetris.Tetris;
import Worlds.Worlds;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Main.Constants.emulogic;

public class MainMenu extends Worlds {

    ArrayList<Button> buttons = new ArrayList<>();
    private final int BUTTON_WIDTH = 300;
    private final int BUTTON_HEIGHT = 50;
    BufferedImage background = ImageLoader.loadImage("/menuRes/background.png");

    /**
     * Constructor
     *
     * @param game
     */
    public MainMenu(Game game) {
        super(game, "MainMenu");
    }

    @Override
    public void init() {
        final int MINESWEEPER_BLOCK_SIZE = 50;
        final int MINESWEEPER_MAP_SIZE = 17;
        final int OFFSET = 30;
        PacMan pacMan = new PacMan(game);
        Snake snake = new Snake(game);
        Snake2 snake2 = new Snake2(game);
        Minesweeper minesweeper = new Minesweeper(game, MINESWEEPER_BLOCK_SIZE, MINESWEEPER_MAP_SIZE);
        Arkanoid arkanoid = new Arkanoid(game);
        Pong pong = new Pong(game);
        Tetris tetris = new Tetris(game, true);
        FlappyBird flappyBird = new FlappyBird(game);

        Worlds world;
        for (int i = 1; i < 5; i++) {
            world = switch (i) {
                default -> pacMan;
                case 2 -> snake;
                case 3 -> snake2;
                case 4 -> minesweeper;
            };
            buttons.add(new Button(60, 600 + i * BUTTON_HEIGHT + (i - 1) * OFFSET, BUTTON_WIDTH, BUTTON_HEIGHT, world, game));
        }
        for (int i = 1; i < 5; i++) {
            world = switch (i) {
                default -> arkanoid;
                case 2 -> pong;
                case 3 -> tetris;
                case 4 -> flappyBird;
            };
            buttons.add(new Button(670, 600 + i * BUTTON_HEIGHT + (i - 1) * OFFSET, BUTTON_WIDTH, BUTTON_HEIGHT, world, game));
        }
    }

    @Override
    public void tick() {
        for (Button b : buttons) {
            b.tick();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(background, 0, 0, 1028, 1028, null);
        for (Button b : buttons) {
            b.render(g);
        }
    }
}
