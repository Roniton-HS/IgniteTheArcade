package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener{
    private static int clickX, clickY;

    public static int getClickX(){
        return clickX;
    }

    public static int getClickY(){
        return clickY;
    }

    public static void reset(){
        clickX = 0;
        clickY = 0;
    }

    @Override
    public void mouseClicked(MouseEvent e){
        clickX = e.getX();
        clickY = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e){

    }

    @Override
    public void mouseReleased(MouseEvent e){

    }

    @Override
    public void mouseEntered(MouseEvent e){

    }

    @Override
    public void mouseExited(MouseEvent e){

    }
}
