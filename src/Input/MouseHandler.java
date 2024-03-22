package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {
    private int clickX = 10000;
    private int clickY = 10000;
    private int lClickX = 10000;
    private int lClickY = 10000;
    private int x = 10000;
    private int y = 10000;

    public int getClickX(){
        return clickX;
    }

    public int getClickY(){
        return clickY;
    }

    public void reset(){
        clickX = 10000;
        clickY = 10000;
        lClickX = 10000;
        lClickY = 10000;
    }

    public int getLClickX() {
        return lClickX;
    }


    public int getLClickY() {
        return lClickY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
}
