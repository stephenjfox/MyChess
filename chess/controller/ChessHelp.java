package chess.controller;

import chess.model.board.BoardLocation;
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

            // Knights don't worry about paths, for they jump
            if(pieceToMove.toString().equalsIgnoreCase("n")) return pieceToMove.isValidMove(start, destination);
            return pieceToMove.isValidMove(start, destination) && pathIsClear(start, destination);
        }
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

    public static boolean pathIsClear(BoardLocation start, BoardLocation destination){
        ModeOfTravel modeOfTravel;

        int startX = start.getX();
        int startY = start.getX();
        int destX = destination.getX();
        int destY = destination.getY();
        int dX, dY;
        // if its a vertical move
        if (startX == destX) {
            dX = 0;
        }
        else { // Diagonal typed setter
            dX = (startX - destY) / Math.abs(startX - destX);
        }

        // if it's a horizontal
        if (startY == destY) {
            dY = 0;
        }
        else { // Diagonal typed setter
            dY = (startY - destY) / Math.abs(startY - destY);
        }

//        modeOfTravel = (((dX / dY) == 1) ? ModeOfTravel.DIAGONAL : ModeOfTravel.STRAIGHT);

        while (startX != destX || startY != destY) {
            // Increment to check along the algebraically determined path
            startX += dX;
            startY += dY;

            // If there isn't a piece
            if (containerForTheGame.getFunctionalBoard()[startY][startX] != null) {
                return true;
            }
        }

        return false;
        // Return false if something is in the way
//        return (pathObstacles(start, destination, modeOfTravel).isEmpty());
    }

    private enum ModeOfTravel {
        DIAGONAL,
        STRAIGHT
    }
}
