package chess.model.board;

import chess.model.pieces.*;
import chess.viewmodel.FileInputHandler;

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
	public void placePiece(ChessPiece c, String destination) {
        BoardLocation tempLoc = new BoardLocation(destination);
        functionalBoard[tempLoc.getY() - ONE_BASED_OFFSET][tempLoc.getX() - ONE_BASED_OFFSET] = tempLoc;
        tempLoc.placePiece(c);
        System.out.println(c.getClass().getSimpleName() + " was placed at " + destination);
    }

    public void movePiece(String origin, String destination) {

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
