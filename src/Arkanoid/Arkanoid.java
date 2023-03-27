package Arkanoid;

import Main.Game;
import Worlds.Worlds;

import java.awt.*;

public class Arkanoid extends Worlds {

    final int WINDOW_WIDTH = 500;
    final int WINDOW_HEIGHT = 1000;
    final int PLAYER_SPEED = 5;
    Rectangle player = new Rectangle(200, 900, 100, 10);
    Rectangle collision = new Rectangle(player.x - PLAYER_SPEED, player.y, player.width + (2 * PLAYER_SPEED), player.height);
    Rectangle borderL = new Rectangle(40, 50, 10, 900);
    Rectangle borderR = new Rectangle(450, 50, 10, 900);
    Rectangle borderT = new Rectangle(50, 40, 400, 10);
    Rectangle borderB = new Rectangle(50, 950, 400, 10);

    /**
     * Constructor
     */
    public Arkanoid(Game game) {
        super(game);
        game.getDisplay().resize(WINDOW_WIDTH+16, WINDOW_HEIGHT+39);
    }

    @Override
    public void tick() {

        if (game.getKeyHandler().a) {
            if (collision.getBounds().intersects(borderL.getBounds())) {
                player.x = borderL.x + borderL.width;
                collision.x = player.x - PLAYER_SPEED;
            } else {
                player.x -= PLAYER_SPEED;
                collision.x -= PLAYER_SPEED;
            }
        }
        if (game.getKeyHandler().d) {
            if (collision.getBounds().intersects(borderR.getBounds())) {
                player.x = borderR.x - player.width;
                collision.x = player.x - PLAYER_SPEED;
            } else {
                player.x += PLAYER_SPEED;
                collision.x += PLAYER_SPEED;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        g.setColor(Color.GRAY);
        g.fillRect(50, 50, WINDOW_WIDTH - 100, WINDOW_HEIGHT - 100);


        g.setColor(Color.blue);
        g.fillRect(player.x, player.y, player.width, player.height);
        //g.fillOval(500,500,200,200);
        g.setColor(Color.red);
        g.fillRect(borderL.x, borderL.y, borderL.width, borderL.height);
        g.fillRect(borderR.x, borderR.y, borderR.width, borderR.height);
        g.fillRect(borderT.x, borderT.y, borderT.width, borderT.height);
        g.fillRect(borderB.x, borderB.y, borderB.width, borderB.height);
    }
}
