package chess.model.pieces;

import chess.model.board.BoardLocation;

/**
 * Created by Stephen on 5/9/2014.
 */
public class King extends ChessPiece {
    public King(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(BoardLocation start, BoardLocation destination) {
        int dX = start.getX() - destination.getX();
        int dY = start.getY() - destination.getY();

        return (Math.abs(dX) <= 1) && (Math.abs(dY) <= 1);
    }

    @Override
    public String toString() {
        return isWhite() ? "K" : "k";
    }
}
