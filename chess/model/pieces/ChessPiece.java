package chess.model.pieces;

import chess.model.board.BoardLocation;

/**
 * Created by Stephen on 5/9/2014.
 */
public abstract class ChessPiece {
    private boolean isWhite;

    private boolean moved; // false initially

    public void setMoved(){
        moved = true;
    }

    public boolean isMoved() {
        return moved;
    }
    protected ChessPiece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public abstract boolean isValidMove(BoardLocation start, BoardLocation destination);
    public boolean isWhite() {
        return isWhite;
    }
}
