package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.TreeMap;

public interface Pieces {

    // set the position of the piece at the start of the game
    void whiteInitialPosition(int x);

    void blackInitialPosition(int x);

    // move the piece
    boolean validMove(int x, int y, TreeMap<Integer, TreeMap<Integer, Pieces>> pieces);

    boolean isPiece(int x, int y, TreeMap<Integer, TreeMap<Integer, Pieces>> pieces);

    // check that your movement does not go out of bounds
    boolean outOfBounds(int x, int y);

    // captures another piece and moves to that square
    void capture();

    //removes the piece and prevents it from moving/being captured again
    void captured();

    boolean getIsWhite();
}
