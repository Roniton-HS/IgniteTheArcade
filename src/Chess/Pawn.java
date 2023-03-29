package Chess;

import Input.ImageLoader;

public class Pawn extends Figures {

    /**
     * Constructor Pawn
     */
    public Pawn(int x, int y, boolean black, Figures[][] figuresSave) {
        super(x, y, black, figuresSave);
        if (black) {
            image = ImageLoader.loadImage("/chess/Black/PawnBlack.png");
        } else {
            image = ImageLoader.loadImage("/chess/White/PawnWhite.png");
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
            if (figuresSave[x][y + 1] == null && ((y + 1) != 9)) { //basic move forward
                coordinates.add(new Coordinates(x, y + 1));
            }

            if (((figuresSave[x - 1][y + 1] != null) && !figuresSave[x - 1][y + 1].black) && ((x - 1) != 0 && (y + 1) != 9)) { //white figure is diagonal left
                coordinates.add(new Coordinates(x - 1, y + 1));
            }

            if (((figuresSave[x + 1][y + 1] != null) && !figuresSave[x + 1][y + 1].black) && ((x + 1) != 9 && (y + 1) != 9)) { //white figure is diagonal right
                coordinates.add(new Coordinates(x + 1, y + 1));
            }
        }

        /*
        Moves for whites pawns
         */
        if (!black) {
            if (figuresSave[x][y - 1] == null && ((y - 1) != 0)) { //basic move forward
                coordinates.add(new Coordinates(x, y - 1));
            }

            if (((figuresSave[x - 1][y - 1] != null) && figuresSave[x - 1][y - 1].black) && ((x - 1) != 0 && (y - 1) != 0)) { //black figure is diagonal left
                coordinates.add(new Coordinates(x - 1, y - 1));
            }

            if (((figuresSave[x + 1][y - 1] != null) && figuresSave[x + 1][y - 1].black) && ((x + 1) != 9 && (y - 1) != 0)) { //black figure is diagonal right
                coordinates.add(new Coordinates(x + 1, y - 1));
            }
        }
    }
}
