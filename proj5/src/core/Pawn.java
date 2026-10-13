package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.TreeMap;

public class Pawn implements Pieces {
    private boolean isWhite;
    private Board board;
    private World world;
    private int posX;
    private int posY;
    private boolean firstMove;

    public Pawn(int initialXPos, boolean isWhite, Board board, World world) {
        this.isWhite = isWhite;
        this.board = board;
        this.world = world;
        this.firstMove = true;
        if (isWhite) {
            whiteInitialPosition(initialXPos);
        } else {
            blackInitialPosition(initialXPos);
        }
    }

    @Override
    public void whiteInitialPosition(int x) {
        world.getTET()[x][board.getBottom() + 1] = Tileset.WPAWN;
        posX = x;
        posY = board.getBottom() + 1;
    }

    @Override
    public void blackInitialPosition(int x) {
        world.getTET()[x][board.getTop() - 1] = Tileset.BPAWN;
        posX = x;
        posY = board.getTop() - 1;
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
        int distY = y - posY;

        // capture on diagonal
        if (distX == 1 && distY == 1 && pieces.get(x).get(y) == null) {
            return false;
        }

        // can't capture on super long diagonals
        if (distX > 1) {
            return false;
        }

        // don't capture in front
        if (distX == 0 && pieces.get(x).get(y) != null) {
            return false;
        }

        // move forward
        if (firstMove) {
            if (Math.abs(distY) != 1 && Math.abs(distY) != 2) {
                return false;
            } else {
                firstMove = false;
            }
        } else {
            if (isWhite) {
                if (distY != 1) {
                    return false;
                }
            }
            else {
                if (distY != -1) {
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

    public boolean getIsWhite() {
        return isWhite;
    }
}
