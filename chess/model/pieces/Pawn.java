package chess.model.pieces;

/**
 * Created by Stephen on 5/9/2014.
 */
public class Pawn extends ChessPiece {
    private boolean moved; // false initially
    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    public void setMoved(){
        moved = true;
    }
    public boolean isMoved() {
        return moved;
    }
    @Override
    public String toString() {
        return isWhite() ? "P" : "p";
    }
}
