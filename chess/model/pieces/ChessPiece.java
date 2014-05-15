package chess.model.pieces;

/**
 * Created by Stephen on 5/9/2014.
 */
public abstract class ChessPiece {
    private boolean isWhite;

    private boolean moved; // false initially

    protected void setMoved(){
        moved = true;
    }
    protected boolean isMoved() {
        return moved;
    }
    protected ChessPiece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isWhite() {
        return isWhite;
    }
}
