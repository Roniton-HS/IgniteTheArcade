package MainMenu;

import Input.MouseHandler;
import Main.Constants;
import Main.Game;
import Worlds.Worlds;

import java.awt.*;

import static Main.Constants.emulogic;

public class Button {

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Worlds world;
    private final Game game;

    private boolean highlighted;

    public Button(int x, int y, int width, int height, Worlds world, Game game) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.world = world;
        this.game = game;
    }

    public void tick() {
        input();
    }

    public void input(){

        //check mouse click
        int clickX = game.getMouseHandler().getClickX();
        int clickY = game.getMouseHandler().getClickY();

        if(clickX >= x && clickX <= x + width && clickY >= y && clickY <= y + height){
            game.getMouseHandler().reset();
            Worlds.setWorld(world);
        }


        //check mouse hover
        int mouseX = game.getMouseHandler().getX();
        int mouseY = game.getMouseHandler().getY();
        highlighted = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void render(Graphics g) {

        if (highlighted){
            g.setColor(Color.BLACK);
            g.fillRect(x+5, y+5, width, height);
            g.setColor(Constants.BUTTON_HOVER);
            g.fillRect(x+2, y+2, width, height);
            g.setColor(Color.WHITE);
            g.setFont(emulogic.deriveFont(emulogic.getSize() * 20.0F));
            g.drawString(world.getName(),x+22,y+35);
        }else {
            g.setColor(Color.BLACK);
            g.fillRect(x+5, y+5, width, height);
            g.setColor(Constants.BUTTON);
            g.fillRect(x, y, width, height);
            g.setColor(Color.WHITE);
            g.setFont(emulogic.deriveFont(emulogic.getSize() * 20.0F));
            g.drawString(world.getName(),x+20,y+33);
        }
    }

    /*
    ====================================================================================================================
    Getter / Setter
    ====================================================================================================================
     */

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
