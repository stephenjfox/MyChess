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
        BoardLocation tempLoc = new BoardLocation(destination);

        functionalBoard[tempLoc.getY() - offset][tempLoc.getX() - offset] = tempLoc;
        tempLoc.placePiece(c);

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

}
