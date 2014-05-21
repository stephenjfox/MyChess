package chess.model.pieces;

import chess.model.board.BoardLocation;

/**
 * Created by Stephen on 5/9/2014.
 */
public class Bishop extends ChessPiece {

    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(BoardLocation start, BoardLocation destination) {
        int dX = start.getX() - destination.getX();
        int dY = start.getY() - destination.getY();

        // If the slope is good, the move is valid enough. BoardLocation needs range constraint
        return (Math.abs(dX / dY) == 1);
    }

    @Override
    public String toString() {
        return isWhite() ? "B" : "b";
    }
}
