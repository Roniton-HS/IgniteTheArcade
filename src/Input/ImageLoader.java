package Input;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageLoader {

    /**
     * Loads an Image
     * @param path Path of the Image
     */
    public static BufferedImage loadImage(String path){
        try {
            return ImageIO.read(Objects.requireNonNull(ImageLoader.class.getResource(path)));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
