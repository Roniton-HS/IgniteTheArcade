package Chess;

import Input.MouseHandler;
import Main.Constants;
import Main.Game;
import Worlds.Worlds;

import java.awt.*;
import java.sql.SQLOutput;

public class Chess extends Worlds {

    Figures[][] figuresSave = new Figures[10][10];

    static final int FIELD_SIZE = 100;
    boolean white = true;

    /**
     * Constructor
     *
     * @param game
     */
    public Chess(Game game) {
        super(game);
        game.getDisplay().resize(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        figuresSave[1][1] = new Pawn(1, 1, true, figuresSave);
        figuresSave[2][2] = new Pawn(2, 2, false, figuresSave);
        figuresSave[4][1] = new King(4, 1, true, figuresSave);
        figuresSave[6][4] = new King(6, 4, false, figuresSave);
        figuresSave[7][3] = new Knight(7, 3, true, figuresSave);

    }

    @Override

    public void tick() {

        for (int i = 0; i < figuresSave.length; i++) {
            for (int j = 0; j < figuresSave.length; j++) {
                if (figuresSave[j][i] != null) {
                    figuresSave[j][i].tick();
                }
            }
        }
        input();
    }

    @Override
    public void render(Graphics g) {
        renderField(FIELD_SIZE, g);
        renderFigures(g);
    }

    public void renderField(int fieldSize, Graphics g) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (white) {
                    g.setColor(Color.WHITE);
                    white = !white;
                } else {
                    g.setColor(Color.GRAY);
                    white = !white;
                }
                g.fillRect(100 + (j * fieldSize), 100 + (i * fieldSize), fieldSize, fieldSize);
            }
            white = !white;
        }
    }

    public void renderFigures(Graphics g) {
        for (int i = 0; i < figuresSave.length; i++) {
            for (int j = 0; j < figuresSave.length; j++) {
                if (figuresSave[j][i] != null) {
                    figuresSave[j][i].render(g);
                }
            }
        }
    }

    public void input() {
        //get Mouse X and Y Positions
        int clickX = (MouseHandler.getClickX() / FIELD_SIZE);
        int clickY = (MouseHandler.getClickY() / FIELD_SIZE);
        if (clickX >= 1 && clickX <= 8 && clickY >= 1 && clickY <= 8) { //in bound?

            if (figuresSave[clickX][clickY] != null) { //true if a figure is standing on selected field

                if (!(figuresSave[clickX][clickY].selected)) {
                    figuresSave[clickX][clickY].selected = true; //changes selected to true
                } else {
                    figuresSave[clickX][clickY].selected = false; //if figure is already selected, change selected to false
                }
                for (int i = 0; i < figuresSave.length; i++) {
                    for (int j = 0; j < figuresSave.length; j++) {

                        if ((figuresSave[j][i] != null) && (figuresSave[j][i] != figuresSave[clickX][clickY])) { //changes all not selected figures to false
                            figuresSave[j][i].selected = false;
                        }
                    }
                }
            }
        }
        MouseHandler.reset();
    }
}
