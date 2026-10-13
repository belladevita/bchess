package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.TreeMap;

public class Board {
    private static TETile[][] board;
    private static int left;
    private static int right;
    private static int bottom;
    private static int top;
    private static TreeMap<Integer, TreeMap<Integer, TETile>> tileColors = new TreeMap<>();

    public Board(World world) {
        left = 2;
        right = 9;
        bottom = 2;
        top = 9;
        initializeYMap();
        generateBoard(world.getTET());
    }

    public static void initializeYMap() {
        for (int lr = left; lr <= right; lr++) {
            TreeMap<Integer, TETile> yMap = new TreeMap<>();
            for (int bt = bottom; bt <= top; bt++) {
                yMap.put(bt, null);
            }
            tileColors.put(lr, yMap);
        }
    }

    public void generateBoard(TETile[][] world) {
        board = new TETile[8][8];
        boolean white = true;
        for (int x = 2; x < 10; x++) {
            for (int y = 2; y < 10; y++) {
                if (white) {
                    world[x][y] = Tileset.WHITE;
                    tileColors.get(x).put(y, Tileset.WHITE);
                } else {
                    world[x][y] = Tileset.BLACK;
                    tileColors.get(x).put(y, Tileset.BLACK);
                }
                white = !(white);
            }
            white = !(white);
        }
    }

    public TETile[][] getTET() {
        return board;
    }

    public TreeMap<Integer, TreeMap<Integer, TETile>> getTileColors() {
        return tileColors;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }

    public int getTop() {
        return top;
    }
}
