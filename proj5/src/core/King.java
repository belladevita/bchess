package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.TreeMap;

public class King implements Pieces {
    private boolean isWhite;
    private Board board;
    private World world;
    private int posX;
    private int posY;
    private boolean firstMove;
    private boolean midCastle;

    public King(int initialXPos, boolean isWhite, Board board, World world) {
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
        world.getTET()[x][board.getBottom()] = Tileset.WKING;
        posX = x;
        posY = board.getBottom();
    }

    @Override
    public void blackInitialPosition(int x) {
        world.getTET()[x][board.getTop()] = Tileset.BKING;
        posX = x;
        posY = board.getTop();
    }

    // right now, the king can move into check. can't have that.
    // need to check for check and checkmate

    @Override
    public boolean validMove(int x, int y, TreeMap<Integer, TreeMap<Integer, Pieces>> pieces) {
        if (midCastle) {
            midCastle = false;
        }

        if (outOfBounds(x, y)) {
            return false;
        }

        if (isPiece(x, y, pieces) && isWhite == pieces.get(x).get(y).getIsWhite()) {
            return false;
        }

        int distX = x - posX;
        int distY = Math.abs(y - posY);

        // castle
        if (firstMove && distY == 0 && Math.abs(distX) == 2) {
            if (pieces.get(posX + ((int) Math.signum(distX))).get(posY) == null && pieces.get(posX + ((int) Math.signum(distX) * 2)).get(posY) == null) {
                int rookpos = (distX < 0) ? 1 : 0;
                if (pieces.get(posX + ((int) Math.signum(distX) * (3 + rookpos))).get(posY) instanceof Rook rook && rook.firstMove()) {
                    rook.castle();
                    firstMove = false;
                    midCastle = true;
                    posX = x;
                    posY = y;
                    return true;
                }
            }
        }

        if (Math.abs(distX) > 1 || distY > 1) {
            return false;
        }

        // check if the king will move into check
        if (inCheck(pieces)) {
            System.out.println("in check");
            return false;
        }

        posX = x;
        posY = y;
        firstMove = false;
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

    public boolean midCastle() {
        return midCastle;
    }

    public boolean inCheck(TreeMap<Integer, TreeMap<Integer, Pieces>> pieces) {
        int[][] KNIGHT_OFFSETS = {{-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}};
        int[][] ROOK_DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int[][] BISHOP_DIRECTIONS = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        // knights
        for (int[] offset : KNIGHT_OFFSETS) {
            int x = posX + offset[0];
            int y = posY + offset[1];

            if (outOfBounds(x, y)) {
                continue;
            }

            if (pieces.get(x).get(y) instanceof Knight knight && knight.getIsWhite() != isWhite) {
                return true;
            }
        }

        // rooks and queens (orthogonal)
        for (int[] dir : ROOK_DIRECTIONS) {
            int x = posX + dir[0];
            int y = posY + dir[1];

            while (!outOfBounds(x, y)) {
                Pieces piece = pieces.get(x).get(y);

                if (piece != null) {
                    if ((piece instanceof Rook || piece instanceof Queen) && piece.getIsWhite() != isWhite) {
                        return true;
                    }
                    break; // blocked, friend or foe
                }

                x += dir[0];
                y += dir[1];
            }
        }

        // bishops and queens (diagonal)
        for (int[] dir : BISHOP_DIRECTIONS) {
            int x = posX + dir[0];
            int y = posY + dir[1];

            while (!outOfBounds(x, y)) {
                Pieces piece = pieces.get(x).get(y);

                if (piece != null) {
                    if ((piece instanceof Bishop || piece instanceof Queen) && piece.getIsWhite() != isWhite) {
                        return true;
                    }
                    break;
                }

                x += dir[0];
                y += dir[1];
            }
        }

        // pawns
        int pawnDirection = isWhite ? 1 : -1;
        int pawnY = posY + pawnDirection;

        for (int dx : new int[]{-1, 1}) {
            int x = posX + dx;
            if (outOfBounds(x, pawnY)) {
                continue;
            }
            if (pieces.get(x).get(pawnY) instanceof Pawn pawn && pawn.getIsWhite() != isWhite) {
                return true;
            }
        }

        return false;
    }



    public boolean inCheckmate(TreeMap<Integer, TreeMap<Integer, Pieces>> pieces) {
        int[][] KING_DIRECTIONS = {{0,-1},{-1,1},{1,-1},{1,0},{1,1},{0,1},{-1,-1},{-1,0}};
        if (inCheck(pieces)) {
            for (int[] dir : KING_DIRECTIONS) {
                if (validMove(posX + dir[0], posY + dir[1], pieces)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // whenever another piece moves, it needs to check:
    // if its own king is in check (because you can't move somewhere else if your own king is in check)
    // if the other king is in check - actually no it doesn't, just need to do the first check since it'll switch to checking own king

}
