package PacMan;

import Main.Game;
import Worlds.Worlds;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static Main.Constants.emulogic;

public class PacMan extends Worlds {

    //size of the game
    private static final int WIDTH = 19;
    private static final int HEIGHT = 25;
    private static final int BLOCK_SIZE = 38;

    //time of the game
    public int ticks = 0;

    //PacMan Player
    Player player;
    private int hp = 3;
    private int ghostsEaten = 0;
    private int score = 0;

    //pause game
    public boolean gamePaused;
    boolean spacePressed = false;
    boolean gameOver = false;
    private long gameOverTime;

    //Collectables
    private final ArrayList<Rectangle> points = new ArrayList<>(); //stores all points
    private final ArrayList<Rectangle> powerUps = new ArrayList<>(); //stores all powerUps
    private final ArrayList<Fruit> fruits = new ArrayList<>(); //stores all fruits

    //World bounds
    public ArrayList<Rectangle> worldBounds = new ArrayList<>();

    //Enemies
    public ArrayList<Ghost> ghosts = new ArrayList<>(); //stores all ghosts

    //teleports
    public final Rectangle left = new Rectangle(-2 * BLOCK_SIZE, 12 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
    public final Rectangle right = new Rectangle(20 * BLOCK_SIZE, 12 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);

    /*
    ====================================================================================================================
    Init Methods
    ====================================================================================================================
     */
    public PacMan(Game game) {
        super(game, "PacMan");
    }

    @Override
    public void init() {
        player = new Player(9 * BLOCK_SIZE, 20 * BLOCK_SIZE, this, game);
        ghosts.add(new Ghost(8 * BLOCK_SIZE, 11 * BLOCK_SIZE, 1, this, game));
        ghosts.add(new Ghost(10 * BLOCK_SIZE, 13 * BLOCK_SIZE, 2, this, game));
        ghosts.add(new Ghost(8 * BLOCK_SIZE, 14 * BLOCK_SIZE, 3, this, game));
        ghosts.add(new Ghost(10 * BLOCK_SIZE, 12 * BLOCK_SIZE, 4, this, game));
        gamePaused = true;
        setWorldBounds();
        setPoints();
    }

    public void setWorldBounds() {

        worldBounds.add(new Rectangle(0, 0, BLOCK_SIZE * WIDTH, BLOCK_SIZE));
        worldBounds.add(new Rectangle(0, BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE * 7));
        worldBounds.add(new Rectangle(3 * BLOCK_SIZE, 7 * BLOCK_SIZE, BLOCK_SIZE, 5 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(3 * BLOCK_SIZE, 13 * BLOCK_SIZE, BLOCK_SIZE, 5 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(0, 18 * BLOCK_SIZE, BLOCK_SIZE, 7 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(0, HEIGHT * BLOCK_SIZE, BLOCK_SIZE * WIDTH, BLOCK_SIZE));
        worldBounds.add(new Rectangle((WIDTH - 1) * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE * 7));
        worldBounds.add(new Rectangle((WIDTH - 4) * BLOCK_SIZE, 7 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE * 5));
        worldBounds.add(new Rectangle((WIDTH - 4) * BLOCK_SIZE, 13 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE * 5));
        worldBounds.add(new Rectangle((WIDTH - 1) * BLOCK_SIZE, 18 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE * 7));

        worldBounds.add(new Rectangle(0, 7 * BLOCK_SIZE, 4 * BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle(0, 17 * BLOCK_SIZE, 4 * BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle((WIDTH - 4) * BLOCK_SIZE, 7 * BLOCK_SIZE, 4 * BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle((WIDTH - 4) * BLOCK_SIZE, 17 * BLOCK_SIZE, 4 * BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle(0, 0, BLOCK_SIZE * WIDTH, BLOCK_SIZE));

        worldBounds.add(new Rectangle(2 * BLOCK_SIZE, 2 * BLOCK_SIZE, 2 * BLOCK_SIZE, 2 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(2 * BLOCK_SIZE, 5 * BLOCK_SIZE, 2 * BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle(5 * BLOCK_SIZE, 2 * BLOCK_SIZE, 3 * BLOCK_SIZE, 2 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(9 * BLOCK_SIZE, 2 * BLOCK_SIZE, BLOCK_SIZE, 2 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(11 * BLOCK_SIZE, 2 * BLOCK_SIZE, 3 * BLOCK_SIZE, 2 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(15 * BLOCK_SIZE, 2 * BLOCK_SIZE, 2 * BLOCK_SIZE, 2 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(5 * BLOCK_SIZE, 5 * BLOCK_SIZE, BLOCK_SIZE, 7 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(7 * BLOCK_SIZE, 5 * BLOCK_SIZE, 5 * BLOCK_SIZE, 2 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(13 * BLOCK_SIZE, 5 * BLOCK_SIZE, BLOCK_SIZE, 7 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(15 * BLOCK_SIZE, 5 * BLOCK_SIZE, 2 * BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle(9 * BLOCK_SIZE, 7 * BLOCK_SIZE, BLOCK_SIZE, 2 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(6 * BLOCK_SIZE, 8 * BLOCK_SIZE, 2 * BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle(11 * BLOCK_SIZE, 8 * BLOCK_SIZE, 2 * BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle(5 * BLOCK_SIZE, 13 * BLOCK_SIZE, BLOCK_SIZE, 5 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(13 * BLOCK_SIZE, 13 * BLOCK_SIZE, BLOCK_SIZE, 5 * BLOCK_SIZE));

        worldBounds.add(new Rectangle(2 * BLOCK_SIZE, (HEIGHT - 2) * BLOCK_SIZE, 6 * BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle(11 * BLOCK_SIZE, (HEIGHT - 2) * BLOCK_SIZE, 6 * BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle(5 * BLOCK_SIZE, (HEIGHT - 4) * BLOCK_SIZE, BLOCK_SIZE, 2 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(7 * BLOCK_SIZE, (HEIGHT - 4) * BLOCK_SIZE, 5 * BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle(9 * BLOCK_SIZE, (HEIGHT - 3) * BLOCK_SIZE, BLOCK_SIZE, 2 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(13 * BLOCK_SIZE, (HEIGHT - 4) * BLOCK_SIZE, BLOCK_SIZE, 2 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(15 * BLOCK_SIZE, (HEIGHT - 5) * BLOCK_SIZE, BLOCK_SIZE, 2 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(17 * BLOCK_SIZE, (HEIGHT - 4) * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE));

        worldBounds.add(new Rectangle(2 * BLOCK_SIZE, (HEIGHT - 6) * BLOCK_SIZE, 2 * BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle(3 * BLOCK_SIZE, (HEIGHT - 5) * BLOCK_SIZE, BLOCK_SIZE, 2 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(BLOCK_SIZE, (HEIGHT - 4) * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle(15 * BLOCK_SIZE, (HEIGHT - 6) * BLOCK_SIZE, 2 * BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle(5 * BLOCK_SIZE, (HEIGHT - 6) * BLOCK_SIZE, 3 * BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle(11 * BLOCK_SIZE, (HEIGHT - 6) * BLOCK_SIZE, 3 * BLOCK_SIZE, BLOCK_SIZE));

        worldBounds.add(new Rectangle(9 * BLOCK_SIZE, (HEIGHT - 7) * BLOCK_SIZE, BLOCK_SIZE, 2 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(7 * BLOCK_SIZE, (HEIGHT - 8) * BLOCK_SIZE, 5 * BLOCK_SIZE, BLOCK_SIZE));

        worldBounds.add(new Rectangle(7 * BLOCK_SIZE, 10 * BLOCK_SIZE, BLOCK_SIZE, 6 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(11 * BLOCK_SIZE, 10 * BLOCK_SIZE, BLOCK_SIZE, 6 * BLOCK_SIZE));
        worldBounds.add(new Rectangle(8 * BLOCK_SIZE, 10 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle(10 * BLOCK_SIZE, 10 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle(8 * BLOCK_SIZE, 15 * BLOCK_SIZE, 3 * BLOCK_SIZE, BLOCK_SIZE));

        worldBounds.add(new Rectangle(-2 * BLOCK_SIZE, 11 * BLOCK_SIZE, 5 * BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle(-2 * BLOCK_SIZE, 13 * BLOCK_SIZE, 5 * BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle((WIDTH - 3) * BLOCK_SIZE, 11 * BLOCK_SIZE, 5 * BLOCK_SIZE, BLOCK_SIZE));
        worldBounds.add(new Rectangle((WIDTH - 3) * BLOCK_SIZE, 13 * BLOCK_SIZE, 5 * BLOCK_SIZE, BLOCK_SIZE));
    }


    private void setPoints() {
        powerUps.add(new Rectangle(50 + 16 * BLOCK_SIZE, 50 + 2 * BLOCK_SIZE, 15, 15));
        powerUps.add(new Rectangle(50, 50 + 2 * BLOCK_SIZE, 15, 15));
        powerUps.add(new Rectangle(50, 50 + 19 * BLOCK_SIZE, 15, 15));
        powerUps.add(new Rectangle(50 + 16 * BLOCK_SIZE, 50 + 19 * BLOCK_SIZE, 15, 15));
        for (int i = 0; i < 17; i++) {
            points.add(new Rectangle(55 + i * BLOCK_SIZE, 17 + BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 17; i++) {
            points.add(new Rectangle(55 + i * BLOCK_SIZE, 17 + 4 * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 9; i++) {
            points.add(new Rectangle(55 + (i + 4) * BLOCK_SIZE, 17 + 20 * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 17; i++) {
            points.add(new Rectangle(55 + i * BLOCK_SIZE, 17 + 24 * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 22; i++) {
            points.add(new Rectangle(55 + 3 * BLOCK_SIZE, 55 + i * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 22; i++) {
            points.add(new Rectangle(55 + 13 * BLOCK_SIZE, 55 + i * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 6; i++) {
            points.add(new Rectangle(55, 55 + i * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 6; i++) {
            points.add(new Rectangle(55 + 16 * BLOCK_SIZE, 55 + i * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55, 55 + (i + 18) * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + 16 * BLOCK_SIZE, 55 + (i + 18) * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55, 55 + (i + 21) * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + 16 * BLOCK_SIZE, 55 + (i + 21) * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + 9 * BLOCK_SIZE, 55 + (i + 1) * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + 7 * BLOCK_SIZE, 55 + (i + 1) * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + 11 * BLOCK_SIZE, 55 + (i + 20) * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + 5 * BLOCK_SIZE, 55 + (i + 20) * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + (1 + i) * BLOCK_SIZE, 55 + 5 * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + (14 + i) * BLOCK_SIZE, 55 + 5 * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 8; i++) {
            points.add(new Rectangle(55 + i * BLOCK_SIZE, 55 + 17 * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 8; i++) {
            points.add(new Rectangle(55 + (i + 9) * BLOCK_SIZE, 55 + 17 * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 3; i++) {
            points.add(new Rectangle(55 + BLOCK_SIZE, 55 + (19 + i) * BLOCK_SIZE, 5, 5));
        }
        points.add(new Rectangle(55 + 2 * BLOCK_SIZE, 55 + 21 * BLOCK_SIZE, 5, 5));
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + (6 + i) * BLOCK_SIZE, 55 + 21 * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + (9 + i) * BLOCK_SIZE, 55 + 21 * BLOCK_SIZE, 5, 5));
        }
        points.add(new Rectangle(55 + 9 * BLOCK_SIZE, 55 + 22 * BLOCK_SIZE, 5, 5));
        points.add(new Rectangle(55 + 7 * BLOCK_SIZE, 55 + 22 * BLOCK_SIZE, 5, 5));
        points.add(new Rectangle(55 + 9 * BLOCK_SIZE, 55 + 18 * BLOCK_SIZE, 5, 5));
        points.add(new Rectangle(55 + 7 * BLOCK_SIZE, 55 + 18 * BLOCK_SIZE, 5, 5));
        for (int i = 0; i < 3; i++) {
            points.add(new Rectangle(55 + 15 * BLOCK_SIZE, 55 + (19 + i) * BLOCK_SIZE, 5, 5));
        }
        points.add(new Rectangle(55 + 14 * BLOCK_SIZE, 55 + 21 * BLOCK_SIZE, 5, 5));
        for (int i = 0; i < 3; i++) {
            points.add(new Rectangle(55 + 5 * BLOCK_SIZE, 55 + (4 + i) * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 3; i++) {
            points.add(new Rectangle(55 + 11 * BLOCK_SIZE, 55 + (4 + i) * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + (6 + i) * BLOCK_SIZE, 55 + 6 * BLOCK_SIZE, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + (9 + i) * BLOCK_SIZE, 55 + 6 * BLOCK_SIZE, 5, 5));
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(0, points.size() + 1);
        int randomType = ThreadLocalRandom.current().nextInt(0, 7 + 1);
        setFruit(randomIndex, randomType);

    }

    /*
    ====================================================================================================================
    Game logic
    ====================================================================================================================
    */

    /**
     * tick the world
     */
    public void tick() {
        input();
        if (gameOver) {
            gamePaused = true;
            if (System.currentTimeMillis() - gameOverTime > 3000) {
                resetGame();
            }
        } else if (!gamePaused) {
            checkPoints();
            checkPowerUp();
            player.tick();
            tickGhosts();
            teleport();
            ticks++;
        }
    }

    /**
     * tick all the ghosts
     */
    boolean slow = false;

    private void tickGhosts() {
        for (Ghost o : ghosts) {
            if (o.fear) {
                if (!slow) {
                    o.tick();
                }
            } else {
                o.tick();
            }
        }
        slow = !slow;
        checkGhosts();
    }

    /**
     * handles keyboard input
     */
    public void input() {
        if (game.getKeyHandler().space && !spacePressed) {
            gamePaused = !gamePaused;
            spacePressed = true;
        } else if (!game.getKeyHandler().space) {
            spacePressed = false;
        }
    }

    /**
     * teleport player when leaving left or right path
     */
    private void teleport() {
        if (player.getNextBound().intersects(left.getBounds())) {
            player.setX(player.getX() + 20 * BLOCK_SIZE);
        } else if (player.getNextBound().intersects(right.getBounds())) {
            player.setX(player.getX() - 20 * BLOCK_SIZE);
        }
    }

    /**
     * reset ghosts and player
     */
    private void reset() {
        ghosts.set(0, new Ghost(8 * BLOCK_SIZE, 11 * BLOCK_SIZE, 1, this, game));
        ghosts.set(1, new Ghost(10 * BLOCK_SIZE, 13 * BLOCK_SIZE, 2, this, game));
        ghosts.set(2, new Ghost(8 * BLOCK_SIZE, 14 * BLOCK_SIZE, 3, this, game));
        ghosts.set(3, new Ghost(10 * BLOCK_SIZE, 12 * BLOCK_SIZE, 4, this, game));
        player = new Player(9 * BLOCK_SIZE, 20 * BLOCK_SIZE, this, game);
        gamePaused = true;
        ticks = 0;
    }

    /**
     * resets everything
     */
    private void resetGame() {
        reset();
        hp = 3;
        score = 0;
        points.clear();
        setPoints();
        gameOver = false;
    }


    /*
    ====================================================================================================================
    Check Collision
    ====================================================================================================================
     */

    /**
     * checks ghost collision
     * fear = true -> eat ghost
     * fear = false -> hp--; pause game;
     * hp <= 0 -> reset game
     */
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
                    player.setCords(139 + 9 * BLOCK_SIZE, 10 + 20 * BLOCK_SIZE);
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

    /**
     * checks for fruits and points and collects them
     * if there are no points left -> reset
     */
    private void checkPoints() {
        if (points.size() == 0) {
            setPoints();
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
                player.displayScore(fruit.getScore());
                score += fruit.getScore();
                fruits.remove(fruit);
            }
        }

    }

    /**
     * checks PowerUp collision
     * collects powerUps and sets ghost fear to true
     */
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
                ghostsEaten = 0;

            }
        }
        for (Ghost g : ghosts) {
            if (g.fear) {
                return;
            }
        }
        ghostsEaten = 0;
    }


    /*
    ====================================================================================================================
    Render Methods
    ====================================================================================================================
     */

    /**
     * renders World
     *
     * @param g Graphics g
     */
    public void render(Graphics g) {
        renderBorders(g);
        renderPoints(g);
        player.render(g);
        renderGhosts(g);
        renderStats(g);
        renderStatus(g);
    }

    /**
     * renders "Ready!" and "Game Over"
     */
    public void renderStatus(Graphics g) {
        if (gameOver) {
            g.setFont(emulogic.deriveFont(emulogic.getSize() * 20.0F));
            g.setColor(Color.RED);
            g.drawString("Game Over", 6 + 7 * BLOCK_SIZE, 28 + 16 * BLOCK_SIZE);
        } else if (gamePaused) {
            g.setFont(emulogic.deriveFont(emulogic.getSize() * 20.0F));
            g.setColor(Color.YELLOW);
            g.drawString("Ready!", 8 * BLOCK_SIZE, 28 + 16 * BLOCK_SIZE);
        }

    }

    /**
     * renders Ghosts
     */
    private void renderGhosts(Graphics g) {
        for (Ghost o : ghosts) {
            o.render(g);
        }
    }

    /**
     * draws HP and Score
     */
    private void renderStats(Graphics g) {
        //points
        g.setColor(Color.WHITE);
        g.setFont(emulogic.deriveFont(emulogic.getSize() * 20.0F));
        g.drawString("Score:" + score, 7, 29);

        //hp
        for (int i = 0; i < hp; i++) {
            g.drawImage(player.image1, 16 * BLOCK_SIZE + i * 36, 3, null);
        }
    }

    /**
     * renders points
     */
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

    /**
     * renders blue world borders
     */
    private void renderBorders(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, 19 * BLOCK_SIZE + 1, 26 * BLOCK_SIZE);
        g.setColor(Color.blue);
        for (Rectangle box : worldBounds) {
            g.drawRect((int) box.getX(), (int) box.getY(), (int) box.getWidth(), (int) box.getHeight());

        }
        removeBlueLines(g);
    }

    /**
     * fixes overlapping border edges
     */
    private void removeBlueLines(Graphics g) {
        g.setColor(Color.black);

        g.fillRect(3 * BLOCK_SIZE + 1, 17 * BLOCK_SIZE, BLOCK_SIZE - 1, 1);
        g.fillRect(15 * BLOCK_SIZE + 1, 17 * BLOCK_SIZE, BLOCK_SIZE - 1, 1);

        g.fillRect(3 * BLOCK_SIZE + 1, 20 * BLOCK_SIZE, BLOCK_SIZE - 1, 1);
        g.fillRect(15 * BLOCK_SIZE + 1, 20 * BLOCK_SIZE, BLOCK_SIZE - 1, 1);

        g.fillRect(BLOCK_SIZE, 21 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(18 * BLOCK_SIZE, 21 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);

        g.fillRect(5 * BLOCK_SIZE + 1, 23 * BLOCK_SIZE, BLOCK_SIZE - 1, 1);
        g.fillRect(13 * BLOCK_SIZE + 1, 23 * BLOCK_SIZE, BLOCK_SIZE - 1, 1);
        g.fillRect(1, 25 * BLOCK_SIZE, BLOCK_SIZE - 1, 1);
        g.fillRect(1 + 18 * BLOCK_SIZE, 25 * BLOCK_SIZE, BLOCK_SIZE - 1, 1);
        g.fillRect(9 * BLOCK_SIZE + 1, 22 * BLOCK_SIZE, BLOCK_SIZE - 1, 1);
        g.fillRect(9 * BLOCK_SIZE + 1, 18 * BLOCK_SIZE, BLOCK_SIZE - 1, 1);
        g.fillRect(1, 18 * BLOCK_SIZE, BLOCK_SIZE - 1, 1);
        g.fillRect(1 + 18 * BLOCK_SIZE, 18 * BLOCK_SIZE, BLOCK_SIZE - 1, 1);
        g.fillRect(3 * BLOCK_SIZE + 1, 8 * BLOCK_SIZE, BLOCK_SIZE - 1, 1);
        g.fillRect(15 * BLOCK_SIZE + 1, 8 * BLOCK_SIZE, BLOCK_SIZE - 1, 1);
        g.fillRect(3 * BLOCK_SIZE, 17 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(16 * BLOCK_SIZE, 17 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(3 * BLOCK_SIZE, 13 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(3 * BLOCK_SIZE, 11 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(16 * BLOCK_SIZE, 13 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(16 * BLOCK_SIZE, 11 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(3 * BLOCK_SIZE, 7 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(16 * BLOCK_SIZE, 7 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(BLOCK_SIZE, 7 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(18 * BLOCK_SIZE, 7 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(1, 7 * BLOCK_SIZE, BLOCK_SIZE - 1, 1);
        g.fillRect(1 + 18 * BLOCK_SIZE, 7 * BLOCK_SIZE, BLOCK_SIZE - 1, 1);
        g.fillRect(1, BLOCK_SIZE, BLOCK_SIZE - 1, 1);
        g.fillRect(1 + 18 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE - 1, 1);
        g.fillRect(6 * BLOCK_SIZE, 8 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(13 * BLOCK_SIZE, 8 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(8 * BLOCK_SIZE, 10 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(11 * BLOCK_SIZE, 10 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(8 * BLOCK_SIZE, 15 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(11 * BLOCK_SIZE, 15 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(9 * BLOCK_SIZE + 1, 7 * BLOCK_SIZE, BLOCK_SIZE - 1, 1);
        g.fillRect(0, 13 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(0, 11 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(19 * BLOCK_SIZE, 13 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
        g.fillRect(19 * BLOCK_SIZE, 11 * BLOCK_SIZE + 1, 1, BLOCK_SIZE - 1);
    }


    /*
    ====================================================================================================================
    getters / setters
    ====================================================================================================================
     */
    public ArrayList<Rectangle> getWorldBounds() {
        return worldBounds;
    }

    public Player getPlayer() {
        return player;
    }

    static public int getBlockSize() {
        return BLOCK_SIZE;
    }

    private void setFruit(int i, int type) {
        fruits.add(new Fruit((int) points.get(i).getX() - 13, (int) points.get(i).getY() - 13, type));
        points.remove(i);
    }

}
