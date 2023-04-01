package Chess;

import Input.ImageLoader;

public class Queen extends Figures {
    /**
     * Constructor Queen
     */
    public Queen(int x, int y, boolean black, Figures[][] figuresSave) {
        super(x, y, black, figuresSave);
        if (black) {
            image = ImageLoader.loadImage("/chessRes/Black/QueenBlack.png");
        } else {
            image = ImageLoader.loadImage("/chessRes/White/QueenWhite.png");
        }
    }
}
