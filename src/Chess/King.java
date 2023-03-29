package Chess;

import Input.ImageLoader;

public class King extends Figures {

    /**
     * Constructor King
     */
    public King(int x, int y, boolean black, Figures[][] figuresSave) {
        super(x, y, black, figuresSave);
        if (black) {
            image = ImageLoader.loadImage("/chess/Black/KingBlack.png");
        } else {
            image = ImageLoader.loadImage("/chess/White/KingWhite.png");
        }
    }

    public void tick() {
        whereTo();
        moveFigure();
    }

    public void whereTo() {
        coordinates.clear();

        /*
        Moves for black king
         */
        if (black) {
            //left up
            if ((figuresSave[x - 1][y - 1] == null || !figuresSave[x - 1][y - 1].black) && ((x - 1) != 0 && (y - 1) != 0)) {
                coordinates.add(new Coordinates(x - 1, y - 1));
            }
            //up
            if ((figuresSave[x][y - 1] == null || !figuresSave[x][y - 1].black) && ((y - 1) != 0)) {
                coordinates.add(new Coordinates(x, y - 1));
            }
            //right up
            if ((figuresSave[x + 1][y - 1] == null || !figuresSave[x + 1][y - 1].black) && ((x + 1) != 9 && (y - 1) != 0)) {
                coordinates.add(new Coordinates(x + 1, y - 1));
            }
            //left
            if ((figuresSave[x - 1][y] == null || !figuresSave[x - 1][y].black) && ((x - 1) != 0)) {
                coordinates.add(new Coordinates(x - 1, y));
            }
            //right
            if ((figuresSave[x + 1][y] == null || !figuresSave[x + 1][y].black) && ((x + 1) != 9)) {
                coordinates.add(new Coordinates(x + 1, y));
            }
            //left down
            if ((figuresSave[x - 1][y + 1] == null || !figuresSave[x - 1][y + 1].black) && ((x - 1) != 0 && (y + 1) != 9)) {
                coordinates.add(new Coordinates(x - 1, y + 1));
            }
            //down
            if ((figuresSave[x][y + 1] == null || !figuresSave[x][y + 1].black) && ((y + 1) != 9)) {
                coordinates.add(new Coordinates(x, y + 1));
            }
            //right down
            if ((figuresSave[x + 1][y + 1] == null || !figuresSave[x + 1][y + 1].black) && ((x + 1) != 9 && (y + 1) != 9)) {
                coordinates.add(new Coordinates(x + 1, y + 1));
            }
        }

        /*
        Moves for white king
         */
        if (!black) {
            //left up
            if ((figuresSave[x - 1][y - 1] == null || figuresSave[x - 1][y - 1].black) && ((x - 1) != 0 && (y - 1) != 0)) {
                coordinates.add(new Coordinates(x - 1, y - 1));
            }
            //up
            if ((figuresSave[x][y - 1] == null || figuresSave[x][y - 1].black) && ((y - 1) != 0)) {
                coordinates.add(new Coordinates(x, y - 1));
            }
            //right up
            if ((figuresSave[x + 1][y - 1] == null || figuresSave[x + 1][y - 1].black) && ((x + 1) != 9 && (y - 1) != 0)) {
                coordinates.add(new Coordinates(x + 1, y - 1));
            }
            //left
            if ((figuresSave[x - 1][y] == null || figuresSave[x - 1][y].black) && ((x - 1) != 0)) {
                coordinates.add(new Coordinates(x - 1, y));
            }
            //right
            if ((figuresSave[x + 1][y] == null || figuresSave[x + 1][y].black) && ((x + 1) != 9)) {
                coordinates.add(new Coordinates(x + 1, y));
            }
            //left down
            if ((figuresSave[x - 1][y + 1] == null || figuresSave[x - 1][y + 1].black) && ((x - 1) != 0 && (y + 1) != 9)) {
                coordinates.add(new Coordinates(x - 1, y + 1));
            }
            //down
            if ((figuresSave[x][y + 1] == null || figuresSave[x][y + 1].black) && ((y + 1) != 9)) {
                coordinates.add(new Coordinates(x, y + 1));
            }
            //right down
            if ((figuresSave[x + 1][y + 1] == null || figuresSave[x + 1][y + 1].black) && ((x + 1) != 9 && (y + 1) != 9)) {
                coordinates.add(new Coordinates(x + 1, y + 1));
            }
        }
    }

}
