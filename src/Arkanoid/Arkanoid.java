package Arkanoid;

import Main.Constants;
import Main.Game;
import Worlds.Worlds;

import java.awt.*;

public class Arkanoid extends Worlds {

    final int PLAYER_SPEED = 5;
    Rectangle player = new Rectangle(500, 500, 50, 50);
    Rectangle collision = new Rectangle(player.x - 10, player.y, player.width + (2*10), player.height);
    Rectangle obj = new Rectangle(100, 503, 50, 44);

    /**
     * Constructor
     */
    public Arkanoid(Game game) {
        super(game);
        game.getDisplay().resize(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
    }

    @Override
    public void tick() {

        if (game.getKeyHandler().a) {
            if (player.getBounds().intersects(obj.getBounds())) {
                //System.out.println(obj.x + obj.width);
                player.x = obj.x + obj.width + 1;
                collision.x = player.x-10;
            }
            player.x -= PLAYER_SPEED;
            collision.x -= PLAYER_SPEED;
        }
        if (game.getKeyHandler().d) {
            player.x += PLAYER_SPEED;
            collision.x += PLAYER_SPEED;
        }

        if (collision.getBounds().intersects(obj.getBounds())) {
            System.out.println("Collision");
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(player.x, player.y, player.width, player.height);
        //g.fillOval(500,500,200,200);
        g.setColor(Color.red);
        g.fillRect(obj.x, obj.y, obj.width, obj.height);
        g.setColor(Color.GRAY);
        g.drawRect(collision.x, collision.y, collision.width, collision.height);
    }
}
