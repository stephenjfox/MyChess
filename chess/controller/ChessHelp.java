package chess.controller;

import chess.model.board.BoardLocation;
import chess.model.board.ChessBoard;
import chess.model.pieces.*;

import java.util.ArrayList;
import java.util.HashMap;

import static chess.controller.GameController.containerForTheGame;

/**
 * Created by Stephen on 5/14/2014.
 */
public class ChessHelp {

//    private static HashMap<String, Method> methodMap = new HashMap<String, Method>(){
//        {
//            try {
//                put("King", this.getClass().getDeclaredMethod("isValidKingMove"));
//                put("Queen", this.getClass().getMethod("isValidQueenMove"));
//                put("Bishop", this.getClass().getMethod("isValidBishopMove"));
//                put("Knight", this.getClass().getMethod("isValidKnightMove"));
//                put("Rook", this.getClass().getMethod("isValidRookMove"));
//                put("Pawn", this.getClass().getMethod("isValidPawnMove"));
//            } catch (NoSuchMethodException e) {
//                ErrorLogger.logError(e.getLocalizedMessage()+"\nThe methodMap in ChessHelp needs HELP!");
//            }
//        }
//    };

    public static ChessPiece getNewPiece(String rep, final char color) {
        return new HashMap<String, ChessPiece>() {
            {
                put("King", new King(color == 'l'));
                put("Queen", new Queen(color == 'l'));
                put("Bishop", new Bishop(color == 'l'));
                put("Knight", new Knight(color == 'l'));
                put("Rook", new Rook(color == 'l'));
                put("Pawn", new Pawn(color == 'l'));
            }
        }.get(rep);
    }

    public static boolean isValidMove(BoardLocation start, BoardLocation destination) {

        if (start.getPresentPiece() != null) {

            ChessPiece pieceToMove = start.getPresentPiece(); // FIXED : That's going to throw a NullPointer
//
//            try {
//                return (Boolean) methodMap.get(pieceToMove.getClass().getCanonicalName())
//                        .invoke(start, destination);
//            } catch (IllegalAccessException e) {
//                ErrorLogger.logError(e.getLocalizedMessage()+"\nCouldn't access the boolean isValid...Move()");
//            } catch (InvocationTargetException e) {
//                ErrorLogger.logError(e.getLocalizedMessage()+"\nReflection invocation failed ");
//            }

            if (pieceToMove.isWhite() != GameController.isWhiteTurn()) {
                System.err.println("It's not "+ (!isWhiteTurn() ? "White's" : "Black's") +" turn to play.");
                return false;
            }

            // Knights don't worry about paths, for they jump
            if(pathIsClear(start, destination) || pieceToMove.toString().equalsIgnoreCase("n") )
                return pieceToMove.isValidMove(start, destination);
//            else
//                return pieceToMove.isValidMove(start, destination) ;

        }
        return false;

    }

    public static boolean movePutsKingInCheck(BoardLocation start, BoardLocation destination) {
        assert start.getPresentPiece() != null;
        ChessPiece pieceToMove = start.getPresentPiece(); // FIXED : That's going to throw a NullPointer

        if(pathIsClear(start, destination) || pieceToMove.toString().equalsIgnoreCase("n") )
            return pieceToMove.isValidMove(start, destination);
//
        return false;
    }

    public static boolean isValidCastle(BoardLocation kingSquare, BoardLocation rookSquare,
                                        BoardLocation kingDest, BoardLocation rookDest) {
        // TODO: validate for the castling maneuver
        // After making sure they are a valid King and Rook
        if(kingSquare.getPresentPiece() instanceof King && rookSquare.getPresentPiece() instanceof Rook) {
            // Make sure they haven't moved
            if (!kingSquare.getPresentPiece().isMoved() && !rookSquare.getPresentPiece().isMoved()) {
                // Which color side are we on
            }
        }
        return false;
    }

    private static boolean isWhiteTurn() {
        return GameController.isWhiteTurn();
    }

    public static ArrayList<BoardLocation> pathObstacles
            (BoardLocation start, BoardLocation destination, ModeOfTravel modeOfTravel){

        ArrayList<BoardLocation> conflictPieces = new ArrayList<>();

        int startX = start.getX();
        int startY = start.getY();

        int destX = destination.getX();
        int destY = destination.getY();

        int deltaX = startX - destX;
        int deltaY = startY - destY;

        BoardLocation[][] referenceBoard = containerForTheGame.getFunctionalBoard();

// TODO: Collision checking
        if (modeOfTravel == ModeOfTravel.STRAIGHT) {

            for (int i = referenceBoard.length - 1; i >= 0; i--) { // so we can look at the from white's side
                nullEscape:
                for(int j = 0; j < referenceBoard[i].length; j++) {

                    BoardLocation thisLocation;

                    if (referenceBoard[i][j] != null) {

                        thisLocation = referenceBoard[i][j];

                        if ( (startY < destY) && ((startX - destX) == 0) ) { // White side pawns and Rooks and Queens
                            if (thisLocation.getY() < destY && thisLocation.getY() > startY) {

                                if(thisLocation.getX() == startX) // Catch if it's in the right column
                                {
                                    conflictPieces.add(thisLocation);
                                }

                            }
                        }
                        else if(startY > destY && ((startX - destX) == 0) ) { // Black side pawns and Rooks and Queens
                            if (thisLocation.getY() < startY && thisLocation.getY() > destY) {

                                if(thisLocation.getX() == startX) // Catch if it's in the right column
                                {
                                    conflictPieces.add(thisLocation);
                                }

                            }
                        }
                        else if ( (startX < destX) && ((startY - destY) == 0)) { // To the right
                            if (thisLocation.getX() < startX && thisLocation.getX() > destX) {

                                if(thisLocation.getY() == startY) // Catch if it's in the right row
                                {
                                    conflictPieces.add(thisLocation);
                                }

                            }

                        }
                        else if ( (startX > destX) && ((startY - destY) == 0) ) { // To the left
                            if (thisLocation.getX() < startX && thisLocation.getX() > destX) {

                                if(thisLocation.getY() == startY) // Catch if it's in the right row
                                {
                                    conflictPieces.add(thisLocation);
                                }

                            }
                        }


                    } else {
                        break nullEscape;
                    }
                }
            }
        }
        else { // DIAGONAL modeOfTravel
            for (int i = referenceBoard.length - 1; i >= 0; i--) { // so we can look at the from white's side
                nullEscape:

                for(int j = 0; j < referenceBoard[i].length; j++) {

                    BoardLocation thisLocation;
                    if(referenceBoard[i][j] != null){
                        // TODO: Diagonals towrads black side of the board
                        thisLocation = referenceBoard[i][j];
                        if (thisLocation.getY() <= destY && thisLocation.getY() > startY) {

                            if (thisLocation.getX() <= destX && thisLocation.getX() > startX) {

                                conflictPieces.add(thisLocation);

                            }

                        }
                        else if (thisLocation.getY() > destY) {
                            // TODO: Diagonal towards white side of the board
                        }

                    }
                    else {
                        break nullEscape;
                    }
                }
            }
        }

        return conflictPieces;
    }

    /**
     * Calculates the total distance that needs to be moved, pulled from BoardLocation
     * and then gets the number of steps that should be necessary by dividing
     * the calculated distance
     * i.e.: 5 right, 5 up for a slope of 1/1, stepped through 5 times
     *
     * @param start BoardLocation that should have the moving piece
     * @param destination BoardLocation of the destination
     * @return whether or not the path is clear based on the math
     */
    public static boolean pathIsClear(BoardLocation start, BoardLocation destination){
        ModeOfTravel modeOfTravel;

        ChessPiece startPiece = start.getPresentPiece();
        
        int startX = start.getX();
        int startY = start.getY();

        int destX = destination.getX();
        int destY = destination.getY();

        int dX, dY; // Deltas of movement

        // if its a vertical move
        if (startX == destX) {
            dX = 0;
        }
        else { // Diagonal typed incrementer setter
            dX = (startX - destX) / Math.abs(startX - destX);
        }

        // if it's a horizontal
        if (startY == destY) {
            dY = 0;
        }
        else { // Diagonal typed incrementer setter
            dY = (startY - destY) / Math.abs(startY - destY);
        }

//        modeOfTravel = (((dX / dY) == 1) ? ModeOfTravel.DIAGONAL : ModeOfTravel.STRAIGHT);

        if((startPiece.toString().equalsIgnoreCase("q") ||
            startPiece.toString().equalsIgnoreCase("b") ||
            startPiece.toString().equalsIgnoreCase("p") ||
            startPiece.toString().equalsIgnoreCase("k"))
                && ((Math.abs(startX - destX) == 1) && (Math.abs(startY - destY) == 1)) )
            return true; // They're moving one square. Who cares what is about to go down

        if((startPiece.toString().equalsIgnoreCase("q") ||
            startPiece.toString().equalsIgnoreCase("r") ||
            startPiece.toString().equalsIgnoreCase("k"))
                && ((Math.abs(startX - destX) == 1) ^ (Math.abs(startY - destY) == 1)) )
            return true;

        // Not equal because we can increment up or down, depending on the piece and side of the board
        while (startX != destX || startY != destY) {

            // Increment to check along the algebraically determined path
        //
        //            // TODO: Fix bug where Kasparov can't move Bishop, because diagonals don't work anymore
        //            System.out.println("deltaX: "+dX + "\n" + "deltaY: "+dY + "\n" + start + "\n" + destination); // DEBUG
        //            // TODO: NOTE - pathing randomly makes two new BoardLocation with different values

            startX -= dX;// Positives (blacks) move "down"/"-" negatives (whites) move "up"/"-- or +"
            startY -= dY;// ^^^^

            BoardLocation nextSquareOnPath = new BoardLocation(startX, startY);
            nextSquareOnPath.placePiece(startPiece);

            BoardLocation squareBeforeDest = destination.subtract(1, 1);
            
            if (containerForTheGame.getFunctionalBoard()[startY- 1][startX -1] != null) {
                // If there isn't a piece there at the start
                if (containerForTheGame.getFunctionalBoard()[startY -1][startX -1].getPresentPiece() == null)
                    return true;
            }
            else if (containerForTheGame.getFunctionalBoard()[startY - 1][startX - 1] == null) {
                // If square their moving to is null, it's clear
                return pathIsClear(nextSquareOnPath, destination);
            }
            else if (containerForTheGame.getFunctionalBoard()[destY - 1][destX - 1] == null) {
                // If the destination is clear
                return pathIsClear(start, squareBeforeDest);
            }

        }

        return false;
        // Return false if something is in the way
//        return (pathObstacles(start, destination, modeOfTravel).isEmpty());
    }

    public static void printPlayerTurn() {
        System.out.println(GameController.isWhiteTurn()? "White's turn" : "Black's turn");
    }

    public static void callCheck() {
        ChessBoard.CheckFinder finder = containerForTheGame.getCheckFinder();

        if(finder.blackIsInCheck()) {
            System.out.println("Black King is in check");

//            System.exit(0);
        } else {
            System.out.println("Black King is not in check");
        }

        if(finder.whiteIsInCheck()) {
            System.out.println("White King is in check");

//            System.exit(0);
        } else {
            System.out.println("White King is not in check");
        }
    }

    private enum ModeOfTravel {
        DIAGONAL,
        STRAIGHT
    }
}
