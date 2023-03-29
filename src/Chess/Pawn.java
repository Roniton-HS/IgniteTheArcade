package Chess;

import Input.ImageLoader;
import Input.MouseHandler;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Pawn {
    BufferedImage image;

    private final int x, y;
    final int WIDTH = 90;
    final int HEIGHT = 90;

    final int RWIDTH = 100;
    final int RHEIGHT = 100;

    boolean lookDown;
    boolean select = false;
    int xSave, ySave;
    int xRec, yRec;

    public Pawn(int x, int y){
        this.x = x;
        this.y = y;

        image = ImageLoader.loadImage("/chess/White/PawnWhite.png");
    }

    public void tick() {
        input();
        MouseHandler.reset();
    }

    /*
    ===================================================================================================
    render methods
    ===================================================================================================
     */
    public void render(Graphics g){
        g.drawImage(image, x, y, WIDTH, HEIGHT, null);
        if(select) {
            renderSelect(g);
        }
    }

    public void renderSelect(Graphics g) {
        g.setColor(Color.RED);
        drawThickRect(g, xRec, yRec, RWIDTH, RHEIGHT, 5);

    }

    public static void drawThickRect(Graphics g, int x, int y, int width, int height, int thickness) {
        for (int i = 0; i < thickness; i++) {
            g.drawRect(x + i, y + i, width - 2 * i, height - 2 * i);
        }
    }

    public void move(){
        if(lookDown){

        }
    }


    /**
    checks Mouse input
     */
    public void input() {
        //get Mouse X and Y Positions
        int clickX = MouseHandler.getClickX();
        int clickY = MouseHandler.getClickY();

        //Row check
        if(clickX > 100 && clickX < 200){ //Row 1
            xSave = 0;
        } if(clickX > 200 && clickX < 300){ //Row 2
            xSave = 1;
        } if(clickX > 300 && clickX < 400){ //Row 3
            xSave = 2;
        } if(clickX > 400 && clickX < 500){ //Row 4
            xSave = 3;
        } if(clickX > 500 && clickX < 600){ //Row 5
            xSave = 4;
        } if(clickX > 600 && clickX < 700){ //Row 6
            xSave = 5;
        } if(clickX > 700 && clickX < 800){ //Row 7
            xSave = 6;
        }if(clickX > 800 && clickX < 900){ //Row 8
            xSave = 7;
        }

        System.out.println("xSave: " + xSave);

        //Column check
        if(clickY > 100 && clickY < 200){ //Column 1
            ySave = 0;
        } if(clickY > 200 && clickY < 300){//Column 2
            ySave = 1;
        } if(clickY > 300 && clickY < 400){ //Column 3
            ySave = 2;
        } if(clickY > 400 && clickY < 500){//Column 4
            ySave = 3;
        } if(clickY > 500 && clickY < 600){ //Column 5
            ySave = 4;
        } if(clickY > 600 && clickY < 700){ //Column 6
            ySave = 5;
        } if(clickY > 700 && clickY < 800){ //Column 7
            ySave = 6;
        }if(clickY > 800 && clickY < 900){ //Column 8
            ySave = 7;
        }

        System.out.println("ySave: " + ySave);

        //checks if a Pawn is on the selected field and creates coordinates for the Rectangle
        if(ChessWorld.figuresSave[xSave][ySave] == 1 && !select) {
            xRec = (xSave+1)*100;
            yRec = (ySave+1)*100;
            System.out.println("xRec: " + xRec);
            System.out.println("yRec: " + yRec);
            select = true;
        } else {
            select = false;
        }


    }
}
