package chess.model.board;

import chess.model.pieces.ChessPiece;

/**
 * Created by Stephen on 5/28/2014.
 * In project: MyChess
 */
public class NullBoardLocation extends BoardLocation {

    public NullBoardLocation() {
        this("j0");

        this.placePiece(new ChessPiece(false) {

            @Override
            public boolean isValidMove(BoardLocation start,
                                       BoardLocation destination) {
                return false;
            }

        });
    }

    public NullBoardLocation(char lowercase, int y) {
        super(lowercase, y);
    }

    public NullBoardLocation(String location) {
        super(location);
    }

    @Override
    public void placePiece(ChessPiece piece) {
        super.placePiece(piece);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
