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
        Moves for black bishops
         */
        if (black) {
            int j = x;

            for (int i = y + 1; i < 9; i++) { //down left
                if (j >= 2) {
                    j = j - 1;
                } else {
                    break;
                }
                if (figuresSave[j][i] == null || !figuresSave[j][i].black) {
                    coordinates.add(new Coordinates(j, i));
                    if (figuresSave[j][i] != null) {
                        break;
                    }
                } else {
                    break;
                }
            }
            j = x;
            for (int i = y + 1; i < 9; i++) { //down right
                if (j <= 7) {
                    j = j + 1;
                } else {
                    break;
                }
                if (figuresSave[j][i] == null || !figuresSave[j][i].black) {
                    coordinates.add(new Coordinates(j, i));
                    if (figuresSave[j][i] != null) {
                        break;
                    }
                } else {
                    break;
                }
            }

            j = x;
            for (int i = y - 1; i > 0; i--) { //up right
                if (j <= 7) {
                    j = j + 1;
                } else {
                    break;
                }
                if (figuresSave[j][i] == null || !figuresSave[j][i].black) {
                    coordinates.add(new Coordinates(j, i));
                    if (figuresSave[j][i] != null) {
                        break;
                    }
                } else {
                    break;
                }
            }
            j = x;
            for (int i = y - 1; i > 0; i--) { //up left
                if (j >= 2) {
                    j = j - 1;
                } else {
                    break;
                }
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

        /*
        Moves for white bishops
         */
        if (!black) {
            int j = x;

            for (int i = y + 1; i < 9; i++) { //down left
                if (j >= 2) {
                    j = j - 1;
                } else {
                    break;
                }
                if (figuresSave[j][i] == null || figuresSave[j][i].black) {
                    coordinates.add(new Coordinates(j, i));
                    if (figuresSave[j][i] != null) {
                        break;
                    }
                } else {
                    break;
                }
            }
            j = x;
            for (int i = y + 1; i < 9; i++) { //down right
                if (j <= 7) {
                    j = j + 1;
                } else {
                    break;
                }
                if (figuresSave[j][i] == null || figuresSave[j][i].black) {
                    coordinates.add(new Coordinates(j, i));
                    if (figuresSave[j][i] != null) {
                        break;
                    }
                } else {
                    break;
                }
            }

            j = x;
            for (int i = y - 1; i > 0; i--) { //up right
                if (j <= 7) {
                    j = j + 1;
                } else {
                    break;
                }
                if (figuresSave[j][i] == null || figuresSave[j][i].black) {
                    coordinates.add(new Coordinates(j, i));
                    if (figuresSave[j][i] != null) {
                        break;
                    }
                } else {
                    break;
                }
            }
            j = x;
            for (int i = y - 1; i > 0; i--) { //up left
                if (j >= 2) {
                    j = j - 1;
                } else {
                    break;
                }
                if (figuresSave[j][i] == null || figuresSave[j][i].black) {
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
