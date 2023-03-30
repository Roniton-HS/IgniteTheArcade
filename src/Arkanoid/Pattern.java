package Arkanoid;

import java.util.ArrayList;

public class Pattern {

    private final ArrayList<ArrayList<Brick>> patterns = new ArrayList<>();

    public ArrayList<ArrayList<Brick>> getPatterns() {
        return patterns;
    }

    public void createPattern() {
        patterns.add(createPattern1());
        patterns.add(createPattern2());
        patterns.add(createPattern3());
    }

    private ArrayList<Brick> createPattern1() {
        ArrayList<Brick> list = new ArrayList<>();
        list.add(new Brick(60, 60, 60, 10, 5));
        list.add(new Brick(140, 60, 60, 10, 5));
        list.add(new Brick(220, 60, 60, 10, 5));
        list.add(new Brick(300, 60, 60, 10, 5));
        list.add(new Brick(380, 60, 60, 10, 5));
        list.add(new Brick(60, 80, 60, 10, 4));
        list.add(new Brick(140, 80, 60, 10, 4));
        list.add(new Brick(220, 80, 60, 10, 4));
        list.add(new Brick(300, 80, 60, 10, 4));
        list.add(new Brick(380, 80, 60, 10, 4));
        list.add(new Brick(60, 100, 60, 10, 3));
        list.add(new Brick(140, 100, 60, 10, 3));
        list.add(new Brick(220, 100, 60, 10, 3));
        list.add(new Brick(300, 100, 60, 10, 3));
        list.add(new Brick(380, 100, 60, 10, 3));
        list.add(new Brick(60, 120, 60, 10, 2));
        list.add(new Brick(140, 120, 60, 10, 2));
        list.add(new Brick(220, 120, 60, 10, 2));
        list.add(new Brick(300, 120, 60, 10, 2));
        list.add(new Brick(380, 120, 60, 10, 2));
        list.add(new Brick(60, 140, 60, 10, 1));
        list.add(new Brick(140, 140, 60, 10, 1));
        list.add(new Brick(220, 140, 60, 10, 1));
        list.add(new Brick(300, 140, 60, 10, 1));
        list.add(new Brick(380, 140, 60, 10, 1));
        return list;
    }

    private ArrayList<Brick> createPattern2() {
        ArrayList<Brick> list = new ArrayList<>();
        list.add(new Brick(60, 60, 60, 10, 5));
        list.add(new Brick(60, 80, 60, 10, 5));
        list.add(new Brick(60, 100, 60, 10, 5));
        list.add(new Brick(60, 120, 60, 10, 5));
        list.add(new Brick(60, 140, 60, 10, 5));
        list.add(new Brick(140, 80, 60, 10, 4));
        list.add(new Brick(140, 100, 60, 10, 4));
        list.add(new Brick(140, 120, 60, 10, 4));
        list.add(new Brick(140, 140, 60, 10, 4));
        list.add(new Brick(220, 140, 60, 10, 3));
        list.add(new Brick(220, 100, 60, 10, 3));
        list.add(new Brick(220, 120, 60, 10, 3));
        list.add(new Brick(300, 140, 60, 10, 2));
        list.add(new Brick(300, 140, 60, 10, 2));
        list.add(new Brick(300, 140, 60, 10, 1));
        list.add(new Brick(300, 140, 60, 10, 1));
        return list;
    }

    private ArrayList<Brick> createPattern3() {
        ArrayList<Brick> list = new ArrayList<>();
        list.add(new Brick(60, 60, 60, 10, 5));
        list.add(new Brick(140, 80, 60, 10, 3));
        list.add(new Brick(220, 100, 60, 10, 1));
        list.add(new Brick(300, 80, 60, 10, 3));
        list.add(new Brick(380, 60, 60, 10, 5));
        list.add(new Brick(60, 160, 60, 10, 5));
        list.add(new Brick(140, 140, 60, 10, 3));
        list.add(new Brick(220, 120, 60, 10, 1));
        list.add(new Brick(300, 140, 60, 10, 3));
        list.add(new Brick(380, 160, 60, 10, 5));
        return list;
    }

}
