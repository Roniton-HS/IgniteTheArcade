package Chess;

import Main.Constants;
import Main.Game;
import Worlds.Worlds;

import java.awt.*;

public class ChessWorld extends Worlds {

    int[][] figuresSave = new int[8][8];

    int fieldSize = 100;
    boolean white = true;

    Pawn pawn = new Pawn(100, 100);
    /**
     * Constructor
     *
     * @param game
     */
    public ChessWorld(Game game) {
        super(game);
        game.getDisplay().resize(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
    }

    @Override

    public void tick(){

    }

    @Override
    public void render(Graphics g){
        renderField(fieldSize, g);
        pawn.render(g);
    }

    public void renderField(int fieldSize, Graphics g){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(white){
                    g.setColor(Color.WHITE);
                    white = !white;
                } else {
                    g.setColor(Color.GRAY);
                    white = !white;
                }
                g.fillRect(100+(j*fieldSize),100+(i*fieldSize), fieldSize, fieldSize);
            }
            white = !white;
        }
    }

    public void renderFigures(){

    }
}
