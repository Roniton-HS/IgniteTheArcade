package Tetris;

import Chess.Coordinates;
import Main.Game;
import Worlds.Worlds;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

import static Main.Constants.emulogic;

public class Tetris extends Worlds {
    private final int WIDTH = 10;
    private final int HEIGHT = 20;
    private int[][] map = new int[WIDTH][HEIGHT];
    private final int BLOCK_SIZE = 40;
    private long timer = System.currentTimeMillis();
    private boolean keyPressed = false;
    private boolean[] piecesUsed = new boolean[7];
    private int state = 0;
    char current = getNewPiece();
    char next = getNewPiece();
    int score = 0;
    /*
    0:  empty

    1:  static o
    2:  static s
    3:  static z
    4:  static l
    5:  static j
    6:  static t
    7:  static i

    8:  moving o
    9:  moving s
    10: moving z
    11: moving l
    12: moving j
    13: moving t
    14: moving i
     */

    /**
     * Constructor
     */
    public Tetris(Game game) {
        super(game);
        game.getDisplay().resize(1000, 1000);
    }

    /**
     * ticks the game
     */
    @Override
    public void tick() {
        input();
        if (!movingBlock()) {
            spawnPiece();
        }
        if (System.currentTimeMillis() - timer > 800) {
            moveDown();
            timer = System.currentTimeMillis();
        }
        checkLine();
    }

    /**
     * handles mouse and keyboard input
     */
    private void input() {
        if (game.getKeyHandler().s) {
            moveDown();
        }
        if (game.getKeyHandler().a && !keyPressed) {
            moveLeft();
            keyPressed = true;
        }
        if (game.getKeyHandler().d && !keyPressed) {
            moveRight();
            keyPressed = true;
        }
        if (game.getKeyHandler().w && !keyPressed) {
            rotate();
            keyPressed = true;
        }
        if (!game.getKeyHandler().a && !game.getKeyHandler().d && !game.getKeyHandler().w) {
            keyPressed = false;
        }
    }

    /*
    ====================================================================================================================
    PIECE SPAWNING
    ====================================================================================================================
     */

    /**
     * creates a random piece
     */
    private char getNewPiece() {
        boolean full = true;
        for (boolean b : piecesUsed) {
            if (!b) {
                full = false;
                break;
            }
        }
        if (full) {
            Arrays.fill(piecesUsed, false);
        }
        Random r = new Random();
        int randomInt;
        do {
            randomInt = r.nextInt(7) + 1;
        } while (piecesUsed[randomInt - 1]);

        char out = switch (randomInt) {
            case 1 -> 'o';
            case 2 -> 'i';
            case 3 -> 's';
            case 4 -> 'z';
            case 5 -> 'l';
            case 6 -> 'j';
            case 7 -> 't';
            default -> throw new IllegalStateException("Unexpected value: " + randomInt);
        };

        piecesUsed[randomInt - 1] = true;
        return out;
    }

    /**
     * spawn a new piece
     */
    private void spawnPiece() {
        switch (next) {
            case 'o' -> {
                map[5][0] = 1;
                map[6][0] = 1;
                map[5][1] = 1;
                map[6][1] = 1;
            }
            case 'l' -> {
                map[5][0] = 4;
                map[5][1] = 4;
                map[5][2] = 4;
                map[6][2] = 4;
            }
            case 'j' -> {
                map[6][0] = 5;
                map[6][1] = 5;
                map[6][2] = 5;
                map[5][2] = 5;
            }
            case 's' -> {
                map[5][0] = 2;
                map[6][0] = 2;
                map[6][1] = 2;
                map[7][1] = 2;
            }
            case 'z' -> {
                map[5][1] = 3;
                map[6][1] = 3;
                map[6][0] = 3;
                map[7][0] = 3;
            }
            case 'i' -> {
                map[5][1] = 7;
                map[5][2] = 7;
                map[5][3] = 7;
                map[5][4] = 7;
            }
            case 't' -> {
                map[6][0] = 6;
                map[5][1] = 6;
                map[6][1] = 6;
                map[7][1] = 6;
            }
        }
        current = next;
        next = getNewPiece();
    }

    /**
     * @return true if there are moving blocks, false if there are no moving blocks
     */
    private boolean movingBlock() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (map[j][i] <= 7 && map[j][i] != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
    ====================================================================================================================
    MOVEMENT
    ====================================================================================================================
     */
    private void moveLeft() {
        if (checkLeft()) {
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    if (map[j][i] <= 7 && map[j][i] != 0 && j - 1 >= 0) {
                        map[j - 1][i] = map[j][i];
                        map[j][i] = 0;
                    }
                }
            }
        }
    }

    private void moveRight() {
        if (checkRight()) {
            for (int i = HEIGHT - 1; i >= 0; i--) {
                for (int j = WIDTH - 1; j >= 0; j--) {
                    if (map[j][i] <= 7 && map[j][i] != 0 && j + 1 <= WIDTH - 1) {
                        map[j + 1][i] = map[j][i];
                        map[j][i] = 0;
                    }
                }
            }
        }

    }

    private void moveDown() {
        if (checkDown()) {
            for (int i = HEIGHT - 1; i >= 0; i--) {
                for (int j = 0; j < WIDTH; j++) {
                    if (map[j][i] <= 7 && map[j][i] != 0) {
                        map[j][i + 1] = map[j][i];
                        map[j][i] = 0;
                    }
                }
            }
        } else {
            makeStatic();
        }
    }

    private boolean checkDown() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (map[j][i] <= 7 && map[j][i] != 0) {
                    if (i == HEIGHT - 1 || map[j][i + 1] >= 8) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkRight() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (map[j][i] <= 7 && map[j][i] != 0) {
                    if (j == WIDTH - 1 || map[j + 1][i] >= 8) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkLeft() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (map[j][i] <= 7 && map[j][i] != 0) {
                    if (j == 0 || map[j - 1][i] >= 8) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * makes all moving blocks static
     */
    private void makeStatic() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (map[j][i] <= 7 && map[j][i] != 0) {
                    map[j][i] += 7;
                }
            }
        }
        state = 0;
    }

    /*
    ====================================================================================================================
    ROTATION
    ====================================================================================================================
     */

    /**
     * rotates the current piece
     */
    private void rotate() {
        switch (current) {
            default -> {
            }
            case 'i' -> rotateI();
            case 'l' -> rotateL();
            case 'j' -> rotateJ();
            case 's' -> rotateS();
            case 'z' -> rotateZ();
            case 't' -> rotateT();
        }
    }

    /**
     * rotates I piece
     */
    private void rotateI() {
        Coordinates x, m, a, b, c;
        if (state == 0) {
            /*
                o
                o
                m => a b m c
                x
             */
            x = getBlock('b'); //bottom block
            m = new Coordinates(x.getX(), x.getY() - 1);
            a = new Coordinates(m.getX() - 2, m.getY());
            b = new Coordinates(m.getX() - 1, m.getY());
            c = new Coordinates(m.getX() + 1, m.getY());
            if (isSpace(a, b, c)) {
                clearMoving();
                setBlocks(7, a, b, c, m);
                state = 1;
            }
        } else if (state == 1) {
            /*
                           a
                           b
                x o m o => m
                           c
             */
            x = getBlock('l');
            m = new Coordinates(x.getX() + 2, x.getY());
            a = new Coordinates(m.getX(), m.getY() - 2);
            b = new Coordinates(m.getX(), m.getY() - 1);
            c = new Coordinates(m.getX(), m.getY() + 1);
            if (isSpace(a, b, c)) {
                clearMoving();
                setBlocks(7, a, b, c, m);
                state = 0;
            }
        }
    }

    /**
     * rotates L piece
     */
    private void rotateL() {
        Coordinates x, m, a, b, c;
        switch (state) {
            case 0 -> {
                /*
                    x
                    m   => b m c
                    o o    a
                 */
                x = getBlock('t');
                m = new Coordinates(x.getX(), x.getY() + 1);
                a = new Coordinates(m.getX() - 1, m.getY() + 1);
                b = new Coordinates(m.getX() - 1, m.getY());
                c = new Coordinates(m.getX() + 1, m.getY());
                if (isSpace(a, b, c)) {
                    clearMoving();
                    setBlocks(4, a, b, c, m);
                    state = 1;
                }
            }
            case 1 -> {
                /*
                             a b
                    o m x =>   m
                    o          c
                 */
                x = getBlock('r');
                m = new Coordinates(x.getX() - 1, x.getY());
                a = new Coordinates(m.getX() - 1, m.getY() - 1);
                b = new Coordinates(m.getX(), m.getY() - 1);
                c = new Coordinates(m.getX(), m.getY() + 1);
                if (isSpace(a, b, c)) {
                    clearMoving();
                    setBlocks(4, a, b, c, m);
                    state = 2;
                }
            }
            case 2 -> {
                /*
                    o o        c
                      m => a m b
                      x
                 */
                x = getBlock('b');
                m = new Coordinates(x.getX(), x.getY() - 1);
                a = new Coordinates(m.getX() - 1, m.getY());
                b = new Coordinates(m.getX() + 1, m.getY());
                c = new Coordinates(m.getX() + 1, m.getY() - 1);
                if (isSpace(a, b, c)) {
                    clearMoving();
                    setBlocks(4, a, b, c, m);
                    state = 3;
                }
            }
            case 3 -> {
                /*
                        o    a
                    x m o => m
                             b c
                 */
                x = getBlock('l');
                m = new Coordinates(x.getX() + 1, x.getY());
                a = new Coordinates(m.getX(), m.getY() - 1);
                b = new Coordinates(m.getX(), m.getY() + 1);
                c = new Coordinates(m.getX() + 1, m.getY() + 1);
                if (isSpace(a, b, c)) {
                    clearMoving();
                    setBlocks(4, a, b, c, m);
                    state = 0;
                }
            }
        }
    }

    private void rotateJ() {
        Coordinates x, m, a, b, c;
        switch (state) {
            case 0 -> {
                /*
                          o
                          m => a
                        x o    b m c
                 */
                x = getBlock('l');
                m = new Coordinates(x.getX() + 1, x.getY() - 1);
                a = new Coordinates(m.getX() - 1, m.getY() - 1);
                b = new Coordinates(m.getX() - 1, m.getY());
                c = new Coordinates(m.getX() + 1, m.getY());
                if (isSpace(a, b, c)) {
                    clearMoving();
                    setBlocks(5, a, b, c, m);
                    state = 1;
                }
            }
            case 1 -> {
                /*
                                b a
                      o     =>  m
                      o m x     c
                 */
                x = getBlock('r');
                m = new Coordinates(x.getX() - 1, x.getY());
                a = new Coordinates(m.getX() + 1, m.getY() - 1);
                b = new Coordinates(m.getX(), m.getY() - 1);
                c = new Coordinates(m.getX(), m.getY() + 1);
                if (isSpace(a, b, c)) {
                    clearMoving();
                    setBlocks(5, a, b, c, m);
                    state = 2;
                }
            }
            case 2 -> {
                /*
                   o o
                   m   => a m b
                   x          c
                 */
                x = getBlock('b');
                m = new Coordinates(x.getX(), x.getY() - 1);
                a = new Coordinates(m.getX() - 1, m.getY());
                b = new Coordinates(m.getX() + 1, m.getY());
                c = new Coordinates(m.getX() + 1, m.getY() + 1);
                if (isSpace(a, b, c)) {
                    clearMoving();
                    setBlocks(5, a, b, c, m);
                    state = 3;
                }
            }
            case 3 -> {
                /*
                    x m o     a
                        o =>  m
                            c b
                 */
                x = getBlock('l');
                m = new Coordinates(x.getX() + 1, x.getY());
                a = new Coordinates(m.getX(), m.getY() - 1);
                b = new Coordinates(m.getX(), m.getY() + 1);
                c = new Coordinates(m.getX() - 1, m.getY() + 1);
                if (isSpace(a, b, c)) {
                    clearMoving();
                    setBlocks(5, a, b, c, m);
                    state = 0;
                }
            }
        }

    }

    private void rotateS() {

        Coordinates x, m, a, b, c;
        switch (state) {
            case 0 -> {
                /*
                      o o     a
                    x m   =>  m b
                                c
                 */
                x = getBlock('l');
                m = new Coordinates(x.getX() + 1, x.getY());
                a = new Coordinates(m.getX(), m.getY() - 1);
                b = new Coordinates(m.getX() + 1, m.getY());
                c = new Coordinates(m.getX() + 1, m.getY() + 1);
                if (isSpace(a, b, c)) {

                    clearMoving();
                    setBlocks(2, a, b, c, m);
                    state = 1;
                }
            }
            case 1 -> {
                /*
                        x        m c
                        m o => a b
                          o
                 */
                x = getBlock('t');
                m = new Coordinates(x.getX(), x.getY() + 1);
                a = new Coordinates(m.getX() - 1, m.getY() + 1);
                b = new Coordinates(m.getX(), m.getY() + 1);
                c = new Coordinates(m.getX() + 1, m.getY());
                if (isSpace(a, b, c)) {
                    clearMoving();
                    setBlocks(2, a, b, c, m);
                    state = 0;
                }
            }
        }
    }

    private void rotateZ() {
        Coordinates x, m, a, b, c;
        switch (state) {
            case 0 -> {
                /*
                        x m        a
                          o o => b m
                                 c
                 */
                x = getBlock('l');
                m = new Coordinates(x.getX() + 1, x.getY());
                a = new Coordinates(m.getX(), m.getY() - 1);
                b = new Coordinates(m.getX() - 1, m.getY());
                c = new Coordinates(m.getX() - 1, m.getY() + 1);
                if (isSpace(a, b, c)) {
                    clearMoving();
                    setBlocks(3, a, b, c, m);
                    state = 1;
                }
            }
            case 1 -> {
                /*
                       x
                     o m => a m
                     o        b c
                 */
                x = getBlock('t');
                m = new Coordinates(x.getX(), x.getY() + 1);
                a = new Coordinates(m.getX() - 1, m.getY());
                b = new Coordinates(m.getX(), m.getY() + 1);
                c = new Coordinates(m.getX() + 1, m.getY() + 1);
                if (isSpace(a, b, c)) {
                    clearMoving();
                    setBlocks(3, a, b, c, m);
                    state = 0;
                }
            }
        }
    }

    private void rotateT() {
        Coordinates x, m, a, b, c;
        switch (state) {
            case 0 -> {
                /*
                        x       a
                      o m o =>  m b
                                c
                 */
                x = getBlock('t');
                m = new Coordinates(x.getX(), x.getY() + 1);
                a = new Coordinates(m.getX(), m.getY() - 1);
                b = new Coordinates(m.getX() + 1, m.getY());
                c = new Coordinates(m.getX(), m.getY() + 1);
                if (isSpace(a, b, c)) {
                    clearMoving();
                    setBlocks(6, a, b, c, m);
                    state = 1;
                }
            }
            case 1 -> {
                /*
                    x
                    m o => a m b
                    o        c

                 */
                x = getBlock('t');
                m = new Coordinates(x.getX(), x.getY() + 1);
                a = new Coordinates(m.getX() - 1, m.getY());
                b = new Coordinates(m.getX() + 1, m.getY());
                c = new Coordinates(m.getX(), m.getY() + 1);
                if (isSpace(a, b, c)) {
                    clearMoving();
                    setBlocks(6, a, b, c, m);
                    state = 2;
                }
            }
            case 2 -> {
                /*
                               b
                    x m o => a m
                      o        c
                 */
                x = getBlock('l');
                m = new Coordinates(x.getX() + 1, x.getY());
                a = new Coordinates(m.getX() - 1, m.getY());
                b = new Coordinates(m.getX(), m.getY() - 1);
                c = new Coordinates(m.getX(), m.getY() + 1);
                if (isSpace(a, b, c)) {
                    clearMoving();
                    setBlocks(6, a, b, c, m);
                    state = 3;
                }
            }
            case 3 -> {
                /*
                      x      b
                    o m => a m c
                      o
                 */
                x = getBlock('t');
                m = new Coordinates(x.getX(), x.getY() + 1);
                a = new Coordinates(m.getX() - 1, m.getY());
                b = new Coordinates(m.getX(), m.getY() - 1);
                c = new Coordinates(m.getX() + 1, m.getY());
                if (isSpace(a, b, c)) {
                    clearMoving();
                    setBlocks(6, a, b, c, m);
                    state = 0;
                }
            }
        }
    }

    /**
     * @param position t -> top, b -> bottom, l -> left, r -> right
     * @return Coordinates of the first block found
     */
    private Coordinates getBlock(char position) {
        switch (position) {
            case 't' -> {
                for (int i = 0; i < HEIGHT; i++) {
                    for (int j = 0; j < WIDTH; j++) {
                        if (map[j][i] != 0 && map[j][i] <= 7) {
                            return new Coordinates(j, i);
                        }
                    }
                }
            }
            case 'b' -> {
                for (int i = HEIGHT - 1; i >= 0; i--) {
                    for (int j = 0; j < WIDTH; j++) {
                        if (map[j][i] != 0 && map[j][i] <= 7) {
                            return new Coordinates(j, i);
                        }
                    }
                }
            }
            case 'l' -> {
                for (int i = 0; i < WIDTH; i++) {
                    for (int j = 0; j < HEIGHT; j++) {
                        if (map[i][j] != 0 && map[i][j] <= 7) {
                            return new Coordinates(i, j);
                        }
                    }
                }
            }
            case 'r' -> {
                for (int i = WIDTH - 1; i >= 0; i--) {
                    for (int j = 0; j < HEIGHT; j++) {
                        if (map[i][j] != 0 && map[i][j] <= 7) {
                            return new Coordinates(i, j);
                        }
                    }
                }
            }
        }
        return new Coordinates(0, 0);
    }

    /**
     * @param cords cords to check
     * @return true if empty, false if not empty
     */
    private boolean isSpace(Coordinates... cords) {
        for (Coordinates c : cords) {
            //in bounds?
            if (c.getX() >= 0 && c.getX() < WIDTH && c.getY() >= 0 && c.getY() < HEIGHT) {
                //not empty?
                if (map[c.getX()][c.getY()] >= 8) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * @param type  type of block 0-14
     * @param cords cords to place the blocks
     */
    private void setBlocks(int type, Coordinates... cords) {
        for (Coordinates c : cords) {
            map[c.getX()][c.getY()] = type;
        }
    }

    /**
     * removes all blocks that are moving
     */
    private void clearMoving() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (map[j][i] <= 7) {
                    map[j][i] = 0;
                }
            }

        }
    }

    /*
    ====================================================================================================================
    FULL LINE
    ====================================================================================================================
     */

    /**
     * checks if there is a full line and removes it
     */
    private void checkLine() {
        for (int i = 0; i < HEIGHT; i++) {
            boolean line = true;
            for (int j = 0; j < WIDTH; j++) {
                if (map[j][i] <= 7) {
                    line = false;
                    break;
                }
            }
            if (line) {
                score += 100;
                removeLine(i);
                moveLines(i);
            }
        }
    }

    /**
     * @param line line to remove
     */
    private void removeLine(int line) {
        for (int i = 0; i < WIDTH; i++) {
            map[i][line] = 0;
        }
    }

    /**
     * moves all lines down by one
     *
     * @param line last line to move
     */
    private void moveLines(int line) {
        for (int i = line; i >= 0; i--) {
            for (int j = 0; j < WIDTH; j++) {
                if (map[j][i] >= 8) {
                    map[j][i + 1] = map[j][i];
                    map[j][i] = 0;
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        final int SPACING = 2 * BLOCK_SIZE;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                switch (map[j][i]) {
                    default -> g.setColor(Color.WHITE);
                    case 1, 8 -> g.setColor(Color.YELLOW);
                    case 2, 9 -> g.setColor(Color.GREEN);
                    case 3, 10 -> g.setColor(Color.RED);
                    case 4, 11 -> g.setColor(Color.ORANGE);
                    case 5, 12 -> g.setColor(Color.CYAN);
                    case 6, 13 -> g.setColor(new Color(211, 16, 211));
                    case 7, 14 -> g.setColor(new Color(77, 0, 77));
                }
                g.fillRect(SPACING + j * BLOCK_SIZE, SPACING + i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(SPACING + j * BLOCK_SIZE, SPACING + i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
        }

        final int NEXT_POSITION_X = 13 * BLOCK_SIZE;
        final int NEXT_POSITION_Y = 2 * BLOCK_SIZE;
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(NEXT_POSITION_X, NEXT_POSITION_Y, 5 * BLOCK_SIZE, 5 * BLOCK_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(NEXT_POSITION_X, NEXT_POSITION_Y, 5 * BLOCK_SIZE, 5 * BLOCK_SIZE);
        switch (next) {
            default -> {
            }
            case 'o' -> {
                final int x = NEXT_POSITION_X + 3 * BLOCK_SIZE / 2;
                final int y = NEXT_POSITION_Y + 3 * BLOCK_SIZE / 2;
                g.setColor(Color.YELLOW);
                g.fillRect(x, y, 2 * BLOCK_SIZE, 2 * BLOCK_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x + BLOCK_SIZE, y, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x + BLOCK_SIZE, y + BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x, y + BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
            case 'i' -> {
                final int x = NEXT_POSITION_X + 2 * BLOCK_SIZE / 2;
                final int y = NEXT_POSITION_Y + BLOCK_SIZE / 2;
                g.setColor(new Color(77, 0, 77));
                g.fillRect(x + BLOCK_SIZE, y, BLOCK_SIZE, 4 * BLOCK_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(x + BLOCK_SIZE, y, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x + BLOCK_SIZE, y + BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x + BLOCK_SIZE, y + 2 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x + BLOCK_SIZE, y + 3 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
            case 'l' -> {
                final int x = NEXT_POSITION_X + 3 * BLOCK_SIZE / 2;
                final int y = NEXT_POSITION_Y + 2 * BLOCK_SIZE / 2;
                g.setColor(Color.ORANGE);
                g.fillRect(x, y, BLOCK_SIZE, 3 * BLOCK_SIZE);
                g.fillRect(x + BLOCK_SIZE, y + 2 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x, y + BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x, y + 2 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x + BLOCK_SIZE, y + 2 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
            case 'j' -> {
                final int x = NEXT_POSITION_X + 5 * BLOCK_SIZE / 2;
                final int y = NEXT_POSITION_Y + 2 * BLOCK_SIZE / 2;
                g.setColor(Color.CYAN);
                g.fillRect(x, y, BLOCK_SIZE, 3 * BLOCK_SIZE);
                g.fillRect(x - BLOCK_SIZE, y + 2 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x, y + BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x, y + 2 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x - BLOCK_SIZE, y + 2 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
            case 's' -> {
                final int x = NEXT_POSITION_X + 2 * BLOCK_SIZE / 2;
                final int y = NEXT_POSITION_Y + 3 * BLOCK_SIZE / 2;
                g.setColor(Color.GREEN);
                g.fillRect(x + BLOCK_SIZE, y, 2 * BLOCK_SIZE, BLOCK_SIZE);
                g.fillRect(x, y + BLOCK_SIZE, 2 * BLOCK_SIZE, BLOCK_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(x + BLOCK_SIZE, y, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x + 2 * BLOCK_SIZE, y, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x, y + BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x + BLOCK_SIZE, y + BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
            case 'z' -> {
                final int x = NEXT_POSITION_X + 2 * BLOCK_SIZE / 2;
                final int y = NEXT_POSITION_Y + 3 * BLOCK_SIZE / 2;
                g.setColor(Color.RED);
                g.fillRect(x, y, 2 * BLOCK_SIZE, BLOCK_SIZE);
                g.fillRect(x + BLOCK_SIZE, y + BLOCK_SIZE, 2 * BLOCK_SIZE, BLOCK_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x + BLOCK_SIZE, y, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x + BLOCK_SIZE, y + BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x + 2 * BLOCK_SIZE, y + BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
            case 't' -> {
                final int x = NEXT_POSITION_X + 2 * BLOCK_SIZE / 2;
                final int y = NEXT_POSITION_Y + 3 * BLOCK_SIZE / 2;
                g.setColor(new Color(211, 16, 211));
                g.fillRect(x + BLOCK_SIZE, y, BLOCK_SIZE, BLOCK_SIZE);
                g.fillRect(x, y + BLOCK_SIZE, 3 * BLOCK_SIZE, BLOCK_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(x + BLOCK_SIZE, y, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x, y + BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x + BLOCK_SIZE, y + BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.drawRect(x + 2 * BLOCK_SIZE, y + BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
        final int SCORE_POSITION_X = 13 * BLOCK_SIZE;
        final int SCORE_POSITION_Y = 2 * BLOCK_SIZE + 6*BLOCK_SIZE;
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(SCORE_POSITION_X, SCORE_POSITION_Y, 5 * BLOCK_SIZE, 4 * BLOCK_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(SCORE_POSITION_X, SCORE_POSITION_Y, 5 * BLOCK_SIZE, 4 * BLOCK_SIZE);
        g.setFont(emulogic.deriveFont(emulogic.getSize() * 20.0F));
        g.drawString("Score:",SCORE_POSITION_X + BLOCK_SIZE, SCORE_POSITION_Y + 3 * BLOCK_SIZE / 2);
        g.drawString(""+score,SCORE_POSITION_X + BLOCK_SIZE, SCORE_POSITION_Y + 5 * BLOCK_SIZE / 2);
    }
}
