package chess.controller;

import chess.model.board.BoardLocation;
import chess.model.pieces.*;

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
// TODO: Possibly interface the piece validation. It's principle-based refactoring
    private static boolean isValidKingMove(BoardLocation start, BoardLocation destination) {
        assert !(start.getPresentPiece() == null);

        int dX = start.getX() - destination.getX();
        int dY = start.getY() - destination.getY();

        King king = (King) start.getPresentPiece();
        // TODO: Write king validation

        return false;
    }
    private static boolean isValidQueenMove(BoardLocation start, BoardLocation destination) {
        assert !(start.getPresentPiece() == null);

        int dX = start.getX() - destination.getX();
        int dY = start.getY() - destination.getY();

        Queen queen = (Queen) start.getPresentPiece();
        // TODO: Queen validation will return isValidRookMove() || isValidBishopMove()
        return isValidBishopMove(start, destination) || isValidRookMove(start, destination);
    }

//    private static boolean isValidBishopMove(int changeOfX, int changeOfY) {
//        return Math.abs(changeOfX) == Math.abs(changeOfY);
//    }

    private static boolean isValidBishopMove(BoardLocation start, BoardLocation destination) {
        // TODO: Bishop validation should DEMAND a slope of 1 or -1
        int dX = start.getX() - destination.getX();
        int dY = start.getY() - destination.getY();

        // If the slope is good, the move is valid enough. BoardLocation needs range constraint
        return (Math.abs(dX / dY) == 1);
    }

    private static boolean isValidRookMove(BoardLocation start, BoardLocation destination) {
        // TODO: Rook validation should DEMAND x ^ y = 0

        int dX = start.getX() - destination.getX();
        int dY = start.getY() - destination.getY();

        return (dX == 0) ^ (dY == 0);
    }

    private static boolean isValidPawnMove(BoardLocation start, BoardLocation destination) {
        assert !(start.getPresentPiece() == null);

        int dX = start.getX() - destination.getX();
        int dY = start.getY() - destination.getY();

        Pawn pawn =  (Pawn) start.getPresentPiece();
//            return ( dY == (pawn.isMoved() ? 1 : 2 | 1) && dX ==  )
        return false;
    }

    public static boolean isValidCastle() {
        return false;
    }
}
