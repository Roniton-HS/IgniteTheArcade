package FlappyBird;

import Main.Game;
import Worlds.Worlds;

import java.awt.*;
import java.util.ArrayList;

import static Main.Constants.*;

public class FlappyBird extends Worlds {
    final private int WINDOW_WIDTH = 1000;
    final private int WINDOW_HEIGHT = 750;
    private Player player;
    private Rectangle borderB, borderT;
    private ArrayList<Pipe> pipes = new ArrayList<>();
    private boolean spacePressed = false;
    private boolean pPressed = false;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private long gameOverTime;
    private boolean debug = false;
    private int score;
    private int highScore;
    private boolean isHighScore = false;

    public FlappyBird(Game game) {
        super(game, "Flappy Bird");
    }

    @Override
    public void init() {
        game.getDisplay().resize(WINDOW_WIDTH, WINDOW_HEIGHT);
        createGame();
    }

    private void createGame() {
        player = new Player(WINDOW_HEIGHT);
        borderB = new Rectangle(0, WINDOW_HEIGHT - 50, WINDOW_WIDTH, 1);
        borderT = new Rectangle(0, 0, WINDOW_WIDTH, 1);
        createPipes();
    }

    private void createPipes() {
        pipes.add(new Pipe(0));
        pipes.add(new Pipe(pipes.get(pipes.size() - 1).getIntX()));
        pipes.add(new Pipe(pipes.get(pipes.size() - 1).getIntX()));
    }

    @Override
    public void tick() {
        input();
        if (gameStarted) {
            movePlayer();
            movePipes();
            checkPipes();
            checkCollision();
        }

        if (gameOver) {
            gameStarted = false;
            if (score > highScore){
                highScore = score;
                isHighScore = true;
            }

            if (System.currentTimeMillis() - gameOverTime > 3000){
                resetGame();
            }
        }
    }

    private void input() {
        if (game.getKeyHandler().space && !gameOver && !spacePressed) {
            spacePressed = true;
            if (!gameStarted) {
                gameStarted = true;
            }

            //jump
            player.setSpeed(-20);
        }

        if (!game.getKeyHandler().space) {
            spacePressed = false;
        }

        if (game.getKeyHandler().p && !pPressed) {
            debug = !debug;
            pPressed = true;
        }

        if (!game.getKeyHandler().p) {
            pPressed = false;
        }
    }

    private void movePlayer() {
        if (player.getSpeed() < 10) {
            player.setSpeed(player.getSpeed() + player.getAcceleration());
        }

        player.setIntY(player.getIntY() + player.getSpeed());
    }

    private void movePipes() {
        for (Pipe pipe : pipes) {
            pipe.updatePipe(pipe.getIntX() - pipe.getSPEED());
        }
    }

    private void checkPipes() {
        if (pipes.get(pipes.size() - 1).getIntX() < WINDOW_WIDTH) {
            pipes.add(new Pipe(pipes.get(pipes.size() - 1).getIntX()));
        }
        if (pipes.get(0).getIntX() < -100) {
            pipes.remove(0);
        }
    }

    private void checkCollision() {
        if (player.getBounds().intersects(borderB.getBounds())) {
            gameOver = true;
            gameOverTime = System.currentTimeMillis();
            player.setIntY(borderB.y - player.getBIRD_SIZE());
        }

        if (player.getBounds().intersects(borderT.getBounds())) {
            player.setIntY(borderT.y);
        }

        for (Pipe pipe : pipes) {
            if (player.getBounds().intersects(pipe.getPipeT().getBounds()) || player.getBounds().intersects(pipe.getPipeB().getBounds())) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
            if (player.getBounds().intersects(pipe.getScoreBorder().getBounds())) {
                score++;
                pipe.delScoreBorder();
            }
        }
    }

    private void resetGame() {
        player.setIntY((WINDOW_HEIGHT-100)/2);
        pipes.clear();
        createPipes();
        score = 0;
        gameOver = false;
    }

    @Override
    public void render(Graphics g) {
        renderBackground(g);
        for (Pipe pipe : pipes) {
            pipe.render(g);
        }
        player.render(g);
        renderScore(g);

        if(gameOver){
            renderGameOver(g);
        }
    }

    private void renderBackground(Graphics g) {
        g.setColor(LIGHT_BLUE);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g.setColor(LIGHT_GREEN);
        g.fillRect(borderB.x, borderB.y, borderT.width, WINDOW_HEIGHT - borderB.y);
    }

    private void renderScore(Graphics g) {
        g.setColor(ALMOST_WHITE);
        g.setFont(emulogic.deriveFont(emulogic.getSize() * 60.0F));
        if (score < 10) {
            g.drawString("" + score, WINDOW_WIDTH / 2 - 32, 100);
        } else {
            g.drawString("" + score, WINDOW_WIDTH / 2 - 64, 100);
        }

        g.setFont(emulogic.deriveFont(emulogic.getSize() * 20.0F));
        g.drawString("High Score:"+highScore, WINDOW_WIDTH-300,20);

    }

    private void renderGameOver(Graphics g){
        g.setFont(emulogic.deriveFont(emulogic.getSize() * 50.0F));
        g.setColor(new Color(158, 0, 29));
        g.fillRect(WINDOW_WIDTH / 2 - 230, WINDOW_HEIGHT / 2 - 50, 465, 65);

        g.setColor(new Color(209, 0, 38));
        g.fillRect(WINDOW_WIDTH / 2 - 225, WINDOW_HEIGHT / 2 - 45, 455, 55);

        g.setColor(Color.white);
        g.drawString("GAME OVER", WINDOW_WIDTH / 2 - 225, WINDOW_HEIGHT / 2 + 5);

        if (isHighScore){

        }
    }
}
