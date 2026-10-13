package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.TreeMap;

public class Bishop implements Pieces {
    private boolean isWhite;
    private Board board;
    private World world;
    private int posX;
    private int posY;

    public Bishop(int initialXPos, boolean isWhite, Board board, World world) {
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
        world.getTET()[x][board.getBottom()] = Tileset.WBISHOP;
        posX = x;
        posY = board.getBottom();
    }

    @Override
    public void blackInitialPosition(int x) {
        world.getTET()[x][board.getTop()] = Tileset.BBISHOP;
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

        // find the absolute distance

        int dist = Math.abs(posX - x);
        int distX = Integer.signum(x - posX);
        int distY = Integer.signum(y - posY);

        if (Math.abs(posX - x) != Math.abs(posY - y)) {
            return false;
        } else {
            for (int w = 1; w < dist; w++) {
                if (isPiece(posX + (distX * w), posY + (distY * w), pieces)) {
                    return false;
                }
            }
        }
        posX = x;
        posY = y;
        return true;
    }

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
