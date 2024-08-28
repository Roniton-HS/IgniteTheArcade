package Pong;

import Main.Game;
import Worlds.Worlds;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static Main.Constants.*;

public class Pong extends Worlds {
    final private int WINDOW_SIZE = 510;

    private Player playerLeft;
    private Player playerRight;
    private Ball ball;
    private boolean directionX; // false = left, true = right
    private Rectangle borderL, borderR, borderT, borderB;

    private boolean gameStarted = false;
    private boolean keyPressed = false;
    private boolean debug = false;
    private final Random random = new Random();
    private long gameTime, gameOverTime;
    ArrayList<Clip> sounds = new ArrayList<>();
    ArrayList<String> soundPaths = new ArrayList<>();


    public Pong(Game game) {
        super(game, "Pong");
    }

    @Override
    public void init() {
        loadSoundPaths();
        createGame();
        createBorders();
        createStartDirection();
        setStartSpeed();
    }


    private void loadSoundPaths() {
        File folder = new File("res/sounds/pong");
        File[] files = folder.listFiles();
        assert files != null;
        for (File file : files) {
            String path = "res/sounds/pong/" + file.getName();
            soundPaths.add(path);
            /*
            try {
                Clip clip = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
                clip.open(AudioSystem.getAudioInputStream(new File(path)));
                sounds.add(clip);
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                throw new RuntimeException(e);

            }
             */
        }
    }

    private void createGame() {
        playerLeft = new Player(WINDOW_SIZE, true);
        playerRight = new Player(WINDOW_SIZE, false);
        ball = new Ball(WINDOW_SIZE);
    }

    private void createBorders() {
        borderL = new Rectangle(0, 0, 1, WINDOW_SIZE);
        borderR = new Rectangle(WINDOW_SIZE - 1, 0, 1, WINDOW_SIZE);
        borderT = new Rectangle(0, 0, WINDOW_SIZE, 1);
        borderB = new Rectangle(0, WINDOW_SIZE - 1, WINDOW_SIZE, 1);
    }

    private void createStartDirection() {
        directionX = random.nextBoolean();
    }

    private void setStartSpeed() {
        ball.setSpeed(5);
        ball.setSpeedY(0);

        if (!directionX) {
            ball.setSpeedX(-5);
        } else {
            ball.setSpeedX(5);
        }
    }

    @Override
    public void tick() {
        input();
        if (gameStarted) {
            moveBall();
            if (ball.getSpeed() < 10 && System.currentTimeMillis() - gameTime > 5000) {
                ball.setSpeed(ball.getSpeed() + 0.5F);
                gameTime = System.currentTimeMillis();
            }
        }
        if (playerLeft.isWon() || playerRight.isWon()) {
            gameStarted = false;
            if (System.currentTimeMillis() - gameOverTime > 3000) {
                resetGame();
            }
        }
    }

    private void input() {
        // start game
        if (game.getKeyHandler().space && !gameStarted) {
            gameStarted = true;
            gameTime = System.currentTimeMillis();
        }

        // movement left player
        if (game.getKeyHandler().w) {
            if (playerLeft.getCollisionPlayer().getBounds().intersects(borderT.getBounds())) {
                playerLeft.setIntY(borderT.x + borderT.height);
            } else {
                playerLeft.setIntY(playerLeft.getIntY() - playerLeft.getSPEED());
            }
        }
        if (game.getKeyHandler().s) {
            if (playerLeft.getCollisionPlayer().getBounds().intersects(borderB.getBounds())) {
                playerLeft.setIntY(borderB.y - playerLeft.getHEIGHT());
            } else {
                playerLeft.setIntY(playerLeft.getIntY() + playerLeft.getSPEED());
            }
        }
        playerLeft.moveCollision();
        playerLeft.moveBorders();

        // movement right player
        if (game.getKeyHandler().up) {
            if (playerRight.getCollisionPlayer().getBounds().intersects(borderT.getBounds())) {
                playerRight.setIntY(borderT.y + borderT.height);
            } else {
                playerRight.setIntY(playerRight.getIntY() - playerRight.getSPEED());
            }
        }
        if (game.getKeyHandler().down) {
            if (playerRight.getCollisionPlayer().getBounds().intersects(borderB.getBounds())) {
                playerRight.setIntY(borderB.y - playerRight.getHEIGHT());
            } else {
                playerRight.setIntY(playerRight.getIntY() + playerRight.getSPEED());
            }
        }
        playerRight.moveCollision();
        playerRight.moveBorders();

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
        ball.setIntX((int) (ball.getIntX() + ball.getSpeedX()));
        ball.setIntY((int) (ball.getIntY() - ball.getSpeedY()));

        if (ball.getBounds().intersects(playerLeft.getBorderR().getBounds())) {
            playerLeft.calculatePlayerBounce(ball);
            playSound();
        }

        if (ball.getBounds().intersects(playerLeft.getBorderT().getBounds())) {
            ball.setIntY(playerLeft.getBorderT().y + playerLeft.getBorderT().height);
            ball.setSpeedY(-ball.getSpeedY());
            playSound();
        }
        if (ball.getBounds().intersects(playerLeft.getBorderB().getBounds())) {
            ball.setIntY(playerLeft.getBorderB().y - ball.getDIAMETER());
            ball.setSpeedY(-ball.getSpeedY());
            playSound();
        }

        if (ball.getBounds().intersects(playerRight.getBorderL().getBounds())) {
            playerRight.calculatePlayerBounce(ball);
            playSound();
        }

        if (ball.getBounds().intersects(playerRight.getBorderT().getBounds())) {
            ball.setIntY(playerRight.getBorderT().y + playerRight.getBorderT().height);
            ball.setSpeedY(-ball.getSpeedY());
            playSound();
        }
        if (ball.getBounds().intersects(playerRight.getBorderB().getBounds())) {
            ball.setIntY(playerRight.getBorderB().y - ball.getDIAMETER());
            ball.setSpeedY(-ball.getSpeedY());
            playSound();
        }

        if (ball.getBounds().intersects(borderT.getBounds())) {
            ball.setIntY(borderT.y + borderT.height);
            ball.setSpeedY(-ball.getSpeedY());
            playSound();
        }
        if (ball.getBounds().intersects(borderB.getBounds())) {
            ball.setIntY(borderB.y - ball.getDIAMETER());
            ball.setSpeedY(-ball.getSpeedY());
            playSound();
        }

        if (ball.getBounds().intersects(borderL.getBounds())) {
            directionX = true;
            playerRight.addPoint();
            if (playerRight.checkScore()) {
                gameOverTime = System.currentTimeMillis();
            }
            reset();
        }
        if (ball.getBounds().intersects(borderR.getBounds())) {
            directionX = false;
            playerLeft.addPoint();
            if (playerLeft.checkScore()) {
                gameOverTime = System.currentTimeMillis();
            }
            reset();
        }

        if (Math.abs(ball.getSpeedX()) < 1) {
            if (ball.getSpeedX() < 0) {
                ball.setSpeedX(-1);
            } else {
                ball.setSpeedX(1);
            }
        }

        if (ball.getSpeedY() != 0 && Math.abs(ball.getSpeedY()) < 1) {
            if (ball.getSpeedY() < 0) {
                ball.setSpeedY(-1);
            } else {
                ball.setSpeedY(1);
            }
        }
    }

    //TODO sound delay bug
    //it seams that the fist sound is delayed bc the thread isn't stated yet.
    //after escaping out of the game and re-enter it the first sound isn't delayed
    private void playSound() {
        int index = random.nextInt(soundPaths.size());
        new Thread(() -> {
            try {
                Clip clip = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
                clip.open(AudioSystem.getAudioInputStream(new File(soundPaths.get(index))));
                clip.start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            /*
            int index2 = random.nextInt(sounds.size());
            Clip clip = sounds.get(index2);
            try {
                clip.open();
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
            clip.start();
             */
        }).start();
    }

    private void reset() {
        gameStarted = false;
        if (!directionX) {
            ball.setIntX(playerRight.getIntX() - ball.getDIAMETER());
            ball.setIntY(playerRight.getIntY() + playerRight.getHEIGHT() / 2 - ball.getDIAMETER() / 2);
        } else {
            ball.setIntX(playerLeft.getIntX() + ball.getDIAMETER());
            ball.setIntY(playerLeft.getIntY() + playerLeft.getHEIGHT() / 2 - ball.getDIAMETER() / 2);
        }
        setStartSpeed();
    }

    private void resetGame() {
        createGame();
        createStartDirection();
        setStartSpeed();
    }

    @Override
    public void render(Graphics g) {
        renderBackground(g);
        ball.render(g);
        renderPlayer(g);
        renderScore(g);
        if (debug) {
            renderBorders(g);
            renderDebug(g);
        }
        if (playerLeft.isWon()) {
            renderGameOver(g, playerLeft);
        } else if (playerRight.isWon()) {
            renderGameOver(g, playerRight);
        }
    }

    private void renderBackground(Graphics g) {
        g.setColor(ALMOST_BLACK);
        g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE);
        g.setColor(ALMOST_WHITE);

        final int WIDTH = 4;
        final int HEIGHT = 10;
        for (int i = 0; i < 21; i++) {
            g.fillRect(WINDOW_SIZE / 2 - WIDTH / 2, (int) (2.5 * HEIGHT * i), WIDTH, HEIGHT);
        }
    }

    private void renderPlayer(Graphics g) {
        playerLeft.render(g);
        playerRight.render(g);
    }

    private void renderScore(Graphics g) {
        g.setColor(Color.white);
        g.setFont(emulogic.deriveFont(emulogic.getSize() * 50.0F));
        g.drawString(playerLeft.createScore(), 140, 60);
        g.drawString(playerRight.createScore(), 263, 60);
    }

    private void renderBorders(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(borderR.x, borderR.y, borderR.width, borderR.height);
        g.drawRect(borderL.x, borderL.y, borderL.width, borderL.height);
        g.drawRect(borderT.x, borderT.y, borderT.width, borderT.height);
        g.drawRect(borderB.x, borderB.y, borderB.width, borderB.height);
        playerLeft.renderBorder(g);
        playerRight.renderBorder(g);
        ball.renderBorder(g);
    }

    private void renderDebug(Graphics g) {
        g.setColor(Color.orange);
        g.setFont(emulogic.deriveFont(emulogic.getSize() * 10.0F));
        g.drawString("X: " + ball.getIntX(), 10, 460);
        g.drawString("Y: " + ball.getIntY(), 10, 470);
        g.drawString("SpeedX: " + ball.getSpeedX(), 10, 480);
        g.drawString("SpeedY: " + ball.getSpeedY(), 10, 490);
        g.drawString("Speed: " + Math.sqrt(Math.pow(ball.getSpeedX(), 2) + Math.pow(ball.getSpeedY(), 2)), 10, 500);
    }

    private void renderGameOver(Graphics g, Player player) {
        g.setColor(new Color(10, 123, 34));
        g.fillRect(WINDOW_SIZE / 2 - 75, WINDOW_SIZE / 2 - 20, 150, 40);

        g.setColor(new Color(15, 186, 51));
        g.fillRect(WINDOW_SIZE / 2 - 72, WINDOW_SIZE / 2 - 17, 144, 34);

        g.setColor(Color.white);
        g.setFont(emulogic.deriveFont(emulogic.getSize() * 15.0F));
        g.drawString(player.createWon(), WINDOW_SIZE / 2 - 50, WINDOW_SIZE / 2 + 5);
    }
}
