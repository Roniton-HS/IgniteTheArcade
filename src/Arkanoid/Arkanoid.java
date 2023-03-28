package Arkanoid;

import Main.Game;
import Worlds.Worlds;

import java.awt.*;

import static Main.Constants.pixelFont;

public class Arkanoid extends Worlds {

    private final int WINDOW_WIDTH = 500;
    private final int WINDOW_HEIGHT = 1000;

    private boolean gameStarted = false;
    private boolean gameOver = false;
    private int lives = 1;
    private int score = 0;
    private long gameOverTime;

    private final int PLAYER_SPEED = 5;
    private final int PLAYER_WIDTH = 100;
    private Rectangle player = new Rectangle((WINDOW_WIDTH / 2) - (PLAYER_WIDTH / 2), 900, PLAYER_WIDTH, 10);
    private Rectangle collisionPlayer = new Rectangle(player.x - PLAYER_SPEED, player.y, player.width + (2 * PLAYER_SPEED), player.height);

    private int ballSpeedY = 5;
    private int ballSpeedX = 0;
    private int ballAngle = 0;
    private final int BALL_DIAMETER = 10;
    private Rectangle ball = new Rectangle(WINDOW_WIDTH / 2 - BALL_DIAMETER, 890, BALL_DIAMETER, BALL_DIAMETER);

    private Rectangle borderL = new Rectangle(40, 50, 10, 900);
    private Rectangle borderR = new Rectangle(450, 50, 10, 900);
    private Rectangle borderT = new Rectangle(50, 40, 400, 10);
    private Rectangle borderB = new Rectangle(50, 950, 400, 10);

    /**
     * Constructor
     */
    public Arkanoid(Game game) {
        super(game);
        game.getDisplay().resize(WINDOW_WIDTH + 16, WINDOW_HEIGHT + 39);
    }

    @Override
    public void tick() {

        input();
        if (gameStarted) {
            moveBall();
        }
        if (gameOver){
            gameStarted = false;
            if (System.currentTimeMillis() - gameOverTime > 3000) {
                resetGame();
            }
        }
    }

    private void input() {
        //bar movement
        if (game.getKeyHandler().a) {
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
        if (game.getKeyHandler().d) {
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
        if (game.getKeyHandler().space) {
            if (!gameStarted) {
                gameStarted = true;
            }
        }
    }

    private void moveBall() {
        ball.y -= ballSpeedY;

        if (ball.getBounds().intersects(player.getBounds())) {
            ballSpeedY = -ballSpeedY;
        }

        if (ball.getBounds().intersects(borderT.getBounds())) {
            ballSpeedY = -ballSpeedY;
        }

        if (ball.getBounds().intersects(borderB.getBounds())) {
            gameStarted = false;
            reset();
            lives -= 1;
            if (lives <= 0) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
        }
    }

    private void reset() {
        player.x = (WINDOW_WIDTH / 2) - (PLAYER_WIDTH / 2);
        player.y = 900;

        collisionPlayer.x = player.x - PLAYER_SPEED;
        collisionPlayer.y = player.y;

        ball.x = WINDOW_WIDTH / 2 - BALL_DIAMETER/2;
        ball.y = 890;
    }

    private void resetGame() {
        reset();
        lives = 3;
        score = 0;
        gameOver = false;
    }

    @Override
    public void render(Graphics g) {
        renderBackground(g);
        renderPlayer(g);
        renderStats(g);

        // render borders (only for debugging)
//        g.setColor(Color.red);
//        g.fillRect(borderL.x, borderL.y, borderL.width, borderL.height);
//        g.fillRect(borderR.x, borderR.y, borderR.width, borderR.height);
//        g.fillRect(borderT.x, borderT.y, borderT.width, borderT.height);
//        g.fillRect(borderB.x, borderB.y, borderB.width, borderB.height);

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
        g.setFont(pixelFont.deriveFont(pixelFont.getSize() * 15.0F));
        g.drawString("Score: " + score, 40, 35);

        g.fillOval(400, 15, 20, 20);
        g.drawString("x" + lives, 420, 35);
    }

    private void renderGameOver(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(WINDOW_WIDTH / 2 - 75, WINDOW_HEIGHT / 2 - 20, 150, 40 );
        g.setColor(new Color(96, 6, 6));
        g.drawRect(WINDOW_WIDTH / 2 - 75, WINDOW_HEIGHT / 2 - 20, 150, 40);
        g.setColor(Color.white);
        g.drawString("GAME OVER", WINDOW_WIDTH / 2 - 70, WINDOW_HEIGHT / 2 +5);
    }
}