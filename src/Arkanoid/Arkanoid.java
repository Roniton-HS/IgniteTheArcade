package Arkanoid;

import Main.Game;
import Worlds.Worlds;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static Main.Constants.*;
import static java.lang.Math.*;

public class Arkanoid extends Worlds {

    // ui variables
    private final int WINDOW_WIDTH = 490;
    private final int WINDOW_HEIGHT = 700;

    // game variables
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private boolean gameWon = false;
    private boolean debug = false;
    private boolean keyPressed = false;
    private int lives = 3;
    private int score = 0;
    private long gameOverTime;

    // player variables
    private Player player;

    // ball variables
    private final ArrayList<Ball> balls = new ArrayList<>();
    private final ArrayList<Ball> rmBalls = new ArrayList<>();

    // borders
    private Rectangle borderL, borderR, borderT, borderB;
    private final ArrayList<Rectangle> borders = new ArrayList<>(); // contains only the left (index 0) and right (index 1) border

    // pattern variables
    private final Pattern pattern = new Pattern();
    private final ArrayList<Brick> bricks = new ArrayList<>();
    private final ArrayList<Brick> rmBricks = new ArrayList<>();
    private int numberPattern;

    // powerUp variables
    private final PowerUps powerUps = new PowerUps();
    private final Random random = new Random();
    private final ArrayList<PowerUp> powers = new ArrayList<>();
    private final ArrayList<PowerUp> rmPowers = new ArrayList<>();
    private long fireTime;

    /**
     * Constructor
     */
    public Arkanoid(Game game) {
        super(game, "Arkanoid");
    }

    @Override
    public void init() {
        game.getDisplay().resize(WINDOW_WIDTH + WIN10_WIDTH_DIFF, WINDOW_HEIGHT + WIN10_HEIGHT_DIFF);
        createGame();
        createBorders();
        pattern.createPattern();
        createBricks();
        powerUps.createPowerUps();
    }

    private void createGame() {
        player = new Player(WINDOW_WIDTH, WINDOW_HEIGHT);
        balls.add(new Ball(player));
    }

    private void createBorders() {
        borderL = new Rectangle(40, 0, 10, WINDOW_HEIGHT);
        borderR = new Rectangle(440, 0, 10, WINDOW_HEIGHT);
        borderT = new Rectangle(0, 40, WINDOW_WIDTH, 10);
        borderB = new Rectangle(0, 650, WINDOW_WIDTH, 10);
        borders.add(borderL);
        borders.add(borderR);
    }

    private void createBricks() {
        Random random = new Random();
        numberPattern = random.nextInt(pattern.getPatterns().size());
        bricks.clear();
        for (Brick brick : pattern.getPatterns().get(numberPattern)) {
            bricks.add(new Brick(brick.x, brick.y, brick.getHp()));
        }
    }

    @Override
    public void tick() {
        input();
        if (gameStarted) {
            moveBall();
            movePowerUps();
            if (System.currentTimeMillis() - fireTime > 10000) {
                for (Ball ball : balls) {
                    ball.setFire(false);
                }
            }
            checkBalls();
            removeBricks();
            removePowers();

        }
        if (gameOver) {
            gameStarted = false;
            if (System.currentTimeMillis() - gameOverTime > 3000) {
                resetGame();
            }
        }
        if (gameWon) {
            gameStarted = false;
            if (System.currentTimeMillis() - gameOverTime > 3000) {
                reset();
                createBricks();
                gameWon = false;
            }
        }
    }

    private void input() {
        //bar movement
        if (game.getKeyHandler().a && !gameOver) {
            if (player.getCollisionPlayer().getBounds().intersects(borderL.getBounds())) {
                player.setIntX(borderL.x + borderL.width);
            } else {
                player.setIntX(player.getIntX() - player.getSpeed());
            }
            player.moveCollision();

            if (!gameStarted) {
                for (Ball ball : balls) {
                    ball.setIntX((player.getIntX() + (player.getIntWidth() / 2)) - (ball.getDIAMETER() / 2));
                }
            }
        }
        if (game.getKeyHandler().d && !gameOver) {
            if (player.getCollisionPlayer().getBounds().intersects(borderR.getBounds())) {
                player.setIntX(borderR.x - player.getIntWidth());
            } else {
                player.setIntX(player.getIntX() + player.getSpeed());
            }
            player.moveCollision();

            if (!gameStarted) {
                for (Ball ball : balls) {
                    ball.setIntX((player.getIntX() + (player.getIntWidth() / 2)) - (ball.getDIAMETER() / 2));
                }
            }
        }

        // start game
        if (game.getKeyHandler().space && !gameOver) {
            if (!gameStarted) {
                gameStarted = true;
            }
        }

        // toggle debug screen
        if (game.getKeyHandler().p && !keyPressed) {
            debug = !debug;
            keyPressed = true;
        }
        if (!game.getKeyHandler().p) {
            keyPressed = false;
        }
    }

    private void moveBall() {
        for (Ball ball : balls) {
            ball.setIntX((int) (ball.getIntX() - ball.getSpeedX()));
            ball.setIntY((int) (ball.getIntY() - ball.getSpeedY()));

            if (ball.getBounds().intersects(player.getBounds())) {
                player.calculatePlayerBounce(ball);
            }

            if (ball.getBounds().intersects(borderT.getBounds())) {
                ball.setIntY(borderT.y + borderT.height);
                ball.setSpeedY(-ball.getSpeedY());
            }
            if (ball.getBounds().intersects(borderL.getBounds())) {
                ball.setIntX(borderL.x + borderL.width);
                ball.setSpeedX(-ball.getSpeedX());
            }
            if (ball.getBounds().intersects(borderR.getBounds())) {
                ball.setIntX(borderR.x - ball.getDIAMETER());
                ball.setSpeedX(-ball.getSpeedX());
            }
            if (ball.getBounds().intersects(borderB.getBounds())) {
                if(!ball.getDestroy()){
                    ball.setDestroy(true);
                    rmBalls.add(ball);
                    ball.setIntX(10000);
                    ball.setIntY(10000);
                    ball.setSpeedX(0);
                    ball.setSpeedY(0);
                }
            }

            for (Brick brick : bricks) {
                if (ball.getBounds().intersects(brick.getBounds())) {
                    if (!ball.isFire()) {
                        brick.setHp(brick.getHp() - 1);
                        checkBrickBorder(brick, ball);
                    } else {
                        brick.setHp(0);
                    }
                    checkBrick(brick, ball);
                }
            }

            if (abs(ball.getSpeedY()) < 1 && !ball.getDestroy()) {
                if (ball.getSpeedY() < 0) {
                    ball.setSpeedY(-1);
                } else {
                    ball.setSpeedY(1);
                }
            }
            if (abs(ball.getSpeedX()) < 1 && ball.getSpeedX() != 0 && !ball.getDestroy()) {
                if (ball.getSpeedX() < 0) {
                    ball.setSpeedX(-1);
                } else {
                    ball.setSpeedX(1);
                }
            }
        }
    }

    private void checkBalls() {
        if (balls.size() == rmBalls.size()){
            gameStarted = false;
            lives--;
            if (lives <= 0) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            } else {
                reset();
            }
        }
    }

    private void removeBricks(){
        bricks.removeAll(rmBricks);
        rmBricks.clear();
        if (bricks.isEmpty()) {
            gameWon = true;
            gameOverTime = System.currentTimeMillis();
        }
    }

    private void removePowers() {
        powers.removeAll(rmPowers);
        rmPowers.clear();
    }

    private void checkBrickBorder(Brick brick, Ball ball) {
        for (Rectangle border : brick.getBorders()) {
            if (ball.getBounds().intersects(border.getBounds())) {
                calculateBrickBounce(border, brick.getBorders().indexOf(border), ball);
            }
        }
    }

    private void calculateBrickBounce(Rectangle border, int index, Ball ball) {
        switch (index) {
            case 0 -> ball.setIntY(border.y - ball.getDIAMETER());
            case 1 -> ball.setIntY(border.y);
            case 2 -> ball.setIntX(border.x - ball.getDIAMETER());
            case 3 -> ball.setIntX(border.x);
            default -> {
            }
        }

        if (index == 0 || index == 1) {
            ball.setSpeedY(-ball.getSpeedY());
        } else {
            ball.setSpeedX(-ball.getSpeedX());
        }
    }

    private void checkBrick(Brick brick, Ball ball) {
        if (brick.getHp() <= 0) {
            score += brick.getScore();
            rmBricks.add(brick);

            if (ball.isFire()){
                spawnPowerUp(brick,5);
            }
            else {
                spawnPowerUp(brick, 20);
            }
        }
    }

    private void movePowerUps() {
        for (int i = 0; i < powers.size(); i++) {
            PowerUp power = powers.get(i);
            power.y += power.getSpeed();
            if (power.getBounds().intersects(borderB.getBounds())) {
                rmPowers.add(power);
            }
            if (power.getBounds().intersects(player.getBounds())) {
                power.getEffect(player, balls, borders);
                rmPowers.add(power);
                if (power.getId() == 3) {
                    fireTime = System.currentTimeMillis();
                }
            }
        }
    }

    private void spawnPowerUp(Brick brick, int probability) {
        if (random.nextInt(100) <= probability) {
            powerUps.setValid(0, player.getIntWidth() > 50);
            powerUps.setValid(1, player.getIntWidth() < 150);

            boolean valid = false;
            int numberPowerUp = random.nextInt(powerUps.getValid().length);
            while (!valid) {
                numberPowerUp = random.nextInt(powerUps.getValid().length);
                valid = powerUps.getValid()[numberPowerUp];
            }
            PowerUp power = powerUps.getPowerUps().get(numberPowerUp);
            powers.add(new PowerUp(brick.x + brick.width / 2 - 16, brick.y + brick.height, power.getIcon(), power.getId()));
        }
    }

    private void reset() {
        powers.clear();
        balls.clear();
        balls.add(new Ball(player));
        rmBalls.clear();
        player.changeWidth(100,Math.abs(100-player.getIntWidth()));
    }

    private void resetGame() {
        powers.clear();
        createGame();
        createBricks();
        lives = 3;
        score = 0;
        gameOver = false;
    }

    @Override
    public void render(Graphics g) {
        renderBackground(g);
        renderStats(g);

        for (Brick brick : bricks) {
            brick.render(g);
        }
        for (PowerUp power : powers) {
            power.render(g);
        }

        player.render(g);
        for (Ball ball : balls) {
            ball.render(g);
        }

        if (gameOver) {
            renderGameOver(g);
        }
        if (gameWon) {
            renderGameWon(g);
        }
        if (debug) {
            renderDebug(g);
        }
    }

    private void renderBackground(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        g.setColor(new Color(26, 26, 26));
        g.fillRect(WINDOW_WIDTH / 2 - 198, WINDOW_HEIGHT / 2 - 303, 396, 606);

        g.setColor(Color.GRAY);
        g.fillRect(WINDOW_WIDTH / 2 - 195, WINDOW_HEIGHT / 2 - 300, 390, 600);
    }

    private void renderStats(Graphics g) {
        g.setColor(Color.white);
        g.setFont(emulogic.deriveFont(emulogic.getSize() * 15.0F));
        g.drawString("Score:" + score, WINDOW_WIDTH / 2 - 198, 35);

        g.fillOval(392, 15, 20, 20);
        g.drawString("x" + lives, 412, 35);
    }

    private void renderDebug(Graphics g) {
        g.setColor(Color.orange);
        g.setFont(emulogic.deriveFont(emulogic.getSize() * 10.0F));
        g.drawString("Pattern: " + numberPattern, 50, 673);
        g.drawString("Num balls/pows: " + balls.size() + "/" + powers.size(), 50, 683);
        g.drawString("Player width: " + player.getIntWidth(), 50, 693);

        g.setColor(Color.black);
        for (Brick brick : bricks) {
            for (Rectangle border : brick.getBorders()) {
                g.fillRect(border.x, border.y, border.width, border.height);
            }
        }
        renderBorder(g);
        player.renderBorder(g);
        for (Ball ball : balls) {
            ball.renderBorder(g);
        }
        for (PowerUp power : powers) {
            power.renderBorder(g);
        }
    }

    private void renderGameOver(Graphics g) {
        g.setColor(new Color(158, 0, 29));
        g.fillRect(WINDOW_WIDTH / 2 - 75, WINDOW_HEIGHT / 2 - 20, 150, 40);

        g.setColor(new Color(209, 0, 38));
        g.fillRect(WINDOW_WIDTH / 2 - 72, WINDOW_HEIGHT / 2 - 17, 144, 34);

        g.setColor(Color.white);
        g.drawString("GAME OVER", WINDOW_WIDTH / 2 - 70, WINDOW_HEIGHT / 2 + 5);
    }

    private void renderGameWon(Graphics g) {
        g.setColor(new Color(10, 123, 34));
        g.fillRect(WINDOW_WIDTH / 2 - 75, WINDOW_HEIGHT / 2 - 20, 150, 40);

        g.setColor(new Color(15, 186, 51));
        g.fillRect(WINDOW_WIDTH / 2 - 72, WINDOW_HEIGHT / 2 - 17, 144, 34);

        g.setColor(Color.white);
        g.drawString("GAME WON", WINDOW_WIDTH / 2 - 60, WINDOW_HEIGHT / 2 + 5);
    }

    private void renderBorder(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(borderT.x, borderT.y, borderT.width, borderT.height);
        g.fillRect(borderB.x, borderB.y, borderB.width, borderB.height);
        g.fillRect(borderL.x, borderL.y, borderL.width, borderL.height);
        g.fillRect(borderR.x, borderR.y, borderR.width, borderR.height);
    }
}