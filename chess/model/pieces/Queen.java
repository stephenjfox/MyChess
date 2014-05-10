package chess.model.pieces;

/**
 * Created by Stephen on 5/9/2014.
 */
public class Queen extends ChessPiece {
    public Queen(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String toString() {
        return isWhite() ? "Q" : "q";
    }
}
