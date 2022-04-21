package Worlds;

import Main.Game;
import PacMan.Player;
import PacMan.Ghost;

import java.awt.*;
import java.util.ArrayList;

public class PacMan extends Worlds {

    private final int width = 19;
    private final int height = 25;

    static public int getBlockSize() {
        return blockSize;
    }

    static private final int blockSize = 38;

    private final int maxPoints;

    private static ArrayList points = new ArrayList();

    public static ArrayList worldBounds = new ArrayList();

    public static ArrayList ghosts = new ArrayList();

    Rectangle left = new Rectangle(101 - blockSize, 10 + 12 * blockSize, blockSize, blockSize);
    Rectangle right = new Rectangle(139 + 20 * blockSize, 10 + 12 * blockSize, blockSize, blockSize);

    public static ArrayList getWorldBounds() {
        return worldBounds;
    }



    static Player player;

    public static Player getPlayer() {
        return player;
    }
    /**
     * Constructor
     */
    public PacMan(Game game) {
        super(game);
        player = new Player(139 + blockSize, 10 + blockSize, game);
        ghosts.add(new Ghost(139 + 5 * blockSize, 10 + blockSize, 1, game));
        ghosts.add(new Ghost(139 + 10 * blockSize, 10 + blockSize, 2, game));
        ghosts.add(new Ghost(139 + 4 * blockSize, 10 + 20 * blockSize, 3, game));
        ghosts.add(new Ghost(139 + 4 * blockSize, 10 + 20 * blockSize, 4, game));
        setWorldBounds();
        setPoints();
        maxPoints = points.size();
    }

    @Override
    public void tick() {
        checkPoints();
        player.tick();
        tickGhosts();
        teleport();
    }

    private void tickGhosts(){
        for (Object o : ghosts) {
            Ghost ghost = (Ghost) o;
            ghost.tick();
        }
        checkGhosts();
    }

    @Override
    public void render(Graphics g) {
        renderBorders(g);
        renderPoints(g);
        player.render(g);
        renderGhosts(g);
        pointsEaten(g);
    }

    private void checkGhosts(){
        for (Object o : ghosts) {
            Ghost ghost = (Ghost) o;
            if (player.getBounds().intersects(ghost.getBounds())) {
                player.setCords(139 + blockSize, 10 + blockSize);
            }
        }

    }

    private void renderGhosts(Graphics g){
        for (Object o : ghosts) {
            Ghost ghost = (Ghost) o;
            ghost.render(g);
        }
    }
    private void pointsEaten(Graphics g){
        g.setColor(Color.black);
        g.drawString("Points:" + (maxPoints - points.size() + " / " + maxPoints),10, 10);
    }

    private void teleport() {
        if (player.getNextBound().intersects(left.getBounds())) {
            player.setX(player.getX() + 20 * blockSize);
        } else if (player.getNextBound().intersects(right.getBounds())) {
            player.setX(player.getX() - 20 * blockSize);
        }
    }

    private void setPoints() {
        for (int i = 0; i < 17; i++) {
            points.add(new Rectangle(194 + i * blockSize, 27 + blockSize, 5, 5));
        }
        for (int i = 0; i < 17; i++) {
            points.add(new Rectangle(194 + i * blockSize, 27 + 4 * blockSize, 5, 5));
        }
        for (int i = 0; i < 17; i++) {
            points.add(new Rectangle(194 + i * blockSize, 27 + 20 * blockSize, 5, 5));
        }
        for (int i = 0; i < 17; i++) {
            points.add(new Rectangle(194 + i * blockSize, 27 + 23 * blockSize, 5, 5));
        }
        for (int i = 0; i < 23; i++) {
            points.add(new Rectangle(194 + 3 * blockSize, 65 + i * blockSize, 5, 5));
        }
        for (int i = 0; i < 23; i++) {
            points.add(new Rectangle(194 + 13 * blockSize, 65 + i * blockSize, 5, 5));
        }
        for (int i = 0; i < 6; i++) {
            points.add(new Rectangle(194, 65 + i * blockSize, 5, 5));
        }
        for (int i = 0; i < 6; i++) {
            points.add(new Rectangle(194 + 16 * blockSize, 65 + i * blockSize, 5, 5));
        }
        for (int i = 0; i < 6; i++) {
            points.add(new Rectangle(194, 65 + (i + 17) * blockSize, 5, 5));
        }
        for (int i = 0; i < 6; i++) {
            points.add(new Rectangle(194 + 16 * blockSize, 65 + (i + 17) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + 9 * blockSize, 65 + (i + 1) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + 7 * blockSize, 65 + (i + 1) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + 9 * blockSize, 65 + (i + 20) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + 7 * blockSize, 65 + (i + 20) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + (1 + i) * blockSize, 65 + 5 * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + (14 + i) * blockSize, 65 + 5 * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + (1 + i) * blockSize, 65 + 17 * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + (14 + i) * blockSize, 65 + 17 * blockSize, 5, 5));
        }
        for (int i = 0; i < 3; i++) {
            points.add(new Rectangle(194 + 5 * blockSize, 65 + (4 + i) * blockSize, 5, 5));
        }
        for (int i = 0; i < 3; i++) {
            points.add(new Rectangle(194 + 11 * blockSize, 65 + (4 + i) * blockSize, 5, 5));
        }
        for (int i = 0; i < 3; i++) {
            points.add(new Rectangle(194 + 5 * blockSize, 65 + (16 + i) * blockSize, 5, 5));
        }
        for (int i = 0; i < 3; i++) {
            points.add(new Rectangle(194 + 11 * blockSize, 65 + (16 + i) * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + (6 + i) * blockSize, 65 + 6 * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + (9 + i) * blockSize, 65 + 6 * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + (6 + i) * blockSize, 65 + 16 * blockSize, 5, 5));
        }
        for (int i = 0; i < 2; i++) {
            points.add(new Rectangle(194 + (9 + i) * blockSize, 65 + 16 * blockSize, 5, 5));
        }

    }

    private void renderPoints(Graphics g) {
        for (Object o : points) {
            Rectangle point = (Rectangle) o;
            g.setColor(Color.white);
            g.fillRect(point.getBounds().x, point.getBounds().y, point.getBounds().width, point.getBounds().height);
        }
    }

    private void checkPoints() {
        if (points.size() == 0) {
            System.exit(0);
        }
        for (int i = 0; i < points.size(); i++) {
            Rectangle point = (Rectangle) points.get(i);
            if (player.getNextBound().intersects(point.getBounds())) {
                points.remove(point);
            }
        }
    }

    public void setWorldBounds() {
        worldBounds.add(new Rectangle(139, 10, blockSize * width, blockSize));
        worldBounds.add(new Rectangle(139, 10 + blockSize, blockSize, blockSize * 7));
        worldBounds.add(new Rectangle(139 + 3 * blockSize, 10 + 7 * blockSize, blockSize, 5 * blockSize));
        worldBounds.add(new Rectangle(139 + 3 * blockSize, 10 + 13 * blockSize, blockSize, 5 * blockSize));
        worldBounds.add(new Rectangle(139, 10 + 18 * blockSize, blockSize, 6 * blockSize));
        worldBounds.add(new Rectangle(139, 10 + (height - 1) * blockSize, blockSize * width, blockSize));
        worldBounds.add(new Rectangle(139 + (width - 1) * blockSize, 10 + blockSize, blockSize, blockSize * 7));
        worldBounds.add(new Rectangle(139 + (width - 4) * blockSize, 10 + 7 * blockSize, blockSize, blockSize * 5));
        worldBounds.add(new Rectangle(139 + (width - 4) * blockSize, 10 + 13 * blockSize, blockSize, blockSize * 5));
        worldBounds.add(new Rectangle(139 + (width - 1) * blockSize, 10 + 18 * blockSize, blockSize, blockSize * 6));

        worldBounds.add(new Rectangle(139, 10 + 7 * blockSize, 4 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139, 10 + 17 * blockSize, 4 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + (width - 4) * blockSize, 10 + 7 * blockSize, 4 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + (width - 4) * blockSize, 10 + 17 * blockSize, 4 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139, 10, blockSize * width, blockSize));

        worldBounds.add(new Rectangle(139 + 2 * blockSize, 10 + 2 * blockSize, 2 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 2 * blockSize, 10 + 5 * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 5 * blockSize, 10 + 2 * blockSize, 3 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 9 * blockSize, 10 + 2 * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 11 * blockSize, 10 + 2 * blockSize, 3 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 15 * blockSize, 10 + 2 * blockSize, 2 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 5 * blockSize, 10 + 5 * blockSize, blockSize, 7 * blockSize));
        worldBounds.add(new Rectangle(139 + 7 * blockSize, 10 + 5 * blockSize, 5 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 13 * blockSize, 10 + 5 * blockSize, blockSize, 7 * blockSize));
        worldBounds.add(new Rectangle(139 + 15 * blockSize, 10 + 5 * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 9 * blockSize, 10 + 7 * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 6 * blockSize, 10 + 8 * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 11 * blockSize, 10 + 8 * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 5 * blockSize, 10 + 13 * blockSize, blockSize, 7 * blockSize));
        worldBounds.add(new Rectangle(139 + 13 * blockSize, 10 + 13 * blockSize, blockSize, 7 * blockSize));

        worldBounds.add(new Rectangle(139 + 2 * blockSize, 10 + (height - 4) * blockSize, 2 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 5 * blockSize, 10 + (height - 4) * blockSize, 3 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 9 * blockSize, 10 + (height - 4) * blockSize, blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 11 * blockSize, 10 + (height - 4) * blockSize, 3 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 15 * blockSize, 10 + (height - 4) * blockSize, 2 * blockSize, 2 * blockSize));

        worldBounds.add(new Rectangle(139 + 2 * blockSize, 10 + (height - 6) * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 15 * blockSize, 10 + (height - 6) * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 7 * blockSize, 10 + (height - 7) * blockSize, 5 * blockSize, 2 * blockSize));
        worldBounds.add(new Rectangle(139 + 9 * blockSize, 10 + (height - 9) * blockSize, blockSize, 2 * blockSize));

        worldBounds.add(new Rectangle(139 + 7 * blockSize, 10 + 10 * blockSize, blockSize, 5 * blockSize));
        worldBounds.add(new Rectangle(139 + 11 * blockSize, 10 + 10 * blockSize, blockSize, 5 * blockSize));
        worldBounds.add(new Rectangle(139 + 8 * blockSize, 10 + 10 * blockSize, blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 10 * blockSize, 10 + 10 * blockSize, blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 8 * blockSize, 10 + 14 * blockSize, 3 * blockSize, blockSize));

        worldBounds.add(new Rectangle(139, 10 + 11 * blockSize, 3 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139, 10 + 13 * blockSize, 3 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + (width - 3) * blockSize, 10 + 11 * blockSize, 3 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + (width - 3) * blockSize, 10 + 13 * blockSize, 3 * blockSize, blockSize));

        worldBounds.add(new Rectangle(139 + 6 * blockSize, 10 + 16 * blockSize, 2 * blockSize, blockSize));
        worldBounds.add(new Rectangle(139 + 11 * blockSize, 10 + 16 * blockSize, 2 * blockSize, blockSize));
    }

    private void renderBorders(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(139, 10, 19 * blockSize, 25 * blockSize);
        /* outer stuff */
        g.setColor(Color.blue);
        g.drawRect(139, 10, blockSize * width, blockSize);//upper border
        g.drawRect(139, 10, blockSize, blockSize * 8);
        g.drawRect(139 + 3 * blockSize, 10 + 7 * blockSize, blockSize, 5 * blockSize);
        g.drawRect(139 + 3 * blockSize, 10 + 13 * blockSize, blockSize, 5 * blockSize);
        g.drawRect(139, 10 + 17 * blockSize, blockSize, 8 * blockSize);
        g.drawRect(139, 10 + (height - 1) * blockSize, blockSize * width, blockSize);
        g.drawRect(139 + (width - 1) * blockSize, 10, blockSize, blockSize * 8);
        g.drawRect(139 + (width - 4) * blockSize, 10 + 7 * blockSize, blockSize, blockSize * 5);
        g.drawRect(139 + (width - 4) * blockSize, 10 + 13 * blockSize, blockSize, blockSize * 5);
        g.drawRect(139 + (width - 1) * blockSize, 10 + 17 * blockSize, blockSize, blockSize * 8);
        g.drawRect(139, 10 + 7 * blockSize, 4 * blockSize, blockSize);
        g.drawRect(139, 10 + 17 * blockSize, 4 * blockSize, blockSize);
        g.drawRect(139 + (width - 4) * blockSize, 10 + 7 * blockSize, 4 * blockSize, blockSize);
        g.drawRect(139 + (width - 4) * blockSize, 10 + 17 * blockSize, 4 * blockSize, blockSize);
        g.drawRect(139, 10, blockSize * width, blockSize);
        g.drawRect(139, 10 + 11 * blockSize, 4 * blockSize, blockSize);
        g.drawRect(139, 10 + 13 * blockSize, 4 * blockSize, blockSize);
        g.drawRect(139 + (width - 4) * blockSize, 10 + 11 * blockSize, 4 * blockSize, blockSize);
        g.drawRect(139 + (width - 4) * blockSize, 10 + 13 * blockSize, 4 * blockSize, blockSize);
        /*inner stuff*/
        g.drawRect(139 + 2 * blockSize, 10 + 2 * blockSize, 2 * blockSize, 2 * blockSize);
        g.drawRect(139 + 2 * blockSize, 10 + 5 * blockSize, 2 * blockSize, blockSize);
        g.drawRect(139 + 5 * blockSize, 10 + 2 * blockSize, 3 * blockSize, 2 * blockSize);
        g.drawRect(139 + 9 * blockSize, 10 + 2 * blockSize, blockSize, 2 * blockSize);
        g.drawRect(139 + 11 * blockSize, 10 + 2 * blockSize, 3 * blockSize, 2 * blockSize);
        g.drawRect(139 + 15 * blockSize, 10 + 2 * blockSize, 2 * blockSize, 2 * blockSize);
        g.drawRect(139 + 5 * blockSize, 10 + 5 * blockSize, blockSize, 7 * blockSize);
        g.drawRect(139 + 7 * blockSize, 10 + 5 * blockSize, 5 * blockSize, 2 * blockSize);
        g.drawRect(139 + 13 * blockSize, 10 + 5 * blockSize, blockSize, 7 * blockSize);
        g.drawRect(139 + 15 * blockSize, 10 + 5 * blockSize, 2 * blockSize, blockSize);
        g.drawRect(139 + 9 * blockSize, 10 + 7 * blockSize, blockSize, 2 * blockSize);
        g.drawRect(139 + 6 * blockSize, 10 + 8 * blockSize, 2 * blockSize, blockSize);
        g.drawRect(139 + 11 * blockSize, 10 + 8 * blockSize, 2 * blockSize, blockSize);
        g.drawRect(139 + 5 * blockSize, 10 + 13 * blockSize, blockSize, 7 * blockSize);
        g.drawRect(139 + 13 * blockSize, 10 + 13 * blockSize, blockSize, 7 * blockSize);
        g.drawRect(139 + 2 * blockSize, 10 + (height - 4) * blockSize, 2 * blockSize, 2 * blockSize);
        g.drawRect(139 + 5 * blockSize, 10 + (height - 4) * blockSize, 3 * blockSize, 2 * blockSize);
        g.drawRect(139 + 9 * blockSize, 10 + (height - 4) * blockSize, blockSize, 2 * blockSize);
        g.drawRect(139 + 11 * blockSize, 10 + (height - 4) * blockSize, 3 * blockSize, 2 * blockSize);
        g.drawRect(139 + 15 * blockSize, 10 + (height - 4) * blockSize, 2 * blockSize, 2 * blockSize);
        g.drawRect(139 + 2 * blockSize, 10 + (height - 6) * blockSize, 2 * blockSize, blockSize);
        g.drawRect(139 + 15 * blockSize, 10 + (height - 6) * blockSize, 2 * blockSize, blockSize);
        g.drawRect(139 + 7 * blockSize, 10 + (height - 7) * blockSize, 5 * blockSize, 2 * blockSize);
        g.drawRect(139 + 9 * blockSize, 10 + (height - 9) * blockSize, blockSize, 2 * blockSize);
        g.drawRect(139 + 7 * blockSize, 10 + 10 * blockSize, blockSize, 5 * blockSize);
        g.drawRect(139 + 11 * blockSize, 10 + 10 * blockSize, blockSize, 5 * blockSize);
        g.drawRect(139 + 8 * blockSize, 10 + 10 * blockSize, blockSize, blockSize);
        g.drawRect(139 + 10 * blockSize, 10 + 10 * blockSize, blockSize, blockSize);
        g.drawRect(139 + 8 * blockSize, 10 + 14 * blockSize, 3 * blockSize, blockSize);
        g.drawRect(139 + 6 * blockSize, 10 + 16 * blockSize, 2 * blockSize, blockSize);
        g.drawRect(139 + 11 * blockSize, 10 + 16 * blockSize, 2 * blockSize, blockSize);
    }
}
