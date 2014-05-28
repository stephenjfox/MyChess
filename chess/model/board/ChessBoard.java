package chess.model.board;

import chess.controller.ChessHelp;
import chess.controller.GameController;
import chess.model.pieces.*;

import java.util.ArrayList;

/**
 * This class is meant to facilitate the 2-D array that is to be the board.
 * The class is called "ChessBoard" because all of the things to be done are ON the board.
 */
public class ChessBoard {
	final int rowCount = 8;
	final int columnCount = 8;
    final int offset = 1; // 1-based offset because regulars count from 1
	private BoardLocation[][] functionalBoard = new BoardLocation[columnCount][rowCount];

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

    /**
     * Not overloaded, because its purpose is defined in its name, not
     * in the similarity to its older brother
     *
     * Will only be receiving valid BoardLocations.
     * This method dodges the need for arithmetic hokum by simply
     * placing the pieces down, after plucking.
     * @param c, King or rook to be castled
     * @param destination is the end location for that castle
     */
    private void placePieceForCastle(ChessPiece c, BoardLocation destination) {

    }

    /**
     *
     * @param origin: The origin board square
     * @param destination: The destination board square
     */
    public void movePiece(String origin, String destination) {

        BoardLocation tempOrigin = new BoardLocation(origin);
        BoardLocation tempDest = new BoardLocation(destination);

        if(    ChessHelp.isValidMove(
                getActualBoardSquare(tempOrigin),
                getActualBoardSquare(tempDest)) )
        {

            // fetch the piece from the array
            ChessPiece removed =
                    getActualBoardSquare(tempOrigin).remove();

//            // place it on the virtual board square
//            tempDest.placePiece(removed);


            // assign the board location to the array where appropriate
            ChessPiece destinationPiece = getPieceAtLocation(tempDest);
            if ( destinationPiece != null) {

                if( removed.colorMatches(destinationPiece) ) {

                    // If the color matches after passing in a decent move arg
                    System.err.println(removed.fancyName() + " at " + tempOrigin.getName()+
                            " tried taking allied " + destinationPiece.fancyName() + " at " + tempDest.getName());

                    getActualBoardSquare(tempOrigin).placePiece(removed);

                }
                else {
                    // Must be opposite color, so commence capture
                    getActualBoardSquare(tempDest).placePiece(removed);

                    removed.setMoved();

                    GameController.triggerDrawBoard();
                    GameController.flipPlayerTurn();
                }

            }
            else {
                // Place piece, because the destination is empty
                getActualBoardSquare(tempDest).placePiece(removed);

                removed.setMoved();

                GameController.triggerDrawBoard();
                GameController.flipPlayerTurn();
            }
        }

        else {
            System.err.printf("%s to %s was an invalid move, maybe the %s or pathIsClear()\n",
                    origin, destination, getPieceAtLocation(tempOrigin).fancyName());
        }

    }

    public void moveTwoPiece(String[] locations) {
        moveTwoPiece(locations[0], locations[1], locations[2], locations[3]);
    }

    /**
     * Perform the castinlg maneuver
     * @param o1: origin chessboard index for piece 1
     * @param d1: destination chessboard index for piece 1
     * @param o2: origin chessboard index for piece 2
     * @param d2: destination chessboard index for piece 2
     */
    public void moveTwoPiece(String o1, String d1, String o2, String d2) {
        BoardLocation castler1Location = new BoardLocation(o1), castler2Location = new BoardLocation(o2);
        BoardLocation moveForC1 = new BoardLocation(d1), moveForC2 = new BoardLocation(d2);


        // Position integers
        int c1LocX = castler1Location.getX(), c1LocY = castler1Location.getY(),
                c2LocX = castler2Location.getX(), c2LocY = castler2Location.getY();

        // Chess pieces at those board locations
        ChessPiece c1 = getPieceAtLocation(castler1Location),
                c2 = getPieceAtLocation(castler2Location);

        // Just check the starting locations (FIRST OFF)
        if( !(c1 instanceof King) ){
            System.err.println("If it's not a king, it can't castle");
            return; // Leave the method
        }

        if( !(c2 instanceof Rook)) {
            System.err.println("You're not even moving a Rook, so we're DEFINITELY not castling.");
            return; // Leave the method
        }

        King king = (King) removePieceAtLocation(castler1Location); // If we're good, we should be good
        Rook rook = (Rook) removePieceAtLocation(castler2Location);

        // TODO: This method should be receiving a valid King-Rook pairing, SO DON'T MESS UP
        performCastleManeuver(king, moveForC1, rook, moveForC2);
    }

    private void performCastleManeuver(King king, BoardLocation kingDest, Rook rook, BoardLocation rookDest) {

        // TODO: Placepiece() based castling method
        BoardLocation kingTrueDest = getActualBoardSquare(kingDest);
        BoardLocation rookTrueDest = getActualBoardSquare(rookDest);

        placePieceForCastle(king, kingTrueDest);
        placePieceForCastle(rook, rookTrueDest);
    }

    /**
     * @param boardLocation any BoardLocation object with desired, 1-based x and y coordinates
     * @return the piece in the 2-d at the compensated, proper location
     */
    private ChessPiece getPieceAtLocation(BoardLocation boardLocation) {
        return functionalBoard[boardLocation.getY()-offset][boardLocation.getX()-offset].getPresentPiece();
    }

    /**
     * @param boardLocation any BoardLocation object with desired, 1-based x and y coordinates
     * @return the piece in the 2-d at the compensated, proper location
     */
    private ChessPiece removePieceAtLocation(BoardLocation boardLocation) {
        return functionalBoard[boardLocation.getY()-offset][boardLocation.getX()-offset].remove();
    }

    /**
     * This method pulls the x and y properties from the BoardLocation passed.
     * From there, the values are offset to compensate for the 1-based counting,
     * we retrieve BoardLocation at those indices and return it.
     *
     * @param falsePositive the wrapper object for the desired indices
     * @return the true BoardLocation at the index in the 2-D array
     */
    private BoardLocation getActualBoardSquare(BoardLocation falsePositive) {

        /*
        Compensate for the 1-based counting and return the actual square with the proper
        properties
        */
        if (functionalBoard[falsePositive.getY()-offset][falsePositive.getX()-offset] == null) {

            functionalBoard[falsePositive.getY()-offset][falsePositive.getX()-offset]
                    = new BoardLocation(falsePositive.getName());
        }

        return functionalBoard[falsePositive.getY()-offset][falsePositive.getX()-offset];

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
        System.out.println("\n");
    }

    // Initialize for user play
    public void init(){
        boolean black = false, white = true;
        // Black royalty
        placePiece(new Rook(black), "a8");
        placePiece(new Knight(black), "b8");
        placePiece(new Bishop(black), "c8");
        placePiece(new Queen(black), "d8");
        placePiece(new King(black), "e8");
        placePiece(new Bishop(black), "f8");
        placePiece(new Knight(black), "g8");
        placePiece(new Rook(black), "h8");

        placePiece(new Pawn(black), "a7");
        placePiece(new Pawn(black), "b7");
        placePiece(new Pawn(black), "c7");
        placePiece(new Pawn(black), "d7");
        placePiece(new Pawn(black), "e7");
        placePiece(new Pawn(black), "f7");
        placePiece(new Pawn(black), "g7");
        placePiece(new Pawn(black), "h7");

        // White royalty
        placePiece(new Pawn(white), "a2");
        placePiece(new Pawn(white), "b2");
        placePiece(new Pawn(white), "c2");
        placePiece(new Pawn(white), "d2");
        placePiece(new Pawn(white), "e2");
        placePiece(new Pawn(white), "f2");
        placePiece(new Pawn(white), "g2");
        placePiece(new Pawn(white), "h2");

        placePiece(new Rook(white), "a1");
        placePiece(new Knight(white), "b1");
        placePiece(new Bishop(white), "c1");
        placePiece(new Queen(white), "d1");
        placePiece(new King(white), "e1");
        placePiece(new Bishop(white), "f1");
        placePiece(new Knight(white), "g1");
        placePiece(new Rook(white), "h1");
    }

    class BoardSquareLocator {

        private ArrayList<BoardLocation> pullSquaresWithPieces()
        { // Get the squares that have pieces on them
            ArrayList<BoardLocation> piecesExist = new ArrayList<>();

            for (BoardLocation[] boardLocations : functionalBoard) {

                for (BoardLocation boardLocation : boardLocations) {

                    if(boardLocation.getPresentPiece() != null)
                        piecesExist.add(boardLocation);
                }

            }

            return piecesExist;
        }

        public ArrayList<BoardLocation> pullSquaresWithWhites() {
            ArrayList<BoardLocation> returner = new ArrayList<>();

            // Returner array of BoardLocations with White Pieces
            for (BoardLocation boardLocation : pullSquaresWithPieces()) {

                if(boardLocation.getPresentPiece().isWhite())
                    returner.add(boardLocation); // Add the squares to the returner
            }

            return returner;
        }

        public ArrayList<BoardLocation> pullSquaresWithBlacks() {
            ArrayList<BoardLocation> returner = new ArrayList<>();

            // Returner array of BoardLocations with Black Pieces
            for (BoardLocation boardLocation : pullSquaresWithPieces()) {

                if(!boardLocation.getPresentPiece().isWhite())
                    returner.add(boardLocation); // Add the squares to the returner
            }

            return returner;
        }
    }

    class CheckFinder {

    }

}
