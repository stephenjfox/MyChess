package chess.model.board;

import chess.model.pieces.*;

public class ChessBoard {
	final int rowCount = 8;
	final int columnCount = 8;
    final int offset = 1; // 1-based offset because regulars count from 1
	BoardLocation[][] functionalBoard = new BoardLocation[columnCount][rowCount];

    /**
     * Primary for the initialization of the board
     * @param c ChessPiece object to be placed on the board
     * @param destination the grid location on the board
     */
	public void placePiece(ChessPiece c, String destination) {
        BoardLocation tempDest = new BoardLocation(destination);

        functionalBoard[tempDest.getY() - offset][tempDest.getX() - offset] = tempDest;
        tempDest.placePiece(c);
//        System.out.println(c.getClass().getSimpleName() + " was placed at " + destination);
    }

    public void movePiece(String origin, String destination) {
        BoardLocation tempOrigin = new BoardLocation(origin);
        BoardLocation tempDest = new BoardLocation(destination);

        ChessPiece removed =
                functionalBoard[tempOrigin.getY() - offset][tempOrigin.getX() - offset].remove();
        tempDest.placePiece(removed);
        functionalBoard[tempDest.getY() - offset][tempDest.getX() - offset] = tempDest;
    }

    public void printBoard(){
		for (int i = rowCount - 1; i >= 0; i--) {
	        // If the board piece at a location is holding a piece
			for (int k = 0; k < columnCount; k++) {
                if(functionalBoard[i][k] != null) {
                    System.out.print("[ " + ((functionalBoard[i][k].getPresentPiece() == null) ? "-" :
                            functionalBoard[i][k].getPresentPiece().toString()) + " ]");
                }
                else { // it is null
                    System.out.print("[ - ]");
                }
			}
			System.out.println();
	    }

	}

    // Initialize for user play
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

        placePiece(new Pawn(false), "a7");
        placePiece(new Pawn(false), "b7");
        placePiece(new Pawn(false), "c7");
        placePiece(new Pawn(false), "d7");
        placePiece(new Pawn(false), "e7");
        placePiece(new Pawn(false), "f7");
        placePiece(new Pawn(false), "g7");
        placePiece(new Pawn(false), "h7");

        placePiece(new Pawn(true), "a2");
        placePiece(new Pawn(true), "b2");
        placePiece(new Pawn(true), "c2");
        placePiece(new Pawn(true), "d2");
        placePiece(new Pawn(true), "e2");
        placePiece(new Pawn(true), "f2");
        placePiece(new Pawn(true), "g2");
        placePiece(new Pawn(true), "h2");
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

}
