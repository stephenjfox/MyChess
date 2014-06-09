package chess.model.board;

import chess.controller.ChessHelp;
import chess.controller.GameController;
import chess.controller.MoveProjector;
import chess.model.pieces.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is meant to facilitate the 2-D array that is to be the board.
 * The class is called "ChessBoard" because all of the things to be done are ON the board.
 */
public class ChessBoard {
	final int rowCount = 8;
	final int columnCount = 8;
    final int offset = 1; // 1-based offset because regulars count from 1

	private BoardLocation[][] functionalBoard = new BoardLocation[columnCount][rowCount];

//    CheckFinder searchForCheck = null;


    public ChessBoard() {
    }

    public ChessBoard(BoardLocation[][] functionalBoard) {
        this.functionalBoard = functionalBoard;
    }

    public CheckFinder getCheckFinder() {
//        return searchForCheck;
        return new CheckFinder();
    }

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
     * @param castlePiece, King or rook to be castled
     * @param destination is the end location for that castle
     */
    private void placePieceForCastle(ChessPiece castlePiece, BoardLocation destination) {
        getActualBoardSquare(destination).placePiece(castlePiece);
    }

    /**
     *
     * @param origin: The origin board square
     * @param destination: The destination board square
     */
    public void movePiece(String origin, String destination) {
//
//        if(searchForCheck == null){
//            searchForCheck = new CheckFinder();
//        }

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
//                    ChessHelp.callCheck();
                }

            }
            else {
                // Place piece, because the destination is empty
                getActualBoardSquare(tempDest).placePiece(removed);

                removed.setMoved();

                GameController.triggerDrawBoard();
                GameController.flipPlayerTurn();
//                ChessHelp.callCheck();
            }

        }

        else {
            System.err.printf("%s to %s was an invalid move. It might be the %s or pathIsClear()\n",
                    origin, destination, getPieceAtLocation(tempOrigin).fancyName());
        }

    }

    public void movePieceWithoutTurnCheck(String origin, String destination) {
//
//        if(searchForCheck == null){
//            searchForCheck = new CheckFinder();
//        }

        BoardLocation tempOrigin = new BoardLocation(origin);
        BoardLocation tempDest = new BoardLocation(destination);

//        if(    getPieceAtLocation(tempOrigin).isValidMove(
//                getActualBoardSquare(tempOrigin),
//                getActualBoardSquare(tempDest)) )
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

//                    GameController.triggerDrawBoard();
//                    GameController.flipPlayerTurn();
//                    ChessHelp.callCheck();
                }

            }
            else {
                // Place piece, because the destination is empty
                getActualBoardSquare(tempDest).placePiece(removed);

                removed.setMoved();

//                GameController.triggerDrawBoard();
//                GameController.flipPlayerTurn();
//                ChessHelp.callCheck();
            }

        }

//        else {
//            System.err.printf("%s to %s was an invalid move. It might be the %s or pathIsClear()\n",
//                    origin, destination, getPieceAtLocation(tempOrigin).fancyName());
//        }

    }

    /**
     * Simply a relay to the realy method
     * @param locations an String[] array of size 4; no exceptions
     */
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

        int c1DeltaX = castler1Location.getX() - moveForC1.getX();
        int c2DeltaX = castler2Location.getX() - moveForC2.getX();

        // Check the difference in x and y between the pieces

        // (delta-Y should be 0 and y == 1 | 8, delta-X == 5 - 1 | 5 - 8)

        // TODO: in leisure, finish castling
        {
            int deltaX = c1LocX - c2LocX;
            int deltaY = c1LocY - c2LocY;

            if(deltaY == 0 && (c1LocY == 8 || c2LocY == 1)) {
                System.out.println("DEBUG: Passed vertical castling test");

                if(deltaX == 4 || deltaX == -3){
                    System.out.println("DEBUG: Passed horizontal castling test");
                }
            }
            else {
                System.out.println("DEBUG: Invalid castling maneuver was attempted");

            }

        }



        // Chess pieces at those board locations
        ChessPiece c1 = getPieceAtLocation(castler1Location),
                c2 = getPieceAtLocation(castler2Location);

        // Just check the starting locations (FIRST OFF)
        if( !(c1 instanceof King) ){
            System.err.println("If it's not a king, it can't castle");
        }

        else if( !(c2 instanceof Rook)) {
            System.err.println("You're not even moving a Rook, so we're DEFINITELY not castling.");
        }

        else {

            King king = (King) removePieceAtLocation(castler1Location); // If we're here, we should be good
            Rook rook = (Rook) removePieceAtLocation(castler2Location);

            System.out.println("Castling INITIATED");
            // This method should be receiving a valid King-Rook pairing, SOOOO no worries
            performCastleManeuver(king, moveForC1, rook, moveForC2);
        }

    }

    private void performCastleManeuver(King king, BoardLocation kingDest, Rook rook, BoardLocation rookDest) {

        BoardLocation rookTrueDest = getActualBoardSquare(rookDest);
        BoardLocation kingTrueDest = getActualBoardSquare(kingDest);

        // Place the piece on a specific square
        placePieceForCastle(king, kingTrueDest);
        placePieceForCastle(rook, rookTrueDest);

        GameController.triggerDrawBoard();
        GameController.flipPlayerTurn();
    }

    /**
     * @param boardLocation any BoardLocation object with desired, 1-based x and y coordinates
     * @return the piece in the 2-d at the compensated, proper location
     */
    private ChessPiece getPieceAtLocation(BoardLocation boardLocation) {

        return getActualBoardSquare(boardLocation).getPresentPiece();
    }

    /**
     * @param boardLocation any BoardLocation object with desired, 1-based x and y coordinates
     * @return the piece in the 2-d at the compensated, proper location
     */
    private ChessPiece removePieceAtLocation(BoardLocation boardLocation) {
        return getActualBoardSquare(boardLocation).remove();
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

    /**
     * The inner CheckFinder class's sole purpose is it tell the [ChessHelp]
     * that a King is in check. And which one.
     */
    public class CheckFinder {
        // TODO: determine check and give the shout out
//        BoardSquareLocator forTheKings = new BoardSquareLocator();

        BoardLocation whiteKingLocation;
        BoardLocation blackKingLocation;
        MoveProjector projector;

        public CheckFinder() {

            whiteKingLocation = getWhiteKingSquare();

            blackKingLocation = getBlackKingSquare();

            projector = new MoveProjector(ChessBoard.this);
        }

        public boolean blackIsInCheck() {

            ArrayList<BoardLocation> enemyLocation = pullSquaresWithColor(true);

            boolean blackInCheck = false;

            for (Iterator<BoardLocation> iterator = enemyLocation.iterator(); iterator.hasNext() && !blackInCheck; ) {

                BoardLocation whiteLocation = iterator.next();

                if (ChessHelp.testMoveForCheck(whiteLocation, blackKingLocation)) {

                    blackInCheck = true;

                }

            }

            return blackInCheck;
        }

        public boolean whiteIsInCheck() {
            // Tell someone that IntelliJ auto-suggested black as enemy to white
            ArrayList<BoardLocation> enemyLocations = pullSquaresWithColor(false);
            boolean whiteInCheck = false;

            for (Iterator<BoardLocation> iterator = enemyLocations.iterator(); iterator.hasNext() && !whiteInCheck; ) {
                BoardLocation blackLocation = iterator.next();

                if (ChessHelp.testMoveForCheck(blackLocation, whiteKingLocation)) {

                    whiteInCheck = true;

                }

            }

            return whiteInCheck;
        }


        public boolean blackIsInCheckMate() {

            ArrayList<BoardLocation> allies = pullSquaresWithColor(false);
            // The allied board squares : BlackPiece squares

//            ArrayList<BoardLocation> potentialAlliedMoves = new ArrayList<>();

            ArrayList<BoardLocation> enemyLocations = pullSquaresWithColor(true);

//            for (BoardLocation allyLocation : allies) { // get all possible moves for all the animals
//                potentialAlliedMoves.addAll(projector.projectValidMoves(
//                        allyLocation, NumberCruncher.pieceMaxRange(allyLocation.getPresentPiece())));
//            }
//
//            potentialAlliedMoves.removeIf(location -> projector.projectValidMoves(blackKingLocation, 1).contains(location));

            boolean blackInCheckmate;

            blackInCheckmate = projector.projectCheckScenario(blackKingLocation, enemyLocations, allies);

            return blackInCheckmate;
        }

        public boolean whiteIsInCheckMate() {

            ArrayList<BoardLocation> allies = pullSquaresWithColor(true);
            // The allied board squares : WhitePiece squares

//            ArrayList<BoardLocation> potentialMoves = projector.projectValidMoves(whiteKingLocation, 1);

            ArrayList<BoardLocation> enemyLocations = pullSquaresWithColor(false);

//            for (BoardLocation allyLocation : allies) { // get all possible moves for all the animals
//                potentialMoves.addAll( projector.projectValidMoves(
//                        allyLocation, NumberCruncher.pieceMaxRange(allyLocation.getPresentPiece())) );
//            }

            boolean whiteInCheckmate;

            whiteInCheckmate = projector.projectCheckScenario(whiteKingLocation, enemyLocations, allies);

            return whiteInCheckmate;
        }

        public boolean gameIsInStaleMate() {

            // TODO: modify body to compliance with MoveProjector API

//            boolean state1 = whiteIsInCheck(), state2 = false;

            boolean gameTurn = GameController.isWhiteTurn();

            ArrayList<BoardLocation> enemyLocations = pullSquaresWithColor(gameTurn);
            ArrayList<BoardLocation> allies = pullSquaresWithColor(!gameTurn);

            BoardLocation kingSquare = gameTurn ? blackKingLocation : whiteKingLocation;

            return projector.projectCheckScenario(kingSquare, enemyLocations, allies);
        }

        /**
         * Search the entire 2D array for BoardLocation's that have pieces
         * @return An ArrayList<BoardLocation> fully populated
         */
        private ArrayList<BoardLocation> pullSquaresWithPieces()
        { // Get the squares that have pieces on them
            ArrayList<BoardLocation> piecesExist = new ArrayList<>();

            for (BoardLocation[] row : functionalBoard) {

                for (BoardLocation boardLocation : row) {

                    if (boardLocation != null) {
                            if(boardLocation.getPresentPiece() != null) {
                            piecesExist.add(boardLocation);
    //                        System.out.println("Added a piece");
                        }
                    }
                }

            }

            return piecesExist;
        }

        public ArrayList<BoardLocation> pullSquaresWithColor(boolean isWhite) {
            ArrayList<BoardLocation> returner = new ArrayList<>();

            // Returner array of BoardLocations with White Pieces
            for (BoardLocation boardLocation : pullSquaresWithPieces()) {

                if(boardLocation.getPresentPiece().isWhite() == isWhite)
                    returner.add(boardLocation); // Add the squares to the returner
            }

            return returner;
        }

        public BoardLocation getWhiteKingSquare() {

            // Iterate thought the search with proper boolean for this color

            for (BoardLocation boardLocation : pullSquaresWithColor(true)) {

                // DEBUG
//                System.out.println(boardLocation.getPresentPiece());
                // DEBUG

                if (boardLocation.getPresentPiece().toString().equals("K"))
                    return boardLocation;
            }
            return new NullBoardLocation(); // Null-rep board square
        }

        public BoardLocation getBlackKingSquare() {

            // Iterate thought the search with proper boolean for this color

            for (BoardLocation boardLocation : pullSquaresWithColor(false)) {

                // DEBUG
//                System.out.println(boardLocation.getPresentPiece());
                // DEBUG

                if (boardLocation.getPresentPiece().toString().equals("k"))
                    return boardLocation;
            }
            return new NullBoardLocation(); // Null-rep board square
        }
    }

}
