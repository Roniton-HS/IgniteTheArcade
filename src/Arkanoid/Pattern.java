package Arkanoid;

import java.util.ArrayList;

public class Pattern {
    /**
     * To create your own pattern crate a method in which the pattern is created and add it to the patterns list in createPattern().
     * The standard brick size is 30x15. These values are stored in the variables BRICK_WIDTH and BRICK_HEIGHT.
     * To create a standard brick use "new Brick(x, y, hp)", to create a custom brick use "new Brick(x, y, width, height, hp)".
     * The borders of the game are at x = 49, x = 440, y = 49 and the player is at y = 620.
     * Make sure that the bricks are inside this area.
     * The hp should be greater than 0, otherwise the bricks will be created with one hp. Bricks with more than 5 hp will have the same color.
     */

    private final ArrayList<ArrayList<Brick>> patterns = new ArrayList<>();
    private final int BRICK_WIDTH = 30;
    private final int BRICK_HEIGHT = 15;

    public ArrayList<ArrayList<Brick>> getPatterns() {
        return patterns;
    }

    public void createPattern() {
        patterns.add(createPatternBlock());
        patterns.add(createPatternStair1());
        patterns.add(createPatternStair2());
        patterns.add(createPatternSpace());
        patterns.add(createDebug());
    }

    private ArrayList<Brick> createPatternBlock() {
        ArrayList<Brick> list = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 3; j++) {
                list.add(new Brick(80 + (BRICK_WIDTH * i), 80 + (BRICK_HEIGHT * j), 3));
            }
        }

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 3; j++) {
                list.add(new Brick(80 + (BRICK_WIDTH * i), 125 + (BRICK_HEIGHT * j), 2));
            }
        }

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 3; j++) {
                list.add(new Brick(80 + (BRICK_WIDTH * i), 170 + (BRICK_HEIGHT * j), 1));
            }
        }

        return list;
    }

    private ArrayList<Brick> createPatternStair1() {
        ArrayList<Brick> list = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 10; j++) {
                if (i <= j) {
                    list.add(new Brick(95 + (BRICK_WIDTH * i), 80 + (BRICK_HEIGHT * j), (i % 5) + 1));
                }
            }
        }
        return list;
    }

    private ArrayList<Brick> createPatternStair2() {
        ArrayList<Brick> list = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 10; j++) {
                if (i <= j) {
                    list.add(new Brick(95 + (BRICK_WIDTH * i), 80 + (BRICK_HEIGHT * j), (j % 5) + 1));
                }
            }
        }
        return list;
    }

    private ArrayList<Brick> createPatternSpace() {
        ArrayList<Brick> list = new ArrayList<>();

        list.add(new Brick(140, 80, 2));
        list.add(new Brick(140, 95, 2));
        list.add(new Brick(320, 80, 2));
        list.add(new Brick(320, 95, 2));
        list.add(new Brick(170, 110, 2));
        list.add(new Brick(170, 125, 2));
        list.add(new Brick(290, 110, 2));
        list.add(new Brick(290, 125, 2));
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                list.add(new Brick(140 + (BRICK_WIDTH * i), 140 + (BRICK_HEIGHT * j), 3));
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 2; j++) {
                if (i != 2 && i != 6) {
                    list.add(new Brick(110 + (BRICK_WIDTH * i), 170 + (BRICK_HEIGHT * j), 3));
                } else {
                    list.add(new Brick(110 + (BRICK_WIDTH * i), 170 + (BRICK_HEIGHT * j), 1));
                }
            }
        }
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 3; j++) {
                list.add(new Brick(80 + (BRICK_WIDTH * i), 200 + (BRICK_HEIGHT * j), 3));
            }
        }
        for (int i = 0; i < 11; i++) {
            if (i != 1 && i != 9) {
                list.add(new Brick(80 + (BRICK_WIDTH * i), 245, 3));
            }
        }
        for (int i = 0; i < 11; i++) {
            if (i == 0 || i == 2 || i == 8 || i == 10) {
                for (int j = 0; j < 2; j++) {
                    list.add(new Brick(80 + (BRICK_WIDTH * i), 260 + (BRICK_HEIGHT * j), 3));
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            if (i != 2) {
                for (int j = 0; j < 2; j++) {
                    list.add(new Brick(170 + (BRICK_WIDTH * i), 290 + (BRICK_HEIGHT * j), 3));
                }
            }
        }
        return list;
    }

    private ArrayList<Brick> createDebug() {
        ArrayList<Brick> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 9; j++) {
                list.add(new Brick(80 + (BRICK_WIDTH * i), 80 + (BRICK_HEIGHT * j), 1));
            }
        }
        return list;
    }
}
