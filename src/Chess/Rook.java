package Chess;

import Input.ImageLoader;

public class Rook extends Figures{
    /**
     * Constructor Rook
     */
    public Rook(int x, int y, boolean black, Figures[][] figuresSave) {
        super(x, y, black, figuresSave);
        if (black) {
            image = ImageLoader.loadImage("/chess/Black/RookBlack.png");
        } else {
            image = ImageLoader.loadImage("/chess/White/RookWhite.png");
        }
    }
}
