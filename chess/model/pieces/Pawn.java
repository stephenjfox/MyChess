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


        if ( isWhite() ) {
            return (dY > (isMoved() ? -2 : -3) && dY < 0) &&
                (Math.abs(dX) == ((destination.getPresentPiece() == null) ? 0 : ( 1) ) );
        }
        else {
            //
            return (dY < (isMoved() ? 2 : 3 ) && dY > 0) &&
                ( Math.abs(dX) == ( (destination.getPresentPiece() == null) ? 0 : ( 1 ) ) );
        }
    }

    @Override
    public String toString() {
        return isWhite() ? "P" : "p";
    }
}
