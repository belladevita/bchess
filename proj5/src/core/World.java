package core;

import tileengine.TETile;
import tileengine.Tileset;

public class World {
    private TETile[][] blankSpace;
    private int width;
    private int height;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        blankSpace = new TETile[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                blankSpace[x][y] = Tileset.NOTHING;
            }
        }
    }

    public TETile[][] getTET() {
        return blankSpace;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
