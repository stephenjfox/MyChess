package chess.model.pieces;

import chess.model.board.BoardLocation;

/**
 * Created by Stephen on 5/9/2014.
 */
public class Queen extends ChessPiece {
    public Queen(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(BoardLocation start, BoardLocation destination) {
        int dX = start.getX() - destination.getX();
        int dY = start.getY() - destination.getY();

        return (Math.abs(dX / dY) == 1) || ((dX == 0) ^ (dY == 0));
    }

    @Override
    public String toString() {
        return isWhite() ? "Q" : "q";
    }
}
