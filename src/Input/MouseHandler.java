package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener{
    private static int clickX = 10000;
    private static int clickY = 10000;

    public static int getClickX(){
        return clickX;
    }

    public static int getClickY(){
        return clickY;
    }

    public static void reset(){
        clickX = 10000;
        clickY = 10000;
    }

    @Override
    public void mouseClicked(MouseEvent e){

    }

    @Override
    public void mousePressed(MouseEvent e){
        clickX = e.getX();
        clickY = e.getY();
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
