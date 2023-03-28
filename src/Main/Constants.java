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

}
