package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.TreeMap;

public class Queen implements Pieces {
    private boolean isWhite;
    private Board board;
    private World world;
    private Coordinate coord;
    private int posX;
    private int posY;

    public Queen(int initialXPos, boolean isWhite, Board board, World world) {
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
        world.getTET()[x][board.getBottom()] = Tileset.WQUEEN;
        posX = x;
        posY = board.getBottom();
    }

    @Override
    public void blackInitialPosition(int x) {
        world.getTET()[x][board.getTop()] = Tileset.BQUEEN;
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

        int dist = 0;
        if (x != posX) {
            dist = Math.abs(posX - x);
        } else {
            dist = Math.abs(posY - y);
        }
        int distX = Integer.signum(x - posX);
        int distY = Integer.signum(y - posY);

        // move in direction of x. see if you hit a piece or can get to y
        if ((x != posX && y != posY) && Math.abs(posX - x) != Math.abs(posY - y)) {
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

    public String getCoord() {
        return coord.getCoord();
    }

    public boolean getIsWhite() {
        return isWhite;
    }
}
