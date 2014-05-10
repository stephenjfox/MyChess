package chess.model.pieces;

/**
 * Created by Stephen on 5/9/2014.
 */
public abstract class ChessPiece {
    private boolean isWhite;

    protected ChessPiece(boolean isWhite) {
        this.isWhite = isWhite;
    }
}
