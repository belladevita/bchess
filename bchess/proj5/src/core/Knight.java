package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.TreeMap;

public class Knight implements Pieces {
    private boolean isWhite;
    private Board board;
    private World world;
    private int posX;
    private int posY;

    public Knight(int initialXPos, boolean isWhite, Board board, World world) {
        this.isWhite = isWhite;
        this.world = world;
        this.board = board;
        if (isWhite) {
            whiteInitialPosition(initialXPos);
        } else {
            blackInitialPosition(initialXPos);
        }
    }

    @Override
    public void whiteInitialPosition(int x) {
        world.getTET()[x][board.getBottom()] = Tileset.WKNIGHT;
        posX = x;
        posY = board.getBottom();
    }

    @Override
    public void blackInitialPosition(int x) {
        world.getTET()[x][board.getTop()] = Tileset.BKNIGHT;
        posX = x;
        posY = board.getTop();
    }

    @Override
    public boolean validMove(int x, int y, TreeMap<Integer, TreeMap<Integer, Pieces>> pieces) {
        if (outOfBounds(x, y)) {
            return false;
        }

        if (isPiece(x, y, pieces) && isWhite == pieces.get(x).get(y).getIsWhite()) {
            return false;
        }

        int distX = Math.abs(x - posX);
        int distY = Math.abs(y - posY);

        if ((distX != 1 && distY != 1) || (distX + distY) != 3) {
            return false;
        }

        posX = x;
        posY = y;
        return true;
    }

    @Override
    public boolean isPiece(int x, int y, TreeMap<Integer, TreeMap<Integer, Pieces>> pieces) {
        return pieces.get(x).get(y) != null;
    }

    @Override
    public boolean outOfBounds(int x, int y) {
        return x > board.getRight() || x < board.getLeft() || y > board.getTop() || y < board.getBottom();
    }

    @Override
    public void capture() {

    }

    @Override
    public void captured() {

    }

    public boolean getIsWhite() {
        return isWhite;
    }
}
