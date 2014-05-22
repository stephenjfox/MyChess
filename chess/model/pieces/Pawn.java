package chess.model.pieces;

import chess.model.board.BoardLocation;

/**
 * Created by Stephen on 5/9/2014.
 */
public class Pawn extends ChessPiece {
    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(BoardLocation start, BoardLocation destination) {
        int dX = start.getX() - destination.getX();
        int dY = start.getY() - destination.getY();


        return ( (Math.abs(dY) < (isMoved() ? 2 : (3)) ) // less than 2 or 3 is to insure it sticks
                && ( Math.abs(dX) == ((destination.getPresentPiece() == null) ? 0 : (1)) ) );
    }

    @Override
    public String toString() {
        return isWhite() ? "P" : "p";
    }
}
