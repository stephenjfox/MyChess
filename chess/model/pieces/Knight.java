package chess.model.pieces;

/**
 * Created by Stephen on 5/9/2014.
 */
public class Knight extends ChessPiece {
    public Knight(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String toString() {
        return "N";
    }
}
