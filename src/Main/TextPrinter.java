package Main;

import java.awt.*;

@SuppressWarnings("unused")
public class TextPrinter{
    private static int textCounter;
    private static int textDelay;
    private static String output = "";

    public static void addText(String text, int x, int y, Graphics g){
        if(textDelay == 0){
            if(textCounter < text.length()){
                char letter = text.charAt(textCounter);
                output += letter;
                textCounter++;
            }
        }
        textDelay++;
        if(textDelay >= 150){
            textDelay = 0;
        }

        g.setFont(new Font("Monospaced", Font.BOLD, 50));
        g.setColor(Color.black);
        g.drawString(output, x, y);
    }

    public static void clearText(){
        output = "";
        textCounter = 0;
    }
}