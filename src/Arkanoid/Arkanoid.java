package Arkanoid;

import Main.Game;
import Worlds.Worlds;

import java.awt.*;
import java.util.ArrayList;

import static Main.Constants.emulogic;
import static java.lang.Math.PI;

public class Arkanoid extends Worlds {

    private final int WINDOW_WIDTH = 500;
    private final int WINDOW_HEIGHT = 1000;

    private boolean gameStarted = false;
    private boolean gameOver = false;
    private int lives = 3;
    private int score = 0;
    private long gameOverTime;

    private final int PLAYER_SPEED = 5;
    private final int PLAYER_WIDTH = 100;
    private Rectangle player;
    private Rectangle collisionPlayer;

    private final int BALL_SPEED = 5;
    private int ballSpeedX = 0;
    private int ballAngle = 0;
    private final int BALL_DIAMETER = 10;
    private final double MAX_ANGLE = 75 * PI / 180;
    private Ball ball;

    private Rectangle borderL = new Rectangle(40, 50, 10, 900);
    private Rectangle borderR = new Rectangle(450, 50, 10, 900);
    private Rectangle borderT = new Rectangle(50, 40, 400, 10);
    private Rectangle borderB = new Rectangle(50, 950, 400, 10);

    private ArrayList<Brick> bricks = new ArrayList<>();

    /**
     * Constructor
     */
    public Arkanoid(Game game) {
        super(game);
        game.getDisplay().resize(WINDOW_WIDTH + 16, WINDOW_HEIGHT + 39);
        createGame();
        createBricks();
    }

    private void createGame() {
        player = new Rectangle((WINDOW_WIDTH / 2) - (PLAYER_WIDTH / 2), 900, PLAYER_WIDTH, 10);
        collisionPlayer = new Rectangle(player.x - PLAYER_SPEED, player.y, player.width + (2 * PLAYER_SPEED), player.height);
        ball = new Ball(WINDOW_WIDTH / 2 - BALL_DIAMETER / 2, 890, BALL_DIAMETER, BALL_DIAMETER);
    }

    private void createBricks() {
        bricks.add(new Brick(60, 60, 60, 10, 1));
        bricks.add(new Brick(140, 60, 60, 10, 2));
        bricks.add(new Brick(220, 60, 60, 10, 3));
        bricks.add(new Brick(300, 60, 60, 10, 4));
        bricks.add(new Brick(380, 60, 60, 10, 5));
    }

    @Override
    public void tick() {
        input();
        if (gameStarted) {
            moveBall();
            checkHp();
        }
        if (gameOver) {
            gameStarted = false;
            if (System.currentTimeMillis() - gameOverTime > 3000) {
                resetGame();
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
    }

    private void moveBall() {
        ball.y -= ball.getSpeedX();
        ball.y -= ball.getSpeedY();

        if (ball.getBounds().intersects(player.getBounds())) {
            calculatePlayerBounce();
        }

        if (ball.getBounds().intersects(borderT.getBounds())) {
            calculateBorderBounce();
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
            }
        }
    }

    private void calculatePlayerBounce() {
        int relativeCollision = player.x - ball.x - player.width / 2;
        int normRelativeCollision = relativeCollision / (player.width / 2);

        ball.setAngle(normRelativeCollision * MAX_ANGLE);

        ball.setSpeedX(Math.cos(ball.getAngle()) * BALL_SPEED);
        ball.setSpeedY(-Math.sin(ball.getAngle()) * BALL_SPEED);

    }

    private void calculateBorderBounce() {
        ball.setSpeedX(Math.cos(PI / 2 + (PI / 2 - (ball.getAngle()))) * BALL_SPEED);
        ball.setSpeedY(-Math.sin(PI / 2 + (PI / 2 - (ball.getAngle()))) * BALL_SPEED);
    }

    private void checkHp() {
        for (int i = 0; i < bricks.size(); i++) {
            Brick brick = bricks.get(i);
            if (brick.getHp() <= 0) {
                score += brick.getScore();
                bricks.remove(brick);
            }
        }
    }

    private void reset() {
        ball.x = player.x + player.width / 2 - BALL_DIAMETER / 2;
        ball.y = 890;
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
    }

    private void renderBackground(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g.setColor(Color.GRAY);
        g.fillRect(50, 50, WINDOW_WIDTH - 100, WINDOW_HEIGHT - 100);
    }

    private void renderPlayer(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(player.x, player.y, player.width, player.height);
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

    private void renderGameOver(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(WINDOW_WIDTH / 2 - 75, WINDOW_HEIGHT / 2 - 20, 150, 40);
        g.setColor(new Color(96, 6, 6));
        g.drawRect(WINDOW_WIDTH / 2 - 75, WINDOW_HEIGHT / 2 - 20, 150, 40);
        g.setColor(Color.white);
        g.drawString("GAME OVER", WINDOW_WIDTH / 2 - 70, WINDOW_HEIGHT / 2 + 5);
    }
}