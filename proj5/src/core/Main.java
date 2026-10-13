package core;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;
import java.util.TreeMap;

public class Main {
    private static World world;
    private static Board board;
    private static TreeMap<Integer, TreeMap<Integer, Pieces>> pieces = new TreeMap<>();
    private static King whiteKing;
    private static King blackKing;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(12, 12);
        drawWorldBoard();
        initializePMap();
        setWhitePieces();
        setBlackPieces();
        ter.renderFrame(world.getTET());
        playGame(ter);
    }

    public static void drawWorldBoard() {
        world = new World(12, 12);
        board = new Board(world);
    }

    public static void initializePMap() {
        for (int lr = board.getLeft(); lr <= board.getRight(); lr++) {
            TreeMap<Integer, Pieces> yMap = new TreeMap<>();
            for (int bt = board.getBottom(); bt <= board.getTop(); bt++) {
                yMap.put(bt, null);
            }
            pieces.put(lr, yMap);
        }
    }

    public static void setWhitePieces() {
        //set White Pawns
        for (int i = board.getLeft(); i <= board.getRight(); i++) {
            Pawn wPawn = new Pawn(i, true, board, world);
            pieces.get(i).put(board.getBottom() + 1, wPawn);
        }

        //set White Rooks
        for (int i = board.getLeft(); i <= board.getRight(); i += 7) {
            Rook wRook = new Rook(i, true, board, world);
            pieces.get(i).put(board.getBottom(), wRook);
        }

        //set White Knights
        for (int i = board.getLeft() + 1; i <= board.getRight() - 1; i += 5) {
            Knight wKnight = new Knight(i, true, board, world);
            pieces.get(i).put(board.getBottom(), wKnight);
        }

        //set White Bishops
        for (int i = board.getLeft() + 2; i <= board.getRight() - 2; i += 3) {
            Bishop wBishop = new Bishop(i, true, board, world);
            pieces.get(i).put(board.getBottom(), wBishop);
        }

        //set White Queen
        Queen wQueen = new Queen(board.getLeft() + 3, true, board, world);
        pieces.get(board.getLeft() + 3).put(board.getBottom(), wQueen);

        //set White King
        King wKing = new King(board.getRight() - 3, true, board, world);
        whiteKing = wKing;
        pieces.get(board.getRight() - 3).put(board.getBottom(), wKing);
    }

    public static void setBlackPieces() {
        //set Black Pawns
        for (int i = board.getLeft(); i <= board.getRight(); i++) {
            Pawn bPawn = new Pawn(i, false, board, world);
            pieces.get(i).put(board.getTop() - 1, bPawn);
        }

        //set Black Rooks
        for (int i = board.getLeft(); i <= board.getRight(); i += 7) {
            Rook bRook = new Rook(i, false, board, world);
            pieces.get(i).put(board.getTop(), bRook);
        }

        //set Black Knights
        for (int i = board.getLeft() + 1; i <= board.getRight() - 1; i += 5) {
            Knight bKnight = new Knight(i, false, board, world);
            pieces.get(i).put(board.getTop(), bKnight);
        }

        //set Black Bishops
        for (int i = board.getLeft() + 2; i <= board.getRight() - 2; i += 3) {
            Bishop bBishop = new Bishop(i, false, board, world);
            pieces.get(i).put(board.getTop(), bBishop);
        }

        //set Black Queen
        Queen bQueen = new Queen(board.getLeft() + 3, false, board, world);
        pieces.get(board.getLeft() + 3).put(board.getTop(), bQueen);

        //set Black King
        King bKing = new King(board.getRight() - 3, false, board, world);
        blackKing = bKing;
        pieces.get(board.getRight() - 3).put(board.getTop(), bKing);
    }

    public static void playGame(TERenderer ter) {
        int selectedX = -1;
        int selectedY = -1;
        boolean whitesMove = true;

        while (true) {

            TETile[][] tBoard = board.getTET();
            TETile[][] tWorld = world.getTET();

            if (StdDraw.isMousePressed()) {
                int mouseX = (int) StdDraw.mouseX();
                int mouseY = (int) StdDraw.mouseY();

                boolean selectedPiece = tWorld[mouseX][mouseY] != Tileset.BLACK && tWorld[mouseX][mouseY] != Tileset.WHITE && tWorld[mouseX][mouseY] != Tileset.NOTHING;

                if (selectedX == -1 && selectedY == -1) {
                    if (selectedPiece) {
                        selectedX = mouseX;
                        selectedY = mouseY;
                    }
                } else {
                    // check if the move is valid
                    Pieces p = pieces.get(selectedX).get(selectedY);
                    King currentKing = whitesMove ? whiteKing : blackKing;
                    boolean validMove = p.validMove(mouseX, mouseY, pieces);

                    if (validMove && !currentKing.inCheck(pieces)) {

                        // move piece
                        tWorld[mouseX][mouseY] = tWorld[selectedX][selectedY];
                        tWorld[selectedX][selectedY] = board.getTileColors().get(selectedX).get(selectedY);

                        // adjust pieces map
                        pieces.get(selectedX).put(selectedY, null);
                        pieces.get(mouseX).put(mouseY, p);

                        // switch whose turn it is
                        whitesMove = !(whitesMove);

                        if (p instanceof King king && king.midCastle()) {
                            int rookIXPos = (mouseX == 4) ? 2 : 9;
                            int rookNXPos = (mouseX == 4) ? 5 : 7;
                            tWorld[rookNXPos][selectedY] = tWorld[rookIXPos][selectedY];
                            tWorld[rookIXPos][selectedY] = board.getTileColors().get(rookIXPos).get(selectedY);

                            // adjust pieces map for rook
                            pieces.get(rookIXPos).put(selectedY, null);
                            pieces.get(rookNXPos).put(selectedY, pieces.get(rookIXPos).get(selectedY));
                        }
                    }

                    // clear selection
                    selectedX = -1;
                    selectedY = -1;
                }
                StdDraw.pause(200);

            }

            // Draws the world to the screen.
            // This is in the while(true) loop, because we want to frequently re-render the world.
            ter.renderFrame(world.getTET());
        }
    }
}