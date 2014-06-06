package chess.model.board;

import chess.model.pieces.ChessPiece;

/**
 * Created by Stephen on 6/6/2014.
 * In project: NewChess
 */
public class NumberCruncher {

    public static int pieceMaxRange(ChessPiece chessPiece) {

        switch (chessPiece.toString().toLowerCase()) {

            case "k":
                return 1;
            case "r":
            case "b":
            case "q":
                return 8;
            case "n":
                return 3;
//                    case "p":
//                        return 2;
            default:
                return 1;
        }

    }

}
