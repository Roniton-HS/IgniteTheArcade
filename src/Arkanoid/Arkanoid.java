package Arkanoid;

import Main.Game;
import Worlds.Worlds;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static Main.Constants.emulogic;
import static java.lang.Math.PI;
import static java.lang.Math.abs;

public class Arkanoid extends Worlds {

    private final int WINDOW_WIDTH = 500;
    private final int WINDOW_HEIGHT = 1000;

    private boolean gameStarted = false;
    private boolean gameOver = false;
    private boolean gameWon = false;
    private boolean debug = false;
    private int lives = 3;
    private int score = 0;
    private long gameOverTime;
    private long gameWonTime;

    private final int PLAYER_SPEED = 5;
    private Rectangle player;
    private Rectangle collisionPlayer;

    private final int BALL_DIAMETER = 10;
    private Ball ball;

    private final Rectangle borderL = new Rectangle(40, 50, 10, 900);
    private final Rectangle borderR = new Rectangle(450, 50, 10, 900);
    private final Rectangle borderT = new Rectangle(50, 40, 400, 10);
    private final Rectangle borderB = new Rectangle(50, 950, 400, 10);

    private final Pattern pattern = new Pattern();
    private ArrayList<Brick> bricks;
    private int index;

    /**
     * Constructor
     */
    public Arkanoid(Game game) {
        super(game);
        game.getDisplay().resize(WINDOW_WIDTH + 16, WINDOW_HEIGHT + 39);
        createGame();
        pattern.createPattern();
        createBricks();
    }

    private void createGame() {
        int PLAYER_WIDTH = 100;
        player = new Rectangle((WINDOW_WIDTH / 2) - (PLAYER_WIDTH / 2), 900, PLAYER_WIDTH, 10);
        collisionPlayer = new Rectangle(player.x - PLAYER_SPEED, player.y, player.width + (2 * PLAYER_SPEED), player.height);
        ball = new Ball(WINDOW_WIDTH / 2 - BALL_DIAMETER / 2, 890, BALL_DIAMETER);
    }

    private void createBricks() {
        Random random = new Random();
        index = random.nextInt(pattern.getPatterns().size());
        bricks = pattern.getPatterns().get(index);
    }

    @Override
    public void tick() {
        input();
        if (gameStarted) {
            moveBall();
            checkBrick();
        }
        if (gameOver) {
            gameStarted = false;
            if (System.currentTimeMillis() - gameOverTime > 3000) {
                resetGame();
            }
        }
        if (gameWon) {
            gameStarted = false;
            if (System.currentTimeMillis() - gameWonTime > 3000) {
                reset();
                createBricks();
                gameWon = false;
            }
        }
    }

    private void input() {
        //bar movement
        if (game.getKeyHandler().a && !gameOver) {
            if (collisionPlayer.getBounds().intersects(borderL.getBounds())) {
                player.x = borderL.x + borderL.width;
                collisionPlayer.x = player.x - PLAYER_SPEED;
            } else {
                player.x -= PLAYER_SPEED;
                collisionPlayer.x -= PLAYER_SPEED;
            }
            if (!gameStarted) {
                ball.x = player.x + player.width / 2 - BALL_DIAMETER / 2;
            }
        }
        if (game.getKeyHandler().d && !gameOver) {
            if (collisionPlayer.getBounds().intersects(borderR.getBounds())) {
                player.x = borderR.x - player.width;
                collisionPlayer.x = player.x - PLAYER_SPEED;
            } else {
                player.x += PLAYER_SPEED;
                collisionPlayer.x += PLAYER_SPEED;
            }
            if (!gameStarted) {
                ball.x = player.x + player.width / 2 - BALL_DIAMETER / 2;
            }
        }

        // start game
        if (game.getKeyHandler().space && !gameOver) {
            if (!gameStarted) {
                gameStarted = true;
            }
        }

        // toggle debug screen
        if (game.getKeyHandler().p) {
            debug = !debug;
        }
    }

    private void moveBall() {
        ball.x -= ball.getSpeedX();
        ball.y -= ball.getSpeedY();

        if (ball.getBounds().intersects(player.getBounds())) {
            calculatePlayerBounce();
        }

        if (ball.getBounds().intersects(borderT.getBounds())) {
            ball.y = borderT.y + borderT.height;
            ball.setSpeedY(-ball.getSpeedY());
        }
        if (ball.getBounds().intersects(borderL.getBounds())) {
            ball.x = borderL.x + borderL.width;
            ball.setSpeedX(-ball.getSpeedX());
        }
        if (ball.getBounds().intersects(borderR.getBounds())) {
            ball.x = borderR.x - ball.width;
            ball.setSpeedX(-ball.getSpeedX());
        }
        if (ball.getBounds().intersects(borderB.getBounds())) {
            gameStarted = false;
            lives--;
            if (lives <= 0) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            } else {
                reset();
            }
        }

        for (Brick brick : bricks) {
            if (ball.getBounds().intersects(brick.getBounds())) {
                brick.setHp(brick.getHp() - 1);
                checkBrickBorder(brick);
            }
        }

        if (abs(ball.getSpeedY()) < 1) {
            if (ball.getSpeedY() < 0) {
                ball.setSpeedY(-1);
            } else {
                ball.setSpeedY(1);
            }
        }
        if (abs(ball.getSpeedX()) < 1 && ball.getSpeedX() != 0) {
            if (ball.getSpeedX() < 0) {
                ball.setSpeedX(-1);
            } else {
                ball.setSpeedX(1);
            }
        }
    }

    private void calculatePlayerBounce() {
        int BALL_SPEED = 5;
        double MAX_ANGLE = 75 * PI / 180;

        int relativeCollision = -(player.x - ball.x + player.width / 2);
        double normRelativeCollision = relativeCollision / (player.width / 2.0);

        ball.setAngle(normRelativeCollision * MAX_ANGLE);

        ball.y = player.y - ball.height;
        ball.setSpeedX(-Math.sin(ball.getAngle()) * BALL_SPEED);
        ball.setSpeedY(Math.cos(ball.getAngle()) * BALL_SPEED);
    }

    private void checkBrickBorder(Brick brick) {
        for (Rectangle border : brick.getBorders()) {
            if (ball.getBounds().intersects(border.getBounds())) {
                calculateBrickBounce(border, brick.getBorders().indexOf(border));
            }
        }
    }

    private void calculateBrickBounce(Rectangle border, int index) {

        if (index == 0 || index == 1) {
            ball.setSpeedY(-ball.getSpeedY());
        } else {
            ball.setSpeedX(-ball.getSpeedX());
        }

        if (index == 0) {
            ball.y = border.y - ball.height;
        } else if (index == 1) {
            ball.y = border.y;
        } else if (index == 2) {
            ball.x = border.x - ball.width;
        } else if (index == 3) {
            ball.x = border.x;
        }
    }

    private void checkBrick() {
        for (int i = 0; i < bricks.size(); i++) {
            Brick brick = bricks.get(i);
            if (brick.getHp() <= 0) {
                score += brick.getScore();
                bricks.remove(brick);
                if (bricks.size() == 0) {
                    gameWon = true;
                    gameWonTime = System.currentTimeMillis();
                }
            }
        }
    }

    private void reset() {
        ball.x = player.x + player.width / 2 - BALL_DIAMETER / 2;
        ball.y = 890;
        ball.setSpeedX(0);
        ball.setSpeedY(5);
    }

    private void resetGame() {
        createGame();
        createBricks();
        lives = 3;
        score = 0;
        gameOver = false;
    }

    @Override
    public void render(Graphics g) {
        renderBackground(g);
        renderPlayer(g);
        renderStats(g);
        for (Brick brick : bricks) {
            brick.render(g);
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
        for (int i = 0; i < 3; i++) {
            g.drawRect(47 + i, 47 + i, WINDOW_WIDTH - 94 - (2 * i), WINDOW_HEIGHT - 94 - (2 * i));
        }

        g.setColor(Color.GRAY);
        g.fillRect(50, 50, WINDOW_WIDTH - 100, WINDOW_HEIGHT - 100);
    }

    private void renderPlayer(Graphics g) {
        g.setColor(new Color(233, 0, 185));
        g.fillRect(player.x, player.y, player.width, player.height);

        g.setColor(new Color(148, 0, 118));
        for (int i = 0; i < 3; i++) {
            g.drawRect(player.x + i, player.y + i, player.width - 2 * i, player.height - 2 * i);
        }

        g.setColor(Color.white);
        g.fillOval(ball.x, ball.y, ball.width, ball.height);
    }

    private void renderStats(Graphics g) {
        g.setColor(Color.white);
        g.setFont(emulogic.deriveFont(emulogic.getSize() * 15.0F));
        g.drawString("Score:" + score, 50, 35);

        g.fillOval(400, 15, 20, 20);
        g.drawString("x" + lives, 420, 35);
    }

    private void renderDebug(Graphics g) {
        g.setColor(Color.white);
        g.drawString("Pattern: " + index, 50, 900);
        g.drawString("X: " + ball.getSpeedX(), 50, 915);
        g.drawString("Y: " + ball.getSpeedY(), 50, 930);
        g.drawString("Speed: " + Math.sqrt(Math.pow(ball.getSpeedX(), 2) + Math.pow(ball.getSpeedY(), 2)), 50, 945);

        g.setColor(Color.black);
        for (Brick brick : bricks) {
            for (Rectangle border : brick.getBorders()) {
                g.fillRect(border.x, border.y, border.width, border.height);
            }
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
}