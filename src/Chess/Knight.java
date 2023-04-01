package Chess;

import Input.ImageLoader;

public class Knight extends Figures {
    /**
     * Constructor Knight
     */
    public Knight(int x, int y, boolean black, Figures[][] figuresSave) {
        super(x, y, black, figuresSave);
        if (black) {
            image = ImageLoader.loadImage("/chessRes/Black/KnightBlack.png");
        } else {
            image = ImageLoader.loadImage("/chessRes/White/KnightWhite.png");
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
            if ((x - 1) > 0 && (y + 2) < 9) {
                if (figuresSave[x - 1][y + 2] == null || !figuresSave[x - 1][y + 2].black) {
                    coordinates.add(new Coordinates(x - 1, y + 2));
                }
            }

            if ((x + 1) < 9 && (y + 2) < 9) {
                if (figuresSave[x + 1][y + 2] == null || !figuresSave[x + 1][y + 2].black) {
                    coordinates.add(new Coordinates(x + 1, y + 2));
                }
            }

            if ((x - 1) > 0 && (y - 2) > 0) {
                if (figuresSave[x - 1][y - 2] == null || !figuresSave[x - 1][y - 2].black) {
                    coordinates.add(new Coordinates(x - 1, y - 2));
                }
            }

            if ((x + 1) < 9 && (y - 2) > 0) {
                if (figuresSave[x + 1][y - 2] == null || !figuresSave[x + 1][y - 2].black) {
                    coordinates.add(new Coordinates(x + 1, y - 2));
                }
            }

            if ((x - 2) > 0 && (y - 1) > 0) {
                if (figuresSave[x - 2][y - 1] == null || !figuresSave[x - 2][y - 1].black) {
                    coordinates.add(new Coordinates(x - 2, y - 1));
                }
            }

            if ((x - 2) > 0 && (y + 1) < 9) {
                if (figuresSave[x - 2][y + 1] == null || !figuresSave[x - 2][y + 1].black) {
                    coordinates.add(new Coordinates(x - 2, y + 1));
                }
            }

            if ((x + 2) < 9 && (y - 1) > 0) {
                if (figuresSave[x + 2][y - 1] == null || !figuresSave[x + 2][y - 1].black) {
                    coordinates.add(new Coordinates(x + 2, y - 1));
                }
            }

            if ((x + 2) < 9 && (y + 1) < 9) {
                if (figuresSave[x + 2][y + 1] == null || !figuresSave[x + 2][y + 1].black) {
                    coordinates.add(new Coordinates(x + 2, y + 1));
                }
            }
        }

        /*
        Moves for white Knight
         */
        if (!black) {
            if ((x - 1) > 0 && (y + 2) < 9) {
                if (figuresSave[x - 1][y + 2] == null || figuresSave[x - 1][y + 2].black) {
                    coordinates.add(new Coordinates(x - 1, y + 2));
                }
            }

            if ((x + 1) < 9 && (y + 2) < 9) {
                if (figuresSave[x + 1][y + 2] == null || figuresSave[x + 1][y + 2].black) {
                    coordinates.add(new Coordinates(x + 1, y + 2));
                }
            }

            if ((x - 1) > 0 && (y - 2) > 0) {
                if (figuresSave[x - 1][y - 2] == null || figuresSave[x - 1][y - 2].black) {
                    coordinates.add(new Coordinates(x - 1, y - 2));
                }
            }

            if ((x + 1) < 9 && (y - 2) > 0) {
                if (figuresSave[x + 1][y - 2] == null || figuresSave[x + 1][y - 2].black) {
                    coordinates.add(new Coordinates(x + 1, y - 2));
                }
            }

            if ((x - 2) > 0 && (y - 1) > 0) {
                if (figuresSave[x - 2][y - 1] == null || figuresSave[x - 2][y - 1].black) {
                    coordinates.add(new Coordinates(x - 2, y - 1));
                }
            }

            if ((x - 2) > 0 && (y + 1) < 9) {
                if (figuresSave[x - 2][y + 1] == null || figuresSave[x - 2][y + 1].black) {
                    coordinates.add(new Coordinates(x - 2, y + 1));
                }
            }

            if ((x + 2) < 9 && (y - 1) > 0) {
                if (figuresSave[x + 2][y - 1] == null || figuresSave[x + 2][y - 1].black) {
                    coordinates.add(new Coordinates(x + 2, y - 1));
                }
            }

            if ((x + 2) < 9 && (y + 1) < 9) {
                if (figuresSave[x + 2][y + 1] == null || figuresSave[x + 2][y + 1].black) {
                    coordinates.add(new Coordinates(x + 2, y + 1));
                }
            }
        }
    }
}
