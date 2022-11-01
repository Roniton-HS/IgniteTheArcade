package Worlds;

import Input.MouseHandler;
import Main.Game;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Menu extends Worlds {
    Font font;
    /**
     * Constructor
     */
    public Menu(Game game) {
        super(game);
        loadFont();
    }

    @Override
    public void tick() {
        if (game.getKeyHandler().p) {
            game.getDisplay().resize(19 * 38 + 17, 27 * 38 + 2);
            PacMan pacMan = new PacMan(game);
            Worlds.setWorld(pacMan);
        }
        if (game.getKeyHandler().s) {
            SnakeWorld snakeWorld = new SnakeWorld(game);
            Worlds.setWorld(snakeWorld);
        }
        if (game.getKeyHandler().m) {
            MineSweeper mineSweeper = new MineSweeper(game, 50, 20);
            Worlds.setWorld(mineSweeper);
        }
        MouseHandler.reset();
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
        g.setFont(font.deriveFont(font.getSize() * 40.0F));
        g.drawString("Press [P] for PacMan", 300, 100);
        g.drawString("Press [S] for Snake", 300, 200);
        g.drawString("Press [M] for Minesweeper", 300, 300);

    }
}
