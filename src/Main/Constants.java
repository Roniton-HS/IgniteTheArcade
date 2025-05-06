package Main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Constants {
    public static final int WORLD_WIDTH = 1000;
    public static final int WORLD_HEIGHT = 1000;
    public static final int WIN10_WIDTH_DIFF = 16;
    public static final int WIN10_HEIGHT_DIFF = 39;


    /*
    ====================================================================================================================
    FONTS
    ====================================================================================================================
     */
    public static Font emulogic;
    public static Font recursiveBold;

    public static void loadFonts() {
        loadEmulogic();
        loadRecursiveBold();
    }

    public static void loadEmulogic() {
        InputStream is = Constants.class.getResourceAsStream("/fonts/emulogic.ttf");
        try {
            assert is != null;
            emulogic = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadRecursiveBold() {
        InputStream is = Constants.class.getResourceAsStream("/fonts/recursiveBold.ttf");
        try {
            assert is != null;
            recursiveBold = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    ====================================================================================================================
    COLORS
    ====================================================================================================================
     */

    public static final Color ALMOST_BLACK = new Color(9, 9, 9);
    public static final Color ALMOST_WHITE = new Color(237, 237, 237);
    public static final Color YELLOW = new Color(237, 240, 4);
    public static final Color RED = new Color(255, 14, 66);
    public static final Color GREEN = new Color(118, 255, 0);
    public static final Color BLUE = new Color(78, 46, 250);
    public static final Color PINK = new Color(245, 18, 245);
    public static final Color LIGHT_BLUE = new Color(78,192,202);
    public static final Color DARK_GREEN = new Color(85,128,34);
    public static final Color LIGHT_GREEN = new Color(209,236,125);
    public static final Color DARK_YELLOW = new Color(248,183,51);
    public static final Color BUTTON = new Color(246, 125, 0, 50);
    public static final Color BUTTON_HOVER = new Color(206, 72, 0);
    public static final Color BACKGROUND = new Color(138, 138, 138);
    public static final Color BACKGROUND_FILTER = new Color(0, 0, 0, 180);

}
