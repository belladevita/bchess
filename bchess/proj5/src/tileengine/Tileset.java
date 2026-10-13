package tileengine;

import java.awt.Color;
import java.io.File;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final String wPawnImage = "proj5/images/wPawn.png";
    public static final String wKnightImage = "proj5/images/wKnight.png";
    public static final String wBishopImage = "proj5/images/wBishop.png";
    public static final String wRookImage = "proj5/images/wRook.png";
    public static final String wQueenImage = "proj5/images/wQueen.png";
    public static final String wKingImage = "proj5/images/wKing.png";
    public static final String bPawnImage = "proj5/images/bPawn.png";
    public static final String bKnightImage = "proj5/images/bKnight.png";
    public static final String bBishopImage = "proj5/images/bBishop.png";
    public static final String bRookImage = "proj5/images/bRook.png";
    public static final String bQueenImage = "proj5/images/bQueen.png";
    public static final String bKingImage = "proj5/images/bKing.png";

    public static final TETile WHITE = new TETile(' ', Color.white, Color.white, "white", 0);
    public static final TETile BLACK = new TETile(' ', Color.black, Color.black, "black", 1);
    public static final TETile NOTHING = new TETile(' ', Color.gray, Color.gray, "nothing", 2);
    public static final TETile WPAWN = new TETile(' ', Color.white, Color.white, "whitePawn", wPawnImage, 4);
    public static final TETile BPAWN = new TETile(' ', Color.white, Color.white, "blackPawn", bPawnImage, 5);
    public static final TETile WKNIGHT = new TETile(' ', Color.white, Color.white, "whiteKnight", wKnightImage, 6);
    public static final TETile BKNIGHT = new TETile(' ', Color.white, Color.white, "blackKnight", bKnightImage,  7);
    public static final TETile WBISHOP = new TETile(' ', Color.white, Color.white, "whiteBishop", wBishopImage, 8);
    public static final TETile BBISHOP = new TETile(' ', Color.white, Color.white, "blackBishop", bBishopImage, 9);
    public static final TETile WROOK = new TETile(' ', Color.white, Color.white, "whiteRook", wRookImage, 10);
    public static final TETile BROOK = new TETile(' ', Color.white, Color.white, "blackRook", bRookImage, 11);
    public static final TETile WQUEEN = new TETile(' ', Color.white, Color.white, "whiteQueen", wQueenImage, 12);
    public static final TETile BQUEEN = new TETile(' ', Color.white, Color.white, "blackQueen", bQueenImage, 13);
    public static final TETile WKING = new TETile(' ', Color.white, Color.white, "whiteKing", wKingImage, 14);
    public static final TETile BKING = new TETile(' ', Color.white, Color.white, "blackKing", bKingImage, 15);
}