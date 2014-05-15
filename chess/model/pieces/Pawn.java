package chess.model.pieces;

/**
 * Created by Stephen on 5/9/2014.
 */
public class Pawn extends ChessPiece {
    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String toString() {
        return isWhite() ? "P" : "p";
    }
}
