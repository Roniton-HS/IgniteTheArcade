package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener{
    private static int clickX = 10000;
    private static int clickY = 10000;
    private static int lClickX = 10000;
    private static int lClickY = 10000;

    public static int getClickX(){
        return clickX;
    }

    public static int getClickY(){
        return clickY;
    }

    public static void reset(){
        clickX = 10000;
        clickY = 10000;
        lClickX = 10000;
        lClickY = 10000;
    }

    public static int getLClickX() {
        return lClickX;
    }


    public static int getLClickY() {
        return lClickY;
    }

    @Override
    public void mouseClicked(MouseEvent e){

    }

    @Override
    public void mousePressed(MouseEvent e){
        if(e.getButton() == 1){
            clickX = e.getX();
            clickY = e.getY();
        }
        if(e.getButton() == 3){
            lClickX = e.getX();
            lClickY = e.getY();
        }
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
