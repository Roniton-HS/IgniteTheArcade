package Chess;

import Input.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;



public class Pawn {
    BufferedImage image;

    private final int x, y;
    final int WIDTH = 90;
    final int HEIGHT = 90;

    boolean lookDown;

    public Pawn(int x, int y){
        this.x = x;
        this.y = y;

        image = ImageLoader.loadImage("/chess/White/PawnWhite.png");
    }

    public void tick() {

    }

    public void render(Graphics g){
        g.drawImage(image, x, y, WIDTH, HEIGHT, null);
    }

    public void move(){
        if(lookDown){

        }
    }
}
