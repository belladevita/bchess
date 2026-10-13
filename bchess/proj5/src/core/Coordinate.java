package core;

import tileengine.Tileset;

import java.util.TreeMap;

public class Coordinate {
    private World world;
    private Board board;
    private String coord;
    private Pieces piece;
    private static TreeMap<String, Coordinate> allCoords = new TreeMap<>();

    public Coordinate(int x, int y) {
        generateCoord(x, y);
    }

    public void addPiece(Pieces piece) {
        this.piece = piece;
    }

    public void generateCoord(int x, int y) {
        coord = (char)(x + 95) + String.valueOf(y - 1);
        allCoords.put(coord, this);
    }

    public String getCoord() {
        return coord;
    }

    public TreeMap<String, Coordinate> getAllCoords() {
        return allCoords;
    }

}
