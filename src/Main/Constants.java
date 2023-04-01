package Main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Constants {
        public static final int WORLD_WIDTH = 1000;
        public static final int WORLD_HEIGHT = 1000;

        public static Font emulogic;
        public static Font recursiveBold;

        public static void loadFonts(){
                loadEmulogic();
                loadRecursiveBold();
        }

        public static void loadEmulogic(){
                InputStream is = Constants.class.getResourceAsStream("/fonts/emulogic.ttf");
                try {
                        assert is != null;
                        emulogic = Font.createFont(Font.TRUETYPE_FONT, is);
                } catch (FontFormatException | IOException e) {
                        throw new RuntimeException(e);
                }
        }

        public static void loadRecursiveBold(){
                InputStream is = Constants.class.getResourceAsStream("/fonts/recursiveBold.ttf");
                try {
                        assert is != null;
                        recursiveBold = Font.createFont(Font.TRUETYPE_FONT, is);
                } catch (FontFormatException | IOException e) {
                        throw new RuntimeException(e);
                }
        }

        public static final Color ALMOST_BLACK = new Color(9, 9, 9);
        public static final Color ALMOST_WHITE = new Color(195, 195, 195);
        public static final Color YELLOW = new Color(237, 240, 4);
        public static final Color RED = new Color(255, 14, 66);
        public static final Color GREEN = new Color(118, 255, 0);
        public static final Color BLUE = new Color(78, 46, 250);
}
