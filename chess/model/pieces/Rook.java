package chess.model.pieces;

/**
 * Created by Stephen on 5/9/2014.
 */
public class Rook extends ChessPiece {
    public Rook(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String toString() {
        return isWhite() ? "R" : "r";
    }
}
