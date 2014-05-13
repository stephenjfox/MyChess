package chess.model.board;

import chess.model.pieces.ChessPiece;

public class ChessBoard {
	final int rowCount = 8;
	final int columnCount = 8;
	BoardLocation[][] functionalBoard = new BoardLocation[columnCount][rowCount];

	private void placePiece(ChessPiece c, String destination){
        
        System.out.println(c.toString()+ " was place at " + destination);
    }
	
	public void printSelf(){
		for (int i = 0; i < rowCount; i++) {
	        // If the board piece at a location is holding a piece
			for (int k = 0; k < columnCount; k++) {
		        System.out.print("[ " +((functionalBoard[i][k]/*.getPresentPiece()*/ == null) ? "-" :
		        	functionalBoard[i][k].getPresentPiece().toString())+ " ]");
			}
			System.out.println();
	    }

	}
	
}
