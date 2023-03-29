package Chess;

import Input.ImageLoader;
import Input.MouseHandler;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Pawn extends Figures {

    boolean lookDown;

    public Pawn(int x, int y){
        super(x, y);
        image = ImageLoader.loadImage("/chess/White/PawnWhite.png");
    }

    public void move(){
        if(lookDown){

        }
    }
}
