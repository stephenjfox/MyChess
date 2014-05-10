package chess.model.pieces;

/**
 * Created by Stephen on 5/9/2014.
 */
public class Bishop extends ChessPiece {

    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String toString() {
        return "B";
    }
}
