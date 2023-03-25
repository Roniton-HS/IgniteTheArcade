package Main;

import Input.MouseHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;

public class Display {

    private JFrame frame; //JFrame
    private Canvas canvas; //Canvas

    private final String title; //Title of the game
    private int width;
    private int height; //Size of the game

    /**
     * Constructor
     *
     * @param title  Title of the game
     * @param width  Width of the game
     * @param height Height of the game
     */
    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay();
    }

    /**
     * creates the Window
     */
    private void createDisplay() {
        frame = new JFrame(title);
        frame.setSize(width, height);
        Image icon = Toolkit.getDefaultToolkit().getImage("res/icon.png");
        frame.setIconImage(icon);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
        canvas.addMouseListener(new MouseHandler());
    }

    public void resize(int width, int height) {
        frame.setSize(width, height);
    }


    /**
     * @return canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * @return JFrame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * closes the JFrame
     */
    public void closeFrame() {
        frame.dispose();
    }
}
