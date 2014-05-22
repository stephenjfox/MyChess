package chess.model.board;

import chess.controller.ChessHelp;
import chess.model.pieces.*;

/**
 * This class is meant to facilitate the 2-D array that is to be the board.
 * The class is called "ChessBoard" because all of the things to be done are ON the board.
 */
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

    }

    public void movePiece(String origin, String destination) {
        BoardLocation tempOrigin = new BoardLocation(origin);
        BoardLocation tempDest = new BoardLocation(destination);

        if(
                (functionalBoard[tempOrigin.getY() - offset][tempOrigin.getX() - offset])
                        .getPresentPiece().isValidMove(tempOrigin,tempDest) )
        {

            // fetch the piece from the array
            ChessPiece removed =
                    functionalBoard[tempOrigin.getY() - offset][tempOrigin.getX() - offset].remove();
            tempDest.placePiece(removed);
            removed.setMoved();
            // assign the board location to the array where appropriate
            functionalBoard[tempDest.getY() - offset][tempDest.getX() - offset] = tempDest;
        }

        else {
            System.err.printf("%s to %s was an invalid move\n", origin, destination);
        }

    }

    public void moveTwoPiece(String[] locations) {
        moveTwoPiece(locations[0], locations[1], locations[2], locations[3]);
    }

    public void moveTwoPiece(String o1, String d1, String o2, String d2) {
        BoardLocation castler1Location = new BoardLocation(o1), castler2Location = new BoardLocation(o2);
        BoardLocation moveForC1 = new BoardLocation(d1), moveForC2 = new BoardLocation(d2);

        // Position integers
        int c1LocX = castler1Location.getX(), c1LocY = castler1Location.getY(),
                c2LocX = castler2Location.getX(), c2LocY = castler2Location.getY();

        // Chess pieces at those board locations
        ChessPiece c1 = functionalBoard[c1LocY- offset][c1LocX - offset].getPresentPiece(),
                c2 = functionalBoard[c2LocY- offset][c2LocX - offset].getPresentPiece();

        // Just check the starting locations (FIRST OFF)
        if( !(c1 instanceof King) ){
            System.err.println("If it's not a king, it can't castle");
            return; // Leave the method
        }

        // TODO: This method should be receiving a valid King-Rook pairing, SO DON'T MESS UP
        movePiece(o1, d1);
        movePiece(o2, d2);
    }

    public BoardLocation[][] getFunctionalBoard() {
        return functionalBoard.clone();
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
