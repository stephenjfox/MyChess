package chess.model.pieces;

/**
 * Created by Stephen on 5/9/2014.
 */
public class King extends ChessPiece {
    public King(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String toString() {
        return "K";
    }
}
