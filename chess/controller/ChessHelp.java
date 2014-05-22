package chess.controller;

import chess.model.board.BoardLocation;
import chess.model.board.ChessBoard;
import chess.model.pieces.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
            return pieceToMove.isValidMove(start, destination) /*&& pathIsClear(start, destination)*/;
        }
        return false;
    }



    public static boolean isValidCastle() {
        // TODO: validate for the castling maneuver
        return false;
    }

    public static ArrayList<BoardLocation> pathObstacles
            (BoardLocation start, BoardLocation destination, ModeOfTravel modeOfTravel){

        ArrayList<BoardLocation> conflictPieces = new ArrayList<>();

        int startX = start.getX();
        int startY = start.getY();

        int destX = destination.getX();
        int destY = destination.getY();

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
                            // TODO: straight movement to the left

                        }
                        else if ( (startX > destX) && ((startY - destY) == 0) ) { // To the left
                            // TODO: straight movement to the right
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

        int dX = Math.abs( start.getX() - destination.getX() );
        int dY = Math.abs( start.getY() - destination.getY() );

        modeOfTravel = (((dX / dY) == 1) ? ModeOfTravel.DIAGONAL : ModeOfTravel.STRAIGHT);

        // Return false if something is in the way
        return (pathObstacles(start, destination, modeOfTravel).isEmpty());
    }

    private enum ModeOfTravel {
        DIAGONAL,
        STRAIGHT
    }
}
