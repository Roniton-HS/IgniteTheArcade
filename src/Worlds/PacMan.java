package Worlds;

import Input.KeyHandler;
import PacMan.*;
import Main.Game;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PacMan extends Worlds {

    static public boolean gamePaused = false;
    boolean escPressed = false;
    boolean gameOver = false;
    private long gameOverTime;
    static public final int width = 19;
    static public final int height = 25;
    private int hp = 3;

    static public int getBlockSize() {
        return blockSize;
    }

    static private final int blockSize = 38;

    private int score = 0;

    private int ghostsEaten = 0;

    public static Font pixelFont;

    private static final ArrayList<Rectangle> points = new ArrayList<>();
    private static final ArrayList<Rectangle> powerUps = new ArrayList<>();

    private static final ArrayList<Fruit> fruits = new ArrayList<>();

    public static ArrayList<Rectangle> worldBounds = new ArrayList<>();
    public static ArrayList<Rectangle> ghostWorldBounds = new ArrayList<>();

    public static ArrayList<Ghost> ghosts = new ArrayList<>();

    Rectangle left = new Rectangle(101 - blockSize, 10 + 12 * blockSize, blockSize, blockSize);
    Rectangle right = new Rectangle(139 + 20 * blockSize, 10 + 12 * blockSize, blockSize, blockSize);

    public static ArrayList<Rectangle> getWorldBounds() {
        return worldBounds;
    }

    public static ArrayList<Rectangle> getGhostWorldBounds() {
        return ghostWorldBounds;
    }

    static Player player;

    public static Player getPlayer() {
        return player;
    }

    /**
     * Constructor
     */
    public PacMan(Game game) {
        super(game);
        loadFont();
        player = new Player(139 + 9 * blockSize, 10 + 20 * blockSize, game);
        ghosts.add(new Ghost(139 + 8 * blockSize, 10 + 11 * blockSize, 1, game));
        ghosts.add(new Ghost(139 + 10 * blockSize, 10 + 13 * blockSize, 2, game));
        ghosts.add(new Ghost(139 + 8 * blockSize, 10 + 14 * blockSize, 3, game));
        ghosts.add(new Ghost(139 + 10 * blockSize, 10 + 12 * blockSize, 4, game));
        fruits.add(new Fruit(139 + 8 * blockSize, 10 + 16 * blockSize, 1));
        gamePaused = true;
        setWorldBounds();
        setPoints();
    }

    private void reset() {
        ghosts.set(0, new Ghost(139 + 8 * blockSize, 10 + 11 * blockSize, 1, game));
        ghosts.set(1, new Ghost(139 + 10 * blockSize, 10 + 13 * blockSize, 2, game));
        ghosts.set(2, new Ghost(139 + 8 * blockSize, 10 + 14 * blockSize, 3, game));
        ghosts.set(3, new Ghost(139 + 10 * blockSize, 10 + 12 * blockSize, 4, game));
        player.direction = 0;
    }

    private void resetGame() {
        reset();
        hp = 3;
        score = 0;
        points.clear();
        setPoints();
        gameOver = false;
    }

    @Override
    public void tick() {
        input();
        if (gameOver) {
            gamePaused = true;
            if (System.currentTimeMillis() - gameOverTime > 3000) {
                resetGame();
            }
        } else if (gamePaused) {
            if (game.getKeyHandler().w || game.getKeyHandler().a || game.getKeyHandler().s || game.getKeyHandler().d ||
                    game.getKeyHandler().up || game.getKeyHandler().left || game.getKeyHandler().down || game.getKeyHandler().right) {
                gamePaused = false;
            }
        } else {
            checkPoints();
            checkPowerUp();
            player.tick();
            tickGhosts();
            teleport();
        }

    }

    public void input() {
        if (game.getKeyHandler().esc && !escPressed) {
            gamePaused = !gamePaused;
            escPressed = true;
        } else if (!game.getKeyHandler().esc) {
            escPressed = false;
        }
    }

    private void tickGhosts() {
        for (Ghost o : ghosts) {
            o.tick();
        }
        checkGhosts();
    }

    @Override
    public void render(Graphics g) {
        renderBorders(g);
        renderPoints(g);
        player.render(g);
        renderGhosts(g);
        renderStats(g);
        renderStatus(g);
    }

    public void renderStatus(Graphics g) {
        if (gameOver) {
            g.setFont(pixelFont.deriveFont(pixelFont.getSize() * 20.0F));
            g.setColor(Color.RED);
            g.drawString("Game Over", 145 + 7 * blockSize, 17 * blockSize);
        } else if (gamePaused) {
            g.setFont(pixelFont.deriveFont(pixelFont.getSize() * 20.0F));
            g.setColor(Color.YELLOW);
            g.drawString("Ready!", 139 + 8 * blockSize, 17 * blockSize);
        }

    }

    private void checkGhosts() {
        for (Ghost o : ghosts) {
            if (player.getBounds().intersects(o.getBounds())) {
                if (o.fear) {
                    o.eaten = true;
                    o.fear = false;
                    int scoreToAdd = switch (ghostsEaten) {
                        default -> 0;
                        case 0 -> 400;
                        case 1 -> 800;
                        case 2 -> 1200;
                        case 3 -> 1600;
                    };
                    player.displayScore(scoreToAdd);
                    score += scoreToAdd;
                    ghostsEaten++;
                } else if (!o.eaten) {
                    hp--;
                    player.setCords(139 + 9 * blockSize, 10 + 20 * blockSize);
                    player.direction = 0;
                    gamePaused = true;
                    reset();
                    if (hp <= 0) {
                        gameOver = true;
                        gameOverTime = System.currentTimeMillis();
                    }
                }
            }
        }

    }

    private void renderGhosts(Graphics g) {
        for (Ghost o : ghosts) {
            o.render(g);
        }
    }

    private void renderStats(Graphics g) {
        //points
        g.setColor(Color.black);
        g.setFont(pixelFont.deriveFont(pixelFont.getSize() * 10.0F));
        g.drawString("Points:" + score, 10, 30);

        //hp
        g.drawString("HP: " + hp, 10, 45);
    }

    private void loadFont() {
        InputStream is = getClass().getResourceAsStream("/emulogic.ttf");
        try {
            assert is != null;
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void teleport() {
        if (player.getNextBound().intersects(left.getBounds())) {
            player.setX(player.getX() + 20 * blockSize);
        } else if (player.getNextBound().intersects(right.getBounds())) {
            player.setX(player.getX() - 20 * blockSize);
        }
    }

    private void setPoints() {
        powerUps.add(new Rectangle(189 + 16 * blockSize, 60 + 2 * blockSize, 15, 15));
        powerUps.add(new Rectangle(189, 60 + 2 * blockSize, 15, 15));
        powerUps.add(new Rectangle(189, 60 + 19 * blockSize, 15, 15));
        powerUps.add(new Rectangle(189 + 16 * blockSize, 60 + 19 * blockSize, 15, 15));
        for (int i = 0; i < 17; i++) {
            points.add(new Rectangle(194 + i * blockSize, 27 + blockSize, 5, 5));
        }
        for (int i = 0; i < 17; i++) {
            points.add(new Rectangle(194 + i * blockSize, 27 + 4 * blockSize, 5, 5));
        }
        for (int i = 0; i < 9; i++) {
            points.add(new Rectangle(194 + (i + 4) * blockSize, 27 + 20 * blockSize, 5, 5));
        }
        for (int i = 0; i < 17; i++) {
            points.add(new Rectangle(194 + i * blockSize, 27 + 24 * blockSize, 5, 5));
        }
        for (int i = 0; i < 22; i++) {
            points.add(new Rectangle(194 + 3 * blockSize, 65 + i * blockSize, 5, 5));
        }
        for (int i = 0; i < 22; i++) {
            points.add(new Rectangle(194 + 13 * blockSize, 65 + i * blockSize, 5, 5));
        }
        for (int i = 0; i < 6; i++) {
            points.add(new Rectangle(194, 65 + i * blockSize, 5, 5));
        }
        for (int i = 0; i < 6; i++) {
            points.add(new Rectangle(194 + 16 * blockSize, 65 + i * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194, 65 + (i + 18) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + 16 * blockSize, 65 + (i + 18) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194, 65 + (i + 21) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + 16 * blockSize, 65 + (i + 21) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + 9 * blockSize, 65 + (i + 1) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + 7 * blockSize, 65 + (i + 1) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + 11 * blockSize, 65 + (i + 20) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + 5 * blockSize, 65 + (i + 20) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + (1 + i) * blockSize, 65 + 5 * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + (14 + i) * blockSize, 65 + 5 * blockSize, 5, 5));
        }
        for (int i = 0; i < 8; i++) {
            points.add(new Rectangle(194 + i * blockSize, 65 + 17 * blockSize, 5, 5));
        }
        for (int i = 0; i < 8; i++) {
            points.add(new Rectangle(194 + (i + 9) * blockSize, 65 + 17 * blockSize, 5, 5));
        }
        for (int i = 0; i < 3; i++) {
            points.add(new Rectangle(194 + blockSize, 65 + (19 + i) * blockSize, 5, 5));
        }
        points.add(new Rectangle(194 + 2 * blockSize, 65 + 21 * blockSize, 5, 5));
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + (6 + i) * blockSize, 65 + 21 * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + (9 + i) * blockSize, 65 + 21 * blockSize, 5, 5));
        }
        points.add(new Rectangle(194 + 9 * blockSize, 65 + 22 * blockSize, 5, 5));
        points.add(new Rectangle(194 + 7 * blockSize, 65 + 22 * blockSize, 5, 5));
        points.add(new Rectangle(194 + 9 * blockSize, 65 + 18 * blockSize, 5, 5));
        points.add(new Rectangle(194 + 7 * blockSize, 65 + 18 * blockSize, 5, 5));
        for (int i = 0; i < 3; i++) {
            points.add(new Rectangle(194 + 15 * blockSize, 65 + (19 + i) * blockSize, 5, 5));
        }
        points.add(new Rectangle(194 + 14 * blockSize, 65 + 21 * blockSize, 5, 5));
        for (int i = 0; i < 3; i++) {
            points.add(new Rectangle(194 + 5 * blockSize, 65 + (4 + i) * blockSize, 5, 5));
        }
        for (int i = 0; i < 3; i++) {
            points.add(new Rectangle(194 + 11 * blockSize, 65 + (4 + i) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + (6 + i) * blockSize, 65 + 6 * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + (9 + i) * blockSize, 65 + 6 * blockSize, 5, 5));
        }

    }

    private void renderPoints(Graphics g) {
        for (Rectangle o : powerUps) {
            g.setColor(Color.white);
            g.fillRect(o.getBounds().x, o.getBounds().y, o.getBounds().width, o.getBounds().height);
        }

        for (Rectangle o : points) {
            g.setColor(Color.white);
            g.fillRect(o.getBounds().x, o.getBounds().y, o.getBounds().width, o.getBounds().height);
        }

        for (Fruit f : fruits) {
            f.render(g);
        }
    }

    private void checkPoints() {
        if (points.size() == 0) {
            reset();
        }
        for (int i = 0; i < points.size(); i++) {
            Rectangle point = points.get(i);
            if (player.getNextBound().intersects(point.getBounds())) {
                points.remove(point);
                score++;
            }
        }

        for (int i = 0; i < fruits.size(); i++) {
            Fruit fruit = fruits.get(i);
            if (player.getNextBound().intersects(fruit.getBounds())) {
                fruits.remove(fruit);
                int scoreToAdd = switch (fruit.type) {
                    default -> 0;
                    case 0 -> 100;
                    case 1 -> 300;
                    case 2 -> 500;
                    case 3 -> 700;
                    case 4 -> 1000;
                    case 5 -> 2000;
                    case 6 -> 3000;
                    case 7 -> 5000;
                };
                score += scoreToAdd;
                player.displayScore(scoreToAdd);
            }
        }

    }

    private void checkPowerUp() {
        if (powerUps.size() == 0) {
            return;
        }
        for (int i = 0; i < powerUps.size(); i++) {
            Rectangle powerUp = powerUps.get(i);
            if (player.getNextBound().intersects(powerUp.getBounds())) {
                powerUps.remove(powerUp);
                for (Ghost o : ghosts) {
                    o.startFear();
                }

            }
        }
        for (Ghost g : ghosts) {
            if (g.fear) {
                return;
            }
        }
        ghostsEaten = 0;
    }

    public void setWorldBounds() {
        worldBounds.add(new Rectangle(139, 10, blockSize * width, blockSize));
        worldBounds.add(new Rectangle(139, 10 + blockSize, blockSize, blockSize * 7));
        worldBounds.add(new Rectangle(139 + 3 * blockSize, 10 + 7 * blockSize, blockSize, 5 * blockSize));
        worldBounds.add(new Rectangle(139 + 3 * blockSize, 10 + 13 * blockSize, blockSize, 5 * blockSize));
        worldBounds.add(new Rectangle(139, 10 + 18 * blockSize, blockSize, 7 * blockSize));
        worldBounds.add(new Rectangle(139, 10 + height * blockSize, blockSize * width, blockSize));
        worldBounds.add(new Rectangle(139 + (width - 1) * blockSize, 10 + blockSize, blockSize, blockSize * 7));
        worldBounds.add(new Rectangle(139 + (width - 4) * blockSize, 10 + 7 * blockSize, blockSize, blockSize * 5));
        worldBounds.add(new Rectangle(139 + (width - 4) * blockSize, 10 + 13 * blockSize, blockSize, blockSize * 5));
        worldBounds.add(new Rectangle(139 + (width - 1) * blockSize, 10 + 18 * blockSize, blockSize, blockSize * 7));

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
        worldBounds.add(new Rectangle(139 + 5 * blockSize, 10 + 13 * blockSize, blockSize, 5 * blockSize));
        worldBounds.add(new Rectangle(139 + 13 * blockSize, 10 + 13 * blockSize, blockSize, 5 * blockSize));

        worldBounds.add(new Rectangle(139 + 2 * blockSize, 10 + (height - 2) * blockSize, 6 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 11 * blockSize, 10 + (height - 2) * blockSize, 6 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 5 * blockSize, 10 + (height - 4) * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 7 * blockSize, 10 + (height - 4) * blockSize, 5 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 9 * blockSize, 10 + (height - 3) * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 13 * blockSize, 10 + (height - 4) * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 15 * blockSize, 10 + (height - 5) * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 17 * blockSize, 10 + (height - 4) * blockSize, blockSize, blockSize));

        worldBounds.add(new Rectangle(139 + 2 * blockSize, 10 + (height - 6) * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 3 * blockSize, 10 + (height - 5) * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + blockSize, 10 + (height - 4) * blockSize, blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 15 * blockSize, 10 + (height - 6) * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 5 * blockSize, 10 + (height - 6) * blockSize, 3 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 11 * blockSize, 10 + (height - 6) * blockSize, 3 * blockSize, blockSize));

        worldBounds.add(new Rectangle(139 + 9 * blockSize, 10 + (height - 7) * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 7 * blockSize, 10 + (height - 8) * blockSize, 5 * blockSize, blockSize));

        worldBounds.add(new Rectangle(139 + 7 * blockSize, 10 + 10 * blockSize, blockSize, 6 * blockSize));
        worldBounds.add(new Rectangle(139 + 11 * blockSize, 10 + 10 * blockSize, blockSize, 6 * blockSize));
        worldBounds.add(new Rectangle(139 + 8 * blockSize, 10 + 10 * blockSize, blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 10 * blockSize, 10 + 10 * blockSize, blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 8 * blockSize, 10 + 15 * blockSize, 3 * blockSize, blockSize));

        worldBounds.add(new Rectangle(139, 10 + 11 * blockSize, 3 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139, 10 + 13 * blockSize, 3 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + (width - 3) * blockSize, 10 + 11 * blockSize, 3 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + (width - 3) * blockSize, 10 + 13 * blockSize, 3 * blockSize, blockSize));

        ghostWorldBounds.addAll(worldBounds);
        ghostWorldBounds.add(new Rectangle(139 + 3 * PacMan.getBlockSize(), 10 + 12 * PacMan.getBlockSize(), PacMan.getBlockSize(), 5 * PacMan.getBlockSize()));
        ghostWorldBounds.add(new Rectangle(139 + (PacMan.width - 4) * PacMan.getBlockSize(), 10 + 12 * PacMan.getBlockSize(), PacMan.getBlockSize(), 5 * PacMan.getBlockSize()));
    }

    private void renderBorders(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(139, 10, 19 * blockSize, 26 * blockSize);
        g.setColor(Color.blue);
        for (Rectangle box : worldBounds) {
            g.drawRect((int) box.getX(), (int) box.getY(), (int) box.getWidth(), (int) box.getHeight());

        }
    }
}
