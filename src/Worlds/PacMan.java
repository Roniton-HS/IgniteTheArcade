package Worlds;

import Main.Game;
import PacMan.Player;
import Worlds.Worlds;

import java.awt.*;
import java.util.ArrayList;

public class PacMan extends Worlds {

    private final int width = 19;
    private final int height = 25;
    private final int blockSize = 38;

    public static ArrayList worldBounds = new ArrayList();

    public static ArrayList getWorldBounds(){
        return worldBounds;
    }

    Player player;

    /**
     * Constructor
     */
    public PacMan(Game game) {
        super(game);
        player = new Player(100, 100, game);
        setWorldBounds();
    }

    @Override
    public void tick() {
        player.tick();
    }

    @Override
    public void render(Graphics g) {
        player.render(g);
        renderBorders(g);
    }

    public void setWorldBounds() {
        worldBounds.add(new Rectangle(139, 10, blockSize * width, blockSize));
        worldBounds.add(new Rectangle(139, 10 + blockSize, blockSize, blockSize * 7));
        worldBounds.add(new Rectangle(139 + 3 * blockSize, 10 + 7 * blockSize, blockSize, 5 * blockSize));
        worldBounds.add(new Rectangle(139 + 3 * blockSize, 10 + 13 * blockSize, blockSize, 5 * blockSize));
        worldBounds.add(new Rectangle(139, 10 + 18 * blockSize, blockSize, 6 * blockSize));
        worldBounds.add(new Rectangle(139, 10 + (height - 1) * blockSize, blockSize * width, blockSize));
        worldBounds.add(new Rectangle(139 + (width - 1) * blockSize, 10 + blockSize, blockSize, blockSize * 7));
        worldBounds.add(new Rectangle(139 + (width - 4) * blockSize, 10 + 7 * blockSize, blockSize, blockSize * 5));
        worldBounds.add(new Rectangle(139 + (width - 4) * blockSize, 10 + 13 * blockSize, blockSize, blockSize * 5));
        worldBounds.add(new Rectangle(139 + (width - 1) * blockSize, 10 + 18 * blockSize, blockSize, blockSize * 6));

        worldBounds.add(new Rectangle(139, 10 + 7 * blockSize, 4 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139, 10 + 17 * blockSize, 4 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + (width - 4) * blockSize, 10 + 7 * blockSize, 4 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + (width - 4) * blockSize, 10 + 17 * blockSize, 4 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139, 10, blockSize * width, blockSize));

        worldBounds.add(new Rectangle(139 + 2 * blockSize, 10 + 2 * blockSize, 2 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 2 * blockSize, 10 + 5 * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 5 * blockSize, 10 + 2 * blockSize, 3 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 9 * blockSize, 10 + 2 * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 11 * blockSize, 10 + 2 * blockSize, 3 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 15 * blockSize, 10 + 2 * blockSize, 2 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 5 * blockSize, 10 + 5 * blockSize, blockSize, 7 * blockSize));
        worldBounds.add(new Rectangle(139 + 7 * blockSize, 10 + 5 * blockSize, 5 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 13 * blockSize, 10 + 5 * blockSize, blockSize, 7 * blockSize));
        worldBounds.add(new Rectangle(139 + 15 * blockSize, 10 + 5 * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 9 * blockSize, 10 + 7 * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 6 * blockSize, 10 + 8 * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 11 * blockSize, 10 + 8 * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 5 * blockSize, 10 + 13 * blockSize, blockSize, 7 * blockSize));
        worldBounds.add(new Rectangle(139 + 13 * blockSize, 10 + 13 * blockSize, blockSize, 7 * blockSize));

        worldBounds.add(new Rectangle(139 + 2 * blockSize, 10 + (height - 4) * blockSize, 2 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 5 * blockSize, 10 + (height - 4) * blockSize, 3 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 9 * blockSize, 10 + (height - 4) * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 11 * blockSize, 10 + (height - 4) * blockSize, 3 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 15 * blockSize, 10 + (height - 4) * blockSize, 2 * blockSize, 2 * blockSize));

        worldBounds.add(new Rectangle(139 + 2 * blockSize, 10 + (height - 6) * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 15 * blockSize, 10 + (height - 6) * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 7 * blockSize, 10 + (height - 7) * blockSize, 5 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 9 * blockSize, 10 + (height - 9) * blockSize, blockSize, 2 * blockSize));

        worldBounds.add(new Rectangle(139 + 7 * blockSize, 10 + 10 * blockSize, blockSize, 5 * blockSize));
        worldBounds.add(new Rectangle(139 + 11 * blockSize, 10 + 10 * blockSize, blockSize, 5 * blockSize));
        worldBounds.add(new Rectangle(139 + 8 * blockSize, 10 + 10 * blockSize, blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 10 * blockSize, 10 + 10 * blockSize, blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 8 * blockSize, 10 + 14 * blockSize, 3 * blockSize, blockSize));

        worldBounds.add(new Rectangle(139, 10 + 11 * blockSize, 3 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139, 10 + 13 * blockSize, 3 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + (width - 3) * blockSize, 10 + 11 * blockSize, 3 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + (width - 3) * blockSize, 10 + 13 * blockSize, 3 * blockSize, blockSize));

        worldBounds.add(new Rectangle(139 + 6 * blockSize, 10 + 16 * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 11 * blockSize, 10 + 16 * blockSize, 2 * blockSize, blockSize));
    }

    private void renderBorders(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(139, 10, blockSize * width, blockSize);//upper border
        g.fillRect(139, 10 + blockSize, blockSize, blockSize * 7);
        g.fillRect(139 + 3 * blockSize, 10 + 7 * blockSize, blockSize, 5 * blockSize);
        g.fillRect(139 + 3 * blockSize, 10 + 13 * blockSize, blockSize, 5 * blockSize);
        g.fillRect(139, 10 + 18 * blockSize, blockSize, 6 * blockSize);
        g.fillRect(139, 10 + (height - 1) * blockSize, blockSize * width, blockSize);
        g.fillRect(139 + (width - 1) * blockSize, 10 + blockSize, blockSize, blockSize * 7);
        g.fillRect(139 + (width - 4) * blockSize, 10 + 7 * blockSize, blockSize, blockSize * 5);
        g.fillRect(139 + (width - 4) * blockSize, 10 + 13 * blockSize, blockSize, blockSize * 5);
        g.fillRect(139 + (width - 1) * blockSize, 10 + 18 * blockSize, blockSize, blockSize * 6);

        g.fillRect(139, 10 + 7 * blockSize, 4 * blockSize, blockSize);
        g.fillRect(139, 10 + 17 * blockSize, 4 * blockSize, blockSize);
        g.fillRect(139 + (width - 4) * blockSize, 10 + 7 * blockSize, 4 * blockSize, blockSize);
        g.fillRect(139 + (width - 4) * blockSize, 10 + 17 * blockSize, 4 * blockSize, blockSize);
        g.fillRect(139, 10, blockSize * width, blockSize);

        g.fillRect(139 + 2 * blockSize, 10 + 2 * blockSize, 2 * blockSize, 2 * blockSize);
        g.fillRect(139 + 2 * blockSize, 10 + 5 * blockSize, 2 * blockSize, blockSize);
        g.fillRect(139 + 5 * blockSize, 10 + 2 * blockSize, 3 * blockSize, 2 * blockSize);
        g.fillRect(139 + 9 * blockSize, 10 + 2 * blockSize, blockSize, 2 * blockSize);
        g.fillRect(139 + 11 * blockSize, 10 + 2 * blockSize, 3 * blockSize, 2 * blockSize);
        g.fillRect(139 + 15 * blockSize, 10 + 2 * blockSize, 2 * blockSize, 2 * blockSize);
        g.fillRect(139 + 5 * blockSize, 10 + 5 * blockSize, blockSize, 7 * blockSize);
        g.fillRect(139 + 7 * blockSize, 10 + 5 * blockSize, 5 * blockSize, 2 * blockSize);
        g.fillRect(139 + 13 * blockSize, 10 + 5 * blockSize, blockSize, 7 * blockSize);
        g.fillRect(139 + 15 * blockSize, 10 + 5 * blockSize, 2 * blockSize, blockSize);
        g.fillRect(139 + 9 * blockSize, 10 + 7 * blockSize, blockSize, 2 * blockSize);
        g.fillRect(139 + 6 * blockSize, 10 + 8 * blockSize, 2 * blockSize, blockSize);
        g.fillRect(139 + 11 * blockSize, 10 + 8 * blockSize, 2 * blockSize, blockSize);
        g.fillRect(139 + 5 * blockSize, 10 + 13 * blockSize, blockSize, 7 * blockSize);
        g.fillRect(139 + 13 * blockSize, 10 + 13 * blockSize, blockSize, 7 * blockSize);

        g.fillRect(139 + 2 * blockSize, 10 + (height - 4) * blockSize, 2 * blockSize, 2 * blockSize);
        g.fillRect(139 + 5 * blockSize, 10 + (height - 4) * blockSize, 3 * blockSize, 2 * blockSize);
        g.fillRect(139 + 9 * blockSize, 10 + (height - 4) * blockSize, blockSize, 2 * blockSize);
        g.fillRect(139 + 11 * blockSize, 10 + (height - 4) * blockSize, 3 * blockSize, 2 * blockSize);
        g.fillRect(139 + 15 * blockSize, 10 + (height - 4) * blockSize, 2 * blockSize, 2 * blockSize);

        g.fillRect(139 + 2 * blockSize, 10 + (height - 6) * blockSize, 2 * blockSize, blockSize);
        g.fillRect(139 + 15 * blockSize, 10 + (height - 6) * blockSize, 2 * blockSize, blockSize);
        g.fillRect(139 + 7 * blockSize, 10 + (height - 7) * blockSize, 5 * blockSize, 2 * blockSize);
        g.fillRect(139 + 9 * blockSize, 10 + (height - 9) * blockSize, blockSize, 2 * blockSize);

        g.fillRect(139 + 7 * blockSize, 10 + 10 * blockSize, blockSize, 5 * blockSize);
        g.fillRect(139 + 11 * blockSize, 10 + 10 * blockSize, blockSize, 5 * blockSize);
        g.fillRect(139 + 8 * blockSize, 10 + 10 * blockSize, blockSize, blockSize);
        g.fillRect(139 + 10 * blockSize, 10 + 10 * blockSize, blockSize, blockSize);
        g.fillRect(139 + 8 * blockSize, 10 + 14 * blockSize, 3 * blockSize, blockSize);

        g.fillRect(139, 10 + 11 * blockSize, 3 * blockSize, blockSize);
        g.fillRect(139, 10 + 13 * blockSize, 3 * blockSize, blockSize);
        g.fillRect(139 + (width - 3) * blockSize, 10 + 11 * blockSize, 3 * blockSize, blockSize);
        g.fillRect(139 + (width - 3) * blockSize, 10 + 13 * blockSize, 3 * blockSize, blockSize);

        g.fillRect(139 + 6 * blockSize, 10 + 16 * blockSize, 2 * blockSize, blockSize);
        g.fillRect(139 + 11 * blockSize, 10 + 16 * blockSize, 2 * blockSize, blockSize);
    }
}
