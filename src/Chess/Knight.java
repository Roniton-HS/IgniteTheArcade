package Chess;

import Input.ImageLoader;

public class Knight extends Figures {
    /**
     * Constructor Knight
     */
    public Knight(int x, int y, boolean black, Figures[][] figuresSave) {
        super(x, y, black, figuresSave);
        if (black) {
            image = ImageLoader.loadImage("/chess/Black/KnightBlack.png");
        } else {
            image = ImageLoader.loadImage("/chess/White/KnightWhite.png");
        }
    }

    public void tick() {
        whereTo();
        moveFigure();
    }

    public void whereTo() {
        coordinates.clear();

        /*
        Moves for black pawns
         */
        if (black) {
            if ((figuresSave[x - 1][y + 2] == null || !figuresSave[x - 1][y + 2].black) && ((x - 1) != 0 && (y + 2) != 9)) {
                coordinates.add(new Coordinates(x - 1, y + 2));
            }

            if ((figuresSave[x + 1][y + 2] == null || !figuresSave[x + 1][y + 2].black) && ((x + 1) != 9 && (y + 2) != 9)) {
                coordinates.add(new Coordinates(x + 1, y + 2));
            }

            if ((figuresSave[x - 1][y - 2] == null || !figuresSave[x - 1][y - 2].black) && ((x - 1) != 0 && (y - 2) != 0)) {
                coordinates.add(new Coordinates(x - 1, y - 2));
            }

            if ((figuresSave[x + 1][y - 2] == null || !figuresSave[x + 1][y - 2].black) && ((x + 1) != 9 && (y - 2) != 0)) {
                coordinates.add(new Coordinates(x + 1, y - 2));
            }
        }
    }
}
