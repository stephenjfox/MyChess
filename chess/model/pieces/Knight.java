package chess.model.pieces;

import chess.model.board.BoardLocation;

/**
 * Created by Stephen on 5/9/2014.
 */
public class Knight extends ChessPiece {
    public Knight(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(BoardLocation start, BoardLocation destination) {
        int dX = start.getX() - destination.getX();
        int dY = start.getY() - destination.getY();

        int absDX = Math.abs(dX), absDY = Math.abs(dY);

        return ( ((absDX == 2) && (absDY == 1) ) || ((absDX == 1) && (absDY == 2))) && !this.colorMatches(destination.getPresentPiece());
    }

    @Override
    public String toString() {
        return isWhite() ? "N" : "n";
    }
}
