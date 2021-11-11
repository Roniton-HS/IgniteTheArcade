package Worlds;

import Main.Game;
import Main.TextPrinter;
import Snake.Snake;

import java.awt.*;

public class SnakeWorld extends Worlds {

    boolean startScreen = false;

    Snake snake = new Snake(448, 448, game);

    /**
     * Constructor
     *
     * @param game
     */
    public SnakeWorld(Game game) {
        super(game);


    }

    @Override


    public void tick() {
        if (Snake.gameStart) {
            snake.tick();
        } else {
            startScreen = true;
        }
    }

    @Override
    public void render(Graphics g) {


        g.fillRect(64, 64, 863, 863);
        g.setColor(Color.DARK_GRAY);

        g.drawRect(64, 64, 863, 863);
        g.setColor(Color.white);
        snake.render(g);

        if (startScreen) {
            renderStartScreen(g);
            //restart();
        }


    }

    public void renderStartScreen(Graphics g) {
        g.setColor(Color.gray);

        g.fillRect(80, 300, 831, 100);
        g.setFont(new Font("Monospaced", Font.BOLD, 50));
        g.setColor(Color.black);
        g.drawString("Press Space to start", 140, 350);
        if (game.getKeyHandler().space) {
            TextPrinter.clearText();
            g.clearRect(80, 300, 831, 100);
            Snake.gameStart = true;
            startScreen = false;
        }
    }

    public void restart() {
        SnakeWorld snakeWorld = new SnakeWorld(game);
        Worlds.setWorld(snakeWorld);


        /*for (int i = 0; i < snake.getSnake().size() - 1; i++) {
            snake.getSnake().remove(0);
        }
        snake.start = 0;*/
    }
}
