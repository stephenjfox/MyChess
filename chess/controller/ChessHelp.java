package chess.controller;

import chess.model.board.BoardLocation;
import chess.model.pieces.*;

/**
 * Created by Stephen on 5/14/2014.
 */
public class ChessHelp {

    public static ChessPiece getNewPiece(String rep, char color) {
        switch (rep) {
            case "Queen":
                return new Queen(color == 'l');
            case "King":
                return new King(color == 'l');
            case "Bishop":
                return new Bishop(color == 'l');
            case "Knight":
                return new Knight(color == 'l');
            case "Rook":
                return new Rook(color == 'l');
            default:
                return new Pawn(color == 'l');
        }
    }

    public static boolean isValidMove(BoardLocation start, BoardLocation destination) {
        int dX = start.getX() - destination.getX();
        int dY = start.getY() - destination.getY();
        if (start.getPresentPiece() == null) {
            ChessPiece pieceToMove = start.getPresentPiece();
            return true; // TODO: THIS IS A LIE
        } else {
            return false;
        }
    }
    private static boolean isValidPawnMove(BoardLocation start, BoardLocation destination) {
        Pawn pawn = null;
        if (start.getPresentPiece() instanceof Pawn) {
            pawn = (Pawn) start.getPresentPiece();
//            return ( dY == (pawn.isMoved() ? 1 : 2 | 1) && dX ==  )
        }
        return false;
    }
}
