package Chess;

import Input.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;



public class Pawn {
    BufferedImage image;

    private final int x, y;
    int width = 90;
    int height = 90;

    boolean lookDown;

    public Pawn(int x, int y){
        this.x = x;
        this.y = y;

        image = ImageLoader.loadImage("/icon.png");
    }

    public void tick() {

    }

    public void render(Graphics g){
        g.drawImage(image, x, y, width, height, null);
    }

    public void move(){
        if(lookDown){

        }
    }
}
