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
    public void setMoved() {
        super.setMoved();
    }

    // If the piece was moved from the starting position
    @Override
    public boolean isMoved() {
        return super.isMoved();
    }

    @Override
    public boolean isValidMove(BoardLocation start, BoardLocation destination) {
        int dX = start.getX() - destination.getX();
        int dY = start.getY() - destination.getY();

        if(isMoved()) {
            //
        } else {
            //
        }
        return ( dY == (isMoved() ? 1 : 2 | 1) && dX == (destination.getPresentPiece() == null ? 0 : 0 | 1) );
    }

    @Override
    public String toString() {
        return isWhite() ? "P" : "p";
    }
}
