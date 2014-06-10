package chess.model.board;

import chess.model.pieces.ChessPiece;

/**
 * Created by Stephen on 5/9/2014.
 */
public class BoardLocation {
    /**
     * This class is the 1-based board locations on a standard 8x8 chessboard
     */
    private final int x, y; // I hope final won't be a problem
    private String name;
    ChessPiece presentPiece = null;

    public BoardLocation(int paramX, int paramY) {
        this.x = paramX;
        this.y = paramY;
        this.name = charSwap(paramX) + "" + paramY;
    }

    /**
     * Creates a BoardLocation object to be placed in the board array of arrays.
     * @param lowercase the column letter on a valid chess board (i.e. a-h)
     * @param y the number row on a valid chess board (i.e. 1-8)
     */
    public BoardLocation(char lowercase, int y) {
    	this.name = lowercase + "" + y;
        this.x = intSwap(lowercase);
        this.y = y;
    }
    /**
     * Calls the previous constructor with the appropriate versions of their values
     * @param location: a valid chess board location (i.e. e3)
     */
    public BoardLocation(String location) {
    	this(location.charAt(0), Integer.parseInt(String.valueOf(location.charAt(1))) );
    	this.name = location;
    }

    /**
     * @param c: char of horizontal value
     * @return appropriate int ('a' becomes 1, 'b' becomes 2, etc)
     */
    private int intSwap(char c) {
        switch (c) {
            case 'a':
                return 1;
            case 'b':
                return 2;
            case 'c':
                return 3;
            case 'd':
                return 4;
            case 'e':
                return 5;
            case 'f':
                return 6;
            case 'g':
                return 7;
            case 'h':
                return 8;
            default:
                return 0;
        }
    }

    private char charSwap(int xVal) {
        switch (xVal) {
            case 1:
                return 'a';
            case 2:
                return 'b';
            case 3:
                return 'c';
            case 4:
                return 'd';
            case 5:
                return 'e';
            case 6:
                return 'f';
            case 7:
                return 'g';
            case 8:
                return 'h';
            default:
                return 'i';
        }
    }
    
    public void placePiece(ChessPiece piece){
    	this.presentPiece = piece;
    }
    
    public ChessPiece getPresentPiece() {
		return presentPiece;
	}

    public ChessPiece remove() {
    	ChessPiece toReturn = this.presentPiece;
    	this.presentPiece = null;
    	return toReturn;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public BoardLocation add(int xInc, int yInc) {

        int newX = this.x + xInc, newY = this.y + yInc;
        // Handle the math up a level
        return (newX < 9 && newX > 0) && (newY < 9 && newY > 0) ?
                new BoardLocation(newX, newY) : new NullBoardLocation();
    }

    public BoardLocation subtract(int xDec, int yDec) {

        return new BoardLocation(this.x - xDec, this.y - yDec);

    }

    public boolean isSameSquare(BoardLocation location) {
        return this.x == location.getX() && this.y == location.getY()
                /*&& this.getPresentPiece() == null && location.getPresentPiece() == null
                || this.x == location.getX() && this.y == location.getY()
                && this.getPresentPiece().toString().equals(location.getPresentPiece().toString())*/;
    }
    /**
     * Returns a string representation of this point and its location
     * in the {@code (x,y)} coordinate space. This method is
     * intended to be used only for debugging purposes, and the content
     * and format of the returned string may vary between implementations.
     * The returned string may be empty but may not be <code>null</code>.
     *
     * @return  a string representation of this point
     */
    public String toString() {
        return getClass().getSimpleName() + "[x=" + x + ",y=" + y + "]";
//    	return this.name;
    }
}
