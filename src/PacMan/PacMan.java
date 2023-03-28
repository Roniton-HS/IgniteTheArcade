package PacMan;

import Main.Game;
import Worlds.Worlds;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static Main.Constants.emulogic;

public class PacMan extends Worlds {

    //size of the game
    static public final int width = 19;
    static public final int height = 25;
    static private final int blockSize = 38;

    //time of the game
    public static int ticks = 0;

    //PacMan Player
    static Player player;
    private int hp = 3;
    private int ghostsEaten = 0;
    private int score = 0;

    //pause game
    static public boolean gamePaused = false;
    boolean spacePressed = false;
    boolean gameOver = false;
    private long gameOverTime;

    //Collectables
    private static final ArrayList<Rectangle> points = new ArrayList<>(); //stores all points
    private static final ArrayList<Rectangle> powerUps = new ArrayList<>(); //stores all powerUps
    private static final ArrayList<Fruit> fruits = new ArrayList<>(); //stores all fruits

    //World bounds
    public static ArrayList<Rectangle> worldBounds = new ArrayList<>();

    //Enemies
    public static ArrayList<Ghost> ghosts = new ArrayList<>(); //stores all ghosts

    //teleports
    public static final Rectangle left = new Rectangle(-2 * blockSize, 12 * blockSize, blockSize, blockSize);
    public static final Rectangle right = new Rectangle(20 * blockSize, 12 * blockSize, blockSize, blockSize);

    /*
    ====================================================================================================================
    Init Methods
    ====================================================================================================================
     */
    public PacMan(Game game) {
        super(game);
        game.getDisplay().resize(19 * 38 + 17, 27 * 38 + 2);
        player = new Player(9 * blockSize, 20 * blockSize, game);
        ghosts.add(new Ghost(8 * blockSize, 11 * blockSize, 1, game));
        ghosts.add(new Ghost(10 * blockSize, 13 * blockSize, 2, game));
        ghosts.add(new Ghost(8 * blockSize, 14 * blockSize, 3, game));
        ghosts.add(new Ghost(10 * blockSize, 12 * blockSize, 4, game));
        gamePaused = true;
        setWorldBounds();
        setPoints();
    }

    public void setWorldBounds() {

        worldBounds.add(new Rectangle(0, 0, blockSize * width, blockSize));
        worldBounds.add(new Rectangle(0, blockSize, blockSize, blockSize * 7));
        worldBounds.add(new Rectangle(3 * blockSize, 7 * blockSize, blockSize, 5 * blockSize));
        worldBounds.add(new Rectangle(3 * blockSize, 13 * blockSize, blockSize, 5 * blockSize));
        worldBounds.add(new Rectangle(0, 18 * blockSize, blockSize, 7 * blockSize));
        worldBounds.add(new Rectangle(0, height * blockSize, blockSize * width, blockSize));
        worldBounds.add(new Rectangle((width - 1) * blockSize, blockSize, blockSize, blockSize * 7));
        worldBounds.add(new Rectangle((width - 4) * blockSize, 7 * blockSize, blockSize, blockSize * 5));
        worldBounds.add(new Rectangle((width - 4) * blockSize, 13 * blockSize, blockSize, blockSize * 5));
        worldBounds.add(new Rectangle((width - 1) * blockSize, 18 * blockSize, blockSize, blockSize * 7));

        worldBounds.add(new Rectangle(0, 7 * blockSize, 4 * blockSize, blockSize));
        worldBounds.add(new Rectangle(0, 17 * blockSize, 4 * blockSize, blockSize));
        worldBounds.add(new Rectangle((width - 4) * blockSize, 7 * blockSize, 4 * blockSize, blockSize));
        worldBounds.add(new Rectangle((width - 4) * blockSize, 17 * blockSize, 4 * blockSize, blockSize));
        worldBounds.add(new Rectangle(0, 0, blockSize * width, blockSize));

        worldBounds.add(new Rectangle(2 * blockSize, 2 * blockSize, 2 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(2 * blockSize, 5 * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(5 * blockSize, 2 * blockSize, 3 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(9 * blockSize, 2 * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(11 * blockSize, 2 * blockSize, 3 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(15 * blockSize, 2 * blockSize, 2 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(5 * blockSize, 5 * blockSize, blockSize, 7 * blockSize));
        worldBounds.add(new Rectangle(7 * blockSize, 5 * blockSize, 5 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(13 * blockSize, 5 * blockSize, blockSize, 7 * blockSize));
        worldBounds.add(new Rectangle(15 * blockSize, 5 * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(9 * blockSize, 7 * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(6 * blockSize, 8 * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(11 * blockSize, 8 * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(5 * blockSize, 13 * blockSize, blockSize, 5 * blockSize));
        worldBounds.add(new Rectangle(13 * blockSize, 13 * blockSize, blockSize, 5 * blockSize));

        worldBounds.add(new Rectangle(2 * blockSize, (height - 2) * blockSize, 6 * blockSize, blockSize));
        worldBounds.add(new Rectangle(11 * blockSize, (height - 2) * blockSize, 6 * blockSize, blockSize));
        worldBounds.add(new Rectangle(5 * blockSize, (height - 4) * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(7 * blockSize, (height - 4) * blockSize, 5 * blockSize, blockSize));
        worldBounds.add(new Rectangle(9 * blockSize, (height - 3) * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(13 * blockSize, (height - 4) * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(15 * blockSize, (height - 5) * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(17 * blockSize, (height - 4) * blockSize, blockSize, blockSize));

        worldBounds.add(new Rectangle(2 * blockSize, (height - 6) * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(3 * blockSize, (height - 5) * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(blockSize, (height - 4) * blockSize, blockSize, blockSize));
        worldBounds.add(new Rectangle(15 * blockSize, (height - 6) * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(5 * blockSize, (height - 6) * blockSize, 3 * blockSize, blockSize));
        worldBounds.add(new Rectangle(11 * blockSize, (height - 6) * blockSize, 3 * blockSize, blockSize));

        worldBounds.add(new Rectangle(9 * blockSize, (height - 7) * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(7 * blockSize, (height - 8) * blockSize, 5 * blockSize, blockSize));

        worldBounds.add(new Rectangle(7 * blockSize, 10 * blockSize, blockSize, 6 * blockSize));
        worldBounds.add(new Rectangle(11 * blockSize, 10 * blockSize, blockSize, 6 * blockSize));
        worldBounds.add(new Rectangle(8 * blockSize, 10 * blockSize, blockSize, blockSize));
        worldBounds.add(new Rectangle(10 * blockSize, 10 * blockSize, blockSize, blockSize));
        worldBounds.add(new Rectangle(8 * blockSize, 15 * blockSize, 3 * blockSize, blockSize));

        worldBounds.add(new Rectangle(-2 * blockSize, 11 * blockSize, 5 * blockSize, blockSize));
        worldBounds.add(new Rectangle(-2 * blockSize, 13 * blockSize, 5 * blockSize, blockSize));
        worldBounds.add(new Rectangle((width - 3) * blockSize, 11 * blockSize, 5 * blockSize, blockSize));
        worldBounds.add(new Rectangle((width - 3) * blockSize, 13 * blockSize, 5 * blockSize, blockSize));
    }


    private void setPoints() {
        powerUps.add(new Rectangle(50 + 16 * blockSize, 50 + 2 * blockSize, 15, 15));
        powerUps.add(new Rectangle(50, 50 + 2 * blockSize, 15, 15));
        powerUps.add(new Rectangle(50, 50 + 19 * blockSize, 15, 15));
        powerUps.add(new Rectangle(50 + 16 * blockSize, 50 + 19 * blockSize, 15, 15));
        for (int i = 0; i < 17; i++) {
            points.add(new Rectangle(55 + i * blockSize, 17 + blockSize, 5, 5));
        }
        for (int i = 0; i < 17; i++) {
            points.add(new Rectangle(55 + i * blockSize, 17 + 4 * blockSize, 5, 5));
        }
        for (int i = 0; i < 9; i++) {
            points.add(new Rectangle(55 + (i + 4) * blockSize, 17 + 20 * blockSize, 5, 5));
        }
        for (int i = 0; i < 17; i++) {
            points.add(new Rectangle(55 + i * blockSize, 17 + 24 * blockSize, 5, 5));
        }
        for (int i = 0; i < 22; i++) {
            points.add(new Rectangle(55 + 3 * blockSize, 55 + i * blockSize, 5, 5));
        }
        for (int i = 0; i < 22; i++) {
            points.add(new Rectangle(55 + 13 * blockSize, 55 + i * blockSize, 5, 5));
        }
        for (int i = 0; i < 6; i++) {
            points.add(new Rectangle(55, 55 + i * blockSize, 5, 5));
        }
        for (int i = 0; i < 6; i++) {
            points.add(new Rectangle(55 + 16 * blockSize, 55 + i * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55, 55 + (i + 18) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + 16 * blockSize, 55 + (i + 18) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55, 55 + (i + 21) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + 16 * blockSize, 55 + (i + 21) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + 9 * blockSize, 55 + (i + 1) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + 7 * blockSize, 55 + (i + 1) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + 11 * blockSize, 55 + (i + 20) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + 5 * blockSize, 55 + (i + 20) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + (1 + i) * blockSize, 55 + 5 * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + (14 + i) * blockSize, 55 + 5 * blockSize, 5, 5));
        }
        for (int i = 0; i < 8; i++) {
            points.add(new Rectangle(55 + i * blockSize, 55 + 17 * blockSize, 5, 5));
        }
        for (int i = 0; i < 8; i++) {
            points.add(new Rectangle(55 + (i + 9) * blockSize, 55 + 17 * blockSize, 5, 5));
        }
        for (int i = 0; i < 3; i++) {
            points.add(new Rectangle(55 + blockSize, 55 + (19 + i) * blockSize, 5, 5));
        }
        points.add(new Rectangle(55 + 2 * blockSize, 55 + 21 * blockSize, 5, 5));
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + (6 + i) * blockSize, 55 + 21 * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + (9 + i) * blockSize, 55 + 21 * blockSize, 5, 5));
        }
        points.add(new Rectangle(55 + 9 * blockSize, 55 + 22 * blockSize, 5, 5));
        points.add(new Rectangle(55 + 7 * blockSize, 55 + 22 * blockSize, 5, 5));
        points.add(new Rectangle(55 + 9 * blockSize, 55 + 18 * blockSize, 5, 5));
        points.add(new Rectangle(55 + 7 * blockSize, 55 + 18 * blockSize, 5, 5));
        for (int i = 0; i < 3; i++) {
            points.add(new Rectangle(55 + 15 * blockSize, 55 + (19 + i) * blockSize, 5, 5));
        }
        points.add(new Rectangle(55 + 14 * blockSize, 55 + 21 * blockSize, 5, 5));
        for (int i = 0; i < 3; i++) {
            points.add(new Rectangle(55 + 5 * blockSize, 55 + (4 + i) * blockSize, 5, 5));
        }
        for (int i = 0; i < 3; i++) {
            points.add(new Rectangle(55 + 11 * blockSize, 55 + (4 + i) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + (6 + i) * blockSize, 55 + 6 * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(55 + (9 + i) * blockSize, 55 + 6 * blockSize, 5, 5));
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
            if(o.fear){
                if(!slow){
                    o.tick();
                }
            }else {
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
            player.setX(player.getX() + 20 * blockSize);
        } else if (player.getNextBound().intersects(right.getBounds())) {
            player.setX(player.getX() - 20 * blockSize);
        }
    }

    /**
     * reset ghosts and player
     */
    private void reset() {
        ghosts.set(0, new Ghost(8 * blockSize, 11 * blockSize, 1, game));
        ghosts.set(1, new Ghost(10 * blockSize, 13 * blockSize, 2, game));
        ghosts.set(2, new Ghost(8 * blockSize, 14 * blockSize, 3, game));
        ghosts.set(3, new Ghost(10 * blockSize, 12 * blockSize, 4, game));
        player = new Player(9 * blockSize, 20 * blockSize, game);
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
            g.drawString("Game Over", 6 + 7 * blockSize, 28 + 16 * blockSize);
        } else if (gamePaused) {
            g.setFont(emulogic.deriveFont(emulogic.getSize() * 20.0F));
            g.setColor(Color.YELLOW);
            g.drawString("Ready!", 8 * blockSize, 28 + 16 * blockSize);
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
            g.drawImage(player.image1, 16 * blockSize + i * 36, 3, null);
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
        g.fillRect(0, 0, 19 * blockSize + 1, 26 * blockSize);
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

        g.fillRect(3 * blockSize + 1, 17 * blockSize, blockSize - 1, 1);
        g.fillRect(15 * blockSize + 1, 17 * blockSize, blockSize - 1, 1);

        g.fillRect(3 * blockSize + 1, 20 * blockSize, blockSize - 1, 1);
        g.fillRect(15 * blockSize + 1, 20 * blockSize, blockSize - 1, 1);

        g.fillRect(blockSize, 21 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(18 * blockSize, 21 * blockSize + 1, 1, blockSize - 1);

        g.fillRect(5 * blockSize + 1, 23 * blockSize, blockSize - 1, 1);
        g.fillRect(13 * blockSize + 1, 23 * blockSize, blockSize - 1, 1);
        g.fillRect(1, 25 * blockSize, blockSize - 1, 1);
        g.fillRect(1 + 18 * blockSize, 25 * blockSize, blockSize - 1, 1);
        g.fillRect(9 * blockSize + 1, 22 * blockSize, blockSize - 1, 1);
        g.fillRect(9 * blockSize + 1, 18 * blockSize, blockSize - 1, 1);
        g.fillRect(1, 18 * blockSize, blockSize - 1, 1);
        g.fillRect(1 + 18 * blockSize, 18 * blockSize, blockSize - 1, 1);
        g.fillRect(3 * blockSize + 1, 8 * blockSize, blockSize - 1, 1);
        g.fillRect(15 * blockSize + 1, 8 * blockSize, blockSize - 1, 1);
        g.fillRect(3 * blockSize, 17 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(16 * blockSize, 17 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(3 * blockSize, 13 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(3 * blockSize, 11 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(16 * blockSize, 13 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(16 * blockSize, 11 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(3 * blockSize, 7 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(16 * blockSize, 7 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(blockSize, 7 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(18 * blockSize, 7 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(1, 7 * blockSize, blockSize - 1, 1);
        g.fillRect(1 + 18 * blockSize, 7 * blockSize, blockSize - 1, 1);
        g.fillRect(1, blockSize, blockSize - 1, 1);
        g.fillRect(1 + 18 * blockSize, blockSize, blockSize - 1, 1);
        g.fillRect(6 * blockSize, 8 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(13 * blockSize, 8 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(8 * blockSize, 10 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(11 * blockSize, 10 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(8 * blockSize, 15 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(11 * blockSize, 15 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(9 * blockSize + 1, 7 * blockSize, blockSize - 1, 1);
        g.fillRect(0, 13 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(0, 11 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(19 * blockSize, 13 * blockSize + 1, 1, blockSize - 1);
        g.fillRect(19 * blockSize, 11 * blockSize + 1, 1, blockSize - 1);
    }


    /*
    ====================================================================================================================
    getters / setters
    ====================================================================================================================
     */
    public static ArrayList<Rectangle> getWorldBounds() {
        return worldBounds;
    }

    public static Player getPlayer() {
        return player;
    }

    static public int getBlockSize() {
        return blockSize;
    }

    private void setFruit(int i, int type) {
        fruits.add(new Fruit((int) points.get(i).getX() - 13, (int) points.get(i).getY() - 13, type));
        points.remove(i);
    }

}
