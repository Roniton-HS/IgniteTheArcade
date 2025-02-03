package Main;

import Input.KeyHandler;
import Input.MouseHandler;
import MainMenu.MainMenu;
import Worlds.Worlds;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game{

    //General
    public int width, height; //with and height of the game
    public String title; //title of the Window
    boolean running = true;

    //Input
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;

    private Display display;


    //Player

    /**
     * Constructor
     */
    public Game(){
        title = "Ignite The Arcade";
        width = 1028;
        height = 1028;
        init();
    }

    public Display getDisplay() {
        return display;
    }

    /**
     * initializes:
     * Input.KeyHandler
     * JFrame
     * first World
     */
    private void init(){

        display = new Display(title, width, height); //creates Display

        //add input handler
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        display.getFrame().addKeyListener(keyHandler); //adds KeyListener
        display.getCanvas().addMouseListener(mouseHandler);
        display.getCanvas().addMouseMotionListener(mouseHandler);

        //set first world
        MainMenu mainMenu = new MainMenu(this);
        Worlds.setWorld(mainMenu);

        Constants.loadFonts();
    }

    /**
     * GAME LOOP
     * frames -> fps
     * ticks -> tps
     */
    public void run(){
        int tps = 60; //ticks per second
        double timePerTick = 1000000000F / tps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();

        //tps counter
        int ticks = 0;

        //fps counter
        int frames = 0;
        long fpsTpsTimer = System.currentTimeMillis();

        while(running){
            //tick
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;

            if(delta >= 1){
                tick();
                ticks++;
                delta--;
            }

            //render
            render();
            frames++;

            if(System.currentTimeMillis() - fpsTpsTimer > 1000){
                fpsTpsTimer = System.currentTimeMillis();
                System.out.println("FPS: " + frames + "  TPS: " + ticks);
                frames = 0;
                ticks = 0;
            }
        }
    }

    /**
     * updates the game
     */
    private void tick(){
        keyHandler.tick();
        input();
        if(Worlds.getWorld() != null){
            Worlds.getWorld().tick();
        }
    }

    /**
     * renders the game
     */
    private void render(){
        if ( System.getProperty("os.name").startsWith("Linux")){
            Toolkit.getDefaultToolkit().sync();
        }
        //Graphics
        BufferStrategy bs = display.getCanvas().getBufferStrategy();
        if(bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        //Clear Screen
        g.clearRect(0, 0, width, height);

        //Draw Here!
        if(Worlds.getWorld() != null){
            Worlds.getWorld().render(g); //render current world
        }
        //EndDrawing

        bs.show();
        g.dispose();
    }

    private void input(){
        if(keyHandler.esc && !Worlds.getWorld().getName().equals("MainMenu")){
            MainMenu mainMenu = new MainMenu(this);
            Worlds.setWorld(mainMenu);
        }
    }

    public KeyHandler getKeyHandler(){
        return keyHandler;
    }

    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }
}
