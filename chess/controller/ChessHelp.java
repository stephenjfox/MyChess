package chess.controller;

import chess.model.board.BoardLocation;
import chess.model.pieces.*;
import chess.viewmodel.ErrorLogger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by Stephen on 5/14/2014.
 */
public class ChessHelp {

    private static HashMap<String, Method> methodMap = new HashMap<String, Method>(){
        {
            try {
                put("King", this.getClass().getMethod("isValidKingMove"));
                put("Queen", this.getClass().getMethod("isValidQueenMove"));
                put("Bishop", this.getClass().getMethod("isValidBishopMove"));
                put("Knight", this.getClass().getMethod("isValidKnightMove"));
                put("Rook", this.getClass().getMethod("isValidRookMove"));
                put("Pawn", this.getClass().getMethod("isValidPawnMove"));
            } catch (NoSuchMethodException e) {
                ErrorLogger.logError(e.getLocalizedMessage()+"\nThe methodMap in ChessHelp needs HELP!");
            }
        }
    };

    public static ChessPiece getNewPiece(String rep, final char color) {
//        switch (rep) {
//            case "Queen":
//                return new Queen(color == 'l');
//            case "King":
//                return new King(color == 'l');
//            case "Bishop":
//                return new Bishop(color == 'l');
//            case "Knight":
//                return new Knight(color == 'l');
//            case "Rook":
//                return new Rook(color == 'l');
//            default:
//                return new Pawn(color == 'l');
//        }
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
        int dX = start.getX() - destination.getX();
        int dY = start.getY() - destination.getY();
        if (start.getPresentPiece() != null) {
            ChessPiece pieceToMove = start.getPresentPiece(); // That's going to throw a NullPointer

            try {
                return (Boolean) methodMap.get(pieceToMove.getClass().getCanonicalName()).invoke(start, destination);
            } catch (IllegalAccessException e) {
                ErrorLogger.logError(e.getLocalizedMessage()+"\nCouldn't access the boolean isValid...Move()");
            } catch (InvocationTargetException e) {
                ErrorLogger.logError(e.getLocalizedMessage()+"\nReflection invocation failed ");
            }
        }
        return false;
    }
    private static boolean isValidPawnMove(BoardLocation start, BoardLocation destination) {
        assert !(start.getPresentPiece() == null);
        Pawn pawn =  (Pawn) start.getPresentPiece();
//            return ( dY == (pawn.isMoved() ? 1 : 2 | 1) && dX ==  )
        return false;
    }

    public static boolean isValidCastle() {
        return false;
    }
}
