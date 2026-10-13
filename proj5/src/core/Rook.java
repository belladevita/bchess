package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.TreeMap;

public class Rook implements Pieces {
    // implements Pieces
    private boolean isWhite;
    private Board board;
    private World world;
    private int posX;
    private int posY;
    private boolean firstMove;

    public Rook(int initialXPos, boolean isWhite, Board board, World world) {
        this.isWhite = isWhite;
        this.world = world;
        this.board = board;
        this.firstMove = true;
        if (isWhite) {
            whiteInitialPosition(initialXPos);
        } else {
            blackInitialPosition(initialXPos);
        }
    }

    @Override
    public void whiteInitialPosition(int x) {
        world.getTET()[x][board.getBottom()] = Tileset.WROOK;
        posX = x;
        posY = board.getBottom();
    }

    @Override
    public void blackInitialPosition(int x) {
        world.getTET()[x][board.getTop()] = Tileset.BROOK;
        posX = x;
        posY = board.getTop();
    }

    @Override
    public boolean validMove(int x, int y, TreeMap<Integer, TreeMap<Integer, Pieces>> pieces) {
        // check if move is out of bounds
        if (outOfBounds(x, y)) {
            return false;
        }

        // check if you would capture your own piece
        if (isPiece(x, y, pieces) && isWhite == pieces.get(x).get(y).getIsWhite()) {
            return false;
        }

        // if you're moving and you haven't been called to castle, then you need to set firstMove to false
        if (firstMove) {
            firstMove = false;
        }

        // check other valid moves
        int dist = 0;
        if (x != posX) {
            dist = Math.abs(posX - x);
        } else {
            dist = Math.abs(posY - y);
        }
        int distX = Integer.signum(x - posX);
        int distY = Integer.signum(y - posY);

        // move in direction of x. see if you hit a piece or can get to y
        if (x != posX && y != posY) {
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

    public boolean firstMove() {
        return firstMove;
    }

    public boolean castle() {
        firstMove = false;
        posX = posX - 2;
        return true;
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
