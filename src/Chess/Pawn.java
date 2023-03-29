package Chess;

import Input.ImageLoader;
import Input.MouseHandler;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Pawn extends Figures {

    public Pawn(int x, int y, boolean black) {
        super(x, y, black);
        if (black) {
            image = ImageLoader.loadImage("/chess/Black/PawnBlack.png");
        } else {
            image = ImageLoader.loadImage("/chess/White/PawnWhite.png");
        }
    }

    public void tick() {
        whereTo();
    }

    public void whereTo() {
        coordinates.clear();

        if (black) {
            if (Chess.figuresSave[x][y + 1] == null) { //basic move forward
                coordinates.add(new Coordinates(x, y + 1));
            }

            if((Chess.figuresSave[x-1][y+1]!=null)&&!Chess.figuresSave[x-1][y+1].black){ //white figure diagonal left
                coordinates.add(new Coordinates(x-1, y+1));
            }

            if((Chess.figuresSave[x+1][y+1]!=null)&&!Chess.figuresSave[x+1][y+1].black){ //white figure diagonal right
                coordinates.add(new Coordinates(x+1, y+1));
            }
        }
    }
}
