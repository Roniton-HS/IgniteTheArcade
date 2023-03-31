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

    public void tick() {
        whereTo();
        moveFigure();
    }

    public void whereTo() {
        coordinates.clear();

        /*
        Moves for black rooks
         */
        if (black) {
            for (int i = y + 1; i < 9; i++) { //down left
                for (int j = x - 1; j > 0; j--) {
                    if (figuresSave[j][i] == null || !figuresSave[j][i].black) {
                        coordinates.add(new Coordinates(j, i));
                        if (figuresSave[j][i] != null) {
                            break;
                        }
                    } else {
                        break;
                    }
                }

            }
        }
    }
}
