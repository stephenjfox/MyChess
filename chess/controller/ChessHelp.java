package chess.controller;

import chess.model.pieces.*;

/**
 * Created by Stephen on 5/14/2014.
 */
public class ChessHelp {

    public static ChessPiece getNewPiece(String rep, char color) {
        switch (rep) {
            case "Queen":
                return new Queen(color == 'l');
            case "King":
                return new King(color == 'l');
            case "Bishop":
                return new Bishop(color == 'l');
            case "Knight":
                return new Knight(color == 'l');
            case "Rook":
                return new Rook(color == 'l');
            default:
                return new Pawn(color == 'l');
        }
    }
}
