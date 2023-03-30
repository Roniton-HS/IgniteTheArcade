package Chess;

import Input.ImageLoader;

public class Bishop extends Figures {
    /**
     * Constructor Bishop
     */
    public Bishop(int x, int y, boolean black, Figures[][] figuresSave) {
        super(x, y, black, figuresSave);
        if (black) {
            image = ImageLoader.loadImage("/chess/Black/BishopBlack.png");
        } else {
            image = ImageLoader.loadImage("/chess/White/BishopWhite.png");
        }
    }
}
