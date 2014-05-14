package chess.model.board;

import chess.model.pieces.*;

public class ChessBoard {
	final int rowCount = 8;
	final int columnCount = 8;
    final int ONE_BASED_OFFSET = 1;
	BoardLocation[][] functionalBoard = new BoardLocation[columnCount][rowCount];

    /**
     * Primary for the initialization of the board
     * @param c ChessPiece object to be placed on the board
     * @param destination the grid location on the board
     */
	private void placePiece(ChessPiece c, String destination) {
        BoardLocation tempLoc = new BoardLocation(destination);
        functionalBoard[tempLoc.getY() - ONE_BASED_OFFSET][tempLoc.getX() - ONE_BASED_OFFSET] = tempLoc;
        tempLoc.placePiece(c);
        System.out.println(c.getClass().getSimpleName() + " was placed at " + destination + "\n");
    }

    public void init(){
        // Black royalty
        placePiece(new Rook(false), "a8");
        placePiece(new Knight(false), "b8");
        placePiece(new Bishop(false), "c8");
        placePiece(new Queen(false), "d8");
        placePiece(new King(false), "e8");
        placePiece(new Bishop(false), "f8");
        placePiece(new Knight(false), "g8");
        placePiece(new Rook(false), "h8");

        // TODO: Link initialization of pieces with the method calls in the FileInputHandler
        // TODO: perhaps send a "Movement" (object) back up to the Game Controller?
        placePiece(new Pawn(true), "a4");
        // White royalty
        placePiece(new Rook(true), "a1");
        placePiece(new Knight(true), "b1");
        placePiece(new Bishop(true), "c1");
        placePiece(new Queen(true), "d1");
        placePiece(new King(true), "e1");
        placePiece(new Bishop(true), "f1");
        placePiece(new Knight(true), "g1");
        placePiece(new Rook(true), "h1");
    }
    public void printBoard(){
		for (int i = rowCount - 1; i >= 0; i--) {
	        // If the board piece at a location is holding a piece
			for (int k = 0; k < columnCount; k++) {
		        System.out.print("[ " +((functionalBoard[i][k]/*.getPresentPiece()*/ == null) ? "-" :
		        	functionalBoard[i][k].getPresentPiece().toString())+ " ]");
			}
			System.out.println();
	    }

	}
	
}
