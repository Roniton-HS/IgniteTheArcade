package Chess;

import Input.ImageLoader;

public class Rook extends Figures {
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

    public void tick() {
        whereTo();
        moveFigure();
    }

    public void whereTo() {
        coordinates.clear();

        /*
        Moves for black rooks
         */
        for (int i = y + 1; i < 9; i++) { //down
            if (figuresSave[x][i] == null || !figuresSave[x][i].black) {
                coordinates.add(new Coordinates(x, i));
                if (figuresSave[x][i] != null) {
                    break;
                }
            } else {
                break;
            }
        }

        for (int i = y - 1; i > 0; i--) { //up
            if (figuresSave[x][i] == null || !figuresSave[x][i].black) {
                coordinates.add(new Coordinates(x, i));
                if (figuresSave[x][i] != null) {
                    break;
                }
            } else {
                break;
            }
        }

        for (int i = x + 1; i < 9; i++) { //right
            if (figuresSave[i][y] == null || !figuresSave[i][y].black) {
                coordinates.add(new Coordinates(i, y));
                if (figuresSave[i][y] != null) {
                    break;
                }
            } else {
                break;
            }
        }

        for (int i = x - 1; i > 0; i--) { //left
            if (figuresSave[i][y] == null || !figuresSave[i][y].black) {
                coordinates.add(new Coordinates(i, y));
                if (figuresSave[i][y] != null) {
                    break;
                }
            } else {
                break;
            }
        }

        /*
        Moves for white rooks
         */
        for (int i = y + 1; i < 9; i++) { //down
            if (figuresSave[x][i] == null || figuresSave[x][i].black) {
                coordinates.add(new Coordinates(x, i));
                if (figuresSave[x][i] != null) {
                    break;
                }
            } else {
                break;
            }
        }

        for (int i = y - 1; i > 0; i--) { //up
            if (figuresSave[x][i] == null || figuresSave[x][i].black) {
                coordinates.add(new Coordinates(x, i));
                if (figuresSave[x][i] != null) {
                    break;
                }
            } else {
                break;
            }
        }

        for (int i = x + 1; i < 9; i++) { //right
            if (figuresSave[i][y] == null || figuresSave[i][y].black) {
                coordinates.add(new Coordinates(i, y));
                if (figuresSave[i][y] != null) {
                    break;
                }
            } else {
                break;
            }
        }

        for (int i = x - 1; i > 0; i--) { //left
            if (figuresSave[i][y] == null || figuresSave[i][y].black) {
                coordinates.add(new Coordinates(i, y));
                if (figuresSave[i][y] != null) {
                    break;
                }
            } else {
                break;
            }
        }

    }
}
