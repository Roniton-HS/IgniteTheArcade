package Main;

@SuppressWarnings("unused")
public class GameCamera {

    private int xOffset, yOffset;

    /**
     * Constructor
     */
    public GameCamera() {
        xOffset = 0;
        yOffset = 0;
    }

    public void move(float xAmt, float yAmt) {
        xOffset += xAmt;
        yOffset += yAmt;
    }

    /**
     * setter methods for xOffset and yOffset
     */
    public void setXOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public void setYOffset(int yOffset) {
        this.yOffset = yOffset;
    }


    /**
     * getter methods for xOffset and yOffset
     */
    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }


}
